package dad.CoreJuego.Controllers.menu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import dad.CoreJuego.Elementos.main.MonkeyBrosApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class RootMenuController implements Initializable {

	// view

	@FXML
	private BorderPane view;

	@FXML
	private Button menuJugarBoton;

	@FXML
	private Button menuHighscoreBoton;

	@FXML
	private Button menuOpcionesBoton;

	@FXML
	private Button menuSalirBoton;

	@FXML
	private ImageView menuTitulo;

	@FXML
	private VBox menuOpcionesVBox;

	// controllers

	private JugarMenuController jugarMenuController;

	private OpcionesMenuController opcionesMenuController;

	private HighscoreMenuController highscoreMenuController;

	// properties

	private Properties properties = new Properties();

	// paths

	public static final File RUTA_PDF_FOLDER = new File(
			System.getProperty("user.home") + File.separator + ".MonkeyBros" + File.separator + "pdf");
	public static final File RUTA_PLAYERS_FOLDER = new File(
			System.getProperty("user.home") + File.separator + ".MonkeyBros" + File.separator + "players");
	public static final File RUTA_CONFIG_FOLDER = new File(
			System.getProperty("user.home") + File.separator + ".MonkeyBros" + File.separator + "configuracion");
	public static final String RUTAFULL = RUTA_CONFIG_FOLDER.getPath() + File.separator + "configuracion.props";

	public static final String RUTA_PLAYER_IDS = System.getProperty("user.home") + File.separator + ".MonkeyBros"
			+ File.separator + "players" + File.separator + "player_ids.props";

	// audio

	private MediaPlayer mediaplayer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Media media = new Media(getClass().getResource("/audio/Bonus Room Blitz Restored to HD.mp3").toExternalForm());
		mediaplayer = new MediaPlayer(media);
		mediaplayer.setVolume(100.0);
		if(MonkeyBrosApp.mediaPlayerMusica != null) {
			MonkeyBrosApp.mediaPlayerMusica.dispose();
		}
		MonkeyBrosApp.mediaPlayerMusica = mediaplayer;
		MonkeyBrosApp.mediaPlayerMusica.setAutoPlay(true);

		/* Cargar propiedades y en caso de no encontrarlas, crearlas y usarlas */
		try {
			properties.load(new FileInputStream(RootMenuController.RUTAFULL));
			MonkeyBrosApp.properties = properties;
			// java.awt.event.KeyEvent.getKeyText(' '); // en mayus
			Resolucion res = Resolucion.valueOf(properties.getProperty("resolucion"));
			/*
			 * Si surge algún problema al cargar una resolución, se selecciona la resolución
			 * por defecto (1080x720)
			 */
			if (res == null) {
				res = Resolucion.res1080x720p;
			}
			/*
			 * Se cambia la resolución de la ventana partiendo de las propiedades cargadas
			 * del fichero .properties
			 */
			MonkeyBrosApp.primaryStage.setWidth(res.getWidth());
			MonkeyBrosApp.primaryStage.setHeight(res.getHeight());

			/*
			 * Se carga el volumen del fichero de properties y si algo falla se pone por
			 * defecto al 100%
			 */
			int num = 100;
			try {
				num = Integer.parseInt(properties.getProperty("musicaAmount"));
			} catch (NumberFormatException e) {
			}

			/*
			 * El volumen del primaryStage finalmente se cambia por el valor cargado de las
			 * properties
			 */
			MonkeyBrosApp.mediaPlayerMusica.setVolume((double) num / 100);

		} catch (FileNotFoundException e) {
			/*
			 * Si el fichero no se puede cargar porque no existe previamente, se crean unos
			 * valores para las propiedades por defecto
			 */
			//java.awt.event.KeyEvent.ke
			
			try {
				// https://stackoverflow.com/questions/15313469/java-keyboard-keycodes-list
				properties.setProperty("adelante", charANombreKeyCode('d'));
				// el KeyEvent de java.awt y no de javafx
				properties.setProperty("detras", charANombreKeyCode('a'));
				properties.setProperty("saltar", charANombreKeyCode(' '));
				properties.setProperty("escalar", charANombreKeyCode('w'));
				properties.setProperty("bajar", charANombreKeyCode('s'));
				properties.setProperty("volumenAmount", "100");
				properties.setProperty("musicaAmount", "100");
				properties.setProperty("resolucion", "res1080x720p");

				/*
				 * Si la carpeta para los archivos del programa (MonkeyBros) no existe, se crea
				 * una previamente para almacenar posteriormente las properties
				 */
				if (!RootMenuController.RUTA_CONFIG_FOLDER.exists()) {
					RootMenuController.RUTA_CONFIG_FOLDER.mkdirs();
				}

				properties.store(new FileOutputStream(RootMenuController.RUTAFULL), "");

			} catch (IOException e1) {
				/*
				 * Si se produce cualquier otro error al GUARDAR las properties, sale un Alert
				 * avisando de lo sucedido con el error en cuestión
				 */
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setTitle("Error");
				alerta.setHeaderText("No se han podido guardar los controles del teclado.");
				alerta.setContentText(e.getMessage());
				alerta.show();
			}
		} catch (IOException e) {
			/*
			 * Si se produce cualquier otro error al CARGAR las properties, sale un Alert
			 * avisando de lo sucedido con el error en cuestión
			 */
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error");
			alerta.setHeaderText("No se han podido cargar los controles del teclado.");
			alerta.setContentText(e.getMessage());
			alerta.show();
		}
	}

	/* Constructor para cargar la vista del archivo .fxml */
	public RootMenuController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menus/MenuPrincipalView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retorna un String como la clave numérica de teclado que tiene el char
	 * ingresado como parámetro (P.ej, el char "espacio" (' ') retornará un String
	 * cuyo valor sea "32")
	 * 
	 * @param c El caracter a ingresar en la función
	 * @return String como clave numérica de teclado del char ingresado
	 * @author David Alejandro Hernández Alonso
	 */
	public String charANombreKeyCode(char c) {
		return "" + java.awt.event.KeyEvent.getExtendedKeyCodeForChar(c);
	}

	public void setView(BorderPane view) {
		this.view = view;
	}

	public BorderPane getView() {
		return view;
	}

	// Cambiar al menu de jugar
	/**
	 * Al presionar el botón de "Jugar" del menú principal, se cambiará la escena
	 * <br>
	 * al "submenú" de Jugar y se asignará una vista anterior (anteriorView) para
	 * <br>
	 * poder ir hacia el meú anterior.
	 * 
	 * @param event : El FXMLLoader se encarga de vincular el evento con los
	 *              componentes <br>
	 *              correspondientes de la aplicación
	 * @throws IOException
	 * @author David Alejandro Hernández Alonso
	 */
	@FXML
	void onJugarClickAction(MouseEvent event) throws IOException {
		jugarMenuController = new JugarMenuController();
		jugarMenuController.setAnteriorView(view);
		jugarMenuController.setProperties(properties);
		MonkeyBrosApp.scene.setRoot(jugarMenuController.getView());
		// https://stackoverflow.com/questions/37106379/why-doesnt-my-scene-pop-up-when-changing-scenes-javafx
	}

	// Cambiar al menu de Highscore
	/**
	 * Al presionar el botón de "Highscore" del menú principal, se cambiará la
	 * escena <br>
	 * al "submenú" de Highscore y se asignará una vista anterior (anteriorView)
	 * para <br>
	 * poder ir hacia el meú anterior.
	 * 
	 * @param event : El FXMLLoader se encarga de vincular el evento con los
	 *              componentes <br>
	 *              correspondientes de la aplicación
	 * @throws IOException
	 * @author David Alejandro Hernández Alonso
	 */
	@FXML
	void onHighscoreClickAction(MouseEvent event) {
		highscoreMenuController = new HighscoreMenuController();
		highscoreMenuController.setAnteriorView(view);
		MonkeyBrosApp.scene.setRoot(highscoreMenuController.getView());
	}

	// Cambiar al menu de opciones
	/**
	 * 
	 * Al presionar el botón de "Opciones" del menú principal, se cambiará la escena
	 * <br>
	 * al "submenú" de Opciones y se asignará una vista anterior (anteriorView) para
	 * <br>
	 * poder ir hacia el meú anterior.
	 * 
	 * @param event : El FXMLLoader se encarga de vincular el evento con los
	 *              componentes <br>
	 *              correspondientes de la aplicación
	 * @throws IOException
	 * @author David Alejandro Hernández Alonso
	 */
	@FXML
	void onOpcionesClickAction(MouseEvent event) {
		opcionesMenuController = new OpcionesMenuController();
		opcionesMenuController.setAnteriorView(view);
		opcionesMenuController.setProperties(properties);
		MonkeyBrosApp.scene.setRoot(opcionesMenuController.getView());
	}

	// Salir del juego
	/**
	 * 
	 * Al presionar el botón de "Salir" del menú principal, se cierra la <br>
	 * aplicación y se termina el programa por completo. <br>
	 * 
	 * @param event : El FXMLLoader se encarga de vincular el evento con los
	 *              componentes <br>
	 *              correspondientes de la aplicación
	 * @throws IOException
	 * @author David Alejandro Hernández Alonso
	 */
	@FXML
	void onSalirClickAction(MouseEvent event) {
		Stage appVentana = (Stage) menuSalirBoton.getScene().getWindow();
		appVentana.close();
	}
}
