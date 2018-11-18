package es.ucm.gdv.bombitas.GameState;

import java.util.List;

import es.ucm.gdv.bombitas.GameManager;
import es.ucm.gdv.engine.TouchEvent;

public class GameOverGameState extends TileMapGameState {

    public GameOverGameState(GameManager gameManager)
    {
        super(gameManager,25,20);

        init();
    }

    protected void init()
    {
        int offsetY = 4;
        setText("Ha conseguidos " + _gameManager.Score, 0,offsetY,GameManager.SpriteColor.RED);
        setText("puntos", 0,offsetY +1,GameManager.SpriteColor.RED);

        if (_gameManager.Score > _gameManager.MaxScore) {
            setText("BATIO EL RECORD!!", 0, offsetY + 6, GameManager.SpriteColor.RED);
            _gameManager.MaxScore = _gameManager.Score;
        }

        setText("Pulse para volver a empezar", 0,offsetY + 10,GameManager.SpriteColor.RED);
    }

    @Override
    public void update(double elapsedTime) {
        List<TouchEvent> touchEvents = _gameManager.Game.getInput().getTouchEvents();
        for (TouchEvent t: touchEvents)
            if (t.get_touchType() == TouchEvent.TouchType.TOUCH && t.get_ID() == TouchEvent.ButtonType.PRIMARY.ordinal())
                _gameManager.Game.setGameState(new LevelGameState(_gameManager));
    }
}
