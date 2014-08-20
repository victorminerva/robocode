package myrobot;

import java.awt.Color;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

public class CamaroLucas extends Robot
{
   // Variaveis para gerar um numero para locomover o robo;
   double i = (double) (40 + Math.random() * 400);

   double j = (double) (40 + Math.random() * 400);

   // Metodo Principal
   public void run()
   {
      // Cor do Robo
      setBodyColor(Color.YELLOW);
      setGunColor(Color.YELLOW);
      setRadarColor(Color.YELLOW);
      setBulletColor(Color.black);
      setScanColor(Color.green);
      
      while (true)
      {
         turnRadarRight(360);
      }
   }

   // Metodo do radar do Robô encontra um adversario
   public void onScannedRobot(ScannedRobotEvent inimigo)
   {
      mirar(inimigo.getBearing()); // mira o canhao no adversario
      atirar(inimigo.getDistance()); // atira no alvo
   }

   // Metodo para quando o Robô bater em outro Robô
   public void onHitRobot(HitRobotEvent inimigo)
   {
      mirar(inimigo.getBearing());
      fire(inimigo.getBearing());
      back(i);
      if(inimigo.getEnergy() > 50)
      {
         fire(inimigo.getBearing());
         mirar(inimigo.getBearing());
      }
      ahead(j);
   }

   // Metodo para quando o Robô colidir com a parede
   public void onHitWall(HitWallEvent inimigo)
   {
      turnRight(140);
      ahead(i);
   }

   // Metodo para quando o Robô for atingido por uma bala
   public void onHitByBullet(HitByBulletEvent inimigo)
   {
      back(j);
      mirar(inimigo.getBearing());
      fire(inimigo.getBearing());
   }

   // Metodo para quando o Robô ganhar
   public void onWin(WinEvent e)
   {
      for (int i = 0; i < 50; i++)
      {
         turnRight(20);
         turnLeft(20);
      }
   }

   /**
    * Este PROCEDIMENTO melhora o tiro do meu robô, e se caso o inimigo esteja
    * longe e a energia do meu robo for menor que 20, meu robo parará de atirar
    * para nao despediçar energia;
    */
   public void atirar(double distancia)
   {
      /*
       * Condição que verifica a distancia, com o objetivo de gasta menos
       * energia se o robo estiver numa distancia maior qe 250 pixels para o
       * adversario, ou se a energia do meu robo é menor qe 20;
       */
      if (distancia > 250 || getEnergy() < 20)
      {
         fire(1);
         ahead(i/20);
      }
      else if (distancia > 50 && distancia < 250)
      {
         fire(2);
         ahead(i/20);
      }
      else
      {
         fire(3);
         ahead(i/20);
      }
   }

   /** Este PROCEDIMENTO melhora a mira do canhão, torna-o mais rapido; */
   public void mirar(double inimigo)
   {
      double a = getHeading() + inimigo - getGunHeading();
      /*
       * Condição para verificar se o angulo não é maior qe -180 nem menor qe
       * 180 graus; Essa condição serve para saber qual a menor volta pro
       * canhao, pra ele nao perder tempo girando o canhão até alcançar o alvo;
       */
      if (!(a > -180 && a <= 180))
      {
         // se verdadeiro, enquanto o angulo for menor ou igual a -180, soma o
         // valor do angulo com 360;
         while (a <= -180)
         {
            a += 360;
         }
         // se verdadeiro, enquanto o angulo for maior qe 180, subtrai o valor
         // do angulo com 360;
         while (a > 180)
         {
            a -= 360;
         }
      }
      // Gira o canhão para o angulo fornecido;
      turnGunRight(a);
   }

}