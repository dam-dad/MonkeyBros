package dad.CoreJuego.Elementos;

import java.util.Set;

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

	public Game(Canvas canvas) {

		graphicsContext = canvas.getGraphicsContext2D();

		canvas.setOnKeyPressed(e -> input.add(e.getCode()));
		canvas.setOnKeyReleased(e -> input.remove(e.getCode()));
		canvas.setFocusTraversable(true);
		canvas.requestFocus();

		width.bind(canvas.widthProperty());
		height.bind(canvas.heightProperty());
 
		init();

	}

	protected abstract void init();

	protected abstract void processInput(Set<KeyCode> input);

	@Override
	public void start() {
		this.lastNanoTime = System.nanoTime();
		super.start();
	}

	// game loop
	public void handle(long currentNanoTime) {
		timeDifference = (currentNanoTime - lastNanoTime);

		fps.set((int) (1 / timeDifference)); // frames per second

		processInput(input);
		applyPhysics(timeDifference);
		update(timeDifference);
		render(graphicsContext);

		lastNanoTime = currentNanoTime;
	}

	protected void update(float timeDifference) {
		entities.forEach(entity -> entity.update(timeDifference));
	}

	protected void applyPhysics(float timeDifference) {
		physics.update(timeDifference / NANO_TO_SECONDS);
	}

	protected void render(GraphicsContext gc) {
		gc.clearRect(0, 0, getWidth(), getHeight());
		entities.forEach(entity -> entity.render(gc));
	}

	public final javafx.beans.property.ReadOnlyFloatProperty widthProperty() {
		return this.width.getReadOnlyProperty();
	}

	public final float getWidth() {
		return this.widthProperty().get();
	}

	public final javafx.beans.property.ReadOnlyFloatProperty heightProperty() {
		return this.height.getReadOnlyProperty();
	}

	public final float getHeight() {
		return this.heightProperty().get();
	}

	public final javafx.beans.property.ReadOnlyIntegerProperty fpsProperty() {
		return this.fps.getReadOnlyProperty();
	}

	public final int getFps() {
		return this.fpsProperty().get();
	}

	public final ListProperty<Entity> entitiesProperty() {
		return this.entities;
	}

	public final ObservableList<Entity> getEntities() {
		return this.entitiesProperty().get();
	}

	public final SetProperty<KeyCode> inputProperty() {
		return this.input;
	}

	public final ObservableSet<KeyCode> getInput() {
		return this.inputProperty().get();
	}

	public Physics getPhysics() {
		return physics;
	}
	
}
