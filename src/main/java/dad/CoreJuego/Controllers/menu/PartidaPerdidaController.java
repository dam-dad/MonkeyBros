package dad.CoreJuego.Controllers.menu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.CoreJuego.Elementos.main.MonkeyBrosApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaPlayer;

// Gestión de la escena del menú de Juego

/**
 * 
 * Clase que carga la escena cuando pierdes las 3 vidas
 * 
 * @author David Alejandro Hernández Alonso
 *
 */

public class PartidaPerdidaController implements Initializable {
	// view

	@FXML
	private Button menuVolverMenuButton;

	@FXML
	private BorderPane view;

	private BorderPane anteriorView;

	// MediaPlayer
	MediaPlayer mediaplayer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * Constructor que carga la vista del fxml
	 * 
	 */

	public PartidaPerdidaController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menus/MenuPartidaPerdidaView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que devueleve la raiz de la vista
	 * 
	 * @return view retorna el BorderPane
	 */
	public BorderPane getAnteriorView() {
		return anteriorView;
	}

	/**
	 * Metodo que recibe la raiz de la vista
	 * 
	 * @param anteriorView recibe el borderPane
	 */

	public void setAnteriorView(BorderPane anteriorView) {
		this.anteriorView = anteriorView;
	}

	/**
	 * Metodo que devueleve la raiz de la vista
	 * 
	 * @return view retorna el BorderPane
	 */

	public BorderPane getView() {
		return view;
	}

	/**
	 * metodo que nos devuelve al menu y reinicia la musica del menu
	 * 
	 * @param event recibe el evento de raton al darle a un boton
	 */

	@FXML
	void volverAlMenuClickAction(MouseEvent event) {
		RootMenuController rootMenuController = new RootMenuController();
//		Media media = new Media(getClass().getResource("/audio/Bonus Room Blitz Restored to HD.mp3").toExternalForm());
//		mediaplayer = new MediaPlayer(media);
//		double volumen = MonkeyBrosApp.mediaPlayerMusica.getVolume();
//		MonkeyBrosApp.mediaPlayerMusica.dispose();
//		MonkeyBrosApp.mediaPlayerMusica = mediaplayer;
//		MonkeyBrosApp.mediaPlayerMusica.setVolume(volumen);
//		MonkeyBrosApp.mediaPlayerMusica.setAutoPlay(true);
		Scene scene = new Scene(rootMenuController.getView());
		MonkeyBrosApp.scene = scene;
		MonkeyBrosApp.primaryStage.setScene(scene);
	}

}
