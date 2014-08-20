package Examples;

import robocode.*;
import robocode.util.Utils;
import java.awt.geom.*;

public class EpeeistMicro extends AdvancedRobot
{
	//Constants.
	static final int    GUESS_FACTORS = 25;
	static final int    MIDDLE_FACTOR = (GUESS_FACTORS - 1) / 2;
	static final double MAXIMUM_ESCAPE_ANGLE = 0.72727272727272727272727272727273; //8 / 11
	static final double FACTOR_ANGLE = MAXIMUM_ESCAPE_ANGLE / MIDDLE_FACTOR;
	
	//Global variables.
	static double averageLateralVelocity;
	static double direction = 1;
	static double enemyBulletSpeed;
	static double enemyEnergy;
	static double enemyHeading;
	static double enemyVelocity;
	static double hits;
	static int    absoluteEnemyLateralVelocity;
	static int    movementMode;
	static int    timeSinceVelocityChange;
	
	//Array to store the number of times the enemy has visited each guess factor.
	//Segmented on acceleration, time since velocity change, absolute enemy lateral velocity, near wall, and distance.
	static int[][][][][][] guessFactors = new int[3][4][5][2][5][GUESS_FACTORS];
	
	//En garde!
	public void run()
	{
		//Set the radar and gun to turn independently.
		setAdjustRadarForGunTurn(true);
		setAdjustGunForRobotTurn(true);
	}
	
	public void onStatus(StatusEvent e)
	{
		//Turn the radar every tick.
		//Putting the code here instead of in the run() method saves one byte.
		//I believe Wompi discovered this.
    	setTurnRadarRightRadians(1);
	}
	
	public void onScannedRobot(ScannedRobotEvent e)
	{
		//Local variables.	
		//Declare most used integer before most used double to save several bytes.
		int    antiRam;
		double enemyDistance;
		double absoluteBearing;
		double enemyDirection;
		double lateralVelocity;
		double offset;
		double theta;
		int    acceleration;
		
		//Fire a wave.
		Wave wave;
		addCustomEvent(wave = new Wave());
		
		/*********************************************
		 *---------------MOVEMENT CODE---------------*
		 *********************************************/
				
		//Retreat very heavily when the enemy is ramming.
		offset = 2 + (antiRam = (int)(100 / (wave.enemyDistance = enemyDistance = e.getDistance())));
		
		//Wall smoothing based on Toorkild's.
		//Subtract current coordinates from lower wall bounds instead of adding 
		//current coordinates to every fieldRectangle.contains() projection to save six bytes.  Thanks go to Skilgannon.
		Rectangle2D.Double fieldRectangle;
		while(!(fieldRectangle = new Rectangle2D.Double(18 - getX(), 18 - getY(), 764, 564)).
			contains(160 * Math.sin(theta = (wave.absoluteBearing = absoluteBearing = 
			(e.getBearingRadians() + getHeadingRadians())) + direction * (offset -= .02)), 160 * Math.cos(theta)));
		setTurnRightRadians(Math.tan(theta -= getHeadingRadians()));
			
		//Stop and Go movement based on Thorn's.
		//Move when the enemy fires, or when the robot is moving randomly, or when the enemy is ramming.
		double energyDelta;
		if ((energyDelta = (enemyEnergy - (enemyEnergy = e.getEnergy()))) > movementMode - antiRam)
		{			
			//Calculate the length of Stop and Go movement based on enemy bullet power.
			setAhead(((3 + (int)(energyDelta / 0.5000001)) << 3) * Math.signum(Math.cos(theta)));
		}
		
		//Random movement from Toorkild.
		//Don't move randomly if the enemy is ramming, or if the bot is in Stop and Go mode.
		//Reverse direction if the bot gets too close to a wall.
		if (Math.random() + antiRam < (-0.6 * Math.sqrt(enemyBulletSpeed / enemyDistance) + 0.04) * movementMode || offset < Math.PI/3.5)
		{
			direction = -direction;
		}
		
		/********************************************
		 *--------------TARGETING CODE--------------*
		 ********************************************/
		
		//Determine the enemy's lateral velocity and movement direction.
		//Use a simple rolling average to store the previous lateral direction
		//if enemy lateral velocity == 0.  Inspired by LittleBlackBook.
		wave.enemyDirection = enemyDirection = (averageLateralVelocity = ((averageLateralVelocity * .01) +
			(lateralVelocity = ((enemyVelocity = e.getVelocity()) * Math.sin((enemyHeading = e.getHeadingRadians()) - absoluteBearing))))) < 0 ? -FACTOR_ANGLE : FACTOR_ANGLE;
		
		//Determine if the enemy is accelerating or decelerating.
		if ((acceleration = (int)Math.signum(absoluteEnemyLateralVelocity - (absoluteEnemyLateralVelocity = (int)Math.abs(lateralVelocity)))) != 0)
		{
			timeSinceVelocityChange = 0;
		}
					
		//Determine the current situation.
		//Declaring a local array saves two bytes.
		double angle;
		int[] guessFactorsLocal = wave.guessFactors = guessFactors
			[1 + acceleration] //Acceleration.
			[Math.min(3, (int)(Math.pow(280 * timeSinceVelocityChange++ / enemyDistance, .7)))] //Ticks since velocity change.
			[absoluteEnemyLateralVelocity / 2] //Absolute enemy lateral velocity.
			[(int)Math.signum(fieldRectangle.outcode(Math.sin(angle = (absoluteBearing +
				enemyDirection * MIDDLE_FACTOR)) * enemyDistance, Math.cos(angle) * enemyDistance))] //Near wall.  Many thanks go to Skilgannon for the outcode trick.
			[(int)enemyDistance / 200]; //Distance.

		//Find the most visited guess factor for the current situation.
		//Looping like this is ugly, but it saves two bytes over the proper way.
		int mostVisited = MIDDLE_FACTOR;
		int i = 0;
		try
		{
			while (true)
			{
				if (guessFactorsLocal[i] > guessFactorsLocal[mostVisited])
				{
					mostVisited = i;
				}
				i++;
			}			
		}
		catch(Exception ex)
		{
		}
		
		//Turn the gun to the most visited guess factor.
		//The slight offset helps to defeat simple bullet shielding.
		setTurnGunRightRadians(0.0005 + Utils.normalRelativeAngle(absoluteBearing - getGunHeadingRadians()
			+ (enemyDirection * (mostVisited - MIDDLE_FACTOR))));
		
		//Fire medium power bullets most of the time, but use full power at very close range.
		//If the enemy is weak, fire the minimum power needed to destroy them.
		setFire(Math.min(2 + antiRam, enemyEnergy / 4));
		
		//Narrow lock radar.
		setTurnRadarRightRadians(2 * Utils.normalRelativeAngle(absoluteBearing - getRadarHeadingRadians()));
	}
	
