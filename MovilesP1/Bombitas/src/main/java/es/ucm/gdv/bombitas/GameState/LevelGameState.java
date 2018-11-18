package es.ucm.gdv.bombitas.GameState;

import java.util.List;

import es.ucm.gdv.bombitas.GameManager;
import es.ucm.gdv.bombitas.Tile;
import es.ucm.gdv.engine.TouchEvent;

public class LevelGameState extends TileMapGameState {

    //Array con los botones de selección de dificultad
    private Tile[] levels;

    public LevelGameState(GameManager gameManager)
    {
        super(gameManager,25,40);

        init();
    }

    protected void init()
    {
        int offset = 11;
        setText("Elija nivel: 0 (AS) a 5 (principiante)", 0,offset,GameManager.SpriteColor.RED);

        //Contrucción de botones
        levels = new Tile[6];
        levels[0] = createTile(_gameManager.Sprites[GameManager.SpriteColor.WHITE.ordinal()]['0'],10,offset + 2);
        levels[1] = createTile(_gameManager.Sprites[GameManager.SpriteColor.WHITE.ordinal()]['1'],13,offset + 2);
        levels[2] = createTile(_gameManager.Sprites[GameManager.SpriteColor.WHITE.ordinal()]['2'],16,offset + 2);
        levels[3] = createTile(_gameManager.Sprites[GameManager.SpriteColor.WHITE.ordinal()]['3'],19,offset + 2);
        levels[4] = createTile(_gameManager.Sprites[GameManager.SpriteColor.WHITE.ordinal()]['4'],22,offset + 2);
        levels[5] = createTile(_gameManager.Sprites[GameManager.SpriteColor.WHITE.ordinal()]['5'],25,offset + 2);
    }

    @Override
    public void update(double elapsedTime) {
        //Detecta el input de los botones
        List<TouchEvent> touchEvents = _gameManager.Game.getInput().getTouchEvents();
        for (TouchEvent e: touchEvents) {
            for (int i = 0; i < levels.length; i++)
            {
                //Pasa a la siquiente escena si se pulsa en un botón
                if (levels[i].isWithin(e.getPosX(),e.getPosY()))
                {
                    _gameManager.Level = i;
                    _gameManager.Game.setGameState(new VelocityGameState(_gameManager));
                }
            }
        }
    }

}
