package es.ucm.gdv.bombitas.GameState;

import java.util.List;
import java.util.Random;

import es.ucm.gdv.bombitas.GameManager;
import es.ucm.gdv.bombitas.Tile;
import es.ucm.gdv.engine.TouchEvent;

/**
 * Clase que gestiona la lógica del juego del Bombardero
 */
public class BombitasGameState extends TileMapGameState {

    /**
     * Enumerado para el estado de la partida
     */
    private enum BombitasState {
        CINEMATIC, PLAY, WIN, LOSE
    }

    //region Constantes
    //Constantes con los números ASCII de los sprites del juego
    private final int ROOF = 244;
    private final int FLOOR = 143;
    private final int PLANELEFT = 241;
    private final int PLANERIGHT = 242;
    private final int BOMB = 252;
    private final int COLLISION1 = 188;
    private final int COLLISION2 = 238;
    private final int COLLISION3 = 253;

    //Bomba
    private final int MIN_BOMB_FORCE = 2;
    private final int RANDOM_BOMB_FORCE = 2;

    //Edificios
    private final int RANDOM_BUILDING_HEIGHT = 7;
    private final int BUILDINGS = 11;
    private final int INITIAL_BUILDING_POS_X = 4;
    private final int INITIAL_BUILDING_POS_Y = 21;
    private final int INITIAL_COLOR = GameManager.SpriteColor.PURPLE.ordinal();

    //Avión
    private final int INITIAL_PLANE_POS_X = 2;
    private final int INITIAL_PLANE_POS_Y = 1;
    private final int FINAL_PLANE_POS_X = 18;

    //Puntos
    private final int POINTS_BY_FLOOR = 5;
    private final int EXTRA_POINTS = 100;

    //Velocidad de estados
    private final float GAME_VELOCITY = 30f;
    private final float CINEMATIC_VELOCITY = 30f;
    private final int WIN_TICKS = 5;
    private final int DEAD_TICKS = 15;
    //endregion/

    private BombitasState _bombitasState;     //Estado del juego

    //Tiles
    private Tile planeRight;
    private Tile planeLeft;
    private Tile bomb;

    //Frame-rate
    private float nextFrame;    //Auxiliar para ir contando
    private float frameRate;    //frames por segundo
    private float time;         //Contador de tiempo

    //Utilizado para las animaciones
    private int posX;
    private int posY;

    private int bombForce;          //Cuanto daño hace la bomba
    private int ticksCount;         //Contador de ticks utilizado para animaciones de win y lose

    private int[] buildingHeight;   //Guarda la altura de los edificio sin contar el tejado

    private Random _random;    //Semilla para la generación de randoms

    public BombitasGameState(GameManager gameManager) {
        super(gameManager, 25, 20);
        init();
    }

    protected void init() {
        _random = new Random();
        _gameManager.Score = 0;

        //Inicialización frame-rate
        time = 0;
        nextFrame = 0;

        //Empieza con la cinematica de los edificios
        setState(BombitasState.CINEMATIC);
    }

    /**
     * Cambia a un estado de la partida
     * @param bombitasState
     */
    private void setState(BombitasState bombitasState) {
        switch (bombitasState) {
            case CINEMATIC: {
                setCinematic();
                break;
            }
            case PLAY: {
                setPlay();
                break;
            }

            case WIN: {
              setWin();
                break;
            }

            case LOSE: {
              setLose();
                break;
            }
        }
    }

    /**
     * Genera alturas aleatorias para cada edificio, establece el frame-rate correspondiente y situa las variables para dibujar en el Update
     */
    private void setCinematic()
    {
        _bombitasState = BombitasState.CINEMATIC;

        //Creación de alturas de los edificios aleatorias
        buildingHeight = new int[BUILDINGS];
        for (int i = 0; i < buildingHeight.length; i++)
            buildingHeight[i] = _random.nextInt(RANDOM_BUILDING_HEIGHT + 1) + (5 - _gameManager.Level);

        //Velocidad de animación de los edificios
        frameRate = 1 / CINEMATIC_VELOCITY;

        //Posición donde empieza a pintar la animación
        posX = INITIAL_BUILDING_POS_X;
        posY = INITIAL_BUILDING_POS_Y;
    }

