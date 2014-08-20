package Examples;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;


public class DustBunny extends AdvancedRobot {

	static final double GUN_FACTOR = 50;
	static double xForce;
	static double yForce;
	static String lastTarget;
	static double lastDistance;
	public void run() 
	{
		// do this to hit stationary targets
		setAdjustGunForRobotTurn(true);
		// Reset range finder
		lastDistance = Double.POSITIVE_INFINITY;
		// Do infinite radar.  Saves much space (thanks Dr. Loco!)
		turnRadarRightRadians(Double.POSITIVE_INFINITY);
	}
	public void onScannedRobot(ScannedRobotEvent e) 
	{
		// Get Abs bearing for aiming routines (and A-Grav)
		// and distance for just about everything else :)
		double	absoluteBearing = e.getBearingRadians() + getHeadingRadians();
		double  distance = e.getDistance();
		// Use a very simple running average system.  /2 is as cheap as I can get this
		xForce = xForce *.9 - Math.sin(absoluteBearing) / distance;
		yForce = yForce *.9 - Math.cos(absoluteBearing) / distance;
		// Get our turn angle - factor in distance from each wall every time so we get
		// pushed towards the center when close to the walls.  This took a long time to come up with.
		setTurnRightRadians(Utils.normalRelativeAngle(Math.atan2(xForce + 1/getX() - 1/(getBattleFieldWidth() - getX()), 
					   yForce + 1/getY() - 1/(getBattleFieldHeight() - getY()))
						- getHeadingRadians()) );
		// Move ahead!  
		setAhead(Double.POSITIVE_INFINITY);
		// If we need to turn hard, slow down
		setMaxVelocity( 420 / getTurnRemaining() );
		// If we're at 0 and pointed at a target, fire.
		if(getGunHeat() == 0)
		{
			//  Use some energy smarts and reset distance targeting
			setFireBullet(getEnergy() * GUN_FACTOR / distance);
			lastDistance = Double.POSITIVE_INFINITY;
		}
		// Lock onto closest bots																
		if(lastDistance > distance)
		{
			lastDistance = distance;
			lastTarget = e.getName();
		}
		// and only the closest bot
		if(lastTarget == e.getName())
		{
			// Radar lock as we approach shooting time
			if(getGunHeat() < 1)
			{
				// Let this var be equal the the absolute bearing now...
				// and set the radar.
				setTurnRadarLeft(getRadarTurnRemaining());
			}
			// Aim directly at target.
			setTurnGunRightRadians(Utils.normalRelativeAngle(absoluteBearing - getGunHeadingRadians()));
		}
	}
}