package es.ucm.gdv.engine;


/**
 * representa la información de un toque sobre
 * la pantalla (o evento de ratón).
 */
public class TouchEvent {
    private int _x, _y;

    public TouchEvent(int x, int y) {
        _x = x;
        _y = y;
    }

    public int getPosX() {
        return _x;
    }


    public int getPosY() {
        return _y;
    }

    //TODO: detectar el "dedio" con el que se ha pulsado(click izq, click der,etc..) y el tipo de pulsación
}