	public void onBulletHit(BulletHitEvent e)
	{
		//Adjust enemy energy variable when the bot hits the enemy.
		//This makes a big difference against linear targeting.
		enemyEnergy -= 10;
	}
	
	public void onHitByBullet(HitByBulletEvent e)
	{
		//Adjust enemy energy variable when the bot gets hit.
		//Store the velocity of the enemy bullet for the random movement.
		enemyEnergy += 20 - (enemyBulletSpeed = e.getVelocity());	
		
		//If the bot takes a lot of damage on average in Stop and Go mode, switch to Random Movement.
		//Thanks go to Skilgannon.
		if ((hits += (4.25 / enemyBulletSpeed)) > getRoundNum() + 2)
		{
			movementMode = -1;
		}
    }	
	
	static class Wave extends Condition
	{
		//Global variables.
		double absoluteBearing;
		double bearingOffset;
		double enemyDirection;
		double enemyDistance;		
		double waveDistanceTraveled;
		int[]  guessFactors;
		
		public boolean test()
		{
			bearingOffset += (enemyVelocity * Math.sin(enemyHeading - absoluteBearing)) / (enemyDistance += (enemyVelocity * Math.cos(enemyHeading - absoluteBearing)));
			
			//Check if the wave has passed the enemy's current location.
			if (Math.abs((waveDistanceTraveled += 14) - enemyDistance) <= 7)
			{
				//Calculate the guess factor that would have hit the enemy.
				//Increment the bin that represents that guess factor.
				guessFactors[(int)Math.round((bearingOffset / enemyDirection) + MIDDLE_FACTOR)]++;
			}
			return false;
		}
	}
}	
