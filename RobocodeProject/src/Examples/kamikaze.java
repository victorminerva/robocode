package Examples;

import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

public class kamikaze extends Robot
{
	/**
	 * run: Comportamiento general del robot.
	 *      Se ejecuta mientras no recibe un evento externo.
	 */
	public void run() {
		while(true) {
			turnRight(360); // Gira constantemente
		}
	}

	/**
	 * onScannedRobot: Que hacemos cuando detectamos un robot enemigo
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		stop();          // Para de girar (si no paramos de girar
		                 // y avanzamos, no avanzaremos en línea recta)
		ahead(10000);    // y avanza hacia adelante "todo lo que pueda".
	}

	/**
	 * onHitRobot: Que hacemos cuando chocamos con un robot enemigo
	 */
	public void onHitRobot(HitRobotEvent e){
		fire(3); // Dispara con la maxima fuerza [0-3]
		scan();  // Escanea para ver si el enemigo sigue delante.
				 // En caso afirmativo, se llama a onScannedRobot.
	}
}
