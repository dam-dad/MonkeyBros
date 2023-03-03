package dad.CoreJuego.mapaEntidades;

import org.mapeditor.core.Map;

import dad.CoreJuego.Elementos.Banner;
import dad.CoreJuego.Elementos.Entity;
import dad.CoreJuego.Elementos.Game;
import javafx.scene.image.Image;

public class BannerLayer extends TileLayer {

	public BannerLayer(Game game, Map map) {
		super(game, "tilesBanner", map);
	}

	@Override
	protected Entity createEntity(int posX, int posY, Image tileImage) {
		return new Banner(getGame(), posX, posY, tileImage);
	}

}