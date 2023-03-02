package dad.CoreJuego.Elementos;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

/**
 * 
 * Clase que referencias tanto a las fisicas como al mundo de Jbox2d empleado en la creacion de todo el proyecto
 * 
 * @author Gabriel
 *
 */

public class Physics {

	private Vec2 gravity = new Vec2(0, 5.8f);
//	private Vec2 gravity = new Vec2(0, 9.8f);
	private World world = new World(gravity);

	private float physicsTime = 0.0f;
	private float physicsTimeStep = 1.0f / 60.0f;
	private int velocityIterations = 8;
	private int positionIteration = 3;

	/**
	 * metodo que actualiza el mundo a la par que lo hace el canva en @see Game#render(javafx.scene.canvas.GraphicsContext)
	 * 
	 * @param dt float que es el frame actual en el mundo
	 */
	
	public void update(float dt) {

		physicsTime += dt;
		if (physicsTime >= 0) {
			physicsTime -= physicsTimeStep;
			world.step(0.08f, velocityIterations, positionIteration);
		}

	}

	/**
	 * metodo setter usado para cambiar la gravedad en caso de necesitarlo
	 * 
	 * @param gravity gravedad que recibe el mundo
	 */
	
	public void setGravity(Vec2 gravity) {
		this.gravity = gravity;
	}

	/**
	 * metodo getter usado para obtener la gravedad actual de mundo 
	 * 
	 * @return gravity que es la gravedad actual de la fisica
	 */
	
	public Vec2 getGravity() {
		return gravity;
	}

	/**
	 * metodo que retorna el mundo de Jbox2d 
	 * 
	 * @return world mundo generado en la clase {@link #Physics()}
	 */
	
	public World getWorld() {
		return world;
	}

}