    /**
     * Se muestra el UI con la puntuación, se establece el frame-rate.
     * Se establece la posición inicial del avioni
     */
    private void setPlay()
    {
        _bombitasState = BombitasState.PLAY;

        //UI
        setText("____________________", 0, _rows - 2, GameManager.SpriteColor.WHITE);
        setText("PUNTOS", 0, _rows - 1, GameManager.SpriteColor.RED);
        setText(Integer.toString(_gameManager.Score), 7, _rows - 1, GameManager.SpriteColor.WHITE);
        setText("MAX", 12, _rows - 1, GameManager.SpriteColor.RED);
        setText(Integer.toString(_gameManager.MaxScore), 16, _rows - 1, GameManager.SpriteColor.WHITE);

        frameRate = (float) (_gameManager.Velocity + 1) / GAME_VELOCITY;

        //Avión
        planeRight = createTile(_gameManager.Sprites[GameManager.SpriteColor.LIGHT_GREEN.ordinal()][PLANERIGHT], INITIAL_PLANE_POS_X, INITIAL_PLANE_POS_Y);
        planeLeft = createTile(_gameManager.Sprites[GameManager.SpriteColor.LIGHT_GREEN.ordinal()][PLANELEFT], INITIAL_PLANE_POS_X -1, INITIAL_PLANE_POS_Y);

        //Bomba
        bomb = null;
        bombForce = 0;
    }

    /**
     * Inicia el contador de ticks, suma puntos y actualiza la dificultad
     */
    private void setWin()
    {
        _bombitasState = BombitasState.WIN;

        removeTile(planeRight.get_posX(), planeRight.get_posY());
        planeRight = null;
        planeLeft = null;

        _gameManager.Score += EXTRA_POINTS;

        ticksCount = 0;

        if (_gameManager.Level > 0)
            _gameManager.Level--;

        if (_gameManager.Velocity > 0)
            _gameManager.Velocity--;
    }

    /**
     * Inicia el contador de ticks y cambia al frameRate correspondiente
     */
    private void setLose()
    {
        _bombitasState = BombitasState.LOSE;

        //Utilizamos el tile como explosión
        removeTile(planeRight.get_posX(), planeRight.get_posY());
        planeRight = createTile(_gameManager.Sprites[GameManager.SpriteColor.GREEN.ordinal()][COLLISION1], planeRight.get_posX() + 1, planeRight.get_posY());
        planeLeft = null;

        if (bomb != null)
        {
            removeTile(bomb.get_posX(), bomb.get_posY());
            bomb = null;
        }

        ticksCount = 0;
        frameRate = 1/ (CINEMATIC_VELOCITY);
    }

    /**
     * Da un paso en el estado de juego. Actualiza para el estado correspondiente
     * @param elapsedTime
     */
    @Override
    public void update(double elapsedTime) {

        time += elapsedTime;
        if (time > nextFrame) {
            nextFrame = time + frameRate;

            switch (_bombitasState) {

                case CINEMATIC: {
                    updateCinematic();
                    break;
                }

                case PLAY: {
                    updatePlay();
                    break;
                }

                case WIN: {
                    updateWin();
                    break;
                }

                case LOSE: {
                    updateLose();
                    break;
                }
            }
        }
    }

    /**
     * Hace la animación de creación de edificios y cuando termina, pasa al estado de Play
     */
    private void updateCinematic()
    {
        int buildingIndex = posX - INITIAL_BUILDING_POS_X;

        //Se sigue pintando este edificio
        if (posY > (INITIAL_BUILDING_POS_Y - buildingHeight[buildingIndex]))
        {
            createTile(_gameManager.Sprites[INITIAL_COLOR + buildingIndex][ROOF], posX, posY-1);
            createTile(_gameManager.Sprites[INITIAL_COLOR + buildingIndex][FLOOR], posX, posY);

            posY--;
        }
        //Pasa al siguiente edificio
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
    }


