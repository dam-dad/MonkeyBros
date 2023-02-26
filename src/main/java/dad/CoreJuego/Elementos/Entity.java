package dad.CoreJuego.Elementos;

import org.jbox2d.dynamics.World;

import javafx.scene.canvas.GraphicsContext;

/**
 * Se encarga de generar las diferentes entidades en el mundo
 * @author Alejandro, Gabriel
 */

public abstract  class Entity {
	private Game game;
	protected float x, y;
	protected float width, height;
	protected double scale = 5.0;
	
	/**
	 * 
	 * @param game Es la escena del juego
	 */
	public Entity(Game game) {
		this.game = game;
	}
	/**
	 * 
	 * @param scale Le pasas la escala al mundo
	 */
	public void setScale(double scale) {
		this.scale = scale;
	}
	/**
	 * 
	 * @return Devuelve la escala al mundo
	 */
	public double getScale() {
		return scale;
	}
	/**
	 * 
	 * @return Devuelve la escena del juego
	 */
	public Game getGame() {
		return game;
	}
	/**
	 * 
	 * @param gc Los gráficos generados
	 */
	public abstract void render(GraphicsContext gc);
	/**
	 * metodo usado para actualizar la posicion en los cuerpos
	 * 
	 * @param timeDifference Los fps actuales
	 */
	public abstract void update(float timeDifference);
	/**
	 * metodo usado para la creacion de los cuerpo mas las fisicas añadidas a este
	 * 
	 * @param world Le estamos pasando un mundo para que se genere las físicas
	 */
	protected abstract void initBody(World world);
}
