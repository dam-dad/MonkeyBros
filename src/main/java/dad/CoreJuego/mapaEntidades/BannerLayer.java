package dad.CoreJuego.mapaEntidades;

import org.mapeditor.core.Map;

import dad.CoreJuego.Elementos.Banner;
import dad.CoreJuego.Elementos.Entity;
import dad.CoreJuego.Elementos.Game;
import javafx.scene.image.Image;

/**
 * Clase que extiende de TileLayer para el layer tilesBanner del TMX  @see {@link TileLayer}
 * 
 * @author Iván Durán Araya
 *
 */
public class BannerLayer extends TileLayer {
	/**
	 * Constructor que carga el layer del TMX
	 * @param game Parámetro game (Clase Game) instanciado desde super
	 * @param map Parámetro map instanciado desde super
	 */
	public BannerLayer(Game game, Map map) {
		super(game, "tilesBanner", map);
	}
	/**
	 * Metodo que crea una nueva entidad pasando los parámetros
	 * @param posX Posición del vector x
	 * @param posY Posición del vector y
	 * @param tileImage Imagen del tile
	 */
	@Override
	protected Entity createEntity(int posX, int posY, Image tileImage) {
		return new Banner(getGame(), posX, posY, tileImage);
	}

}