package dad.CoreJuego.Controllers.menu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.CoreJuego.Controllers.game.GameController;
import dad.CoreJuego.Elementos.main.MonkeyBrosApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

// Gestión de la escena del menú de Juego
public class PartidaPerdidaController implements Initializable {
	// view

	@FXML
	private Button menuVolverMenuButton;

	@FXML
	private BorderPane view;
	
	private BorderPane anteriorView;
	
	// MediaPlayer
	MediaPlayer mediaplayer;

	// controller
	GameController gameController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// https://www.youtube.com/watch?v=aOcow70vqb4
	}

	public PartidaPerdidaController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menus/MenuPartidaPerdidaView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BorderPane getAnteriorView() {
		return anteriorView;
	}

	public void setAnteriorView(BorderPane anteriorView) {
		this.anteriorView = anteriorView;
	}

	public BorderPane getView() {
		return view;
	}

	@FXML
	void onEmpezarPartidaClickAction(MouseEvent event) {
		
	}
	
	@FXML
    void volverAlMenuClickAction(MouseEvent event) {
		RootMenuController rootMenuController = new RootMenuController();
		Media media = new Media(getClass().getResource("/audio/Bonus Room Blitz Restored to HD.mp3").toExternalForm());
		mediaplayer = new MediaPlayer(media);
		double volumen = MonkeyBrosApp.mediaPlayerMusica.getVolume();
		MonkeyBrosApp.mediaPlayerMusica = mediaplayer;
		MonkeyBrosApp.mediaPlayerMusica.setVolume(volumen);
		MonkeyBrosApp.mediaPlayerMusica.setAutoPlay(true);
		MonkeyBrosApp.scene.setRoot(rootMenuController.getView());
    }

}
