package dad.CoreJuego.Elementos;


import javafx.scene.canvas.GraphicsContext;

public abstract  class Entity {
	private Game game;
	protected float x, y;
	protected float width, height;
	protected double scale = 1.0;
	
	public Entity(Game game) {
		this.game = game;
	}
	
	public void setScale(double scale) {
		this.scale = scale;
	}
	
	public double getScale() {
		return scale;
	}
	
	public Game getGame() {
		return game;
	}
	
	public abstract void render(GraphicsContext gc);
	public abstract void update(float timeDifference);

}
