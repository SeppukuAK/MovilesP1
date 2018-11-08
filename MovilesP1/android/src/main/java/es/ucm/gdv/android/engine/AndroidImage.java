package es.ucm.gdv.android.engine;

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

    public Bitmap getBitmap()
    {
        return _bitmap;
    }
}
