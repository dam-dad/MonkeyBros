package dad.CoreJuego.Elementos.main;

import java.util.Properties;

import dad.CoreJuego.Controllers.game.GameController;
import dad.CoreJuego.Controllers.menu.RootMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * Clase Intermedia para empezar el programa junto con la clase  @see Lanzador#main(String[])
 * 
 * @author David Alejandro
 *
 */

public class MonkeyBrosApp extends Application {

	public static Stage primaryStage;
	public static Scene scene;
	public static Properties properties;
	public static MediaPlayer mediaPlayerMusica;

	// Es necesario inicializar el RootController en el start ya que al iniciarse
	// requiere que primaryStage no sea nulo, debido a que se usa para modificar el tamaño de
	// la ventana en el initialize() del rootController
	private RootMenuController controller;
	private GameController game;

	
	
	/**
	 * metodo que empieza toda la Visualizacion de las escenas
	 * 
	 *  @param primaryStage obtiene la escena principal con la que se Inicializa JavaFx
	 */
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		MonkeyBrosApp.primaryStage = primaryStage;
//		controller = new RootMenuController();
//		scene = new Scene(controller.getView());
		
//		game = new GameController();
		controller = new RootMenuController();
		scene = new Scene(controller.getView());

		primaryStage.setScene(scene); 
		primaryStage.setTitle("MonkeyBros");
		primaryStage.getIcons().add(new Image(MonkeyBrosApp.class.getResourceAsStream("/images/IconoApp/MonkeyBros.png")));
		primaryStage.setResizable(false);
		primaryStage.show();
	}

}
