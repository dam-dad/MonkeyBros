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
import dad.CoreJuego.Elementos.Entity;
import dad.CoreJuego.Elementos.Game;
import dad.CoreJuego.animation.Animation;

//TODO

public class LayerAnimPlatanos extends Entity {

	Map map;
	private TileLayer tilePlatanos;
	private int altura;
	private int ancho;
	
	private Body body; 

	ArrayList<ArrayList<ImageView>> tileY = new ArrayList<ArrayList<ImageView>>();
	
	ArrayList<ImageView> tileX = new ArrayList<ImageView>();
	
	Animation animacion;
	private static final long ANIMATION_SPEED = 70000000; 
	
	public LayerAnimPlatanos(Game game) {
		super(game);
		animacion = new Animation(ANIMATION_SPEED, "objetosMApa/Banana_Animation_32x32x8f.png", 32, 32, 8);
		try {
	  		  map = new TMXMapReader().readMap(getClass().getResource("/map/MapaDefinitivo.tmx"));
	  		  int i = 0;
	  		  while (i < map.getLayerCount()) {
	  			String nombre = map.getLayer(i).getName();
	  			String nombreTiles = nombre.substring(4, nombre.length());
	  			if (nombreTiles.equals("Platanos")){
	  				System.out.println(map.getLayer(i).getName());
	  				tilePlatanos = (TileLayer) map.getLayer(i);
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
	  						if (tilePlatanos.getTileAt(x, y) == null) {
	  							tileX.add(nullV);
	  		            	}
	  						else {  							
	  		            		tile = tilePlatanos.getTileAt(x, y);
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
			for(int x = tileX.size() - 1; x >= 0; x--) {
				if(tileX.get(x) == null) {	
				} else {
					gc.drawImage(animacion.getCurrentFrame(), x*32, y*32);
				}
			}
		}
		
	}

	@Override
	public void update(float timeDifference) {
		animacion.update(timeDifference);
		
	}

	@Override
	protected void initBody(World world) {
	    
           BodyDef bodyDef = new BodyDef();
           bodyDef.type = BodyType.STATIC;
           bodyDef.position.set(x*32, y*32);

 			 PolygonShape shape = new PolygonShape();
 			 shape.setAsBox(32 / 1, 32 / 1);
 		
 			 body = getGame().getPhysics().getWorld().createBody(bodyDef);
 			 body.createFixture(shape, 0.0f); 
		
	}

}
