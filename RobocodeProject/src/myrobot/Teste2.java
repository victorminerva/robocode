package myrobot;

import java.awt.Color;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

public class Teste2 extends Robot {
	//Variaveis para gerar um numero para locomover o robo;
	double i = (double)(40+Math.random()*400);
	double j = (double)(40+Math.random()*400);
	int distancia = 1;
	//Metodo Principal
	public void run(){
		//Cor do Robo
		setBodyColor(Color.YELLOW);
		setGunColor(Color.YELLOW);
		setRadarColor(Color.YELLOW);
		setBulletColor(Color.black);
		setScanColor(Color.green);
		while (true){
			turnRadarRight(720);
			ahead(i);
			back(j);

		}
	}
	//Metodo do radar do Rob� encontra um adversario
	public void onScannedRobot(ScannedRobotEvent inimigo){
		//mira o canhao no adversario
		mirar(inimigo.getBearing());
		//atira no alvo
		atirar(inimigo.getDistance());
		scan();
		if(inimigo.getDistance() <= 10){
			ahead(distancia);
		}
	}
	//Metodo para quando o Rob� bater em outro Rob�
	public void onHitRobot(HitRobotEvent inimigo){
		scan();
		mirar(inimigo.getBearing());
		fire(inimigo.getBearing());
		back(i);
	}
	//Metodo para quando o Rob� colidir com a parede
	public void onHitWall(HitWallEvent inimigo){
		turnRight(90);
		ahead(i);
	}
	//Metodo para quando o Rob� for atingido por uma bala
	public void onHitByBullet(HitByBulletEvent inimigo){
		ahead(distancia);
		distancia *= -1;
		scan();
	}
	//Metodo para quando o Rob� ganhar
	public void onWin(WinEvent e){
		for(int i = 0; i < 50; i++){
			turnRight(20);
			turnLeft(20);
		}
	}
	
	/** Este PROCEDIMENTO melhora o tiro do meu rob�, e se caso o inimigo esteja longe e a energia do meu robo for
	 *  menor que 20, meu robo parar� de atirar para nao despedi�ar energia; */
	public void atirar(double distancia){
		/* Condi��o que verifica a distancia, com o objetivo de gasta menos energia se o robo
		 * estiver numa distancia maior qe 250 pixels para o adversario, ou se a energia do
		 * meu robo � menor qe 20;
		 */
		if (distancia > 250 || getEnergy() < 20){
			fire(1);
		}else if(distancia > 50 && distancia < 250){
			fire(2);
		}else{
			fire(3);
		}
	}
	/** Este PROCEDIMENTO melhora a mira do canh�o, torna-o mais rapido; */
	public void mirar(double inimigo){
		double a = getHeading() + inimigo - getGunHeading();
		/* Condi��o para verificar se o angulo n�o � maior qe -180 nem menor qe 180 graus;
		 * Essa condi��o serve para saber qual a menor volta pro canhao, pra ele nao perder tempo girando
		 * o canh�o at� alcan�ar o alvo;
		 */
		if(!(a > -180 && a <=180)){
			//se verdadeiro, enquanto o angulo for menor ou igual a -180, soma o valor do angulo com 360;
			while(a <= -180){
				a += 360;
			}
			//se verdadeiro, enquanto o angulo for maior qe 180, subtrai o valor do angulo com 360;
			while(a > 180){
				a -= 360;
			}
		}
		//Gira o canh�o para o angulo fornecido;
		turnGunRight(a);
	}
	
	
}