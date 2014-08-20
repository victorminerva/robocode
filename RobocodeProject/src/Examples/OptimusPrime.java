package Examples;

import robocode.HitByBulletEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import java.awt.Color;

public class OptimusPrime extends Robot {
	
	public void run(){
		setBodyColor(Color.yellow);
		setGunColor(Color.yellow);
		setRadarColor(Color.yellow);
		setBulletColor(Color.black);
		setScanColor(Color.green);
		
		while(true){
			ahead(150);
			turnRight(90);
			back(150);
			turnLeft(180);	
		}
	}
	public void onScannedRobot(ScannedRobotEvent e){
		fire(1);
	}
	public void onHitByBullet(HitByBulletEvent e){
		back(10);
		fire(1);
	}
	public void onHitWall(HitWallEvent e){
		back(20);
		fire(1);
		fire(1);
		fire(15);
	}
	
}
