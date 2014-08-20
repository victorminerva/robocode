package Examples;

import robocode.*; 
import java.awt.Color; 
public class Kakaroto extends Robot { 
public void run() { 
setColors(Color.orange,Color.blue,Color.yellow); 
while(true) { 
double i = (double) (40+Math.random()*400); 
double j = (double) (40+Math.random()*400); 
double k = (double) (1+Math.random()*180); 
ahead(i); 
back(j); 
turnRight(k); 
turnGunRight(360); 
setAdjustGunForRobotTurn(true); 
} 
} 
public void onScannedRobot(ScannedRobotEvent e) { 
fire(5); 
} 
public void onHitByBullet(HitByBulletEvent e) { 
ahead(50); 
back(80); 
} 
public void onHitRobot(HitRobotEvent e) { 
back(30); 
} 
public void onHitWall(HitWallEvent e) { 
double x = getX(); 
double y = getY(); 
double m = getBattleFieldWidth(); 
double n = getBattleFieldHeight(); 
if((x==m)||(x==0)) { 
ahead(100); 
} 
if((y==n)||(y==0)) { 
back(40); 
} 
} }
