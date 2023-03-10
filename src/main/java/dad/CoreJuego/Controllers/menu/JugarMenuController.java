package dad.CoreJuego.Controllers.menu;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import dad.Api.GlobalStat.GlobalStat;
import dad.CoreJuego.Controllers.game.GameController;
import dad.CoreJuego.Elementos.main.MonkeyBrosApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Clase que Inicia un menu que ingresa el nombre del jugador y le da la posibilidad de acceder al juego con un boton
 * 
 * @author David Alejandro Hernández Alonso
 *
 */

// Gestión de la escena del menú de Juego
public class JugarMenuController implements Initializable {
	// view

	@FXML
	private Button menuAtrasButton;

	@FXML
	private Button menuEmprezarPartidaButton;

	@FXML
	private VBox menuOpcionesVBox;
	
	@FXML
	private TextField nombreUsuarioTextField;

	@FXML
	private BorderPane view;

	private BorderPane anteriorView;
	
	private Properties properties;
	
	// MediaPlayer
	
	private MediaPlayer mediaplayer;

	// controller
	GameController gameController;

	//TODO
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// https://www.youtube.com/watch?v=aOcow70vqb4
	}

	public JugarMenuController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menus/MenuJugarView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BorderPane getAnteriorView() {
		return anteriorView;
	}
	
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void setAnteriorView(BorderPane anteriorView) {
		this.anteriorView = anteriorView;
	}

	public VBox getMenuOpcionesVBox() {
		return menuOpcionesVBox;
	}

	public BorderPane getView() {
		return view;
	}
/**
 * 
 * @param event recibe el evento de raton al darle a un boton
 */
	@FXML
	void onAtrasClickAction(MouseEvent event) {
		MonkeyBrosApp.scene.setRoot(anteriorView);
	}
	
	/**
	 * Al empezar la partida cambia la vista de la escena a el juego <br>
	 * y cambia la música de fondo con el volumen establecido por <br>
	 * las propiedades
	 * @param event recibe el evento de raton al darle a un boton
	 */

	@FXML
	void onEmpezarPartidaClickAction(MouseEvent event) {
		gameController = new GameController();
		Media media = new Media(getClass().getResource("/audio/Jungle Groove Restored to HD - 128.mp3").toExternalForm());
		mediaplayer = new MediaPlayer(media);
		double volumen = MonkeyBrosApp.mediaPlayerMusica.getVolume();
		MonkeyBrosApp.mediaPlayerMusica.dispose();
		MonkeyBrosApp.mediaPlayerMusica = mediaplayer;
		MonkeyBrosApp.mediaPlayerMusica.setVolume(volumen);
		MonkeyBrosApp.mediaPlayerMusica.setAutoPlay(true);
		MonkeyBrosApp.scene.setRoot(gameController.getView());
		
		if(!nombreUsuarioTextField.getText().isBlank()) {
			try {
				GlobalStat globalStat = new GlobalStat();
				globalStat.createUser(nombreUsuarioTextField.getText());
				gameController.setGlobalStats(globalStat);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
