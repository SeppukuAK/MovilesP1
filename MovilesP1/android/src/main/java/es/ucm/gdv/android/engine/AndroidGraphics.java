package es.ucm.gdv.android.engine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InputStream;

import es.ucm.gdv.engine.Image;

public class AndroidGraphics implements es.ucm.gdv.engine.Graphics {

    private SurfaceView _surfaceView;       //Ventana de android
    private AssetManager _assetManager;     //Utilizado para cargar imagenes

    private Canvas _canvas;                 //Viewport donde se pinta

    AndroidGraphics(SurfaceView surfaceView, AssetManager assetManager) {
        _surfaceView = surfaceView;
        _assetManager = assetManager;
    }

    //TODO: A LO MEJOR ESTAMOS CARGANDO MUCHAS VECES LAS IMAGENES
    @Override
    public Image newImage(String name) {
        InputStream inputStream = null;
        AndroidImage androidImage = null;
        try {
            inputStream = _assetManager.open(name);
            Bitmap sprite = BitmapFactory.decodeStream(inputStream);
            androidImage = new AndroidImage(sprite);
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
        return androidImage;
    }


    public void startFrame(Canvas canvas) {
        _canvas = canvas;
    }

    @Override
    public void drawImage(Image image, int x, int y) {
        if (image != null) {
            AndroidImage androidImage = (AndroidImage) image;
            Bitmap bitmap = androidImage.getBitmap();

            _canvas.drawBitmap(bitmap, x, y, null);
        }
    }

    @Override
    public void drawImage(Image image, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2) {
        if (image != null) {
            AndroidImage androidImage = (AndroidImage) image;
            Bitmap bitmap = androidImage.getBitmap();

            Rect src = new Rect(dx1,dy1,dx2,dy2);
            Rect dst = new Rect(sx1,sy1,sx2,sy2);

            _canvas.drawBitmap(bitmap, src, dst, null);
        }
    }

    @Override
    public void clear(int rgb) {
        _canvas.drawColor(rgb); //ARGB
    }

    @Override
    public int getWidth() {
        return _surfaceView.getWidth();
    }

    @Override
    public int getHeight() {
        return _surfaceView.getHeight();
    }

}
