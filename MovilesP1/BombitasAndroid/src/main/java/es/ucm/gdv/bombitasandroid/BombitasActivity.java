package es.ucm.gdv.bombitasandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import es.ucm.gdv.android.engine.AndroidGame;
import es.ucm.gdv.bombitas.GameManager;

public class BombitasActivity extends AppCompatActivity {

    AndroidGame _androidGame;
    /*
     * Se le llama al inicio de la ejecución.
     * Crea el proceso.
     * Carga recursos.
     * Inicia la ventana.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bombitas);

        _androidGame = new AndroidGame(this);
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
