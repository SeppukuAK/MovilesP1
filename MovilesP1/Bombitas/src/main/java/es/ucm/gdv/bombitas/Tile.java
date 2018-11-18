package es.ucm.gdv.bombitas;

import es.ucm.gdv.engine.Graphics;

/**
 * Objeto Tile. Proporciona facilidades para dibujado y detección de input
 */
public class Tile {
    private Sprite _sprite;
    private int _dx1, _dy1, _dx2, _dy2; //Esquinas donde se dibuja
    private int _posX, _posY;   //Posición en la matriz de tiles

    public Tile(Sprite sprite, int dx1,int dy1, int dx2, int dy2, int posX, int posY)
    {
        _sprite = sprite;
        _dx1 = dx1;
        _dy1 = dy1;
        _dx2 = dx2;
        _dy2 = dy2;

        _posX = posX;
        _posY = posY;
    }

    public void draw(Graphics graphics)
    {
        _sprite.draw(graphics,_dx1,_dy1,_dx2,_dy2);
    }

    /**
     * Devuelve si una coordenada está dentro del Tile
     * @param x
     * @param y
     * @return
     */
    public boolean isWithin(int x, int y){
        return(x> _dx1 && x<_dx2 && y>_dy1 && y<_dy2);
    }

    public int get_posX() {
        return _posX;
    }

    public int get_posY() {
        return _posY;
    }
}
