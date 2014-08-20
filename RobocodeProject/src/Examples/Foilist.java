package Examples;

import robocode.*;
import robocode.util.Utils;
import java.lang.Math;

public class Foilist extends AdvancedRobot
{
	//Global variables.
	static double direction;
	static double deathCount;
		
	//En garde!
	public void run() 
	{
		//Start spinning radar and initialize direction to infinity.
		setTurnRadarRightRadians(direction = Double.POSITIVE_INFINITY);
	}	

	public void onScannedRobot(ScannedRobotEvent e) 
	{		
		//Local variables.
		int integer = 30;
        double absoluteBearing;
        int matchPosition;
		
		//Start with one-way orbit movement, after two total deaths, switch to time-based oscillating movement,
		//after six total deaths, switch to random movement.
		setAhead(direction *= (.92 - (deathCount > 1.25 ? Math.random() : (Math.round(deathCount) - (getTime() % 13)))));
		
		//Pattern Matching.		
		//Record the current enemy lateral velocity.
		enemyHistory = String.valueOf((char) (e.getVelocity() * (Math.sin(e.getHeadingRadians() - (absoluteBearing = e.getBearingRadians() + getHeadingRadians()))))).concat(enemyHistory);
		         
        //Find the longest match allowable in the enemy history.
        while((matchPosition = enemyHistory.indexOf(enemyHistory.substring(0, integer--), 64)) < 0);
		
		//Stay mostly perpendicular to the enemy, but try to maintain a distance of 250 pixels.
		setTurnRightRadians(Math.cos(e.getBearingRadians()) - ((integer = (int)e.getDistance()) - 250) * getVelocity() / 3200);
		
        do
		{ 
           absoluteBearing += ((short) enemyHistory.charAt(--matchPosition)) /  e.getDistance();
        }
		while ((integer -= 13) > 0);
		
		//Aim at the predicted target.
		setTurnGunRightRadians(Utils.normalRelativeAngle(absoluteBearing - getGunHeadingRadians()));
		
		//Fire!
        //Bullet power must be a multiple of 0.33333... or else the gun will be inaccurate.
        setFire(2.333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333);
		
		//Infinite radar lock.
		setTurnRadarLeftRadians(getRadarTurnRemainingRadians());	
	}
	
	public void onDeath (DeathEvent e)
	{
		//Increase the deathCount variable.
		deathCount += .25;
	}

	public void onHitWall(HitWallEvent e) 
	{
		//Reverse direction when the bot hits a wall.
		direction = -direction;
	}
	
	//Symbolic log of enemy movements for pattern matcher.
	//Preloaded to prevent StringIndexOutOfBoundsException.
	static String enemyHistory = ""
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 1
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 2
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char)-1
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char) 0 + (char) 0 + (char) 0
		+ (char) 0 + (char)-2 + (char)-4 + (char)-6
		+ (char)-8 + (char)-8 + (char)-8 + (char)-8
		+ (char)-8 + (char)-8 + (char)-8 + (char)-8
		+ (char)-8 + (char)-8 + (char)-8 + (char)-8
		+ (char)-8 + (char)-8 + (char)-8 + (char)-8
		+ (char)-8 + (char)-8 + (char)-8 + (char)-8
		+ (char)-8 + (char)-8 + (char)-8 + (char)-8
		+ (char)-8 + (char)-8 + (char)-8 + (char)-8
		+ (char)-8 + (char)-8 + (char)-8 + (char)-8
		+ (char)-8 + (char)-8 + (char)-8 + (char)-8
		+ (char)-8 + (char)-8 + (char)-8 + (char)-8
		+ (char)-7 + (char)-6 + (char)-5 + (char)-4
		+ (char)-3 + (char)-2 + (char)-1 + (char)0
		+ (char) 2 + (char) 4 + (char) 6 + (char) 8
		+ (char) 8 + (char) 8 + (char) 8 + (char) 8
		+ (char) 8 + (char) 8 + (char) 8 + (char) 8
		+ (char) 8 + (char) 8 + (char) 8 + (char) 8
		+ (char) 8 + (char) 8 + (char) 8 + (char) 8
		+ (char) 8 + (char) 8 + (char) 8 + (char) 8
		+ (char) 8 + (char) 8 + (char) 8 + (char) 8
		+ (char) 8 + (char) 8 + (char) 8 + (char) 8
		+ (char) 8 + (char) 8 + (char) 8 + (char) 8
		+ (char) 8 + (char) 8 + (char) 8 + (char) 8
		+ (char) 8 + (char) 8 + (char) 8 + (char) 8
		+ (char) 7 + (char) 6 + (char) 5 + (char) 4
		+ (char) 3 + (char) 2 + (char) 1 + (char) 0;
}								
