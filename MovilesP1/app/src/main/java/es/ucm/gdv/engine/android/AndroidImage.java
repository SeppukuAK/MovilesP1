package es.ucm.gdv.engine.android;

import android.graphics.Bitmap;
import es.ucm.gdv.engine.Image;

public class AndroidImage implements Image
{
    private Bitmap _bitmap;

    public AndroidImage(Bitmap bitmap)
    {
        _bitmap = bitmap;
    }

    @Override
    public int getHeight() {
        return _bitmap.getHeight();
    }

    @Override
    public int getWidth() {
        return _bitmap.getWidth();
    }

    //TODO: Esto me lo he inventado
    public Bitmap getBitmap()
    {
        return _bitmap;
    }
}
