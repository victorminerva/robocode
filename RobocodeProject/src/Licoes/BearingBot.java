package Licoes;

import java.awt.Color;

import robocode.Robot;
import robocode.ScannedRobotEvent;

public class BearingBot extends Robot {
	
	//Metodo que executa o Rob�;
	public void run() {
		//Definindo as cores do robo;
		setBodyColor(Color.yellow);
		setGunColor(Color.red);
		setRadarColor(Color.yellow);
		setBulletColor(Color.black);
		setScanColor(Color.white);
		
		setAdjustRadarForRobotTurn(true);
		while (true){
			turnRadarRight(10000);

		}
		
	}
	//Metodo Escaneia o campo � procura de inimigos;
	public void onScannedRobot(ScannedRobotEvent inimigo){
		//Quando digitalizar um rob�, vire na dire��o dele
		turnRight(inimigo.getBearing());
		//Atirar nele
		fire(1);
		//e bater nele
		ahead(inimigo.getDistance());
	}
	public void onWin(){
		
	}
}
