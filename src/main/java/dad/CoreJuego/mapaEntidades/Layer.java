package dad.CoreJuego.mapaEntidades;

import java.util.ArrayList;
import java.util.List;

import dad.CoreJuego.Elementos.Entity;
import dad.CoreJuego.Elementos.Game;
import javafx.scene.canvas.GraphicsContext;

/**
 * Clase abstracta que extiende de Entity encargada de manejar todas las entidadas creadas
 * 
 * @author Iván Durán Araya
 *
 */

public abstract class Layer extends Entity {

	private String name;
	private List<Entity> entities;
	
	/**
	 * Constructor que guarda las entidades
	 * @param game Clase game instanciado desde super
	 * @param name Parametro name que pide el nombre del layer sobre el que se quiere trabajar
	 */
	
	public Layer(Game game, String name) {
		super(game);
		this.name = name;
		this.entities = new ArrayList<>();
	}
	
	/**
	 * Metodo para devolver las entidades de la lista de entidades
	 * @return Retorna las entidades de la lista entities
	 */
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	/**
	 * Metodo que retorna el nombre de la entidad
	 * @return Devuelve el nombre de la entidad
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Metodo que dibuja todas las entidades de la lista entities
	 */
	@Override
	public void render(GraphicsContext gc) {
		entities.forEach(entity -> entity.render(gc));
	}
	
	/**
	 * Metodo que actualiza todas las propiedades físicas de las entidades de la lista entities
	 */
	@Override
	public void update(float timeDifference) {
		entities.forEach(entity -> entity.update(timeDifference));
	}

}