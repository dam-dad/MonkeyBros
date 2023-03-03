package dad.Api.GlobalStat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import dad.CoreJuego.Controllers.menu.RootMenuController;
import io.github.fvarrui.globalstats.GlobalStats;
import io.github.fvarrui.globalstats.model.Rank;
import io.github.fvarrui.globalstats.model.Section;
import io.github.fvarrui.globalstats.model.Stats;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * Clase que contiene todo lo necesario para utilizar la Api de GlobalStats
 * 
 * @author Gabriel
 *
 */

public class GlobalStat {

	String clientId = "nL8l9XT08r7gzzsaPC3f2Dhyu1pjqOWOA4aJ23Ja",
			clientSecret = "Rq4qTegwhwyUN6pPvJfkF3UJeEMinWMenRFg5WMn";
	final String token;
	GlobalStats client;
	Stats stats;

	/**
	 * Constructor que conecta el cliente y le da el Token de acceso
	 * 
	 * @throws Exception lanza una expcecion en caso de fallar algo impidieno
	 *                   continuar
	 */
	public GlobalStat() throws Exception {
		client = new GlobalStats(clientId, clientSecret);
		token = client.getAccessToken();
//		System.out.println(token);
	}

	/**
	 * Cuando se quiera registrar un nuevo usuario
	 * 
	 * @param nombre
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("serial")
	public Stats createUser(String nombre) throws Exception {
		stats = client.createStats(nombre, new HashMap<String, Object>() {
			{

				put("Score", 0);

			}
		});

		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(RootMenuController.RUTA_PLAYER_IDS));
			prop.setProperty(stats.getId(), prop.size() + 1 + "");
			prop.store(new FileOutputStream(RootMenuController.RUTA_PLAYER_IDS), "");
		} catch (FileNotFoundException e) {
			if (!RootMenuController.RUTA_PLAYERS_FOLDER.exists()) {
				RootMenuController.RUTA_PLAYERS_FOLDER.mkdir();
			}
			prop.setProperty(stats.getId(), prop.size() + 1 + "");
			prop.store(new FileOutputStream(RootMenuController.RUTA_PLAYER_IDS), "");
		} catch (IOException e1) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error");
			alerta.setHeaderText("No se ha podido cargar el registro de los jugadores guardados.");
			alerta.setContentText(e1.getMessage());
			alerta.show();
		}
		System.out.println(stats);
		return stats;
	}

	@SuppressWarnings("serial")
	public void updateUser(String s) throws Exception {
		Stats stats = client.updateStats(s, new HashMap<String, Object>() {
			{

				put("Score", "+20");

			}
		});
		System.out.println(stats);
	}

	@SuppressWarnings("serial")
	public void resetUserScore(String s) throws Exception {
		int points = client.getStatsSection(s, "Score").getUserRank().getValue();
		Stats stats = client.updateStats(s, new HashMap<String, Object>() {
			{

				put("Score", "-" + points);

			}
		});
		System.out.println(stats);
	}

	public void getUserStadistic(String id) throws Exception {
		Stats stats = client.getStats(id);
		System.out.println(stats);
	}

	public Section getUserSection(String id) throws Exception {
		Section section = client.getStatsSection(id, "Score");
		// System.out.println(section + " section");
		return section;
	}

	public List<Rank> getLeaderBoard() throws Exception {
		List<Rank> leaderB = client.getLeaderboard("Score", 10); // Retorna los top 10 de la lista
		System.out.println(leaderB + "test");
		return leaderB;
	}

}
