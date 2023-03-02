package dad.CoreJuego.Elementos;

import java.util.Set;

import dad.CoreJuego.Controllers.menu.Resolucion;
import dad.CoreJuego.Elementos.main.MonkeyBrosApp;
import javafx.animation.AnimationTimer;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyFloatWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

/**
 * Se encarga de pintar el juego
 * 
 * @author Fran
 *
 */
public abstract class Game extends AnimationTimer {

	private static final float NANO_TO_SECONDS = 1000000000f;

	private ReadOnlyFloatWrapper width = new ReadOnlyFloatWrapper();
	private ReadOnlyFloatWrapper height = new ReadOnlyFloatWrapper();
	private ReadOnlyIntegerWrapper fps = new ReadOnlyIntegerWrapper();
	private ListProperty<Entity> entities = new SimpleListProperty<>(FXCollections.observableArrayList());
	private SetProperty<KeyCode> input = new SimpleSetProperty<>(FXCollections.observableSet());

	private long lastNanoTime;
	private float timeDifference; // in seconds
	private GraphicsContext graphicsContext;
	private Physics physics = new Physics();

	/**
	 * constructor que Inicializa el mundo, canvas y las dimesiones de este con @see
	 * #init()
	 * 
	 * @param canvas Es el lienzo de javafx
	 */
	public Game(Canvas canvas) {

		graphicsContext = canvas.getGraphicsContext2D();

		canvas.setOnKeyPressed(e -> input.add(e.getCode()));
		canvas.setOnKeyReleased(e -> input.remove(e.getCode()));
		canvas.setFocusTraversable(true);
		canvas.requestFocus();

		int widthRes = 1080;
		int heightRes = 720;

		try {
			Resolucion res = Resolucion.valueOf(MonkeyBrosApp.properties.getProperty("resolucion"));
			widthRes = res.getWidth();
			heightRes = res.getHeight();
		} catch (Exception e) {
//			e.printStackTrace();
		}
		canvas.setWidth(widthRes);
		canvas.setHeight(heightRes);
		width.bind(canvas.widthProperty());
		height.bind(canvas.heightProperty());

		init();

	}

	/**
	 * Inicializa todo
	 */
	protected abstract void init();

	/**
	 * 
	 * @param input
	 */
	protected abstract void processInput(Set<KeyCode> input);

	/**
	 * Crea un contador
	 */
	@Override
	public void start() {
		this.lastNanoTime = System.nanoTime();
		super.start();
	}

	/**
	 * Se encarga del loop del juego
	 * 
	 * @param currentNanoTime El fps actual
	 */
	public void handle(long currentNanoTime) {
		timeDifference = (currentNanoTime - lastNanoTime);

		fps.set((int) (1 / timeDifference)); // frames per second

		processInput(input);
		applyPhysics(timeDifference);
		update(timeDifference);
		render(graphicsContext);

		lastNanoTime = currentNanoTime;
	}

	/**
	 * 
	 * @param timeDifference Obtienes la diferencia de tiempo
	 */
	protected void update(float timeDifference) {
		entities.forEach(entity -> entity.update(timeDifference));
	}

	/**
	 * 
	 * @param timeDifference Obtienes la diferencia de tiempo
	 */
	protected void applyPhysics(float timeDifference) {
		physics.update(timeDifference / NANO_TO_SECONDS);
	}

	/**
	 * Limpia los gráficos generados
	 * 
	 * @param gc Los gráficos generados
	 */
	protected void render(GraphicsContext gc) {
		gc.clearRect(0, 0, getWidth(), getHeight());
		entities.forEach(entity -> entity.render(gc));
	}

	/**
	 * 
	 * @return Te devuelve el ancho
	 */
	public final javafx.beans.property.ReadOnlyFloatProperty widthProperty() {
		return this.width.getReadOnlyProperty();
	}

	/**
	 * 
	 * @return Te devuelve el ancho
	 */
	public final float getWidth() {
		return this.widthProperty().get();
	}

	/**
	 * 
	 * @return Te devuelve el alto
	 */
	public final javafx.beans.property.ReadOnlyFloatProperty heightProperty() {
		return this.height.getReadOnlyProperty();
	}

	/**
	 * 
	 * @return Te devuelve el alto
	 */
	public final float getHeight() {
		return this.heightProperty().get();
	}

	/**
	 * 
	 * @return Te devuelve los fps
	 */
	public final javafx.beans.property.ReadOnlyIntegerProperty fpsProperty() {
		return this.fps.getReadOnlyProperty();
	}

	/**
	 * 
	 * @return Te devuelve los fps
	 */
	public final int getFps() {
		return this.fpsProperty().get();
	}

	/**
	 * 
	 * @return Te devuelve lista de entidades property
	 */
	public final ListProperty<Entity> entitiesProperty() {
		return this.entities;
	}

	/**
	 * 
	 * @return Te devuelve un observable
	 */
	public final ObservableList<Entity> getEntities() {
		return this.entitiesProperty().get();
	}

	/**
	 * 
	 * @return Te devuelve un evento del teclado
	 */
	public final SetProperty<KeyCode> inputProperty() {
		return this.input;
	}

	/**
	 * 
	 * @return Te devuelve el observable del teclado
	 */
	public final ObservableSet<KeyCode> getInput() {
		return this.inputProperty().get();
	}

	/**
	 * 
	 * @return Te devuelve físicas
	 */
	public Physics getPhysics() {
		return physics;
	}

	public GraphicsContext getGraphicsContext() {
		return graphicsContext;
	}
}
