package es.ucm.gdv.bombitasandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import es.ucm.gdv.android.engine.AndroidGame;
import es.ucm.gdv.bombitas.GameManager;

/**
 * Punto de entrada para Android
 */
public class BombitasActivity extends AppCompatActivity {

    private AndroidGame _androidGame;

    /*
     * Se le llama al inicio de la ejecución.
     * Crea el proceso.
     * Carga recursos.
     * Inicia la ventana.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Crea el motor
        _androidGame = new AndroidGame(this);

        //Crea el juego
        new GameManager(_androidGame);
    }

    /*
    Se le llama cuando la aplicación pasa a primer plano.
    Reanuda el bucle de juego.
    */
    @Override
    protected void onResume() {
        super.onResume();
        _androidGame.resume();
    }

    /*
    Se le llama cuando la aplicación pasa a segundo plano.
    Pausa el bucle de juego.
     */
    @Override
    protected void onPause() {
        super.onPause();
        _androidGame.pause();
    }

}
