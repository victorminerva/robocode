package Licoes;

import robocode.*;

public class AdvancedBearingBot extends AdvancedRobot {
	
	public void run() {
		setAdjustRadarForGunTurn(true);
		while (true) {
			// Liga o scanner até encontrar um robô inimigo;
			setTurnRadarRight(10000);
			execute(); // you must call this!!!
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		out.println("escaneando um robô de rolamento " + e.getBearing());
		out.println("meu título atual é " + getHeading());

			//Quando digitalizar um robô, vire na direção dele
			setTurnRight(e.getBearing());
			//Atirar nele
			setFire(3);
			//e bater nele
			setAhead(e.getDistance());

		/*Não tem que ligar para executar aqui, o motor Robocode faz isso por
		* você depois de lidar com um evento.
		**/
	}

	public void onHitRobot(HitRobotEvent e) {
		out.println("atingiu um robô no rolamento " + e.getBearing());
		out.println("meu título atual é " + getHeading());

		if (e.isMyFault()) {
			out.println("I hit " + e.getName());
		} else {
			out.println(e.getName() + " me bateu");
		}
	}
}
