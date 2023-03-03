package dad.CoreJuego.Elementos;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 * Genera el fin del mundo
 * @author Gabriel
 *
 */
public class Floor extends Entity {
	
	private Body body; 
/**
 * Constructor que Inicializa las coordenas, las proiedades de las fisicas y la creacion del body @see #initBody(World)
 * 
 * @param game Es la escena del juego
 * @param x Posicion el eje x de abcisas donde se genera en el canvas
 * @param y Posicion el eje y de abcisas donde se genera en el canvas
 * @param width Ancho de la física generada
 * @param height Alto de la física generada
 */
	public Floor(Game game, float x, float y, float width, float height) {
		super(game);
		
		this.x = x;
		this.y = y;

		this.width = width;
		this.height = height;
		
		initBody(game.getPhysics().getWorld());
	}
	
	/**
     * {@inheritDoc}
     * 
     */
	@Override
	protected void initBody(World world) {
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.STATIC;
		bodyDef.position.set(x/scale, y/scale);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox((width / scale) / 2.0f, (height / scale) / 2.0f);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.friction = 0.1f;
		
		body = world.createBody(bodyDef);
		body.createFixture(fd);		
		body.setUserData(this);
	}
	
	/**
     * {@inheritDoc}
     * 
     */
	public void render(GraphicsContext gc) {
				
		gc.setFill(Color.GREEN);
		gc.fillRect(x, y, width, height);
		
	}
	
	/**
     * {@inheritDoc}
     * 
     */
	@Override
	public void update(float timeDifference) {
		x = body.getPosition().x * scale;
		y = body.getPosition().y * scale;
	}

}
