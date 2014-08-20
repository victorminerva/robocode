package myrobot;

import java.awt.Color;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

public class Teste extends Robot {
	//Metodo Principal
	public void run(){
		//Cor do Robo
		setBodyColor(Color.YELLOW);
		setGunColor(Color.YELLOW);
		setRadarColor(Color.YELLOW);
		setBulletColor(Color.black);
		setScanColor(Color.BLACK);
		
		while (true){
			if(pertoParede()){
				turnRight(90);
				back(100);
			}
			ahead(165);
			turnRight(90);
			ahead(-165);
			turnLeft(90);
			ahead(165);
		}
	}
	//Metodo do radar do Robô encontra um adversario
	public void onScannedRobot(ScannedRobotEvent inimigo){
		mirar(inimigo.getBearing());
		fogo(inimigo.getBearing());
		pertoParede();
		turnRight(90);
		ahead(145);
	}
	//Metodo para quando o Robô bater em outro Robô
	public void onHitRobot(HitRobotEvent inimigo){
		mirar(inimigo.getBearing());
		fogo(inimigo.getBearing());
	}
	//Metodo para quando o Robô colidir com a parede
	public void onHitWall(HitWallEvent inimigo){
		pertoParede();
		turnRight(90);
	}
	//Metodo para quando o Robô for atingido por uma bala
	public void onHitByBullet(HitByBulletEvent inimigo){
		
	}
	//Metodo para quando o Robô ganhar
	public void onWin(WinEvent e){
		for(int i = 0; i < 50; i++){
			turnRight(20);
			turnLeft(20);
		}
		
	}
	
	//FUNÇOES
	public void fogo(double distancia){
		if (distancia > 250 || getEnergy() < 15){
			fire(1);
		}else if(distancia > 50){
			fire(2);
		}else{
			fire(3);
		}
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
	public boolean pertoParede(){
		return (getX() < 50 || getX() > getBattleFieldWidth() - 50 ||
				getY() < 50 || getY() > getBattleFieldHeight() - 50);
	}
}
