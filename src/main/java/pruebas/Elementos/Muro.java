package pruebas.Elementos;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Muro extends Entity {
	
	private Body body; 

	public Muro(Game game, float x, float y, float width, float height) {
		super(game);
		
		this.x = x;
		this.y = y;

		this.width = width;
		this.height = height;
		
		initBody(game.getPhysics().getWorld());
	}
	
	public void render(GraphicsContext gc) {
				
		gc.setFill(Color.GREEN);
		gc.fillRect(x, y, width, height);
		
	}
	
	@Override
	public void update(float timeDifference) {
		x = body.getPosition().x;
		y = body.getPosition().y;
	}

	@Override
	protected void initBody(World world) {
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.STATIC;
		bodyDef.position.set(x, y);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width * 2.0f, height * 2.0f);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.friction = 0.1f;
		
		body = world.createBody(bodyDef);
		body.createFixture(fd);		
		body.setUserData(this);
	}

}
