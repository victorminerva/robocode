package myrobot;

import java.awt.Color;

import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

public class Camaro extends Robot{
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
		if(pertoParede()==true){
			back(100);
		}else{
			ahead(x);
		}
      }
	}
	//Metodo do radar do Robô encontra um adversario
	public void onScannedRobot(ScannedRobotEvent inimigo){
		mirar(inimigo.getBearing()); // mira o canhao no adversario
		atirar(inimigo.getBearing()); // atira no alvo
		turnRight(inimigo.getBearing() + 90); //Deixa o robo perpendicular ao inimigo.
	
		if(inimigo.getEnergy() < 12){
			tiroFatal(inimigo.getEnergy());
		}else{
			fire(2);
		}
	}
	//Metodo para quando o Robô bater em outro Robô
	public void onHitRobot(HitRobotEvent inimigo){
		mirar(inimigo.getBearing());
		atirar(inimigo.getBearing());
	}
	//Metodo para quando o Robô colidir com a parede
	public void onHitWall(HitWallEvent inimigo){
			
	}
	//Metodo para quando o Robô for atingido por uma bala
	public void onHitByBullet(HitByBulletEvent inimigo){
		ahead(100);
		if(pertoParede() == true){
			back(100);
		}else{
			ahead(50);
		}
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
