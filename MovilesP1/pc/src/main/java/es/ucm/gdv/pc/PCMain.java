package es.ucm.gdv.pc;


import javax.swing.JFrame;
import es.ucm.gdv.engine.pc.PCGame;

public class PCMain {

    public static void main (String[] args)
    {
        PCGame pcGame = new PCGame(800,800,JFrame.EXIT_ON_CLOSE,null);

        pcGame.run();
    }
}
