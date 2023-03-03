package dad.CoreJuego.mapaEntidades;

import org.mapeditor.core.Map;

import dad.CoreJuego.Elementos.AnimBundleBanana;
import dad.CoreJuego.Elementos.Entity;
import dad.CoreJuego.Elementos.Game;
import javafx.scene.image.Image;

public class AnimBundleBananaLayer extends TileLayer {

	public AnimBundleBananaLayer(Game game, Map map) {
		super(game, "animPlatanos2", map);
	}

	@Override
	protected Entity createEntity(int posX, int posY, Image tileImage) {
		return new AnimBundleBanana(getGame(), posX, posY, tileImage);
	}
}