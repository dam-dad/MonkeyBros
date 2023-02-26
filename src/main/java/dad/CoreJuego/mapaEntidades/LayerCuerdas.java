package dad.CoreJuego.mapaEntidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.mapeditor.core.Map;
import org.mapeditor.core.Tile;
import org.mapeditor.core.TileLayer;
import org.mapeditor.io.TMXMapReader;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import dad.CoreJuego.Elementos.Entity;
import dad.CoreJuego.Elementos.Game;

//TODO

public class LayerCuerdas extends Entity {

	Map map;
	private TileLayer tileCuerdas;
	private int altura;
	private int ancho;
	
	private Body body; 

	ArrayList<ArrayList<ImageView>> tileY = new ArrayList<ArrayList<ImageView>>();
	
	ArrayList<ImageView> tileX = new ArrayList<ImageView>();
	
	public LayerCuerdas(Game game) {
		super(game);
		try {
	  		  map = new TMXMapReader().readMap(getClass().getResource("/map/MapaDefinitivo.tmx"));
	  		  int i = 0;
	  		  while (i < map.getLayerCount()) {
	  			String nombre = map.getLayer(i).getName();
	  			String nombreTiles = nombre.substring(5, nombre.length());
	  			if (nombreTiles.equals("Cuerdas")){
	  				System.out.println(map.getLayer(i).getName());
	  				tileCuerdas = (TileLayer) map.getLayer(i);
		  			}
		  			i++;
	  		    }
	  		  	
	  			
	  			altura = map.getHeight();
	  			ancho = map.getWidth();

	  			Tile tile;
	  	        int tid;
	  	        
	  	        HashMap<Integer,Image> tileHash = new HashMap<Integer,Image>();
	  	        Image tileImage = null;
	  	        ImageView nullV = null;
	  				
	  				for (int y=0; y < altura; y++) {
	  					tileX = new ArrayList<ImageView>();
	  					for (int x = 0; x < ancho; x++) {
	  						if (tileCuerdas.getTileAt(x, y) == null) {
	  							tileX.add(nullV);
	  		            	}
	  						else {  							
	  		            		tile = tileCuerdas.getTileAt(x, y);
	  		            		tid = tile.getId();
	  			            	if (tile.getId() != null) {
	  			            		tid = tile.getId();
	  				                if (tileHash.containsKey(tid)) {
	  				                    tileImage=tileHash.get(tid);
	  				                } else {
	  				                    try {
	  				                        tileImage=createImage(tile.getImage());
	  				                    } catch (Exception e) {
	  				                        e.printStackTrace(); // TODO!
	  				                    }
	  				                    tileHash.put(tid,tileImage);
	  				                }
	  				                
	  			                ImageView iv = new ImageView(tileImage);
	  			                
	  			                iv.setTranslateX(x*32);
	  			                iv.setTranslateY(y*32);
	  			                
	  			               tileX.add(iv);
	  			               
	  			               // Apartado de Colisones
	  			               
	  			            initBody(game.getPhysics().getWorld());

	  			            	}
	  			            	else {
	  			            	}
	  			            }
	  			        }
	  			     
	  			        tileY.add(tileX);
	  		        }
			}catch(Exception e) {
	            e.printStackTrace();
			}
		}
	
	public static javafx.scene.image.Image createImage(java.awt.Image image) throws Exception {
        if (!(image instanceof RenderedImage)) {
            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
                    image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
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

	@Override
	public void render(GraphicsContext gc) {
		
		for(int y = altura - 1; y >= 0 ; y--) {
			tileX = tileY.get(y);
			//System.out.println("Vuelta " + y + " Tamaño: " + tileX.size());
			for(int x = tileX.size() - 1; x >= 0; x--) {
				if(tileX.get(x) == null) {	
				} else {
					gc.drawImage(tileX.get(x).getImage(), x*32, y*32);
					gc.setFill(Color.PURPLE);
					//gc.fillRect(x*32, (y*32)+10, 32-8, 32-22);
					gc.fillRect(x*32, y*32, 32, 32);
				}
				//gc.drawImage(tileX.get(x).getImage(), x*32, y*32);
					//gc.drawImage(tileX.get(x).getImage(), x*32, y*32);
					//System.out.println(x);
					//gc.drawImage(tileX.get(x).getImage(), x*32, y*32);
			}
		}
		
	}

	@Override
	public void update(float timeDifference) {
		x = body.getPosition().x;
		y = body.getPosition().y;
	}

	@Override
	protected void initBody(World world) {
		 BodyDef bodyDef = new BodyDef();
          bodyDef.type = BodyType.STATIC;
          bodyDef.position.set(x*32, y*32);

 			 PolygonShape shape = new PolygonShape();
 			 shape.setAsBox(32 / 1.0f, 32 / 1.0f);
 		
 			 body = getGame().getPhysics().getWorld().createBody(bodyDef);
 			 body.createFixture(shape, 0.0f);
		
	}

}
