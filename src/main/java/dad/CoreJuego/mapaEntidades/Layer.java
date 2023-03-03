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
	 * Clase que guarda las entidades
	 * @param game Parametro game instanciado desde super
	 * @param name Parametro name que pide el nombre del layer sobre el que se quiere trabajar
	 */
	
	public Layer(Game game, String name) {
		super(game);
		this.name = name;
		this.entities = new ArrayList<>();
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public void render(GraphicsContext gc) {
		entities.forEach(entity -> entity.render(gc));
	}
	
	@Override
	public void update(float timeDifference) {
		entities.forEach(entity -> entity.update(timeDifference));
	}

}