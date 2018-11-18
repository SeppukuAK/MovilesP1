package es.ucm.gdv.engine.pc;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import es.ucm.gdv.engine.Input;
import es.ucm.gdv.engine.TouchEvent;

//TODO: MouseMovement
public class PCInput implements MouseListener, Input {

    private List<TouchEvent> touchEventList;

    public PCInput(JFrame ventana)
    {
        touchEventList = new ArrayList<>();
        ventana.addMouseListener(this);
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        List<TouchEvent> listAux;

        synchronized (this)
        {
            listAux = new ArrayList<>(touchEventList);
            touchEventList.clear();
        }
        return listAux;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
            TouchEvent touchEvent = new TouchEvent(mouseEvent.getX(), mouseEvent.getY());
            synchronized (this)
            {
                touchEventList.add((touchEvent)); //Añadimos el evento a la lista
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }


    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
    }
}
