package dad.CoreJuego.Elementos;

import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import dad.CoreJuego.animation.Animation;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Se encarga de crear el personaje
 * 
 * @author Alejandro, Gabriel
 */

public class Monkey extends Entity {

	private static final float ANIMATION_SPEED = 70000000;
	private static final float ANIMATION_SPEED_RUN = 35000000;

	
	private boolean respo=false;
	private boolean isOnAir;
	private BodyDef bd;
	private Body body;
	private Body bodyAnt;
	private World world;
	private Boolean moving = false;
	private Direction direction = Direction.RIGHT;
	private Animation animationIdle, animationRun, animationJump, actualAnimation;

	/**
	 * Creación del cuerpo y la carga de animaciones del personaje
	 * 
	 * @param game Es la escena del juego
	 * @param posX Posicion el eje x de abcisas donde se genera en el canvas
	 * @param posY Posicion el eje y de abcisas donde se genera en el canvas
	 */
	public Monkey(Game game, float posX, float posY) {
		super(game);

		this.x = posX;
		this.y = posY;

		// variables of character size
		this.width = 54f;
		this.height = 54f;

		// Animaciones
		animationIdle = new Animation(ANIMATION_SPEED, "animacionesMono/Idle_Animation48x54x22f.png", 48, 54, 22);
		animationRun = new Animation(ANIMATION_SPEED_RUN, "animacionesMono/Run_Animation78x54x20f.png", 78, 54, 20);
		animationJump = new Animation(ANIMATION_SPEED_RUN, "animacionesMono/Air_Animation60x80x1f.png", 60, 80, 1);

		initBody(game.getPhysics().getWorld());
	}

	/**
	 * Inicializas el personaje en el mundo
	 * 
	 * @param world Le estamos pasando un mundo para que se genere las físicas
	 */
	@Override
	protected void initBody(World world) {

		this.world=world;
		
		// The complete code snippet would look like:
		// body definition
		bd = new BodyDef();
		bd.position.set(x / scale, y / scale);
		bd.type = BodyType.DYNAMIC;

		// define shape of the body.
		PolygonShape box = new PolygonShape();
		box.setAsBox((width / scale) / 2.0f, (height / scale) / 2.0f);

		// define fixture of the body.
		FixtureDef fd = new FixtureDef();
		fd.shape = box;
		fd.friction = 0.1f;

		// mass 
		MassData massData = new MassData();
		massData.mass = 50.0f;

		// create the body and add fixture to it
		body = world.createBody(bd);
		body.createFixture(fd);
		body.getLinearVelocity();
		body.setMassData(massData);
		body.setUserData(this);
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	public void render(GraphicsContext gc) {

		gc.drawImage(actualAnimation.getCurrentFrame(), x, y);
		
		gc.setFill(Color.YELLOW);
		gc.rect(x, y, width, height);

	}

	public void move(Vec2 vector) {
		body.applyLinearImpulse(vector, body.getWorldCenter());
	}

	/**
	 * 
	 * Gestion de Animaciones en base a un codigo numerico mas un booleano @see
	 * #setMoving(boolean, int)
	 * 
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void update(float timeDifference) {
		
		if(respo) {
		
			respawn();
			respo=false;
		}
		try {
			x = body.getPosition().x * scale;
			y = body.getPosition().y * scale;

			if (moving == false) {
				actualAnimation = animationIdle;
			} else {
				switch (direction) {
					case RIGHT: actualAnimation = animationRun; break;
					case LEFT: actualAnimation = animationRun; break;
					case UP: actualAnimation = animationJump; break;
					case DOWN: /* TODO */ break;
				}
			}
			actualAnimation.update(timeDifference);
		} catch (Exception e) {
			//respawn();
			e.printStackTrace();
		}
		
	}

	/**
	 * 
	 * metodo usado como codigo para gestionar las animaciones en @see
	 * #update(float)
	 * 
	 * @param moving Nos devuelve si hay una accion o no
	 * @param direction   Es codigó generado en base al keyEvent(acción) del personaje
	 */
	public void setMoving(boolean moving, Direction direction) {
		this.moving = moving;
		this.direction = direction;
	}
	
	public void applyForce(float x, float y) {
		body.applyForceToCenter(new Vec2(x, y));
	}
	
	public boolean isOnAir() {
		return isOnAir;
	}
	
	public void setOnAir(boolean isOnAir) {
		this.isOnAir = isOnAir;
	}
	
	public Body getBody() {
		return body;
	}

	public void respawn() {
		
		respo=true;
		
		bodyAnt=body;
		
		bd = new BodyDef();
		bd.position.set(1,1);
		bd.type = BodyType.DYNAMIC;

		// define shape of the body.
		PolygonShape box = new PolygonShape();
		box.setAsBox((width / scale) / 2.0f, (height / scale) / 2.0f);

		// define fixture of the body.
		FixtureDef fd = new FixtureDef();
		fd.shape = box;
		fd.friction = 0.1f;

		body = world.createBody(bd);
		body.createFixture(fd);
		
		// Volver a colocar al personaje en el mundo del juego
		getGame().getEntities().set(3, this);
		getGame().getGraphicsContext().drawImage(actualAnimation.getCurrentFrame(), 1, 1);
		getGame().getPhysics().getWorld().destroyBody(bodyAnt);
	}
	
}
