package myrobot;
/** @author VictorMinerva e LucasHolanda  */
import java.awt.Color;
  
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;
import ClassePrincipal.RobotPtb;

public class CamaroCampeao extends RobotPtb {
		//Variaveis para gerar um numero aleatorio para locomover o robo;
		double x = (double)(40+Math.random()*400);
		
		double y = (double)(40+Math.random()*400);
		

		
		//Metodo Principal
		public void executar(){
			
			// Cores do Robo
			cores(Color.YELLOW,Color.BLACK,Color.BLACK,Color.BLACK,Color.WHITE);
	      
	      while(true){
			radarDireita(360);
			//Condi��o qe verifica se o robo est� proximo a parede;
			if(pertoParede()==true){ //Se sim, ele retorna 100 pixels;
				retorna(100);
			}else{
				avanca(x); //sen�o, ele simplesmente avan�a um valor x no campo;
			}
	      }
		}
		//Metodo do radar do Rob� encontra um adversario
		public void mirouInimigo(ScannedRobotEvent inimigo){
			mirar(inimigo.getBearing()); // mira o canhao no adversario
			atirar(inimigo.getBearing()); // atira no alvo
			direita(inimigo.getBearing() + 90); //Deixa o robo perpendicular ao inimigo.
			
			//Condi��o para dar um tiro fatal quando a energia do adversario for menor qe 12;
			//mais explica��es sobre isso no metodo tiroFatal();
			if(inimigo.getEnergy() < 12){
				tiroFatal(inimigo.getEnergy());
			}else{
				fogo(2);
			}
		}
		//Metodo para quando o Rob� bater em outro Rob�
		public void bateuInimigo(HitRobotEvent inimigo){
			mirar(inimigo.getBearing());
			atirar(inimigo.getBearing());
		}

		//Metodo para quando o Rob� for atingido por uma bala
		public void recebeuTiro(HitByBulletEvent inimigo){
			avanca(100);
			if(pertoParede() == true){
				retorna(100);
			}else{
				avanca(50);
			}
		}
		public void onWin(WinEvent e){
			for(int i = 0; i < 50; i++){
				direita(20);
				esquerda(20);
			}
		}
		/** Este M�TODO verifica se o robo est� proximo a parede, acima h� uma condi��o pra isso; 
		 *  Sua utiliza��o � para evitar qe o rob� venha colidir com a parede; */
		public boolean pertoParede(){
			return (getX() < 50 || getX() > getBattleFieldWidth() - 50 ||
					getY() < 50 || getY() > getBattleFieldHeight() - 50);
		}
		/** Este M�TODO melhora a mira do canh�o, torna-o mais rapido; */
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
		/** Este M�TODO melhora o tiro do meu rob�, e se caso o inimigo esteja longe e a energia do meu robo for
		 *  menor que 20, meu robo parar� de atirar para nao despedi�ar energia; */
		public void atirar(double distancia){
			/* Condi��o que verifica a distancia, com o objetivo de gasta menos energia se o robo
			 * estiver numa distancia maior qe 250 pixels para o adversario, ou se a energia do
			 * meu robo � menor qe 20;
			 */
			if (distancia > 250 || getEnergy() < 20){
				fogo(1);
			}else if(distancia > 50){
				fogo(2);
			}else{
				fogo(3);
			}
		}
		/** Este M�TODO da um tiro fatal no adversario de acordo com a condi��o visto acima;
		 *  Nele o robo n�o perde energia ao atirar, pois ao matar o robo ele ganha energia; */
		public void tiroFatal(double Energia){
			double tiro = (Energia / 4) + .1;
			fogo(tiro);
		}

}
