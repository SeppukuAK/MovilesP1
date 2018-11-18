package es.ucm.gdv.bombitaspc;

import javax.swing.JFrame;

import es.ucm.gdv.bombitas.GameManager;
import es.ucm.gdv.engine.pc.PCGame;

/**
 * Punto de entrada para el PC
 */
public class BombitasPC {

    public static void main (String[] args)
    {
        PCGame pcGame = new PCGame(800,800,JFrame.EXIT_ON_CLOSE,"Bombitas");
        new GameManager(pcGame);

        pcGame.run();
    }
}
