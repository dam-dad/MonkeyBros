package dad.CoreJuego.Controllers.menu;

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
	
	private Resolucion(int w, int h){
		this.width = w;
		this.height = h;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

//	@Override			// res prueba // res pantalla
//	public int compare(Resoluciones o1, Resoluciones o2) {
//		if(o1.getWidth() > o2.getWidth() && o1.getHeight() > o2.getHeight()) {
//			return 1;
//		} else if (o1.getWidth() < o2.getWidth() && o1.getHeight() < o2.getHeight()) {
//			return -1;
//		} else {
//			return 0;
//		}
//	}
	
	@Override
	public String toString() {
		return width + " x " + height;
	}
}
