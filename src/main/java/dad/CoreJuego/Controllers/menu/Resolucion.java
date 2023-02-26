package dad.CoreJuego.Controllers.menu;

/**
 * 
 * clase Enumerada con todas las resoluciones posibles 
 * 
 * @author David Alejandro
 *
 */

public enum Resolucion{
	res1080x720p(1080, 720),
	res1152x768p(1152, 768),
	res1280x720p(1280, 720),
	res1280x800p(1280, 800),
	res1334x750p(1334, 750),
	res1366x768p(1366, 768),
	res1400x1050p(1400, 1050),
	res1400x1400p(1400, 1400),
	res1600x768p(1600, 768),
	res1600x900p(1600, 900),
	res1800x1200p(1800, 1200),
	res1920x1080p(1920, 1080);
	
	int width;
	int height;
	
	/**
	 * metodo que reibe la altura y anchura de la pantalla
	 * 
	 * @param w int que reibe la anchura
	 * @param h int que recibe la altura
	 */
	
	private Resolucion(int w, int h){
		this.width = w;
		this.height = h;
	}

	/**
	 * metodo getter que devuelve la anchura
	 * 
	 * @return width retorna la anchura
	 */
	
	public int getWidth() {
		return width;
	}

	/**
	 * metodo getter que devuelve la altura
	 * 
	 * @return height retorna la altura
	 */
	
	public int getHeight() {
		return height;
	}

	/**
	 * metodo toString que imprime la resolucion actual 
	 * 
	 */
	
	@Override
	public String toString() {
		return width + " x " + height;
	}
}
