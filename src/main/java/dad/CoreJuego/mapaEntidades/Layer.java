package dad.CoreJuego.mapaEntidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.mapeditor.core.Map;
import org.mapeditor.core.Tile;
import org.mapeditor.core.TileLayer;
import org.mapeditor.io.TMXMapReader;

import dad.CoreJuego.Elementos.Entity;
import dad.CoreJuego.Elementos.Game;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Layer extends Entity {

	private Map map;
	private TileLayer tileLayer;
	private int altura;
	private int ancho;
	private String nombreLayer;
	
	private ArrayList<ArrayList<ImageView>> tileY = new ArrayList<ArrayList<ImageView>>();

	private ArrayList<ImageView> tileX = new ArrayList<ImageView>();
	
	public Layer(Game game,String nombreLayer) {
		super(game);
		this.nombreLayer=nombreLayer;
		initialize(game);
	}

	public void initialize(Game game) {
		try {
			map = new TMXMapReader().readMap(getClass().getResource("/map/MapaDefinitivo.tmx"));
			int i = 0;
			while (i < map.getLayerCount()) {
				String nombre = map.getLayer(i).getName();
				String nombreTiles = nombre.substring(5, nombre.length());
				// System.out.println(nombreTiles);
				if (nombreTiles.equals(nombreLayer)) {
					System.out.println(map.getLayer(i).getName());
					tileLayer = (TileLayer) map.getLayer(i);
				}
				i++;
			}

			altura = map.getHeight();
			ancho = map.getWidth();

			Tile tile;
			int tid;

			HashMap<Integer, Image> tileHash = new HashMap<Integer, Image>();
			Image tileImage = null;
			ImageView nullV = null;

			for (int y = 0; y < altura; y++) {
				tileX = new ArrayList<ImageView>();
				for (int x = 0; x < ancho; x++) {
					if (tileLayer.getTileAt(x, y) == null) {
						tileX.add(nullV);
					} else {

						tile = tileLayer.getTileAt(x, y);
						tid = tile.getId();
						if (tile.getId() != null) {
							tid = tile.getId();
							if (tileHash.containsKey(tid)) {
								tileImage = tileHash.get(tid);
							} else {
								try {
									tileImage = createImage(tile.getImage());
								} catch (Exception e) {
									e.printStackTrace();
								}
								tileHash.put(tid, tileImage);
							}

							ImageView iv = new ImageView(tileImage);

							iv.setTranslateX(x * 32);
							iv.setTranslateY(y * 32);

							tileX.add(iv);

							// Apartado de Colisone

							initBody(game.getPhysics().getWorld());
						}
					}
				}

				tileY.add(tileX);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected javafx.scene.image.Image createImage(java.awt.Image image) throws Exception {
		if (!(image instanceof RenderedImage)) {
			BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
					BufferedImage.TYPE_INT_ARGB);
			Graphics g = bufferedImage.createGraphics();
			g.drawImage(image, 0, 0, null);
			g.dispose();
			image = bufferedImage;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write((RenderedImage) image, "png", out);
		out.flush();
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		return new javafx.scene.image.Image(in);
	}

}