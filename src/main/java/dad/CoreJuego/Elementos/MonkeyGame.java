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

import dad.Api.GlobalStat.GlobalStat;
import dad.CoreJuego.Elementos.main.MonkeyBrosApp;
import dad.CoreJuego.mapaEntidades.CollisionsLayer;
import dad.CoreJuego.mapaEntidades.LayerBackground;
import javafx.animation.Animation;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;

/**
 * Clase que Inicia el Juego, tanto el canvas como añadir las entidades y recoje
 * las entradas del usuario
 * 
 * @author Alejandro, Gapaz, David Alejandro
 *
 */

public class MonkeyGame extends Game {

	// model

	private IntegerProperty vidas;

	private Monkey monkey;
	private Properties properties;

	// GlobalStats
	private GlobalStat globalStats;

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
		Camera camera = new PerspectiveCamera(false);
		camera.setTranslateX(1);
		camera.setTranslateY(1);
	}

	/**
	 * Metodo que Inicializa las entidades y colisiones que existen en el mundo
	 * 
	 * {@inheritDoc}
	 */

	@Override
	protected void init() {

		vidas = new SimpleIntegerProperty(0);

		monkey = new Monkey(this, 60, 1);
		monkey.setOnAir(true);

		// bindings

		vidas.bind(monkey.vidasProperty());

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

		Map map;
		try {
			map = new TMXMapReader().readMap(getClass().getResource("/map/MapaDefinitivo.tmx"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		getEntities().addAll(new LayerBackground(this), new CollisionsLayer(this, map),
//				new LayerEscaleras(this),
				new Floor(this, 0, getHeight(), getWidth(), 2),
//				new LayerColisiones(this), 
				monkey);

		this.getPhysics().getWorld().setContactListener(new ContactListener() {

			@Override
			public void beginContact(Contact contact) {

				Object userDataA = contact.getFixtureA().getBody().getUserData();
				Object userDataB = contact.getFixtureB().getBody().getUserData();

				if ((userDataA instanceof Monkey && userDataB instanceof Platform)
						|| (userDataB instanceof Monkey && userDataA instanceof Platform)) {
					monkey.setOnAir(true);
				}

				if ((userDataA instanceof Monkey && userDataB instanceof Floor)
						|| (userDataB instanceof Monkey && userDataA instanceof Floor)) {
					monkey.kill();
				}

			}

			public void endContact(Contact contact) {
			}

			public void preSolve(Contact contact, Manifold oldManifold) {
			}

			public void postSolve(Contact contact, ContactImpulse impulse) {
			}
		});
	}

	/**
	 * Método que detecta constantemente las teclas presionadas y gestiona el
	 * movimiento del <br>
	 * personaje en funcion de la tecla presionada
	 * 
	 * @param input el conjunto de teclas presionadas en el instante de invocación
	 */
	@Override
	protected void processInput(Set<KeyCode> input) {

		float impulsoX = 0f;
		float impulsoY = 0f;

		float gravity = 4f;

		if (input.contains(RIGHT_VALUE)) {
			Platform.xStatic += 3f;
			monkey.setMoving(input.contains(RIGHT_VALUE), Direction.RIGHT);

		}

		if (input.contains(LEFT_VALUE)) {
			Platform.xStatic -= 3f;
			monkey.setMoving(input.contains(LEFT_VALUE), Direction.LEFT);

		}

		if (input.contains(UP_VALUE) && monkey.isOnAir() == true && input.contains(RIGHT_VALUE)) {
			impulsoY -= 3200f;
			monkey.setMoving(input.contains(UP_VALUE), Direction.UP);
			monkey.getBody().applyForce(new Vec2(0, impulsoY), new Vec2(0, 0));
			monkey.setOnAir(false);
		}

		if (input.contains(UP_VALUE) && monkey.isOnAir() == true && input.contains(LEFT_VALUE)) {
			impulsoY -= 3200f;
			monkey.setMoving(input.contains(UP_VALUE), Direction.UP);
			monkey.getBody().applyForce(new Vec2(0, impulsoY), new Vec2(0, 0));
			monkey.setOnAir(false);
		}

		if ((input.contains(RIGHT_VALUE) || input.contains(LEFT_VALUE)) && monkey.isOnAir() == true) {
			monkey.getBody().setLinearVelocity(new Vec2(impulsoX, gravity));
		}
	}

	/**
	 * Método que asigna un valor a la variable de global stats usada para la <br>
	 * invocación de eventos de la api de globalstats
	 * 
	 * @param globalStats el objeto que sobreescribe el valor de la variable usada
	 *                    en la clase
	 */
	public void setGlobalStats(GlobalStat globalStats) {
		this.globalStats = globalStats;
	}

	/**
	 * /** Método que asigna un valor a la variable properties
	 * 
	 * @param globalStats
	 * @param properties  el objeto que sobreescribe el valor de la variable usada
	 *                    en la clase
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * Método que retorna la propiedad actual de las vidas del personaje
	 * 
	 * @return
	 */
	public final IntegerProperty vidasProperty() {
		return this.vidas;
	}
}
