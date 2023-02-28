package dad.CoreJuego.animation;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

/**
 * 
 * Clase que se encarga de gestionar las animaciones
 * 
 * @author Ivan
 *
 */

public class Animation {
	
	private List<Image> frames = new ArrayList<Image>();
	
	private String imageName;
	private int imageWeidth;
	private int alto;
	private int framesAmo;

	private float duration;
	private long timeAcc = 0;
	private int counter = 0;
	
	/**
	 * Constructor que guarda los atributos necesarios para la clase y llamada al metodo @see {@link #putFrames()}
	 * 
	 * @param speedA duracion de la animacion
	 * @param n  nombre de la Imagen
	 * @param anch ancho de la Imagen
	 * @param alt altura de la imagen
	 * @param cantF cantidad de fotogramas
	 */
	
	public Animation(float speedA, String n, int anch, int alt, int cantF) {
		this.imageName = n;
		this.imageWeidth = anch;
		this.alto = alt;
		this.framesAmo = cantF;
		this.duration = speedA;
		
		putFrames();
	}
	
	/**
	 * Metodo que recorta una tira de imagenes segun los parametros pasados y añade la imagen de la tira a una lista de imagenes
	 * 
	 */
	
	private void putFrames() {
		int i = 0;
		Image imagen = new Image(imageName);
		while (i < framesAmo) {
			PixelReader reader = imagen.getPixelReader();
			Image imagen2 = new WritableImage(reader, i*imageWeidth, 0, imageWeidth, alto);
	    	frames.add(imagen2);
	    	i++;
		}
	}
	
	/**
	 * metodo que devuelve una lista con las imagenes actuales
	 * 
	 * @return frames lista de Imagenes
	 */
	public List<Image> getFrames() {
		return frames;
	}
	
	/**
	 * metodo que devuelve la imagen en ese segundo
	 * 
	 * @return frame imagen en ese segundo
	 */
	
	public Image getCurrentFrame() {
		return frames.get(counter);
	}
	
	/**
	 * metodo que resetea el contador
	 * 
	 */
	public void reset() {
		this.counter = 0;
	}
	
	/**
	 * metodo que calcula la diferencia de tiempo y guarda el tiempo actual
	 * 
	 * @param timeDifference recibe la diferencia de tiempo
	 */
	public void update(float timeDifference) {
		if (timeAcc > duration) {
			counter++;
			if (counter >= frames.size()) {
				counter = 0;
			}
			timeAcc = 0;
		}
		timeAcc += timeDifference;
	}

}
