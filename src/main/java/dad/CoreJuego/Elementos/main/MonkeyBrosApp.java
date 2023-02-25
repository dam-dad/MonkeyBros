package dad.CoreJuego.Elementos.main;

import java.util.Properties;

import dad.CoreJuego.Controllers.menu.RootMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class MonkeyBrosApp extends Application {

	public static Stage primaryStage;
	public static Scene scene;
	public static Properties properties;
	public static MediaPlayer mediaPlayerMusica;

	// Es necesario inicializar el RootController en el start ya que al iniciarse
	// requiere que primaryStage no sea nulo, debido a que se usa para modificar el tamaño de
	// la ventana en el initialize() del rootController
	private RootMenuController controller;

	@Override
	public void start(Stage primaryStage) throws Exception {

		MonkeyBrosApp.primaryStage = primaryStage;
		controller = new RootMenuController();
		scene = new Scene(controller.getView());

		primaryStage.setScene(scene);
		primaryStage.setTitle("MonkeyBros");
		primaryStage.setResizable(false);
		// primaryStage.getIcons().add(new
		// Image(MonkeyBrosApp.class.getResourceAsStream("/images/calendar-16x16.png")));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
