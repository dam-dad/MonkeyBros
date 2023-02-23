package dad.CoreJuego.Elementos;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Controller implements Initializable {

	// logic

	private GamePrueba game;

	// view

	@FXML
	private BorderPane view;

	@FXML
	private Canvas canvas;

	public Controller() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	
		game = new GamePrueba(canvas);
		// game.fpsProperty().addListener((o, ov, nv) -> System.out.println(nv +
		// "fps"));
		game.start();

		Media media= new Media(Paths.get("test.mp3").toUri().toString());
		MediaPlayer mediaPlayer= new MediaPlayer(media);
		mediaPlayer.play();
	}

	public BorderPane getView() {
		return view;
	}

	
}
