package es.ucm.gdv.bombitas.GameState;

import java.util.List;

import es.ucm.gdv.bombitas.GameManager;
import es.ucm.gdv.bombitas.Tile;
import es.ucm.gdv.engine.TouchEvent;

public class VelocityGameState extends TileMapGameState {

    //Array con los botones de selección de velocidad
    private Tile[] velocities;

    public VelocityGameState(GameManager gameManager)
    {
        super(gameManager,25,40);

        init();
    }

    protected void init()
    {
        int offsetX = 2;
        int offsetY = 10;
        setText("Elija velocidad: 0 (MAX) a 9 (MIN)", offsetX,offsetY,GameManager.SpriteColor.RED);

        //Contrucción de botones
        velocities = new Tile[10];
        velocities[0] = createTile(_gameManager.Sprites[GameManager.SpriteColor.WHITE.ordinal()]['0'],offsetX + 8,offsetY + 2);
        velocities[1] = createTile(_gameManager.Sprites[GameManager.SpriteColor.WHITE.ordinal()]['1'],offsetX +11,offsetY + 2);
        velocities[2] = createTile(_gameManager.Sprites[GameManager.SpriteColor.WHITE.ordinal()]['2'],offsetX +14,offsetY + 2);
        velocities[3] = createTile(_gameManager.Sprites[GameManager.SpriteColor.WHITE.ordinal()]['3'],offsetX +17,offsetY + 2);
        velocities[4] = createTile(_gameManager.Sprites[GameManager.SpriteColor.WHITE.ordinal()]['4'],offsetX +20,offsetY + 2);
        velocities[5] = createTile(_gameManager.Sprites[GameManager.SpriteColor.WHITE.ordinal()]['5'],offsetX +8,offsetY + 5);
        velocities[6] = createTile(_gameManager.Sprites[GameManager.SpriteColor.WHITE.ordinal()]['6'],offsetX +11,offsetY + 5);
        velocities[7] = createTile(_gameManager.Sprites[GameManager.SpriteColor.WHITE.ordinal()]['7'],offsetX +14,offsetY + 5);
        velocities[8] = createTile(_gameManager.Sprites[GameManager.SpriteColor.WHITE.ordinal()]['8'],offsetX +17,offsetY + 5);
        velocities[9] = createTile(_gameManager.Sprites[GameManager.SpriteColor.WHITE.ordinal()]['9'],offsetX +20,offsetY + 5);
    }

    @Override
    public void update(double elapsedTime) {
        //Detecta el input de los botones
        List<TouchEvent> touchEvents = _gameManager.Game.getInput().getTouchEvents();
        for (TouchEvent e: touchEvents) {
            for (int i = 0; i < velocities.length; i++) {
                //Pasa a la siquiente escena si se pulsa en un botón
                if (velocities[i].isWithin(e.getPosX(),e.getPosY()))
                {
                    _gameManager.Velocity = i;
                    _gameManager.Game.setGameState(new BombitasGameState(_gameManager));
                }
            }
        }
    }
}
