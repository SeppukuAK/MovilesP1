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

30-10.

Hay que hacer un modulo externo para cada una de las plataformas.

Lanzar una excepcion si no carga una imagen

En el caso del boton habia que implementar una interfaz.
En eventos de pulsacion tambien (en java)

Hay dos:
MouseListener. Recibe notificaciones cuando se pulsa en la ventana o cuando se suelta. Cuando el raton entra en la ventana o cuando sale

MouseMovement Listener. Recibe notificaciones cuando el raton se mueve estando dentro de la ventana

Son interfaces que tenemos que implementar

Hay que suscribirse a los eventos y reaccionar

En android los eventos se realizan sobre las vistas, no sobre las actividades.


La lista de los eventos la pide la logica.

La plataforma es la que añade los eventos a la lista

Añadir eventos a la lista se hace una hebra diferente

El uso de la lista se tiene que hacer en exclusion mutua.

Hay que utilizar monitores. Metodos en los que solo hay una hebra ejecutandose, esperan a que el otro acabe si quieren acceder. Funcionan como variables cond
Poniendo syncronized

class MouseInput implements las2interfaces
{
	public syncronized void onEvent()
	{
		añade un evento a la lista
	}

	public syncronized List<> getTouchEvents()
	{
		copiar la lista y la devolvemos
	}


}

Hay que crear un Pool de eventos para ahorrarse news

Un metodo sincronizado tiene que ser pequeño. 

Se puede hacer en un trozo de codigo con 
syncronized(this)
{

}

Esto hay que hacerlo igual en adroid y en pc

Hay que pasarle la ventana en el cosntructor de Input

Lo del estado es lo similar al pinchaGlobos

Va a haber diferentes estados

Se deberia hacer un estado primero de carga

        <!--- android:theme="@style/AppTheme" -->
