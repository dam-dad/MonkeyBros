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

public abstract class TileLayer extends Layer {

	private Map map;
	private int altura;
	private int ancho;

	public TileLayer(Game game, String layerName, Map map) {
		super(game, layerName);
		this.map = map;
		initialize();
	}

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
	
	@Override
	protected void initBody(World world) {
		// do nothing
	}

	protected abstract Entity createEntity(int posX, int posY, Image tileImage);

}