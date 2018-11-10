package es.ucm.gdv.bombitas;

import es.ucm.gdv.engine.Graphics;

public class TileMap {

    public int Rows, Cols;

    private Sprite [][] _matrix;
    private int _offsetX, _offsetY;
    private int _tileSizeX, _tileSizeY;

    public TileMap(Graphics graphics, int rows, int cols)
    {
        Rows = rows;
        Cols = cols;
        _matrix = new Sprite[rows][cols];

            //TODO: Somos muy cutres
        _tileSizeX = graphics.getWidth()/(cols + 2);
        _tileSizeY = graphics.getHeight()/(rows + 2);

        _offsetX =  _tileSizeX + (graphics.getWidth() % (cols + 2))/ 2;
        _offsetY =  _tileSizeY + (graphics.getHeight() % (cols + 2)) / 2;
    }

    public void draw(Graphics graphics)
    {
        for (int i = 0; i < Rows; i++)
        {
            for (int j = 0; j < Cols; j++)
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
