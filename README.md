MovilesP1

Android Studio. 2 builds: PC y Android
Modulo (java) = proyecto (visual)
Se necesita un tercer modulo que tiene la implementación de la tecnologia.
Se necesita un cuarto modulo que tiene la logica del juego
Seguramente haya mas

File->new Module
Java library es para pc

No tiene para recursos. Hay que buscar otro modulo

Seleccionamos modulo que necesita de otro:
Clickderecho modulo app: configurar opciones de modulo
Dependencies:
le damos al +:
module dependency:
engine

En image a lo mejor tienes que meter un load
Pero las imagenes son inmutables

Imagen en java esta en java.awt.Image
Imagen en Android es android.graphics.Bitmap

lA IMPMENETACION TIENE UN ATRIBUTO DE ESE TIPO DE ARRIBA, QUE LO RECIBE EN EL COSNTRUCTOR PUES NOSE

Graphics
Image newImage(String name).

Hay que hacer el patron factory

void drawImage(Image i,x,y);
En PC hay movidas con el graphics.
Porque en cada frame es 1.
No lo puedo recibir en el constructor.

El bucle principal hay que reprogramarlo para ambas plataformas

PCGraphics:
-StartFrame

PCGraphics tenddra un atributo graphics
AGraphics tendrá un atributos canvas
Ambos se pasan en StartFrame
En el drawImage se utiliza ese atributo.
