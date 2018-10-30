package es.ucm.gdv.engine.pc;

import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import es.ucm.gdv.engine.Game;
import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Logic;

public class PCGame implements Game {

    private PCGraphics _pcGraphics;
    private Logic _logic;

    public PCGame(int x, int y, int closeOperation , Logic logic)
    {
        _logic = logic;
        //Creación de ventana - Crea un nuevo hilo
        JFrame ventana = new JFrame("Paint");

        //Inicialización ventana JFrame
        ventana.setSize(x,y);
        ventana.setDefaultCloseOperation(closeOperation);	//Le decimos que cuando se de a la 'x' se cierre la aplicacion

        _pcGraphics = new PCGraphics(ventana);
    }

    public void run()
    {
        BufferStrategy strategy = _pcGraphics.getJFrame().getBufferStrategy();	//Objeto con el que pintamos

        long lastFrameTime = System.nanoTime();		//Cuanto tiempo ha pasado desde el momento en que se ha lanzado medido en nanosegundos

        //Bucle principal de juego
        while(true)
        {
            long currentTime = 	System.nanoTime();	//Hora actual
            double elapsedTime = (double) (currentTime-lastFrameTime) / 1E09;
            lastFrameTime = currentTime;

            //TODO: Input

            //TODO: Lógica
            _logic.update(elapsedTime);

            //Renderizado
            do
            {
                do
                {
                    java.awt.Graphics graphics = strategy.getDrawGraphics();
                    try{
                        //TODO: Render
                        _pcGraphics.startFrame(graphics);
                        _logic.render();
                    }
                    finally	//Finally -> Haga lo que haga, pasa.
                    {
                        graphics.dispose();
                    }

                } while (strategy.contentsRestored());

                strategy.show();	//Da la vuelta al buffer

            } while (strategy.contentsLost());

        }
    }

    @Override
    public Graphics getGraphics() {
        return _pcGraphics;
    }

}