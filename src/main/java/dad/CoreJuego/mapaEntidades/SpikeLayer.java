package dad.CoreJuego.mapaEntidades;


import org.mapeditor.core.Map;

import dad.CoreJuego.Elementos.Entity;
import dad.CoreJuego.Elementos.Game;
import dad.CoreJuego.Elementos.Platform;
import dad.CoreJuego.Elementos.Spikes;
import javafx.scene.image.Image;

public class SpikeLayer extends TileLayer {

	public SpikeLayer(Game game, Map map) {
		super(game, "tilesPinchos", map);
	}

	@Override
	protected Entity createEntity(int posX, int posY, Image tileImage) {
		return new Spikes(getGame(), posX, posY, tileImage);
	}

}
