package es.ucm.gdv.engine;

abstract public class StateGame implements Game {

    protected GameState _gameState;

    public void setGameState(GameState gameState) {
        _gameState = gameState;
    }
}
