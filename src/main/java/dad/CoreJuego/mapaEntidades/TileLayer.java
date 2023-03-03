package dad.CoreJuego.mapaEntidades;

import java.io.IOException;
import java.util.HashMap;

import org.jbox2d.dynamics.World;
import org.mapeditor.core.Map;
import org.mapeditor.core.Tile;

import dad.CoreJuego.Elementos.Entity;
import dad.CoreJuego.Elementos.Game;
import dad.CoreJuego.utils.ImageUtils;
import javafx.scene.image.Image;

/**
 * Clase abstracta TileLayer encargada de leer las posiciones de los tiles del layer, extiende de Layer
 * @author  Iván Durán Araya
 *
 */

public abstract class TileLayer extends Layer {

	private Map map;
	private int altura;
	private int ancho;

	/**
	 * Constructor encargado de manejar las posiciones de los tiles del layer pasado por parametro
	 * @param game Clase game instanciado desde super
	 * @param layerName String del nombre del layer con el que se va a trabajar
	 * @param map Mapa desde el que se lee el layer y las posiciones de los tiles
	 */
	public TileLayer(Game game, String layerName, Map map) {
		super(game, layerName);
		this.map = map;
		initialize();
	}
	/**
	 * Metodo void que lee el mapa y el layer que se le paso anteriormente para empezar una busqueda linear y empezar
	 * a crear entidades con las imagenes y posiciones correspondientes al las que estan en el archivo TMX
	 */

	public void initialize() {

		org.mapeditor.core.TileLayer tileLayer = map.getLayers().stream()
				.filter(layer -> layer.getName().equals(getName()))
				.map(layer -> (org.mapeditor.core.TileLayer) layer)
				.findFirst()
				.orElse(null);
		
		if (tileLayer == null) {
			throw new RuntimeException("tile layer " + getName() + " not exist!");
		}

		altura = map.getHeight();
		ancho = map.getWidth();

		HashMap<Integer, Image> tileHash = new HashMap<Integer, Image>();
		Image tileImage = null;

		for (int y = 0; y < altura; y++) {
			for (int x = 0; x < ancho; x++) {

				if (tileLayer.getTileAt(x, y) == null) {

					continue;

				} else {

					Tile tile = tileLayer.getTileAt(x, y);
					Integer tid = tile.getId();

					if (tid != null) {

						if (tileHash.containsKey(tid)) {

							tileImage = tileHash.get(tid);

						} else {

							try {
								tileImage = ImageUtils.convertImage(tile.getImage());
								tileHash.put(tid, tileImage);
							} catch (IOException e) {
								// TODO cargar una imagen de error
								// tileImage = loadImage("/images/error.png");
								throw new RuntimeException(e.getMessage(), e);
							}

						}

						int posX = x * tile.getWidth();
						int posY = y * tile.getHeight();

						Entity entity = createEntity(posX, posY, tileImage);
						getEntities().add(entity);

					}

				}
			}

		}
	}
	
	/**
	 * No hace nada
	 */
	@Override
	protected void initBody(World world) {
		// do nothing
	}
	
	/**
	 * Metodo abstracto para crear entidades
	 * @param posX Posicion del vector x
	 * @param posY Posicion del vector y
	 * @param tileImage Imagen para dibujar mas tarde
	 * @return
	 */
	protected abstract Entity createEntity(int posX, int posY, Image tileImage);

}