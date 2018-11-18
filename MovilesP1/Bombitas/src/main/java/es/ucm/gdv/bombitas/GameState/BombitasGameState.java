package es.ucm.gdv.bombitas.GameState;

import java.util.List;
import java.util.Random;

import es.ucm.gdv.bombitas.GameManager;
import es.ucm.gdv.bombitas.Tile;
import es.ucm.gdv.engine.TouchEvent;

public class BombitasGameState extends TileMapGameState {

    /**
     * Enumerado para el estado de la partida
     */
    private enum BombitasState {
        CINEMATIC, PLAY, WIN, LOSE
    }

    //Constantes con los números ASCII de los sprites del juego
    private final int ROOF = 244;
    private final int FLOOR = 143;
    private final int PLANELEFT = 241;
    private final int PLANERIGHT = 242;
    private final int BOMB = 252;
    private final int COLLISION1 = 188;
    private final int COLLISION2 = 238;
    private final int COLLISION3 = 253;

    private final int RANDOM_BUILDING_HEIGHT = 7;
    private final int RANDOM_BOMB_FORCE = 2;
    private final int MIN_BOMB_FORCE = 2;

    private final int BUILDINGS = 11;

    private final int INITIAL_PLANE_POS_X = 2;
    private final int INITIAL_PLANE_POS_Y = 1;

    private final int FINAL_PLANE_POS_X = 18;

    private final int INITIAL_BUILDING_POS_X = 4;
    private final int INITIAL_BUILDING_POS_Y = 21;
    private final int INITIAL_COLOR = GameManager.SpriteColor.PURPLE.ordinal();
    private final int EXTRA_POINTS = 100;
    private final int DEAD_TICKS = 15;
    private final int WIN_TICKS = 5;

    private final float GAME_VELOCITY = 30f;
    private final float CINEMATIC_VELOCITY = 30f;

    private final int POINTS_BY_FLOOR = 5;

    private Tile planeRight;
    private Tile planeLeft;
    private Tile bomb;

    private float nextFrame = 0;
    private float frameRate;
    private float time = 0;

    //TODO: QUE LA APLICACION NO SE REINICIE
    private BombitasState _bombitasState;

    //Utilizado para las animaciones
    private int posX;
    private int posY;

    private int bombForce;
    private int collisionSprite;

    private int winTicksCount;

    /**
     * Guarda la altura del edificio sin contar el tejado
     */
    private int[] buildingHeight;

    private Random _random;

    public BombitasGameState(GameManager gameManager) {
        super(gameManager, 25, 20);
        init();
    }

    protected void init() {
        _random = new Random();
        _gameManager.Score = 0;

        setState(BombitasState.CINEMATIC);
    }

    /**
     * Inicia un nuevo estado de la partida
     * @param bombitasState
     */
    private void setState(BombitasState bombitasState) {
        switch (bombitasState) {
            case CINEMATIC: {
                _bombitasState = bombitasState.CINEMATIC;

                //Creación de alturas de los edificios aleatorias
                buildingHeight = new int[BUILDINGS];
                for (int i = 0; i < buildingHeight.length; i++)
                    buildingHeight[i] = _random.nextInt(RANDOM_BUILDING_HEIGHT + 1) + (5 - _gameManager.Level);

                //Velocidad de animación de los edificios
                frameRate = 1 / CINEMATIC_VELOCITY; //TODO: A LO MEJOR DEPENDE DE LA VELOCIDAD O DIFICULTAD

                //Posición donde empieza a pintar la animación
                posX = INITIAL_BUILDING_POS_X;
                posY = INITIAL_BUILDING_POS_Y;

                //Creación del primer tejado
                //createTile(_gameManager.Sprites[INITIAL_COLOR][ROOF], posX, posY);

                break;
            }

            case PLAY: {
                _bombitasState = bombitasState.PLAY;
                setText("____________________", 0, _rows - 2, GameManager.SpriteColor.WHITE);

                setText("PUNTOS", 0, _rows - 1, GameManager.SpriteColor.RED);
                setText(Integer.toString(_gameManager.Score), 7, _rows - 1, GameManager.SpriteColor.WHITE);

                setText("MAX", 12, _rows - 1, GameManager.SpriteColor.RED);
                setText(Integer.toString(_gameManager.MaxScore), 16, _rows - 1, GameManager.SpriteColor.WHITE);

                frameRate = (float) (_gameManager.Velocity + 1) / GAME_VELOCITY;

                planeRight = createTile(_gameManager.Sprites[GameManager.SpriteColor.LIGHT_GREEN.ordinal()][PLANERIGHT], INITIAL_PLANE_POS_X, INITIAL_PLANE_POS_Y);
                planeLeft = createTile(_gameManager.Sprites[GameManager.SpriteColor.LIGHT_GREEN.ordinal()][PLANELEFT], INITIAL_PLANE_POS_X -1, INITIAL_PLANE_POS_Y);

                bomb = null;
                bombForce = 0;

                break;
            }

            case WIN: {
                _bombitasState = bombitasState.WIN;
                _gameManager.Score += EXTRA_POINTS;

                winTicksCount = 0;

                if (_gameManager.Level > 0)
                    _gameManager.Level--;

                if (_gameManager.Velocity > 0)
                    _gameManager.Velocity--;

                break;
            }

            case LOSE: {
                _bombitasState = bombitasState.LOSE;
                collisionSprite = 0;
                frameRate = 1/ (CINEMATIC_VELOCITY);
                break;
            }
        }
    }

