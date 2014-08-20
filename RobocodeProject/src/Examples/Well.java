package Examples;

import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.HitByBulletEvent;
import robocode.*;
import java.awt.*;
public class Well extends Robot{
	
   public void run() {
	  //Definem as cores do rob�	
      setBodyColor(Color.black);
	  setGunColor(Color.black);
	  setRadarColor(Color.red);
	  setScanColor(Color.red);
	  //setBulletColor(Color.writh);
 
	  while(true){	
	     turnRadarRight(360);
	     ahead(100);
	     turnGunRight(360);
	     back(100);
       	 
	  }
   }
   //Detecta os outros rob�s
   public void onScannedRobot(ScannedRobotEvent e) {
      double max = 100;
	 
	
	  //Faz um controle da energia que � gasta no que diz 
	  //respeito � pot�ncia do tiro
      if(e.getEnergy() < max){
         max = e.getEnergy();
         miraCanhao(e.getBearing(), max, getEnergy());
      }else if(e.getEnergy() >= max){
         max = e.getEnergy();
         miraCanhao(e.getBearing(), max, getEnergy());
      }else if(getOthers() == 1){
         max = e.getEnergy();
         miraCanhao(e.getBearing(), max, getEnergy());
      }
    }
    //quando o seu robo colide com outro robo
    public void onHitRobot(HitRobotEvent e) {
	   tiroFatal(e.getBearing(), e.getEnergy(), getEnergy());	
	
    }
    //Quando seu robo� leva um tiro
    public void onHitByBullet(HitByBulletEvent e) { 
	   turnLeft(90);
	   back(100); 
    }
    //Fornece as coordenadas para o ajuste do canh�o.
    public void miraCanhao(double PosIni, double energiaIni, double minhaEnergia) {
       double Distancia = PosIni;
	   double Coordenadas = getHeading() + PosIni - getGunHeading();
	   double PontoQuarenta = (energiaIni / 4) + .1;
	   if (!(Coordenadas > -180 && Coordenadas <= 180)) {
	      while (Coordenadas <= -180) {
		     Coordenadas += 360;
		  }
		  while (Coordenadas > 180) {
		     Coordenadas -= 360;
		  }
	   }
	   turnGunRight(Coordenadas);
		
	   if (Distancia > 200 || minhaEnergia < 15 || energiaIni > minhaEnergia){
          fire(1);
       } else if (Distancia > 50 ) {
          fire(2);
       } else {
          fire(PontoQuarenta);
       }
   }
   //� chamado quando o rob� bate na parede,
   public void onHitWall(HitWallEvent e) {
      turnLeft(90);
      ahead(200);
   }
   //Dan�a da vit�ria
   public void onWin(WinEvent e) {	
      turnRight(72000);
   }
   public void tiroFatal(double PosIni, double energiaIni, double minhaEnergia) {
      double Distancia = PosIni;
	  double Coordenadas = getHeading() + PosIni - getGunHeading();
	  double PontoQuarenta = (energiaIni / 4) + .1;
	  if (!(Coordenadas > -180 && Coordenadas <= 180)) {
	     while (Coordenadas <= -180) {
	        Coordenadas += 360;
		 }
		 while (Coordenadas > 180) {
	        Coordenadas -= 360;
	     }
	  }
	  turnGunRight(Coordenadas);
	  fire(PontoQuarenta);
       
   }
			
}
