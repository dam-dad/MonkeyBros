package dad.CoreJuego.Elementos;

import java.util.Properties;
import java.util.Set;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;
import org.mapeditor.core.Map;
import org.mapeditor.io.TMXMapReader;

import dad.CoreJuego.Elementos.main.MonkeyBrosApp;
import dad.CoreJuego.mapaEntidades.CollisionsLayer;
import dad.CoreJuego.mapaEntidades.LayerBackground;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

/**
 * Clase que Inicia el Juego, tanto el canvas como añadir las entidades y recoje
 * las entradas del usuario
 * 
 * @author Alejandro, Gapaz, David Alejandro
 *
 */

public class MonkeyGame extends Game {

	private Monkey monkey;
	private Properties properties;

	/**
	 * Controles direccionales
	 */
	static KeyCode LEFT_VALUE;
	static KeyCode RIGHT_VALUE;
	static KeyCode UP_VALUE;
	static KeyCode DOWN_VALUE;
	static KeyCode JUMP_VALUE;

	/**
	 * Animación de salto del personaje
	 */
	Animation jumpAnimationAction;

	public MonkeyGame(Canvas canvas) {
		super(canvas);
	}

	/**
	 * Metodo que Inicializa las entidades y colisiones que existen en el mundo
	 * 
	 * {@inheritDoc}
	 */

