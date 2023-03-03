package dad.CoreJuego.Elementos;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class AnimBundleBanana extends Entity {

	private Image image;
	private Body body;

	public AnimBundleBanana(Game game, int x, int y, Image image) {
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
		gc.drawImage(image, x - Platform.xStatic , y);
	}

	@Override
	public void update(float timeDifference) {
		Vec2 vector = new Vec2((x - Platform.xStatic) / scale, y / scale);
		body.setTransform(vector, 0);
		//System.out.println(vector);
		
	}

	@Override
	protected void initBody(World world) {

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.STATIC;
		//bodyDef.position.set(x / scale, y / scale);
		bodyDef.position.set(x /scale, y / scale);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox((width / scale) / 2f, (height / scale) / 2f);

		body = world.createBody(bodyDef);
		body.createFixture(shape, 0.0f);
		body.setUserData(this);
		
	}

}
