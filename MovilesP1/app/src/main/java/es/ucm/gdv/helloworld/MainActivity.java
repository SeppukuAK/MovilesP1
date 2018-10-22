package es.ucm.gdv.helloworld;

import android.content.ContentProvider;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;

//Una ventana es una actividad, Hay que heredar
public class MainActivity extends AppCompatActivity {

    //Necesario sobreescribir métodos de la superclase
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: AHORA TODO ESTO ES DIFERENTE EN EL LABORATORIO
        AssetManager assetManager = getAssets();
        InputStream inputStream = null;

        try
        {
            inputStream = assetManager.open("españita.png");
            _sprite = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        }
        catch(IOException ioe)
        {
            //TODO: ERROR
        }
        finally {
            if (inputStream != null)
            {
                try {
                    inputStream.close();
                }
                catch (IOException ioe)
                {
                    //TODO: ERROR
                }
            }
        }

        _renderView = new MyView(this);
        setContentView(_renderView);
        /*
        //Creación de un botón
        _boton = new Button(this);
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

    @Override
    protected void onResume()
    {
        super.onResume();
        _renderView.resume();
    }

    @Override
    protected  void onPause()
    {
        super.onPause();
        _renderView.pause();
    }

    //TODO: ESTO ES MUY FEO Y SE PUEDE SEPARAR. NO NECESITAMOS REALMENTE HEREDAR DE SURFACE VIEW
    class MyView extends SurfaceView implements Runnable
    {
        public MyView(Context context)
        {
            super (context);
            if (_sprite != null)
                _imageWidth = _sprite.getWidth();
        }

        //
        public void resume()
        {
          if (!_running)
          {
              _running = true;

              _runningThread = new Thread(this);
              _runningThread.start();    //LLAMA A RUN
          }
        }

        public void pause()
        {
            _running = false;
            while (true)
            {
                try {
                    _runningThread.join();
                    break;
                }
                catch (InterruptedException ie) {

                }
            }

        }

        @Override
        public void run()
        {
            long _lastFrameTime = System.nanoTime();
            SurfaceHolder sh = getHolder();

            while (_running)
            {
                long currentTime = System.nanoTime();
                long nanoElapsedTime = currentTime - _lastFrameTime;
                _lastFrameTime = currentTime;
                double elapsedTime = (double) nanoElapsedTime / 1.0e9;

                //LOGIC
                update(elapsedTime);

                //RENDER
                while (!sh.getSurface().isValid());
                Canvas canvas = getHolder().lockCanvas();
                //TODO: TRY CATCH AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
                render(canvas);
                getHolder().unlockCanvasAndPost(canvas);

            }
        }

        protected void update(double deltaTime)
        {
            _x+= _incX * deltaTime;
            if (_x < 0)
            {
                _x = -_x;
                _incX *= -1;
            }

            else if (_x > getWidth() - _imageWidth)
            {
                _x = 2*(getWidth() - _imageWidth) - _x;
                _incX *= -1;
            }
        }

        protected void render(Canvas c)
        {
            if (_sprite != null) {
                c.drawColor(0xFF0000FF); //ARGB
                c.drawBitmap(_sprite, (int) _x, 100, null);
            }
        }

        double _x = 0;
        int _incX = 50;
        int _imageWidth;

        //Volatile: CADA VEZ QUE TENGAS QUE VER EL VALOR, VE A MEMORIA, NO MIRES EN CACHE
        volatile boolean _running = false;

        Thread _runningThread;
    }

    //private Button _boton;
    //private int _numVeces;

    Bitmap _sprite;
    MyView _renderView;
}
