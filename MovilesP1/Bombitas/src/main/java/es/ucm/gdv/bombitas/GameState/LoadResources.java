package es.ucm.gdv.bombitas.GameState;

import es.ucm.gdv.bombitas.GameManager;
import es.ucm.gdv.bombitas.Sprite;
import es.ucm.gdv.engine.GameState;
import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Image;

public class LoadResources implements GameState {

    private GameManager _gameManager;

    private Graphics _graphics;

    //Tamaño de cada uno de los sprites = 16x16
    int _spriteWidth;
    int _spriteHeight;

    //Número de sprites
    final int SPRITES_BY_COLUMN = 16;
    final int SPRITES_BY_ROW = 16;
    final int COLOURS = 16;

    //TODO: SOY TONTI

    public LoadResources(GameManager gameManager) {
        _gameManager = gameManager;
        _graphics = gameManager.Game.getGraphics();

        init();
    }

    private void init() {

        //Imágenes que vamos a cargar
        Image[] images = new Image[COLOURS];

        //Carga de imágenes
        for (int i = 0; i < images.length; i++)
        {
            if (i < 10)
                images[i] = _graphics.newImage("ASCII_0" + i + ".png");
            else
                images[i] = _graphics.newImage("ASCII_" + i + ".png");
        }

        //Tamaño de la imagen
        int height = images[0].getHeight();
        int width = images[0].getWidth();

        //Tamaño de cada uno de los sprites
        _spriteWidth = width / SPRITES_BY_ROW;
        _spriteHeight = height / SPRITES_BY_COLUMN;

        //Creación de los recursos de sprites
        _gameManager.Sprites = new Sprite[COLOURS][SPRITES_BY_ROW*SPRITES_BY_COLUMN];

        for (int i = 0; i < COLOURS; i++)
            for (int j = 0; j < SPRITES_BY_ROW * SPRITES_BY_COLUMN; j++)
                _gameManager.Sprites[i][j] = ASCII_to_image(images[i], j);

    }

    /**
     * Devuelve un sprite de un caracter ASCII
     * @param image SpriteSheet ASCII
     * @param ascii código ASCII
     * @return
     */
    private Sprite ASCII_to_image(Image image, int ascii) {
        return new Sprite(image, _spriteWidth * (ascii % SPRITES_BY_ROW),
                _spriteHeight * (ascii / SPRITES_BY_COLUMN),
                _spriteWidth * ((ascii % SPRITES_BY_ROW) + 1),
                _spriteHeight * ((ascii / SPRITES_BY_COLUMN) + 1));

    }

    @Override
    public void render() {
        _graphics.clear(0xFF000000);
    }

    @Override
    public void update(double elapsedTime) {
        //Cuando se ha acabado de cargar todo, se establece el nuevo GameState
        _gameManager.Game.setGameState(new TutorialGameState(_gameManager));    //TODO: CAMBIAR A LA ESCENA DE VERDAD
    }
}
