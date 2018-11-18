package es.ucm.gdv.android.engine;

import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import es.ucm.gdv.engine.Input;
import es.ucm.gdv.engine.TouchEvent;

public class AndroidInput implements View.OnTouchListener, Input {

    private List<TouchEvent> touchEventList;

    public AndroidInput(SurfaceView ventana) {
        touchEventList = new ArrayList<>();
        ventana.setOnTouchListener(this);
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        List<TouchEvent> listAux;

        synchronized (this) {
            listAux = new ArrayList<>(touchEventList);
            touchEventList.clear();
        }
        return listAux;
    }

    /**
     * Es llamado cada vez que pasa algo
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            TouchEvent touchEvent = new TouchEvent((int) event.getX(), (int) event.getY());
            synchronized (this) {
                touchEventList.add((touchEvent)); //Añadimos el evento a la lista
            }
            return true;
        }
      return false;
    }

}
