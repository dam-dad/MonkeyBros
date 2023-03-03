package dad.CoreJuego.mapaEntidades;


import org.mapeditor.core.Map;

import dad.CoreJuego.Elementos.Entity;
import dad.CoreJuego.Elementos.Game;
import dad.CoreJuego.Elementos.Platform;
import javafx.scene.image.Image;

/**
 * Clase que extiende de TileLayer para el layer tilesDecoraciones del TMX  @see {@link TileLayer}
 * 
 * @author Iván Durán Araya
 *
 */
public class DecorationsLayer extends TileLayer {
	/**
	 * Constructor que carga el layer del TMX
	 * @param game Parámetro game (Clase Game) instanciado desde super
	 * @param map Parámetro map instanciado desde super
	 */
	public DecorationsLayer(Game game, Map map) {
		super(game, "tilesDecoraciones", map);
	}
	/**
	 * Metodo que crea una nueva entidad pasando los parámetros
	 * @param posX Posición del vector x
	 * @param posY Posición del vector y
	 * @param tileImage Imagen del tile
	 */
	@Override
	protected Entity createEntity(int posX, int posY, Image tileImage) {
		return new Platform(getGame(), posX, posY, tileImage);
	}

}
