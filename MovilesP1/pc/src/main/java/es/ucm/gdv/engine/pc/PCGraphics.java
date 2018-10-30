package es.ucm.gdv.engine.pc;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JFrame;
import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Image;

public class PCGraphics implements Graphics {

    private JFrame _jFrame;
    private java.awt.Graphics _graphics;

    PCGraphics(JFrame jFrame) {
        _jFrame = jFrame;

        _jFrame.setIgnoreRepaint(true);		//Anulamos que se llame solo a pintar la ventana
        _jFrame.setVisible(true);			//Mostramos la ventana

        //Creación del buffer strategy
        int intentos = 100;
        while (intentos-- > 0)
        {
            try {
                //Le damos un strategy al buffer
                _jFrame.createBufferStrategy(2);	//2 -> 2 Buffers
            }
            catch(Exception e)
            {

            }
        }
        if (intentos == 0)
        {
            System.err.println("NO HA CARGADO BIEN EL DOBLE BUFFER");
            System.exit(-1);
        }

    }

    @Override
    public Image newImage(String name) {
        PCImage pcImage = null;
        //Carga de recursos
        try{
            java.awt.Image javaImage = javax.imageio.ImageIO.read(new java.io.File(name));
            pcImage = new PCImage(javaImage);
        }
        catch(IOException ioe)
        {
            System.out.println(ioe);	//En condiciones normales debemos salir de manera amigable
        }
        return pcImage;
    }

    public void startFrame(java.awt.Graphics graphics)
    {
        _graphics = graphics;
    }

    @Override
    public void drawImage(Image image, int x, int y) {
        if (image != null)
        {
            PCImage pcImage = (PCImage) image;
            java.awt.Image javaImage = pcImage.getJavaImage();

            _graphics.drawImage(javaImage,x,y,null);
        }
    }

    @Override
    public void clear(int rgb) {
        Color color = new Color(rgb);
        _graphics.setColor(color); //Permite dar color
        _graphics.fillRect(0,0,getWidth(),getHeight());
    }

    @Override
    public int getWidth() {
        return _jFrame.getWidth();
    }

    @Override
    public int getHeight() {
        return _jFrame.getHeight();
    }

    public JFrame getJFrame() {
        return _jFrame;
    }
}
