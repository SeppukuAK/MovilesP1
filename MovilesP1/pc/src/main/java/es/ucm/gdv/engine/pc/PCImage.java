package es.ucm.gdv.engine.pc;

import es.ucm.gdv.engine.Image;

public class PCImage implements Image {

    private java.awt.Image _javaImage;

    public PCImage(java.awt.Image javaImage) {
        _javaImage = javaImage;
    }

    @Override
    public int getWidth() {
        return _javaImage.getWidth(null);
    }

    @Override
    public int getHeight() {
        return _javaImage.getHeight(null);
    }

    public java.awt.Image getJavaImage(){return _javaImage;}
}
