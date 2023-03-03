package dad.CoreJuego.mapaEntidades;

import java.util.ArrayList;

import org.jbox2d.dynamics.World;
import org.mapeditor.core.ImageLayer;
import org.mapeditor.core.Map;
import org.mapeditor.io.TMXMapReader;

import dad.CoreJuego.Elementos.Entity;
import dad.CoreJuego.Elementos.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//TODO

public class LayerBackground extends Entity {

	Map map;
	private ImageLayer imageLayer;
//	private ImageView imagen;
	private Image imagen2;
	private String nombreBackground;
	private int altura;
	private int ancho;
	
	private double alturaImagen;
	private double anchoImagen;
	 

	ArrayList<ArrayList<ImageView>> tileY = new ArrayList<ArrayList<ImageView>>();
	
	ArrayList<ImageView> tileX = new ArrayList<ImageView>();
	
	public LayerBackground(Game game) {
		super(game);
		try {
	  		  map = new TMXMapReader().readMap(getClass().getResource("/map/MapaDefinitivo.tmx"));
	  		  int i = 0;
	  		  while (i < map.getLayerCount()) {
	  			String nombre = map.getLayer(i).getName();
	  			//System.out.println(nombreTiles);
	  			if (nombre.equals("background")){
	  				System.out.println(map.getLayer(i).getName());
	  				imageLayer = (ImageLayer) map.getLayer(i);
		  			}
		  			i++;
	  		    }
	  			altura = map.getHeight();
	  			ancho = map.getWidth();
	  		  			
	  			nombreBackground = imageLayer.getImage().getSource();
	  			imagen2 = new Image("tilesMapa/Fondo.png");
	  			
	  			alturaImagen = imagen2.getHeight();
	  			anchoImagen = imagen2.getWidth();
	  			
			}catch(Exception e) {
	            e.printStackTrace();
			}
		}
	

	@Override
	public void render(GraphicsContext gc) {
		for(int i = 0; i < altura; i++) {
			for(int o = 0; o < ancho; o++) {
				gc.drawImage(imagen2, i*256, o*256);
			}
		}
	}

	@Override
	public void update(float timeDifference) {
		
	}


	@Override
	protected void initBody(World world) {
		// TODO Auto-generated method stub
		
	}

}
