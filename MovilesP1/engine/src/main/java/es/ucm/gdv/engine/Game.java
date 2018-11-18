package es.ucm.gdv.engine;

/**
 * Aglutina todos los comportamientos
 * Encargado de mantener las instancias de Graphics y Input
 */
public interface Game {
    Graphics getGraphics();
    Input getInput();
}
