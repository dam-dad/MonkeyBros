package pruebas.Elementos;


import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;


public class Physics {

	private Vec2 gravity = new Vec2(0, 9.8f);
	private World world = new World(gravity);

	private float physicsTime = 0.0f;
	private float physicsTimeStep = 1.0f / 60.0f;
//	private int velocityIterations = 8;
//	private int positionIteration = 3;
	int velocityIterations = 8;
	int positionIteration = 3;


	public void update(float dt) {

		physicsTime += dt;
		if (physicsTime >= 0) {
			physicsTime -= physicsTimeStep;
			world.step(physicsTime, velocityIterations, positionIteration);
		}

	}

	public void setGravity(Vec2 gravity) {
		this.gravity = gravity;
	}

	public Vec2 getGravity() {
		return gravity;
	}

	public World getWorld() {
		return world;
	}



}
