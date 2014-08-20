package Licoes;

import robocode.*;

public class AdvancedBearingBot extends AdvancedRobot {
	
	public void run() {
		setAdjustRadarForGunTurn(true);
		while (true) {
			// Liga o scanner at� encontrar um rob� inimigo;
			setTurnRadarRight(10000);
			execute(); // you must call this!!!
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		out.println("escaneando um rob� de rolamento " + e.getBearing());
		out.println("meu t�tulo atual � " + getHeading());

			//Quando digitalizar um rob�, vire na dire��o dele
			setTurnRight(e.getBearing());
			//Atirar nele
			setFire(3);
			//e bater nele
			setAhead(e.getDistance());

		/*N�o tem que ligar para executar aqui, o motor Robocode faz isso por
		* voc� depois de lidar com um evento.
		**/
	}

	public void onHitRobot(HitRobotEvent e) {
		out.println("atingiu um rob� no rolamento " + e.getBearing());
		out.println("meu t�tulo atual � " + getHeading());

		if (e.isMyFault()) {
			out.println("I hit " + e.getName());
		} else {
			out.println(e.getName() + " me bateu");
		}
	}
}
