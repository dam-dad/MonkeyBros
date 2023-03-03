package dad.CoreJuego.mapaEntidades;

import org.mapeditor.core.Map;

import dad.CoreJuego.Elementos.Entity;
import dad.CoreJuego.Elementos.Game;
import dad.CoreJuego.Elementos.Platform;
import javafx.scene.image.Image;

public class CollisionsLayer extends TileLayer {

	public CollisionsLayer(Game game, Map map) {
		super(game, "tilesColisiones", map);
	}

	@Override
	protected Entity createEntity(int posX, int posY, Image tileImage) {
		return new Platform(getGame(), posX, posY, tileImage);
	}

}
