package dad.CoreJuego.pruebas.Elementos;



import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CuerpoPersonaje extends Entity{

	Body bodyF;
	
	public CuerpoPersonaje(Game game, float posX, float posY) {
		super(game);
		
		this.x = posX;
		this.y = posY;
		
		// variables of character size
		this.width = 10f;
		this.height = 10f;

		InvocarPersonaje();
		
//		initBody();
	}
	
	public void InvocarPersonaje(){

		//The complete code snippet would look like:
		//body definition
		BodyDef bd = new BodyDef();
//		bd.position.set(50, 50);  
		bd.position.set(x,y);
		bd.type = BodyType.DYNAMIC;
		

		//define shape of the body.
		PolygonShape box = new PolygonShape();
//		box.set(null, 0);

		
//		box.setAsBox(x/5f, y/5f);  aqui se vuelve tamaño estandar

		box.setAsBox(x, y); 
		
		//		box.m_radius = 0.5f;  

		//define fixture of the body.
		FixtureDef fd = new FixtureDef();
		fd.shape = box;
//		fd.density = 0.5f;
		fd.friction = 50f;        
//		fd.restitution = 0.2f;


		//create the body and add fixture to it
		bodyF = getGame().getWorld().getWorldF().createBody(bd);
		bodyF.createFixture(fd);
		bodyF.getLinearVelocity();
	}
	
	public void render(GraphicsContext gc) {
				
		gc.setFill(Color.BLACK);
//		gc.fillOval(x, y, width, height);
		
//		gc.fillRect(toPixelPosX(x, width),toPixelPosY(y, height), width, height);
		
		gc.fillRect(x, y, width*5f, height*5f);
		
//		gc.fillRect(x, y, width, height);
		
//		System.out.println("la X es : " + x +" la Y es: " + y + "el width es: " + width + "el height es : " + height );
//		
//		Double XF= x*9.99;
//		
//		gc.fillRect(XF, y *9.99, width *9.99, height*9.99);
		
//		System.out.println("la X es : " + x +" la Y es: " + y + "el width es: " + width + "el height es : " + height );
		
		
	}
	
	public Body getBodyF() {
		return bodyF;
	}
	
	@Override
	public void update(float timeDifference) {
		x = bodyF.getPosition().x;
		y = bodyF.getPosition().y;
	}
	
}
