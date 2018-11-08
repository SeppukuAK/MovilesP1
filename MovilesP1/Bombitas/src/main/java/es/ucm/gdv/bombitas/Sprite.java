package es.ucm.gdv.bombitas;

import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Image;

public class Sprite {

    private Image _image;
    int _sx1, _sy1, _sx2, _sy2;

    public Sprite(Image image, int sx1, int sy1, int sx2, int sy2)
    {
        _image = image;
        _sx1 = sx1;
        _sx2 = sx2;
        _sy1 = sy1;
        _sy2 = sy2;
    }

    public void draw(Graphics graphics,int dx1,int dy1,int dx2,int dy2)
    {
        graphics.drawImage(_image,dx1,dy1,dx2,dy2,_sx1,_sy1,_sx2,_sy2);
    }

}