	@Override
	protected void init() {
		
		jumpAnimationAction = new Transition() {
			{
				setCycleDuration(Duration.millis(500));
			}

			protected void interpolate(double frac) {
				if (frac >= 0) {
					monkey.applyForce(0.0f, (float) frac * 100f);
				}
			}
		};
		
		/**
		 * Vincular los controles del personaje a las properties cargadas
		 */
		properties = MonkeyBrosApp.properties;
		try {
			for (KeyCode v : KeyCode.values()) {
				if (v.getCode() == Integer.parseInt(properties.getProperty("detras"))) {
					LEFT_VALUE = v;
				}
				if (v.getCode() == Integer.parseInt(properties.getProperty("adelante"))) {
					RIGHT_VALUE = v;
				}
				if (v.getCode() == Integer.parseInt(properties.getProperty("escalar"))) {
					UP_VALUE = v;
				}
				if (v.getCode() == Integer.parseInt(properties.getProperty("bajar"))) {
					DOWN_VALUE = v;
				}
				if (v.getCode() == Integer.parseInt(properties.getProperty("saltar"))) {
					JUMP_VALUE = v;
				}
			}
		} catch (Exception e) {
			/**
			 * Valores por defecto de los controles en caso de que no se hayan podido cargar
			 * las propeties o algo saliera mal al hacerlo
			 */
			LEFT_VALUE = KeyCode.LEFT;
			RIGHT_VALUE = KeyCode.RIGHT;
			UP_VALUE = KeyCode.UP;
			DOWN_VALUE = KeyCode.DOWN;
			JUMP_VALUE = KeyCode.SPACE;
		}

		monkey = new Monkey(this, 1, 1);
		monkey.setOnAir(true);		

		Map map;
		try {
			map = new TMXMapReader().readMap(getClass().getResource("/map/MapaDefinitivo.tmx"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		getEntities().addAll(
				new LayerBackground(this), 
				new CollisionsLayer(this, map), 
//				new LayerEscaleras(this),
				new Floor(this, 0, getHeight(), getWidth(), 2), 
//				new LayerColisiones(this), 
				monkey
		);

		this.getPhysics().getWorld().setContactListener(new ContactListener() {

			@Override
			public void beginContact(Contact contact) {

				Object userDataA = contact.getFixtureA().getBody().getUserData();
				Object userDataB = contact.getFixtureB().getBody().getUserData();

				if ((userDataA instanceof Monkey && userDataB instanceof Platform) ||
					(userDataB instanceof Monkey && userDataA instanceof Platform)) {					
					monkey.setOnAir(true);
				} 

				if ((userDataA instanceof Monkey && userDataB instanceof Floor) ||
					(userDataB instanceof Monkey && userDataA instanceof Floor)) {
					monkey.kill();
				} 

			}
			public void endContact(Contact contact) {}
			public void preSolve(Contact contact, Manifold oldManifold) {}
			public void postSolve(Contact contact, ContactImpulse impulse) {}
		});
	}

	/**
	 * 
	 * metodo usado para limitar el movimiento que hace el personaje con un pequeño
	 * delay
	 * 
	 * @param x posicion del personaje en el canvas y mundo
	 */

//	public void fuerzaGravedad(float x) {
//		float jumpStartTime = System.nanoTime() / 1000000000f;
//		final float JUMP_FORCE = 20f;
//		final float MAX_JUMP_DURATION = 0.3f;
//
//		float jumpDuration = System.nanoTime() / 1000000000f - jumpStartTime;
//		if (jumpDuration < MAX_JUMP_DURATION) {
//			float jumpForce = JUMP_FORCE * (1 - jumpDuration / MAX_JUMP_DURATION);
//			System.out.println(jumpForce);
//		System.out.println("flotando = " + monkey.isOnAir());
//		monkey.applyForce(x, monkey.isOnAir() ? -100.0f : 0f);
//		}
//	}

	/**
	 * Metodo Usado para saltar
	 * 
	 * @param x la posicion en el eje x que recibe respecto al salto
	 * @param y la posicion en el eje y que recibe respecto al salto
	 */

	/*
	 * public void impulsoVertical(float x, float y) { vector = new Vec2(x, y);
	 * cuerpo.body.applyForceToCenter(vector); }
	 */

	/**
	 * 
	 * metodo usado para recibir y procesar las entradas realizador por el usuario
	 * reflejando el movimiento y animacion en el canva
	 * 
	 * param input KeyCode enviado por el usuario al pulsar una tecla
	 */

	@Override
	protected void processInput(Set<KeyCode> input) {

		float impulsoX = 0f;
		float impulsoY = 0f;

		if (input.contains(RIGHT_VALUE)) {
			impulsoX += 2f;
			monkey.setMoving(input.contains(RIGHT_VALUE), Direction.RIGHT);

		} 

		if (input.contains(LEFT_VALUE)) {
			impulsoX -= 2f;
			monkey.setMoving(input.contains(LEFT_VALUE), Direction.LEFT);

		}
		

//		if(input.contains(UP_VALUE) && monkey.isOnAir() == true && !((input.contains(RIGHT_VALUE) || input.contains(LEFT_VALUE)))) {
//			impulsoY -= 500f;
//			monkey.setMoving(input.contains(UP_VALUE), Direction.UP);
//			monkey.getBody().applyForce(new Vec2(1000, impulsoY), new Vec2(0,0));
//			monkey.setOnAir(false);
//		}
		if (input.contains(UP_VALUE) && monkey.isOnAir() == true && input.contains(RIGHT_VALUE)) {
			impulsoY -= 3500f;
			monkey.setMoving(input.contains(UP_VALUE), Direction.UP);
			monkey.getBody().applyForce(new Vec2(1000, impulsoY), new Vec2(0,0));
			monkey.setOnAir(false);
		}
		
		if (input.contains(UP_VALUE) && monkey.isOnAir() == true && input.contains(LEFT_VALUE)) {
			impulsoY -= 3500f;
			monkey.setMoving(input.contains(UP_VALUE), Direction.UP);
			monkey.getBody().applyForce(new Vec2(-1000, impulsoY), new Vec2(0,0));
			monkey.setOnAir(false);
		}

		
	if((input.contains(RIGHT_VALUE) || input.contains(LEFT_VALUE)) && monkey.isOnAir() == true) {
		monkey.getBody().setLinearVelocity(new Vec2(impulsoX, 0)); 
	}
//		monkey.getBody().applyLinearImpulse(new Vec2(impulsoX, impulsoY), new Vec2(0,0)); 
		//monkey.applyForce(impulsoX, impulsoY);
		
		/*
		 * if (input.contains(DOWN_VALUE)) { impulsoY += 100f; }
		 */
//		if (input.contains(RIGHT_VALUE) || input.contains(LEFT_VALUE)) {
//			fuerzaGravedad(impulsoX);
//		}

//		if ((input.contains(JUMP_VALUE)) && !monkey.isOnAir()) {
//			monkey.setOnAir(true);
//			impulsoY = -100f;
//			jumpAnimationAction.playFromStart();
//			System.out.println("salto");
//			monkey.setMoving(true, Direction.UP);
//		}
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
}
