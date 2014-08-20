package myrobot;

import robocode.Robot;
import robocode.ScannedRobotEvent;

public class Teste3 extends Robot{
	double x = (double)(40+Math.random()*400);
	
	double y = (double)(40+Math.random()*400);
	
	public void run(){
		while(true){
			turnRadarRight(360);
			if(pertoParede() == true){
				back(100);
			}else{
				ahead(100);
			}

		}
	}
	public void onScannedRobot(ScannedRobotEvent e){
		mirar(e.getBearing());
		atirar(e.getBearing());
		turnRight(e.getBearing() + 90); //Deixa o robo perpendicular ao inimigo.
	
	}
	
	
	
	
	
	
	
	
	
	
	
	public boolean pertoParede(){
		return (getX() < 50 || getX() > getBattleFieldWidth() - 50 ||
				getY() < 50 || getY() > getBattleFieldHeight() - 50);
	}
	public void mirar(double inimigo){
		double a = getHeading() + inimigo - getGunHeading();
		if(!(a > -180 && a <=180)){
			while(a <= -180){
				a += 360;
			}
			while(a > 180){
				a -= 360;
			}
		}
		turnGunRight(a);
	}
	public void atirar(double distancia){
		if (distancia > 250 || getEnergy() < 15){
			fire(1);
		}else if(distancia > 50){
			fire(2);
		}else{
			fire(3);
		}
	}
}