    @Override
    public void update(double elapsedTime) {

        time += elapsedTime;
        if (time > nextFrame) {
            nextFrame = time + frameRate;

            switch (_bombitasState) {

                case CINEMATIC: {

                    int buildingIndex = posX - INITIAL_BUILDING_POS_X;

                    //Sigo pintando este edificio??
                    if (posY > (INITIAL_BUILDING_POS_Y - buildingHeight[buildingIndex]))
                    {
                        createTile(_gameManager.Sprites[INITIAL_COLOR + buildingIndex][ROOF], posX, posY-1);
                        createTile(_gameManager.Sprites[INITIAL_COLOR + buildingIndex][FLOOR], posX, posY);

                        posY--;
                    }
                        //Paso al sigueinte?
                    else
                    {
                        //Si ha construido todos los edificios
                        if (buildingIndex == (buildingHeight.length - 1))
                            setState(BombitasState.PLAY);
                        else
                            {
                            posX++;
                            posY = INITIAL_BUILDING_POS_Y;
                        }
                    }
                    break;
                }

                case PLAY: {

                    //UPDATE DEL AVION
                    removeTile(planeLeft.get_posX(), planeLeft.get_posY()); //Borramos la cola del avion

                    //Se considera la cabeza la posicion del avion
                    int planePosX = planeRight.get_posX();
                    int planePosY = planeRight.get_posY();

                    planePosX++;

                    //Si he ganado
                    if (planePosX == FINAL_PLANE_POS_X && planePosY == INITIAL_BUILDING_POS_Y) {
                        planePosX--;
                        removeTile(planeRight.get_posX(), planeRight.get_posY());
                        setState(BombitasState.WIN);
                    }

                    //Si he perdido
                    else if (_tileMap[planePosY][planePosX] != null) {
                        posX = planePosX;
                        posY = planePosY;

                        removeTile(planeRight.get_posX(), planeRight.get_posY());
                        setState(BombitasState.LOSE);
                    }

                    //Sigue la partida
                    else {

                        //UPDATE DEL AVIONI
                        //Si tengo que pasar a la siguiente altura
                        if (planePosX == FINAL_PLANE_POS_X) {
                            removeTile(planeRight.get_posX(), planeRight.get_posY()); //Borramos la cabeza del avion

                            planePosX = INITIAL_PLANE_POS_X;
                            planePosY++;
                        }

                        planeRight = createTile(_gameManager.Sprites[GameManager.SpriteColor.LIGHT_GREEN.ordinal()][PLANERIGHT], planePosX, planePosY);
                        planeLeft = createTile(_gameManager.Sprites[GameManager.SpriteColor.LIGHT_GREEN.ordinal()][PLANELEFT], planePosX - 1, planePosY);

                        //UPDATE DE LA BOMBA
                        //Detección de Input
                        List<TouchEvent> touchEvents = _gameManager.Game.getInput().getTouchEvents();
                        if (bomb == null && touchEvents.size() > 0) {
                            bomb = createTile(_gameManager.Sprites[GameManager.SpriteColor.LIGHT_GREEN.ordinal()][BOMB], planePosX, planePosY + 1);
                            bombForce = _random.nextInt(RANDOM_BOMB_FORCE + 1) + MIN_BOMB_FORCE; // 2-4
                        } else if (bomb != null) {
                            int bombPosX = bomb.get_posX();
                            int bombPosY = bomb.get_posY();
                            bombPosY++;

                            removeTile(bomb.get_posX(), bomb.get_posY());

                            //Si ha llegado hasta abajo
                            if (bombForce == 0)
                                bomb = null;

                                //Si me voy a chocar con un edificio
                            else if (_tileMap[bombPosY][bombPosX] != null) {
                                addScore(POINTS_BY_FLOOR);
                                bomb = createTile(_gameManager.Sprites[GameManager.SpriteColor.GREEN.ordinal()][COLLISION2], bombPosX, bombPosY);
                                bombForce--;
                            }

                            //Si se va a chocar contra el suelo
                            else if (bombPosY >= INITIAL_BUILDING_POS_Y)
                                bomb = null;

                                //Si sigue cayendo
                            else
                                bomb = createTile(_gameManager.Sprites[GameManager.SpriteColor.LIGHT_GREEN.ordinal()][BOMB], bombPosX, bombPosY);
                        }
                    }

                }
                break;

                case LOSE: {

                    if (collisionSprite % 3 == 0)
                        createTile(_gameManager.Sprites[GameManager.SpriteColor.GREEN.ordinal()][COLLISION1], posX, posY);
                    else if (collisionSprite % 3 == 1)
                        createTile(_gameManager.Sprites[GameManager.SpriteColor.GREEN.ordinal()][COLLISION2], posX, posY);
                    else
                        createTile(_gameManager.Sprites[GameManager.SpriteColor.GREEN.ordinal()][COLLISION3], posX, posY);

                    collisionSprite++;

                    if (collisionSprite == DEAD_TICKS)
                        _gameManager.Game.setGameState(new GameOverGameState(_gameManager));

                }
                break;

                case WIN: {


                    winTicksCount++;
                    if (winTicksCount == WIN_TICKS)
                        setState(BombitasState.CINEMATIC);

                }
                break;
            }
        }
    }

    private void addScore(int points) {
        _gameManager.Score += points;
        setText(Integer.toString(_gameManager.Score), 7, _rows-1, GameManager.SpriteColor.WHITE);
    }
}
