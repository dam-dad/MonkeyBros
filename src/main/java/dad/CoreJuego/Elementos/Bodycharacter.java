package dad.CoreJuego.Elementos;



import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Bodycharacter extends Entity{

	public Body body;
	FixtureDef fd;

	public Bodycharacter(Game game, float posX, float posY) {
		super(game);

		this.x = posX;
		this.y = posY;

		// variables of character size
		this.width = 10f;
		this.height = 10f;

		initBody(game.getPhysics().getWorld());
	}

	@Override
	protected void initBody(World world) {

		// The complete code snippet would look like:
		// body definition
		BodyDef bd = new BodyDef();
		bd.position.set(x, y);
		bd.type = BodyType.DYNAMIC;

		// define shape of the body.
		PolygonShape box = new PolygonShape();

		box.setAsBox(x, y);

		// define fixture of the body.
		fd = new FixtureDef();
		fd.shape = box;
		fd.friction = 20f;
		
		// create the body and add fixture to it
		body = world.createBody(bd);
		body.createFixture(fd);
		body.getLinearVelocity();
		MassData massData = new MassData();
		massData.mass = 0.5f;
		body.setMassData(massData);
		body.setUserData(this);
	}

	public FixtureDef getFd() {
		return fd;
	}
	
	 public void onCollision(Floor m) {
	    }

	
	public void render(GraphicsContext gc) {

		gc.setFill(Color.BLACK);
		gc.fillRect(x, y, width * scale, height * scale);
	}

	public void move(Vec2 vector) {
		body.applyLinearImpulse(vector, body.getWorldCenter());
	}

	@Override
	public void update(float timeDifference) {
		x = body.getPosition().x;
		y = body.getPosition().y;
	}
	
}
