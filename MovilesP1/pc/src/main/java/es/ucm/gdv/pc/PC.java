package es.ucm.gdv.pc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.swing.JFrame;

public class PC extends JFrame {
    Image _logo;    //Objeto que guarda la imagen

    double _x = 0;    //Posición del objeto
    double _incX = 500;    //Velocidad del objeto

    int _imageWidth;    //Ancho de la imagen

    public PC(String title) {
        super(title);    //Llama a la constructora del padre
    }

    public void init() {
        //Inicialización ventana JFrame
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //Le decimos que cuando se de a la 'x' se cierre la aplicacion

        //Carga de recursos
        try {
            _logo = javax.imageio.ImageIO.read(new java.io.File("pc/src/main/assets/java.png"));
            _imageWidth = _logo.getWidth(null);
        } catch (IOException ioe) {
            System.out.println(ioe);    //En condiciones normales debemos salir de manera amigable
        }

		/*
		//Aplicamos una disposición a la ventana: 3 filas y 1 columna
		setLayout(new java.awt.GridLayout(3,1));

		JButton boton = new JButton("Pulsame");
		add(boton);		//Hay que añadir el botón a la ventana

		//Esto para android también funciona. Hay que habilitarla explicitamente
		boton.addActionListener((ActionEvent e) -> System.out.println("TURURU"));
		*/
    }

    public void update(double elapsedTime) {
        _x += _incX * elapsedTime;

        //Si choca por la izquierda
        if (_x < 0) {
            _x = -_x;    //Si x=-3 -> x=3
            _incX *= -1;
        }

        //Si choca por la derecha
        else if (_x + _imageWidth > getWidth()) {
            _x = 2 * (getWidth() - _imageWidth) - _x;
            _incX *= -1;
        }
    }

    public void render(Graphics g)    //Siempre g es una instancia de graphics2d, podemos hacer un casteo para utilizar funciones mas guays
    {
        //super.paint(g);

        //Borramos anterior
        g.setColor(Color.blue); //Permite dar color
        g.fillRect(0, 0, getWidth(), getHeight());

        //Dibujamos estado actual
        g.drawImage(_logo, (int) _x, 100, null);

        //https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics.html
        //g.setColor(new Color(255,0,0,128)); //Permite dar color con alpha

		/*
		try
		{
			Thread.sleep(16);
		}
		catch (Exception e)
		{
		}
		*/

        //repaint();
    }

    public static void main(String[] args) {
        //Creación de ventana - Crea un nuevo hilo
        PC ventana = new PC("Paint");
        ventana.init();
        ventana.setIgnoreRepaint(true);        //Anulamos que se llame solo a pintar la ventana
        ventana.setVisible(true);            //Mostramos la ventana

        //Creación del buffer strategy
        int intentos = 100;
        while (intentos-- > 0) {
            try {
                //Le damos un strategy al buffer
                ventana.createBufferStrategy(2);    //2 -> 2 Buffers
            } catch (Exception e) {

            }
        }
        if (intentos == 0) {
            System.err.println("NO HA CARGADO BIEN EL DOBLE BUFFER ARRIBA ESPAÑA");
            System.exit(-1);
        }

        BufferStrategy strategy = ventana.getBufferStrategy();    //Objeto con el que pintamos

        long lastFrameTime = System.nanoTime();        //Cuanto tiempo ha pasado desde el momento en que se ha lanzado medido en nanosegundos

        //Bucle principal de juego
        while (true) {
            long currentTime = System.nanoTime();    //Hora actual
            double elapsedTime = (double) (currentTime - lastFrameTime) / 1E09;
            lastFrameTime = currentTime;

            //TODO: Input

            //Lógica
            ventana.update(elapsedTime);

            //Renderizado
            do {
                do {
                    Graphics graphics = strategy.getDrawGraphics();
                    try {
                        ventana.render(graphics);
                    } finally    //Finally -> Haga lo que haga, pasa.
                    {
                        graphics.dispose();
                    }

                } while (strategy.contentsRestored());

                strategy.show();    //Da la vuelta al buffer

            } while (strategy.contentsLost());

        }

        //System.out.println("Se termina el main");
    }
}
