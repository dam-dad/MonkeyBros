package dad.CoreJuego.mapaEntidades;

import org.mapeditor.core.Map;

import dad.CoreJuego.Elementos.AnimBanana;
import dad.CoreJuego.Elementos.Entity;
import dad.CoreJuego.Elementos.Game;
import javafx.scene.image.Image;

public class AnimBananaLayer extends TileLayer {

	public AnimBananaLayer(Game game, Map map) {
		super(game, "animPlatanos", map);
	}

	@Override
	protected Entity createEntity(int posX, int posY, Image tileImage) {
		return new AnimBanana(getGame(), posX, posY, tileImage);
	}
}
