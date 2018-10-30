package es.ucm.gdv.engine.android;

import es.ucm.gdv.engine.Game;
import es.ucm.gdv.engine.Graphics;

public class AndroidGame implements Game {

    private AndroidGraphics _androidGraphics;
    //TODO: Inicializar

    public AndroidGame()
    {

    }

    @Override
    public Graphics getGraphics() {
        return _androidGraphics;
    }
}
