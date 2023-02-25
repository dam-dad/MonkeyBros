package dad.CoreJuego.Elementos.main;


import dad.CoreJuego.Controllers.game.GameController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

	public static Stage primaryStage;

	private GameController controller = new GameController();

	@Override
	public void start(Stage primaryStage) throws Exception {

		Main.primaryStage = primaryStage;

		primaryStage.setTitle("Bouncing Ball");
		primaryStage.setScene(new Scene(controller.getView()));
		primaryStage.show();

	}

}
