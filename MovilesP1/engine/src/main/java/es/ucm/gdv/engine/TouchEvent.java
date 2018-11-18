package es.ucm.gdv.engine;

/**
 * Representa la información de un toque sobre
 * la pantalla (o evento de ratón).
 */
public class TouchEvent {

    public enum TouchType{
       TOUCH, RELEASE, MOVEMENT
    }

    public enum ButtonType{
        PRIMARY, SECONDARY, MIDDLE
    }

    //Posición en la que se ha producido el evento
    private int _x, _y;

    //Tipo de evento
    private TouchType _touchType;

    //Identificador
    private int _ID;

    public TouchEvent(int x, int y, TouchType touchType, int ID) {
        _x = x;
        _y = y;
        _touchType = touchType;
        _ID = ID;
    }

    public int getPosX() {
        return _x;
    }
    public int getPosY() {
        return _y;
    }

    public int get_ID() {
        return _ID;
    }

    public TouchType get_touchType() {
        return _touchType;
    }

}
