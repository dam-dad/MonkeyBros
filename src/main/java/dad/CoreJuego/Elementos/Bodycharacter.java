package dad.CoreJuego.Elementos;

import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import dad.CoreJuego.animation.AnimationPixel;
import javafx.scene.canvas.GraphicsContext;

/**
 * Se encarga de crear el personaje
 * @author Alejandro, Gabriel
 */
 

public class Bodycharacter extends Entity{

	public Body body;
	FixtureDef fd;
	
	Boolean moving = false;
	int pos = 0;
	
	AnimationPixel animationIdle, animationRun, animationJump, actualAnimation;
	
	private static final float ANIMATION_SPEED = 70000000; 
	private static final float ANIMATION_SPEED_RUN = 35000000; 
/**
 * Creación del cuerpo y la carga de animaciones del personaje
 * 
 * @param game Es la escena del juego
 * @param posX Posicion el eje x de abcisas donde se genera en el canvas
 * @param posY Posicion el eje y de abcisas donde se genera en el canvas
 */
	public Bodycharacter(Game game, float posX, float posY) {
		super(game);

		this.x = posX;
		this.y = posY;

		// variables of character size
		this.width = 10f;
		this.height = 10f;
		
		//Animaciones
		
		animationIdle = new AnimationPixel(ANIMATION_SPEED,"animacionesMono/Idle_Animation48x54x22f.png", 48, 54, 22);
				
		animationRun = new AnimationPixel(ANIMATION_SPEED_RUN,"animacionesMono/Run_Animation78x54x20f.png", 78, 54, 20);
				
		animationJump = new AnimationPixel(ANIMATION_SPEED_RUN,"animacionesMono/Air_Animation60x80x1f.png", 60, 80, 1);
		
		initBody(game.getPhysics().getWorld());
	}

	/**
	 * Inicializas el personaje en el mundo
	 * 
	 * @param world Le estamos pasando un mundo para que se genere las físicas
	 */
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

	/**
     * {@inheritDoc}
     * 
     */
	public void render(GraphicsContext gc) {

		gc.drawImage(actualAnimation.getCurrentFrame(), x, y);
		
	}

	public void move(Vec2 vector) {
		body.applyLinearImpulse(vector, body.getWorldCenter());
	}

	/**
	 * 
	 * Gestion de Animaciones en base a un codigo numerico mas un booleano @see #setMoving(boolean, int)
	 * 
     * {@inheritDoc}
     * 
     */
	@Override
	public void update(float timeDifference) {
		x = body.getPosition().x;
		y = body.getPosition().y;
		
		if(moving == false) {
			actualAnimation = animationIdle;
		}else {
			switch (pos) {
			case 1: {
				actualAnimation = animationRun;
				break;
			}
			case 2: {
				actualAnimation = animationRun;
				break;
			}
			case 3: {
				actualAnimation = animationJump;
				break;
			}
			
			}
			
		}
		actualAnimation.update(timeDifference);
	}
	/**
	 * 
	 * metodo usado como codigo para gestionar las animaciones en @see #update(float)
	 * 
	 * @param mov Nos devuelve si hay una accion o no
	 * @param i Es codigó generado en base al keyEvent(acción) del personaje
	 */
	public void setMoving(boolean mov, int i) {
		this.moving = mov;
		this.pos = i;
	}
	
}
