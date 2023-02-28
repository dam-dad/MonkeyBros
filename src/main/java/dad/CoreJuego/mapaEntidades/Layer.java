package dad.CoreJuego.mapaEntidades;

import java.util.ArrayList;
import java.util.List;

import dad.CoreJuego.Elementos.Entity;
import dad.CoreJuego.Elementos.Game;
import javafx.scene.canvas.GraphicsContext;

public abstract class Layer extends Entity {

	private String name;
	private List<Entity> entities;
	
	public Layer(Game game, String name) {
		super(game);
		this.name = name;
		this.entities = new ArrayList<>();
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public void render(GraphicsContext gc) {
		entities.forEach(entity -> entity.render(gc));
	}
	
	@Override
	public void update(float timeDifference) {
		entities.forEach(entity -> entity.update(timeDifference));
	}

}