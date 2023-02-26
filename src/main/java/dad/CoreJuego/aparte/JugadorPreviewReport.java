package dad.CoreJuego.aparte;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dad.CoreJuego.Controllers.menu.Player;

public class JugadorPreviewReport {
	public static List<Player> getJugadorPreview() {
		Player jugador = new Player("1", "Pepe", 200);
		return new ArrayList<Player>(Arrays.asList(jugador));
	}
	
}
