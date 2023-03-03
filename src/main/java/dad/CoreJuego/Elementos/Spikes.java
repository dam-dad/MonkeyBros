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

/**
 * Clase que extiende de entidad, encargada de pintarse en el canvas y tener una representación física
 * 
 * @author Iván Duran Araya
 *
 */

public class Spikes extends Entity {

	private Image image;
	private Body body;

	/**
	 * Constructor que guarda los datos pasados por parametro e inicializa representaciones fisicas en el mapa
	 * @param game Parámetro game (Clase Game) instanciado desde super
	 * @param x Cordenada de posición x
	 * @param y Cordenada de posición y
	 * @param image Imagen del tile
	 */
	public Spikes(Game game, int x, int y, Image image) {
		super(game);
		this.image = image;
		this.x = x;
		this.y = y;
		this.width = (float) image.getWidth();
		this.height = (float) image.getHeight();
		initBody(getGame().getPhysics().getWorld());
	}
	
	/**
	 * Metodo encargado de pintar en el canvas
	 * @param gc El contexto grafico del canvas
	 */
	@Override
	public void render(GraphicsContext gc) {
		gc.drawImage(image, x - Platform.xStatic , y);
	}

	/**
	 * Metodo encargado de actualizar las fisicas del cuerpo a tiempo real
	 * @param timeDifference Dato que referencia el tiempo
	 */
	@Override
	public void update(float timeDifference) {
		Vec2 vector = new Vec2((x - Platform.xStatic) / scale, y / scale);
		body.setTransform(vector, 0);
		
	}

	/**
	 * Metodo donde se define donde y como se generan las fisicas del cuerpo
	 * @param world Mundo encargado de todas las entidades fisicas
	 */
	@Override
	protected void initBody(World world) {

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.STATIC;
		bodyDef.position.set(x /scale, y / scale);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox((width / scale) / 2f, (height / scale) / 2f);

		body = world.createBody(bodyDef);
		body.createFixture(shape, 0.0f);
		body.setUserData(this);
		
	}

}
