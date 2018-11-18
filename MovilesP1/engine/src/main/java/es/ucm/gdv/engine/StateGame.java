package es.ucm.gdv.engine;

/**
 * Juego que funciona por StateGames
 */
abstract public class StateGame implements Game {

    //GameState actual
    protected GameState _gameState;

    /**
     * Establece un nuevo gameState
     * @param gameState
     */
    public void setGameState(GameState gameState) {
        getInput().getTouchEvents(); //Limpia la lista de eventos TODO: REVISAR
        _gameState = gameState;
    }
}
