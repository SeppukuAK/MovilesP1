package es.ucm.gdv.engine.pc;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import es.ucm.gdv.engine.Input;
import es.ucm.gdv.engine.TouchEvent;

public class PCInput implements MouseMotionListener, MouseListener, Input {

    private List<TouchEvent> touchEventList;

    public PCInput(JFrame ventana) {
        touchEventList = new ArrayList<>();
        ventana.addMouseListener(this);
        ventana.addMouseMotionListener(this);
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

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        TouchEvent touchEvent = new TouchEvent(mouseEvent.getX(), mouseEvent.getY(), TouchEvent.TouchType.TOUCH, mouseEvent.getButton()-1);
        synchronized (this) {
            touchEventList.add((touchEvent)); //Añadimos el evento a la lista
        }

    }


    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        TouchEvent touchEvent = new TouchEvent(mouseEvent.getX(), mouseEvent.getY(), TouchEvent.TouchType.RELEASE, mouseEvent.getButton()-1);
        synchronized (this) {
            touchEventList.add((touchEvent)); //Añadimos el evento a la lista
        }

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        TouchEvent touchEvent = new TouchEvent(mouseEvent.getX(), mouseEvent.getY(), TouchEvent.TouchType.MOVEMENT, mouseEvent.getButton()-1);
        synchronized (this) {
            touchEventList.add((touchEvent)); //Añadimos el evento a la lista
        }

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }

}
