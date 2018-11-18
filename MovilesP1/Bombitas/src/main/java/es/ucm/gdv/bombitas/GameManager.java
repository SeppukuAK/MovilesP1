package es.ucm.gdv.bombitas;

import es.ucm.gdv.bombitas.GameState.LoadResources;
import es.ucm.gdv.engine.StateGame;

/**
 * Objeto persistente entre todas los GameState del juego.
 * Gestiona el flujo del juego.
 * Tiene parámetros para poder pasarse de un GameState a otro
 */
public class GameManager {

    /**
     * Enumerado para el color del Spirte
     */
    public enum SpriteColor {
        BLACK, LIGHT_GREEN, RED, PURPLE, GREEN, ORANGE, BLUE, YELLOW, MAGENTA, WHITE, BOTTLE_GREEN, CYAN, KHAKI, PINK, INDIGO, APPLE_GREEN
    }

    //Referencia al motor
    public StateGame Game;

    //Sprites disponibles para dibujar. Se cargan en LoadResources
    public Sprite[][] Sprites;

    //Atributos del juego
    public int Level;
    public int Velocity;
    public int Score;
    public int MaxScore;

    public GameManager(StateGame game) {
        Game = game;
        Level = -1;
        Velocity = -1;
        Score = -1;
        MaxScore = 0;

        Game.setGameState(new LoadResources(this));
    }



}
