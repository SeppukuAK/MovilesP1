package es.ucm.gdv.engine;

/**
 * Funcionalidades sobre la ventana de la aplicación
 */
public interface Graphics {

    //Carga una imagen en memoria a partir de su nombre
    Image newImage(String name);

    //Limpia la ventana rellenandola con el color pasado como parámetro
    void clear(int rgb);

    //Dibuja una imagen en pantalla
    //TODO: DIFERENTES METODOS (ESCALADO DE LA IMAGEN, ETC)
    void drawImage(Image image, int x, int y);

    //D1, D2 = Esquinas del rectángulo que dibujo en la ventana   /   S1, S2 = Esquinas del rectángulo que recorto de la imagen
    void drawImage(Image image, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2);

    //Devuelve el tamaño de la ventana
    int getWidth();
    int getHeight();
}