    private void updateBomb()
    {
        //Detección de Input
        List<TouchEvent> touchEvents = _gameManager.Game.getInput().getTouchEvents();
        if (bomb == null && planeRight.get_posY() < INITIAL_BUILDING_POS_Y)
        {
            for (TouchEvent t: touchEvents)
            {
                if (t.get_touchType() == TouchEvent.TouchType.TOUCH && t.get_ID() == TouchEvent.ButtonType.PRIMARY.ordinal()) {
                    bomb = createTile(_gameManager.Sprites[GameManager.SpriteColor.LIGHT_GREEN.ordinal()][BOMB], planeRight.get_posX(), planeRight.get_posY() + 1);
                    bombForce = _random.nextInt(RANDOM_BOMB_FORCE + 1) + MIN_BOMB_FORCE; // 2-4
                }
            }
        }

        //Actualizamos la posición de la bomba si existe
        else if (bomb != null) {
            int bombPosX = bomb.get_posX();
            int bombPosY = bomb.get_posY();
            removeTile(bomb.get_posX(), bomb.get_posY());

            bombPosY++;

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

    /**
     * Actualiza el avión y la bomba
     */
    private void updatePlay()
    {
        //UPDATE DEL AVION
        removeTile(planeLeft.get_posX(), planeLeft.get_posY()); //Borramos la cola del avion

        //Se considera la cabeza la posicion del avion
        int planePosX = planeRight.get_posX();
        int planePosY = planeRight.get_posY();

        planePosX++;

        //Si he ganado
        if (planePosX == FINAL_PLANE_POS_X && planePosY == INITIAL_BUILDING_POS_Y)
            setState(BombitasState.WIN);

        //Si he perdido
        else if (_tileMap[planePosY][planePosX] != null)
            setState(BombitasState.LOSE);

        //Sigue la partida
        else {
            //UPDATE DEL AVIONI
            //Si tengo que pasar a la siguiente altura
            if (planePosX == FINAL_PLANE_POS_X) {
                removeTile(planeRight.get_posX(), planeRight.get_posY()); //Borramos la cabeza del avion

                planePosX = INITIAL_PLANE_POS_X;
                planePosY++;
            }

            //Dibujamos el avión en su nueva posición
            planeRight = createTile(_gameManager.Sprites[GameManager.SpriteColor.LIGHT_GREEN.ordinal()][PLANERIGHT], planePosX, planePosY);
            planeLeft = createTile(_gameManager.Sprites[GameManager.SpriteColor.LIGHT_GREEN.ordinal()][PLANELEFT], planePosX - 1, planePosY);

            updateBomb();
        }
    }

    /**
     * Avanza un contador de ticks y cuando llega a WIN_TICKS, se reinicia el nivel
     */
    private void updateWin()
    {
        ticksCount++;
        if (ticksCount == WIN_TICKS)
            setState(BombitasState.CINEMATIC);
    }

    /**
     * Avanza un contador de ticks, actualiza la animación y cuando llega a DEAD_TICKS, pasa a la escena de GameOver
     */
    private void updateLose()
    {
        //Actualiza sprite
        if (ticksCount % 3 == 0)
            planeRight = createTile(_gameManager.Sprites[GameManager.SpriteColor.GREEN.ordinal()][COLLISION1], planeRight.get_posX(), planeRight.get_posY());
        else if (ticksCount % 3 == 1)
            planeRight = createTile(_gameManager.Sprites[GameManager.SpriteColor.GREEN.ordinal()][COLLISION2], planeRight.get_posX(), planeRight.get_posY());
        else
            planeRight = createTile(_gameManager.Sprites[GameManager.SpriteColor.GREEN.ordinal()][COLLISION3], planeRight.get_posX(), planeRight.get_posY());

        ticksCount++;

        if (ticksCount == DEAD_TICKS)
            _gameManager.Game.setGameState(new GameOverGameState(_gameManager));
    }


    /**
     * Añade puntuación, modificando el texto
     */
    private void addScore(int points) {
        _gameManager.Score += points;
        setText(Integer.toString(_gameManager.Score), 7, _rows-1, GameManager.SpriteColor.WHITE);
    }
}
