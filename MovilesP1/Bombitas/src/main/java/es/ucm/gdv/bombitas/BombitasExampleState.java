package es.ucm.gdv.bombitas;

import es.ucm.gdv.engine.GameState;
import es.ucm.gdv.engine.Graphics;

public class BombitasExampleState implements GameState {

    private GameManager _gameManager;
    private Graphics _graphics;

    private TileMap _tileMap;

    public BombitasExampleState(GameManager gameManager)
    {
        _gameManager = gameManager;
        _graphics = gameManager.Game.getGraphics();

        init();
    }

    private void init()
    {
        _tileMap = new TileMap(_graphics,20,40);

        _gameManager.setText(_tileMap,"Hola amigui, deja de odiarme por favor", 5,5);

//        _logo = _graphics.newImage("ASCII_01.png");
//        _imageWidth = _logo.getWidth();
    }

    @Override
    public void render() {
        _graphics.clear(0xFF000000);    //A - RGB
        _tileMap.draw(_graphics);
    }

    @Override
    public void update(double elapsedTime) {

    }

}
