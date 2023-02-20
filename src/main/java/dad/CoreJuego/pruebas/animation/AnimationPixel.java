package dad.CoreJuego.pruebas.animation;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class AnimationPixel {
	
	private List<Image> frames = new ArrayList<Image>();
	private String nombreImagen;
	private int ancho;
	private int alto;
	private int cantFrames;
	
	private static final float NANO_TO_SECONDS = 1000000000f;
	private float duration;
	private long timeAcc = 0;
	private int counter = 0;
	
	public AnimationPixel(float speedA, String n, int anch, int alt, int cantF) {
		this.nombreImagen = n;
		this.ancho = anch;
		this.alto = alt;
		this.cantFrames = cantF;
		this.duration = speedA;
		
		meterFrames(n, anch, alt, cantF);
	}
	
	private void meterFrames(String n, int anch, int alt, int cantF) {
		int i = 0;
		Image imagen = new Image(n);
		while (i < cantF) {
			PixelReader reader = imagen.getPixelReader();
			Image imagen2 = new WritableImage(reader, i*anch, 0, anch, alt);
	    	frames.add(imagen2);
	    	i++;
		}
	}
	
	public List<Image> getFrames() {
		return frames;
	}
	
	public Image getCurrentFrame() {
		return frames.get(counter);
	}
	
	public void reset() {
		this.counter = 0;
	}
	
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
