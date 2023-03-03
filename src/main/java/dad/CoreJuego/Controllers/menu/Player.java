package dad.CoreJuego.Controllers.menu;

/**
 * 
 * Clase que contiene al jugador y sus atributos
 * 
 * @author David Alejandro
 *
 */

public class Player {
	String id;
	String name;
	int points;

	/**
	 * Constructor que recibe los parametros para crear al jugador 
	 * 
	 * @param id recibe la identificacion numerica del jugador
	 * @param name recibe el nombre que pone el jugador al iniciar la partida
	 * @param points recibe los puntos del jugador
	 */
	
	public Player(String id, String name, int points) {
		this.id = id;
		this.name = name;
		this.points = points;
	}

	/**
	 * metodo getter que retorna el identificador actual del jugador
	 * 
	 * @return id retorna el identificador actual
	 */
	
	public String getId() {
		return id;
	}

	/**
	 * metodo que actualiza el id que tiene el jugador actual
	 * 
	 * @param id recibe el id que tiene el jugador
	 */
	
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * metodo que retorna el nombre actual del jugador
	 * 
	 * @return  name retorna el nombre
	 */
	
	public String getName() {
		return name;
	}

	/**
	 * metodo setter que recibe el nombre y lo actualiza
	 * 
	 * @param name recibe el nombre del jugador 
	 */
	
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * metodo getter retorna los puntos
	 * 
	 * @return points retorna los puntos que tiene el jugador
	 */
	
	public int getPoints() {
		return points;
	}

	/**
	 * metodo que recibe los puntos actualizados
	 * 
	 * @param points recibe los puntos
	 */
	
	public void setPoints(int points) {
		this.points = points;
	}

}
