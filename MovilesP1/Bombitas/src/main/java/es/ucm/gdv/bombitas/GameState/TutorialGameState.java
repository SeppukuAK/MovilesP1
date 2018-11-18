package es.ucm.gdv.bombitas.GameState;

import java.util.List;

import es.ucm.gdv.bombitas.GameManager;
import es.ucm.gdv.engine.TouchEvent;

public class TutorialGameState extends TileMapGameState {

    public TutorialGameState(GameManager gameManager)
    {
        super(gameManager,25,40);

        init();
    }

    protected void init()
    {
        int offset = 1;
        setText("Usted esta pilotando un avion sobre una ciudad desierta y tiene que pasar sobre los edificios para aterrizar y respos-  tar. Su avion se mueve de izquierda a   derecha.", 0,offset,GameManager.SpriteColor.LIGHT_GREEN);
        setText("Al llegar a la derecha, el avion vuelve a salir por la izquierda, pero MAS BAJO.Dispone de un numero limitado de bombas y puede hacerlas caer sobre los edifi-  cios pulsando sobre la pantalla.", 0,offset + 6,GameManager.SpriteColor.LIGHT_GREEN);
        setText("Cada vez que aterriza, sube la altura   de los edificios y la velocidad.", 0,offset + 12,GameManager.SpriteColor.LIGHT_GREEN);
        setText("UNA VEZ DISPARADA UNA BOMBA, YA NO PUEDEDISPARAR OTRA MIENTRAS NO HAYA EXPLOSIO-NADO LA PRIMERA!!!!", 0,offset +15,GameManager.SpriteColor.LIGHT_GREEN);
        setText("Pulse para empezar", 0,offset + 20,GameManager.SpriteColor.RED);
    }

    @Override
    public void update(double elapsedTime) {
        List<TouchEvent> touchEvents = _gameManager.Game.getInput().getTouchEvents();

        for (TouchEvent t: touchEvents)
            if (t.get_touchType() == TouchEvent.TouchType.TOUCH && t.get_ID() == TouchEvent.ButtonType.PRIMARY.ordinal())
                _gameManager.Game.setGameState(new LevelGameState(_gameManager));

    }

}
