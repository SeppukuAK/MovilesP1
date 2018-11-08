package es.ucm.gdv.bombitas;

import es.ucm.gdv.engine.Graphics;

public class TileMap {

    private int _offsetX, _offsetY;
    private int _rows, _cols;
    private Sprite [][] _matrix;
    private int _tileSizeX, _tileSizeY;

    public TileMap(Graphics graphics, int rows, int cols)
    {
        _rows = rows;
        _cols = cols;
        _matrix = new Sprite[rows][cols];

        _tileSizeX = graphics.getWidth()/cols;
        _tileSizeY = graphics.getHeight()/rows;

        _offsetX = (graphics.getWidth() % cols)/ 2 ;
        _offsetY = (graphics.getHeight() % rows) / 2;
    }

    public void draw(Graphics graphics)
    {
        for (int i = 0; i < _rows; i++)
        {
            for (int j = 0; j < _cols; j++)
            {
                if (_matrix[i][j] != null)
                {
                    _matrix[i][j].draw(
                            graphics,
                            _offsetX + (j * _tileSizeX),
                            _offsetY + (i * _tileSizeY),
                            _offsetX + ((j + 1) * _tileSizeX),
                            _offsetY + ((i + 1) * _tileSizeY));
                }
            }
        }
    }

    public void setSpritePosition(Sprite sprite, int x, int y)
    {
        _matrix[y][x] = sprite;
    }

}
