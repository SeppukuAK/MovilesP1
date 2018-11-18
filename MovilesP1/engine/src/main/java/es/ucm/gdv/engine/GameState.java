package es.ucm.gdv.engine;

/**
 * Interfaz de la "Escena" de juego
 * Todas las escenas, deben heredar de esta
 */
public interface GameState {
    void update(double elapsedTime);
    void render();
}
