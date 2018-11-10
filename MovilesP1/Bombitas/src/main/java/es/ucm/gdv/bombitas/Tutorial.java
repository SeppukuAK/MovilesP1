package es.ucm.gdv.bombitas;

import es.ucm.gdv.engine.GameState;
import es.ucm.gdv.engine.Graphics;

public class Tutorial implements GameState {

    private GameManager _gameManager;
    private Graphics _graphics;

    private TileMap _tileMap;

    public Tutorial(GameManager gameManager)
    {
        _gameManager = gameManager;
        _graphics = gameManager.Game.getGraphics();

        init();
    }

    private void init()
    {
        _tileMap = new TileMap(_graphics,35,40);

        int offset = 6;
        _gameManager.setText(_tileMap,"Usted esta pilotando un avion sobre una ciudad desierta y tiene que pasar sobre los edificios para aterrizar y respos-  tar. Su avion se mueve de izquierda a   derecha.", 0,offset);
        _gameManager.setText(_tileMap,"Al llegar a la derecha, el avion vuelve a salir por la izquierda, pero MAS BAJO.Dispone de un numero limitado de bombas y puede hacerlas caer sobre los edifi-  cios pulsando sobre la pantalla.", 0,offset + 6);
        _gameManager.setText(_tileMap,"Cada vez que aterriza, sube la altura   de los edificios y la velocidad.", 0,offset + 12);
        _gameManager.setText(_tileMap,"UNA VEZ DISPARADA UNA BOMBA, YA NO PUEDEDISPARAR OTRA MIENTRAS NO HAYA EXPLOSIO-NADO LA PRIMERA!!!!", 0,offset +15);
        _gameManager.setText(_tileMap,"Pulse para empezar", 0,offset + 20);
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
