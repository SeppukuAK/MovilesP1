package es.ucm.gdv.android.engine;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.StateGame;

public class AndroidGame extends StateGame implements Runnable {

    private AndroidGraphics _androidGraphics;
    private SurfaceView _surfaceView;

    //Volatile: CADA VEZ QUE TENGAS QUE VER EL VALOR, VE A MEMORIA, NO MIRES EN CACHE
    volatile boolean _running = false;  //Variable compartida por las 2 hebras
    Thread _runningThread;

    public AndroidGame(AppCompatActivity context)
    {
        _surfaceView = new SurfaceView(context);
        context.setContentView(_surfaceView);

        AssetManager assetManager = context.getAssets();

        _androidGraphics = new AndroidGraphics(_surfaceView,assetManager);
    }

    /*
       Es llamado cuando la aplicación pasa a primer plano.
       Reinicia el bucle de juego.
    */
    public void resume() {
        if (!_running) {
            _running = true;

            //Crea la hebra
            _runningThread = new Thread(this);
            _runningThread.start();    //LLAMA A RUN
        }
    }

    /*
      Es llamado cuando la aplicación pasa a segundo plano.
      Pausa el bucle de juego
    */
    public void pause() {
        _running = false;
        //Se espera a que acabe el tick y pausa
        while (true) {
            try {
                _runningThread.join();
                break;
            } catch (InterruptedException ie) {

            }
        }

    }


    /*
         Se ejecuta en otro hilo.
         Tiene el bucle de juego
    */
    @Override
    public void run() {
        long _lastFrameTime = System.nanoTime();
        SurfaceHolder sh = _surfaceView.getHolder();     //Referencia a la ventana

        while (_running) {
            //Calculo de elapsedTime
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - _lastFrameTime;
            _lastFrameTime = currentTime;
            double elapsedTime = (double) nanoElapsedTime / 1.0e9;

            //LOGIC
            _gameState.update(elapsedTime);

            //RENDER
            while (!sh.getSurface().isValid()) ; //Esperamos a poder coger el viewport

            Canvas canvas = _surfaceView.getHolder().lockCanvas();   //Obtenemos el canvas y lo bloqueamos
            //TODO: Supuestamente hay que hacer un try catch en algún lado
            _androidGraphics.startFrame(canvas);
            _gameState.render();
            _surfaceView.getHolder().unlockCanvasAndPost(canvas);    //Desbloquea el canvas
        }
    }

    @Override
    public Graphics getGraphics() {
        return _androidGraphics;
    }
}
