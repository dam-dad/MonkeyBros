package dad.CoreJuego.mapaEntidades;


import org.mapeditor.core.Map;

import dad.CoreJuego.Elementos.Entity;
import dad.CoreJuego.Elementos.Game;
import dad.CoreJuego.Elementos.Platform;
import javafx.scene.image.Image;

public class DecorationsLayer extends TileLayer {

	public DecorationsLayer(Game game, Map map) {
		super(game, "tilesDecoraciones", map);
	}

	@Override
	protected Entity createEntity(int posX, int posY, Image tileImage) {
		return new Platform(getGame(), posX, posY, tileImage);
	}

}
