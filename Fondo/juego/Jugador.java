package juego;

import javafx.scene.canvas.GraphicsContext;

public class Jugador extends ObjetoJuego {

	public Jugador(int x, int y, String nombreImagen, int velocidad) {
		super(x, y, nombreImagen, velocidad);

	}

	@Override
	public void pintar(GraphicsContext graficos) {
		graficos.drawImage(Juego.imagenes.get(nombreImagen), x, y);

	}

	@Override
	public void mover() {
		if (x > 2000)
			x = -80;

		if (Juego.derecha)
			x += velocidad;

		if (Juego.izquierda)
			x -= velocidad;

		if (Juego.arriba)
			y -= velocidad;

		if (Juego.abajo)
			y += velocidad;

	}

}
