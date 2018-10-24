MovilesP1

Android Studio. Modulo (java) = proyecto (visual)
4 Módulos:
-Java.
-Android.
-Interfaces(tecnología).
-Lógica del juego.
-Puede que haya más

Los módulos se crean:
-File->new Module
-Java library es para pc. No tiene para recursos. Hay que buscar otro modulo parece.

Para dependencias entre modulos:
-Seleccionamos modulo que necesita de otro.
-Click derecho modulo app: configurar opciones de modulo
-Dependencies:
-le damos al +:
-module dependency: engine

En image a lo mejor tienes que meter un load
Pero las imagenes son inmutables

Imagen en java esta en java.awt.Image
Imagen en Android es android.graphics.Bitmap

La implementación de Image en cada módulo recibe en la constructora el atributo Image o Bitmap

Graphics
Image newImage(String name).
Esto es patrón facatory

void drawImage(Image i,x,y);
En PC hay movidas con el graphics.
Porque en cada frame es 1.
No lo puedo recibir en el constructor.

El bucle principal hay que reprogramarlo para ambas plataformas

XGraphics:
-StartFrame()

PCGraphics tendra un atributo graphics
AGraphics tendrá un atributos canvas
Ambos se pasan en StartFrame
En el drawImage se utiliza ese atributo.
