package dad.CoreJuego.mapaEntidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
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

public class LayerEscaleras extends Entity {

	Map map;
	private TileLayer tileEscaleras;
	private int altura;
	private int ancho;
	
	private int yGuardado = 0;
	private int xGuardado = 0;
	
	private Body body; 

	ArrayList<ArrayList<ImageView>> tileY = new ArrayList<ArrayList<ImageView>>();
	
	ArrayList<ImageView> tileX = new ArrayList<ImageView>();
	
	public LayerEscaleras(Game game) {
		super(game);
		
		//Metodo para conseguir pos y sprites
		
		try {
	  		  map = new TMXMapReader().readMap(getClass().getResource("/map/MapaDefinitivo.tmx"));
	  		  int i = 0;
	  		  while (i < map.getLayerCount()) {
	  			String nombre = map.getLayer(i).getName();
	  			String nombreTiles = nombre.substring(5, nombre.length());
	  			//System.out.println(nombreTiles);
	  			if (nombreTiles.equals("Escaleras")){
	  				System.out.println(map.getLayer(i).getName());
	  				tileEscaleras = (TileLayer) map.getLayer(i);
		  			}
		  			i++;
	  		    }
	  		  	
	  			
	  			altura = map.getHeight();
	  			ancho = map.getWidth();
	  				  			
	  			boolean primerPunto = false;
	  			

	  			Tile tile;
	  	        int tid;
	  	        
	  	        HashMap<Integer,Image> tileHash = new HashMap<Integer,Image>();
	  	        Image tileImage = null;
	  	        ImageView nullV = null;
	  				
	  				for (int y=0; y < altura; y++) {
	  					tileX = new ArrayList<ImageView>();
	  					for (int x = 0; x < ancho; x++) {
	  						if (tileEscaleras.getTileAt(x, y) == null) {
	  							tileX.add(nullV);
	  		            	}
	  						else {  	
	  							
	  		            		tile = tileEscaleras.getTileAt(x, y);
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
	  			               
	  			               if (primerPunto == false) {
	  			            	   xGuardado = x;
	  			            	   yGuardado = y;
	  			            	   primerPunto = true;
	  			               }else {
			  			   			 initBody(game.getPhysics().getWorld(), x, y, xGuardado, yGuardado);
			  			   			
		  			            	   primerPunto = false;
		  			                }
	  			               
	  			            	}
	  			            }
	  			        }
	  			        tileY.add(tileX);
	  		        }
	  				//System.out.println("Tamaño array = " + tileY.size());
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
		//System.out.println(tileY.size());
		for(int y = altura - 1; y >= 0 ; y--) {
			tileX = tileY.get(y);
			for(int x = tileX.size() - 1; x >= 0; x--) {
				if(tileX.get(x) == null) {	
				} else {
					gc.drawImage(tileX.get(x).getImage(), x*32, y*32);
					gc.setFill(Color.PURPLE);
					gc.fillRect(x*32, y*32, 32, 32);
				}
			}
		}
	}
	

	@Override
	public void update(float timeDifference) {

		

	}

	protected void initBody(World world, int x, int y, int xGuardado, int yGuardado) {
		 	/* BodyDef bodyDef = new BodyDef();
             bodyDef.type = BodyType.STATIC;
             bodyDef.position.set(x*32, y*32); */
             
             
             /* Vec2 vs[4];
             vs[0].Set(1.7f, 0.0f);
             vs[1].Set(1.0f, 0.25f);
             vs[2].Set(0.0f, 0.0f);
             vs[3].Set(-1.7f, 0.4f);
              
             b2ChainShape chain;
             chain.CreateLoop(vs, 4); */
           //chain.createLoop(vector, 2);
 			
 			 //System.out.println("xGuardado = " + xGuardado + "yGuardado = " + yGuardado);
             
             //Vec2 vector[] = new Vec2[2];
             //System.out.println("xGuardado= " + xGuardado + " yGuardado= " + yGuardado + " x= " + x + " y= " + y);
             
             Vec2 vector1 = new Vec2(xGuardado, yGuardado); 
             Vec2 vector2 = new Vec2(x, y);
             
             float slope = (vector2.y - vector1.y) / (vector2.x - vector1.x);
             
             float angle = (float) Math.atan2(slope, 1);
             
             BodyDef bodyDef = new BodyDef();
             bodyDef.position.set(xGuardado*32, yGuardado*32);
             Body rampBody = world.createBody(bodyDef);
             
             Vec2[] vertices = {
            	        new Vec2(0, 0),
            	        new Vec2(vector2.x - vector1.x, vector2.y - vector1.y),
            	        new Vec2(0, vector2.y - vector1.y)
            	};
             
             PolygonShape rampShape = new PolygonShape();
             rampShape.set(vertices, vertices.length);

             // Create a FixtureDef for the ramp
             FixtureDef fixtureDef = new FixtureDef();
             fixtureDef.shape = rampShape;

             // Attach the FixtureDef to the Body object
             //Body body = world.createBody(bodyDef);
             rampBody.createFixture(fixtureDef);

             
             /* 
             vector[0] = new Vec2();
             vector[1] = new Vec2();
             vector[0].set(xGuardado, yGuardado);
             vector[1].set(x, y);
 			 
 			 ChainShape chain = new ChainShape();
 			 chain.createLoop(vector, 2);
 			 //chain.createChain(vector, 2);
 			 
 			FixtureDef fixtureDef = new FixtureDef();
 			fixtureDef.shape = chain;
 			fixtureDef.density = 1.0f; 
 			
// 			Body body = world.createBody(bodyDef);
			

 			 body = getGame().getPhysics().getWorld().createBody(bodyDef);
 			 body.createFixture(fixtureDef); */
// 			 body.createFixture(chain, 0.0f);
		
 			 // Codigo anterior
 			 
 			//PolygonShape shape = new PolygonShape();
 			 
 			 //System.out.println("X Guardado= " + xGuardado + " Y Guardado= " + yGuardado);
 			 //System.out.println("X= " + x + " Y= " + y);

 			 //vector.set((xGuardado+x)/2, (yGuardado+y)/2);

 			 //System.out.println(vector);
 			 
 			 //shape.setAsBox(96, 10, vector, 60);
	}

	@Override
	protected void initBody(World world) {
		// TODO Auto-generated method stub
		
	}

}
