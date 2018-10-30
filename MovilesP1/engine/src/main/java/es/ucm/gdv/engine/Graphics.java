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

    //Devuelve el tamaño de la ventana
    int getWidth();
    int getHeight();
}
