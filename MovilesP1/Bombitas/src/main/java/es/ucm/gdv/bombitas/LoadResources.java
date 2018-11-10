package es.ucm.gdv.bombitas;

import es.ucm.gdv.engine.GameState;
import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Image;

public class LoadResources implements GameState {

    private GameManager _gameManager;

    private Graphics _graphics;
    private Image _image;

    private boolean _loading;
    //16x16
    int _height;
    int _width;

    int _spriteWidth; //= 16
    int _spriteHeight ; //= 16

    int _spritesByColumn;
    int _spritesByRow ;

    public LoadResources(GameManager gameManager) {
        _gameManager = gameManager;
        _graphics = gameManager.Game.getGraphics();

        _loading = true;
        init();
    }

    private void init() {
        _gameManager.Game.setGameState(this);

        _image = _graphics.newImage("ASCII_09.png");

        _height = _image.getHeight();
        _width = _image.getWidth();

        _spritesByColumn = 16;
        _spritesByRow = 16;

        _spriteWidth = _width / _spritesByRow; //= 16
        _spriteHeight = _height / _spritesByColumn; //= 16

        _gameManager.Letters = new Sprite[256];

        for(int i = 0; i < 256; i++)
            _gameManager.Letters[i] = ASCII_to_image(i);

        _gameManager.Sprites = new Sprite[9];

        _gameManager.Sprites[GameManager.SpriteType.ROOF.ordinal()] = _gameManager.Letters[244];
        _gameManager.Sprites[GameManager.SpriteType.FLOOR.ordinal()] = _gameManager.Letters[143];
        _gameManager.Sprites[GameManager.SpriteType.PLANELEFT.ordinal()] = _gameManager.Letters[241];
        _gameManager.Sprites[GameManager.SpriteType.PLANERIGHT.ordinal()] = _gameManager.Letters[242];
        _gameManager.Sprites[GameManager.SpriteType.BOMB.ordinal()] = _gameManager.Letters[252];
        _gameManager.Sprites[GameManager.SpriteType.COLLISION1.ordinal()] = _gameManager.Letters[188];
        _gameManager.Sprites[GameManager.SpriteType.COLLISION2.ordinal()] = _gameManager.Letters[238];
        _gameManager.Sprites[GameManager.SpriteType.COLLISION3.ordinal()] = _gameManager.Letters[253];
        _gameManager.Sprites[GameManager.SpriteType.UNDERLINE.ordinal()] = _gameManager.Letters[95];

        _loading = false;
    }

    private Sprite ASCII_to_image(int ascii) {
        return new Sprite(_image, _spriteWidth * (ascii % _spritesByRow),
                _spriteHeight * (ascii / _spritesByColumn),
                _spriteWidth * ((ascii % _spritesByRow) + 1),
                _spriteHeight * ((ascii / _spritesByColumn) + 1));

    }

    //TODO: PREGUNTAR SI HAY QUE HACER HEBRAS Y MOVIDAS
    @Override
    public void render() {
        _graphics.clear(0xFF000000);    //A - RGB
    }

    @Override
    public void update(double elapsedTime) {
        if (!_loading)
            _gameManager.Game.setGameState(new Tutorial(_gameManager));

    }
}
