package myrobot;


import java.awt.Color;

import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

public class Camaro2 extends Robot{
	//Variaveis para gerar um numero aleatorio para locomover o robo;
	double x = (double)(40+Math.random()*400);
	
	double y = (double)(40+Math.random()*400);
	
	double distancia = 50;
	
	//Metodo Principal
	public void run(){
		
	  // Cores do Robo
      setBodyColor(Color.YELLOW);
      setGunColor(Color.BLACK);
      setRadarColor(Color.BLACK);
      setBulletColor(Color.BLACK);
      setScanColor(Color.WHITE);
      while(true){
    	  turnRadarRight(360);
      }
	}
	public void onScannedRobot(ScannedRobotEvent inimigo){
		
	}
	public void onWin(WinEvent e){
		for(int i = 0; i < 50; i++){
			turnRight(20);
			turnLeft(20);
		}
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
		if (distancia > 250 || getEnergy() < 20){
			fire(1);
		}else if(distancia > 50){
			fire(2);
		}else{
			fire(3);
		}
	}
	public void tiroFatal(double Energia){
		double tiro = (Energia / 4) + .1;
		fire(tiro);
	}
}