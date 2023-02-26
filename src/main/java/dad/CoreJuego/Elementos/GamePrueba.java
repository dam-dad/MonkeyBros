package dad.CoreJuego.Elementos;

import java.util.Properties;
import java.util.Set;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;

import dad.CoreJuego.Elementos.main.MonkeyBrosApp;
import dad.CoreJuego.mapaEntidades.LayerBackground;
import dad.CoreJuego.mapaEntidades.LayerColisiones;
import dad.CoreJuego.mapaEntidades.LayerEscaleras;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;

/**
 * Clase que Inicia el Juego, tanto el canvas como añadir las entidades y recoje
 * las entradas del usuario
 * 
 * @author Alejandro, Gapaz , David Alejandro
 *
 */

public class GamePrueba extends Game {

	private Bodycharacter cp;
	Game game;
	boolean isJump;
	Vec2 vector;
	Properties properties;

	/**
	 * Controles direccionales
	 */
	static KeyCode LEFT_VALUE;
	static KeyCode RIGHT_VALUE;
	static KeyCode UP_VALUE;
	static KeyCode DOWN_VALUE;
	static KeyCode JUMP_VALUE;

	public GamePrueba(Canvas canvas) {
		super(canvas);
	}

	/**
	 * Metodo que Inicializa las entidades y colisiones que existen en el mundo
	 * 
	 * {@inheritDoc}
	 */

	@Override
	protected void init() {
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
		
		cp = new Bodycharacter(this, 1, 1);

		getEntities().addAll(new LayerBackground(this), new LayerColisiones(this), new LayerEscaleras(this),  new Floor(this, 0, getHeight() - 100f, getWidth(), 2), new LayerColisiones(this), cp);
		//getEntities().addAll(cp, new Floor(this, 0, getHeight() - 20f, getWidth(), 2));
		//getEntities().addAll(new Floor(this, 0, getHeight() - 20f, getWidth(), 2), new LayerBackground(this), new LayerColisiones(this), cp);

		this.getPhysics().getWorld().setContactListener(new MyContactListener() {

			@Override
			public void beginContact(Contact contact) {

				Object userDataA = contact.getFixtureA().getBody().getUserData();
				Object userDataB = contact.getFixtureB().getBody().getUserData();

				if (userDataA instanceof Bodycharacter && userDataB instanceof Floor) {
//					CuerpoPersonaje personaje = (CuerpoPersonaje) userDataA;
//					Muro muro = (Muro) userDataB;
					isJump = true;
				} else if (userDataB instanceof Bodycharacter && userDataA instanceof Floor) {
//					CuerpoPersonaje personaje = (CuerpoPersonaje) userDataB;
//					Muro muro = (Muro) userDataA;
					isJump = true;
				}
			}
		});
	}

	/**
	 * 
	 * metodo usado para limitar el movimiento que hace el personaje con un pequeño
	 * delay
	 * 
	 * @param x posicion del personaje en el canvas y mundo
	 */

	public void fuerzaGravedad(float x) {
		float jumpStartTime = System.nanoTime() / 1000000000f;
		final float JUMP_FORCE = 20f;
		final float MAX_JUMP_DURATION = 0.3f;

		float jumpDuration = System.nanoTime() / 1000000000f - jumpStartTime;
		if (jumpDuration < MAX_JUMP_DURATION) {
			float jumpForce = JUMP_FORCE * (1 - jumpDuration / MAX_JUMP_DURATION);
			vector = new Vec2(x, jumpForce);
			cp.body.applyForceToCenter(vector);
		}
	}

	/**
	 * Metodo Usado para saltar
	 * 
	 * @param x la posicion en el eje x que recibe respecto al salto
	 * @param y la posicion en el eje y que recibe respecto al salto
	 */

	public void impulsoVertical(float x, float y) {
		vector = new Vec2(x, y);
		cp.body.applyForceToCenter(vector);
	}

	/**
	 * 
	 * metodo usado para recibir y procesar las entradas realizador por el usuario
	 * reflejando el movimiento y animacion en el canva
	 * 
	 * param input KeyCode enviado por el usuario al pulsar una tecla
	 */

	@Override
	protected void processInput(Set<KeyCode> input) {

		float x = 0f, y = 0f;

		if (input.contains(RIGHT_VALUE)) {
			x += 100f;
			cp.setMoving(true, 1);
		} else {
			cp.setMoving(false, 0);
		}

		if (input.contains(LEFT_VALUE)) {
			x -= 100f;
			cp.setMoving(true, 2);
		}

		if ((input.contains(JUMP_VALUE)) && isJump) {
			y -= 100f;
			isJump = false;
			cp.setMoving(true, 3);
		}

		if (input.contains(DOWN_VALUE)) {
			y += 100f;
		}
		if (input.contains(RIGHT_VALUE) || input.contains(LEFT_VALUE)) {
			if (!((input.contains(KeyCode.UP) || input.contains(KeyCode.SPACE) && isJump)
					|| input.contains(KeyCode.DOWN))) {
				fuerzaGravedad(x);
			}
		}
		if ((input.contains(UP_VALUE) || input.contains(JUMP_VALUE) && isJump) || input.contains(DOWN_VALUE)) {
			impulsoVertical(x, y);
		}
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
}
