package es.ucm.gdv.bombitas;

import es.ucm.gdv.engine.StateGame;

public class GameManager {

    //TODO: LOS COLORINES
    public enum SpriteType {
        ROOF, FLOOR, PLANELEFT, PLANERIGHT, BOMB, COLLISION1, COLLISION2, COLLISION3, UNDERLINE
    }

    public StateGame Game;
    public Sprite[] Sprites;
    public Sprite[] Letters;

    public void setText(TileMap tileMap, String text, int x, int y) {

        char [] charText = text.toCharArray();
        for(int i = 0; i < charText.length; i++)
        {
            tileMap.setSpritePosition(Letters[charText[i]], (x + i) % tileMap.Cols, y + (i + x) / tileMap.Cols);
        }
    }

    public GameManager(StateGame game) {
        Game = game;

        new LoadResources(this);
        //TODO: LOAD RESOURCES NO LO UTILIZAMOS COMO UN GAMESTATE
        // Game.setGameState(state);
    }

}
