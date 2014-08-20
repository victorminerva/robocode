package ClassePrincipal;

import java.awt.Color;
import robocode.*;

public class RobotPtb extends Robot
{

    public RobotPtb()
    {
    }

    public void inicializar()
    {
        cores(Color.black, Color.black, Color.black, Color.black, Color.black);
    }

    public void executar()
    {
        do
        {
            avanca(300D);
            direita(90D);
            retorna(100D);
            esquerda(180D);
        } while(true);
    }

    public void mirouInimigo(ScannedRobotEvent evento)
    {
        fogo(1.0D);
    }

    public void recebeuTiro(HitByBulletEvent evento)
    {
        retorna(10D);
    }

    public void bateuParede(HitWallEvent evento)
    {
        retorna(20D);
        avanca(10D);
    }

    public void bateuInimigo(HitRobotEvent evento)
    {
        retorna(20D);
    }

    public void cores(Color tanque, Color canhao, Color radar, Color bala, Color scanner)
    {
        setColors(tanque, canhao, radar, bala, scanner);
    }

    public void avanca(double distancia)
    {
        ahead(distancia);
    }

    public void retorna(double distancia)
    {
        back(distancia);
    }

    public void direita(double graus)
    {
        turnRight(graus);
    }

    public void esquerda(double graus)
    {
        turnLeft(graus);
    }

    public void fogo(double potencia)
    {
        fire(potencia);
    }

    public void canhaoDireita(double graus)
    {
        turnGunRight(graus);
    }

    public void canhaoEsquerda(double graus)
    {
        turnGunLeft(graus);
    }

    public void radarDireita(double graus)
    {
        turnRadarRight(graus);
    }

    public void radarEsquerda(double graus)
    {
        turnRadarLeft(graus);
    }

    public void run()
    {
        inicializar();
        executar();
    }

    public void onScannedRobot(ScannedRobotEvent e)
    {
        mirouInimigo(e);
    }

    public void onHitByBullet(HitByBulletEvent e)
    {
        recebeuTiro(e);
    }

    public void onHitWall(HitWallEvent e)
    {
        bateuParede(e);
    }

    public void onHitRobot(HitRobotEvent e)
    {
        bateuInimigo(e);
    }
}

