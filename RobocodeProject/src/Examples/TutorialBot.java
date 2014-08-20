package Examples;
import robocode.*;
import robocode.util.*;
import java.awt.Color;
 
public class TutorialBot extends AdvancedRobot{
 
    int shots = 0;
    int a = 0;
    public void run() {
        //definir algumas cores
        setBodyColor(new Color(200, 200, 30));
        setGunColor(new Color(45, 100, 0));
        setRadarColor(new Color(0, 255, 100));
        setBulletColor(new Color(255, 255, 100));
        setScanColor(new Color(0, 255, 0));
 
        while (true) {
            turnGunLeft(10);
        }
    }
 
    public void onScannedRobot(ScannedRobotEvent e) {
        if (a % 25 == 0 ) {
           turnGunLeft(180);
        } else {
            double gunTurnAmt = normalRelativeAngle(e.getBearing() + (getHeading() - getRadarHeading()));
            setTurnGunRight(gunTurnAmt);
            a++;
            //System.out.print("n==========================");
            //System.out.print("nradar heading:");
            //System.out.print(getRadarHeading());
            //System.out.print("nenemy angle:");
            //System.out.print(e.getBearing());
            //System.out.print("nremain angle:");
            //System.out.print(getRadarHeading()-e.getBearing());
        }
    }
 
    public double normalRelativeAngle(double angle) {
        if (angle > -180 && angle <= 180) {
            return angle;
        }
 
        double fixedAngle = angle;
        while (fixedAngle <= -180) {
            fixedAngle += 360;
        }
 
        while (fixedAngle > 180) {
            fixedAngle -= 360;
        }
 
        return fixedAngle;
    }
} 