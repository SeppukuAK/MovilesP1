package es.ucm.gdv.helloworld;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    /*
    //Boton a pinchi
    private Button _boton;
    private int _numVeces;
    */

    Bitmap _sprite;
    MyView _renderView;

    /*
     * Se le llama al inicio de la ejecución.
     * Crea el proceso.
     * Carga recursos.
     * Inicia la ventana.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Carga de recursos
        AssetManager assetManager = getAssets();
        InputStream inputStream = null;

        try {
            inputStream = assetManager.open("españita.png");
            _sprite = BitmapFactory.decodeStream(inputStream);

        } catch (IOException ioe) {
            //TODO: ERROR
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ioe) {
                    //TODO: ERROR
                }
            }
        }

        //Creación de la ventana
        _renderView = new MyView(this);
        setContentView(_renderView);
        /*TODO: PUTO BOTOOOOOOOOOOOOOOOON

        //Creación de un botón
        _boton = new Button(context);
        _boton.setText("¡¡Pulsame!!");

        _boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++_numVeces;
                _boton.setText("Pulsado " +_numVeces + " veces");
            }
        });

        setContentView(_boton);
        */
    }

    /*
    Se le llama cuando la aplicación pasa a primer plano.
    Reanuda el bucle de juego.
     */
    @Override
    protected void onResume() {
        super.onResume();
        _renderView.resume();
    }

    /*
    Se le llama cuando la aplicación pasa a segundo plano.
    Pausa el bucle de juego.
     */
    @Override
    protected void onPause() {
        super.onPause();
        _renderView.pause();
    }

    //Una ventana es una actividad, Hay que heredar de surfaceView
    //SurfaceView -> Será un atributo de Graphics.
    //Runnable -> Lo heredará Game
    //TODO: ESTO ES MUY FEO Y SE PUEDE SEPARAR. NO NECESITAMOS REALMENTE HEREDAR DE SURFACE VIEW
    class MyView extends SurfaceView implements Runnable {
        //Españita
        double _x = 0;
        int _incX = 50;
        int _imageWidth;

        //Volatile: CADA VEZ QUE TENGAS QUE VER EL VALOR, VE A MEMORIA, NO MIRES EN CACHE
        volatile boolean _running = false;  //Variable compartida por las 2 hebras
        Thread _runningThread;

        public MyView(Context context) {
            super(context);
            if (_sprite != null)
                _imageWidth = _sprite.getWidth();
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
            SurfaceHolder sh = getHolder();     //Referencia a la ventana

            while (_running) {
                //Calculo de elapsedTime
                long currentTime = System.nanoTime();
                long nanoElapsedTime = currentTime - _lastFrameTime;
                _lastFrameTime = currentTime;
                double elapsedTime = (double) nanoElapsedTime / 1.0e9;

                //LOGIC
                update(elapsedTime);

                //RENDER
                while (!sh.getSurface().isValid()) ; //Esperamos a poder coger el viewport

                Canvas canvas = getHolder().lockCanvas();   //Obtenemos el canvas y lo bloqueamos
                //TODO: Supuestamente hay que hacer un try catch en algún lado
                render(canvas);
                getHolder().unlockCanvasAndPost(canvas);    //Desbloquea el canvas
            }
        }

        /*
        Avanza un frame en la lógica de juego.
         */
        protected void update(double deltaTime) {
            //Control de españita
            _x += _incX * deltaTime;
            if (_x < 0) {
                _x = -_x;
                _incX *= -1;
            } else if (_x > getWidth() - _imageWidth) {
                _x = 2 * (getWidth() - _imageWidth) - _x;
                _incX *= -1;
            }
        }

        /*
        Pinta los GameObjects en el canvas
         */
        protected void render(Canvas c) {
            if (_sprite != null) {
                c.drawColor(0xFF0000FF); //ARGB
                c.drawBitmap(_sprite, (int) _x, 100, null);
            }
        }

    }


}
