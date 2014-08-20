package Examples;

import robocode.*;
import java.awt.Color;

public class Default extends Robot
{
    public void run() {
        setColors(Color.black,Color.yellow,Color.yellow); // Cuerpo, cañón y radar.
        while(true){
            turnGunRight(90);
        }
    }
 
    public void onScannedRobot(ScannedRobotEvent e) {
        stop();
        setAdjustGunForRobotTurn(true); // Desacoplamos el cuerpo y el resto del robot
        if(e.getDistance() < 150 || e.getVelocity() == 0) // Si el enemigo está cerca
            fire(3); // o no se mueve, disparamos con la potencia máxima.
        else
            fire(1); // En caso contrario disparamos con potencia 1.
        turnRight((getGunHeading()-getHeading())/2); // Dirección del cañón - Dirección del robot
        ahead(100);
    }
 
    public void onHitByBullet(HitByBulletEvent e) {
        // Si alguien nos acierta un disparo retrocedemos 10 píxeles.
        back(10);
    }
 
    public void onHitWall(HitWallEvent e) {
        // Si chocamos con un muro retrocedemos 20 píxeles.
        back(20);
    }
}
