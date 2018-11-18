package es.ucm.gdv.engine;

import java.util.List;

/**
 * Funcionalidades para el input
 */
public interface Input {

    /**
     * Devuelve una lista con todos los eventos recibidos desde el ultimo tick
     * @return
     */
    List<TouchEvent> getTouchEvents();
}
