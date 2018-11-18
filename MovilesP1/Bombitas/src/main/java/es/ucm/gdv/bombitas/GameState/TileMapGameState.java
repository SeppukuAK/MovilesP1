package es.ucm.gdv.bombitas.GameState;

import es.ucm.gdv.bombitas.GameManager;
import es.ucm.gdv.bombitas.Sprite;
import es.ucm.gdv.bombitas.Tile;
import es.ucm.gdv.engine.GameState;
import es.ucm.gdv.engine.Graphics;

/**
 * Clase abstracta que representa una escena que funciona con un TileMap
 */
abstract public class TileMapGameState implements GameState {

    protected Tile[][] _tileMap;
    protected Graphics _graphics;
    protected GameManager _gameManager;

    protected int _offsetX, _offsetY;
    protected int _tileSizeX, _tileSizeY;
    protected int _rows, _cols;

    public TileMapGameState(GameManager gameManager, int rows, int cols)
    {
        _gameManager = gameManager;
        _graphics = _gameManager.Game.getGraphics();

        _rows = rows;
        _cols = cols;

        _tileMap = new Tile[_rows][_cols];

        //TODO: Somos muy cutres
        _tileSizeX = _graphics.getWidth()/(cols + 2);
        _tileSizeY = _graphics.getHeight()/(rows + 2);

        _offsetX =  _tileSizeX + (_graphics.getWidth() % (cols + 2))/ 2;
        _offsetY =  _tileSizeY + (_graphics.getHeight() % (cols + 2)) / 2;
    }

    protected abstract void init();

    @Override
    public void render() {
        _graphics.clear(0xFF000000);

        for (int i = 0; i < _rows; i++)
            for (int j = 0; j < _cols; j++)
                if (_tileMap[i][j] != null)
                    _tileMap[i][j].draw(_graphics);

    }

    Tile createTile(Sprite sprite, int x, int y)
    {
        _tileMap[y][x] = new Tile(sprite,
                _offsetX + (x * _tileSizeX),
                _offsetY + (y * _tileSizeY),
                _offsetX + ((x + 1) * _tileSizeX),
                _offsetY + ((y + 1) * _tileSizeY),x,y);

        return _tileMap[y][x];
    }

    void removeTile(int x, int y)
    {
        _tileMap[y][x] = null;
    }

    //TODO: RAYADAS DEL AMIGUI
    public void setText(String text, int x, int y,GameManager.SpriteColor letterColor) {
        //TODO: Detección de error
        char [] charText = text.toCharArray();

        for(int i = 0; i < charText.length; i++)
            createTile(_gameManager.Sprites[letterColor.ordinal()][charText[i]], (x + i) % _cols, y + (i + x) / _cols);
    }
}
