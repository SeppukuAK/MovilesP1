package es.ucm.gdv.engine;

public interface GameState {

    void update(double elapsedTime);
    void render();
}
