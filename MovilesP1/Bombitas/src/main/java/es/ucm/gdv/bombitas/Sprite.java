package es.ucm.gdv.bombitas;

import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Image;

/**
 * Clase utilizada por la logica para dibujar imágenes
 * Puede ser toda la imagen o recortes de esta
 * Tiene un método para el dibujado
 */
public class Sprite {

    private Image _image;   //Referencia a la imagen
    private int _sx1, _sy1, _sx2, _sy2; //Esquinas de recorte de la imagen

    /**
     * @param image
     * @param sx1 coordenada x de la esquina superior izquierda
     * @param sy1 coordenada y de la esquina superior izquierda
     * @param sx2 coordenada x de la esquina inferior derecha
     * @param sy2 coordenada y de la esquina inferior derecha
     */
    public Sprite(Image image, int sx1, int sy1, int sx2, int sy2)
    {
        _image = image;
        _sx1 = sx1;
        _sx2 = sx2;
        _sy1 = sy1;
        _sy2 = sy2;
    }

    /**
     * Dibuja la imagen en un viewPORT
     * @param graphics
     * @param dx1 coordenada x de la esquina superior izquierda
     * @param dy1 coordenada y de la esquina superior izquierda
     * @param dx2 coordenada x de la esquina inferior derecha
     * @param dy2 coordenada y de la esquina inferior derecha
     */
    public void draw(Graphics graphics,int dx1,int dy1,int dx2,int dy2)
    {
        graphics.drawImage(_image,dx1,dy1,dx2,dy2,_sx1,_sy1,_sx2,_sy2);
    }
}
