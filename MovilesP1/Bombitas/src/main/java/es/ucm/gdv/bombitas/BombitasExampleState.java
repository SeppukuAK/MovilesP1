package es.ucm.gdv.bombitas;

import es.ucm.gdv.engine.Game;
import es.ucm.gdv.engine.GameState;
import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Image;

public class BombitasExampleState implements GameState {

    Image _logo;	//Objeto que guarda la imagen

    double _x = 0;	//Posición del objeto
    double _incX = 500;	//Velocidad del objeto

    int _imageWidth;	//Ancho de la imagen

    private Graphics _graphics;

    private TileMap _tileMap;
    private Sprite _sprite;

    public BombitasExampleState(Game game)
    {
        _graphics = game.getGraphics();

        init();
    }

    private void init()
    {
        _tileMap = new TileMap(_graphics,20,20);
        Image imagenPrueba = _graphics.newImage("ASCII_01.png");
        _sprite = new Sprite(imagenPrueba,50,50,100,100);
        _tileMap.setSpritePosition(_sprite,0,0);
//        _logo = _graphics.newImage("ASCII_01.png");
//        _imageWidth = _logo.getWidth();
    }

    @Override
    public void render() {
        _graphics.clear(0xFF000000);    //A - RGB
        _tileMap.draw(_graphics);
//        _graphics.drawImage(_logo,60,60,90,90,0,0,30,30);
    }

    @Override
    public void update(double elapsedTime) {

       /* _x += _incX * elapsedTime;

        //Si choca por la izquierda
        if (_x < 0)
        {
            _x = -_x;	//Si x=-3 -> x=3
            _incX *= -1;
        }

        //Si choca por la derecha
        else if (_x + _imageWidth > _graphics.getWidth())
        {
            _x =2*(_graphics.getWidth()- _imageWidth) - _x;
            _incX *= -1;
        }
        */


    }

}
