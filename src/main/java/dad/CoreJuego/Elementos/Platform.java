package dad.CoreJuego.Elementos;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Platform extends Entity {

	private Image image;
	private Body body;

	public Platform(Game game, int x, int y, Image image) {
		super(game);
		this.image = image;
		this.x = x;
		this.y = y;
		this.width = (float) image.getWidth();
		this.height = (float) image.getHeight();
		initBody(getGame().getPhysics().getWorld());
	}

	@Override
	public void render(GraphicsContext gc) {
		gc.drawImage(image, x, y);
	}

	@Override
	public void update(float timeDifference) {
		// do nothing
	}

	@Override
	protected void initBody(World world) {

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.STATIC;
		bodyDef.position.set(x, y);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2f, height / 2f);

		body = world.createBody(bodyDef);
		body.createFixture(shape, 0.0f);
		body.setUserData(this);
		
	}

}
