package dad.CoreJuego.pruebas.Elementos.main;


import dad.CoreJuego.pruebas.Elementos.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

	public static Stage primaryStage;

	private Controller controller = new Controller();

	@Override
	public void start(Stage primaryStage) throws Exception {

		Main.primaryStage = primaryStage;

		primaryStage.setTitle("Bouncing Ball");
		primaryStage.setScene(new Scene(controller.getView()));
		primaryStage.show();

	}

}
