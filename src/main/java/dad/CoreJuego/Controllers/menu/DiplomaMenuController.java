package dad.CoreJuego.Controllers.menu;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import dad.Api.GlobalStat.GlobalStat;
import dad.CoreJuego.Elementos.main.MonkeyBrosApp;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * 
 * Clase que implementa la vista que mas tarde imprimira el diploma en
 * JasperReport
 * 
 * @author David Alejandro Hernández Alonso
 *
 */

public class DiplomaMenuController implements Initializable {

	// model
	private ListProperty<Player> playerList = new SimpleListProperty<>(FXCollections.observableArrayList());

	@FXML
	private Label atrasLabel;

	@FXML
	private GridPane diplomaGridPane;

	@FXML
	private StackPane diplomaStackpane;

	@FXML
	private Button generarButton;

	@FXML
	private Label highscoreLabel;

	@FXML
	private Label mensajeLabel;

	@FXML
	private Label jugadoresRegistradosLabel;

	@FXML
	private ListView<Player> jugadoresLista;

	@FXML
	private BorderPane view;

	private BorderPane anteriorView;

	// properties

	private Properties playersProperties;

	// GlobalStats

	private GlobalStat globalStat;

	/**
	 * Constructor que carga el FXML
	 * 
	 */

	public DiplomaMenuController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menus/MenuDiplomaView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Metodo que Inicializa la vista, con las propiedades y los elementos de la
	 * vista junto con los bindings
	 * 
	 */

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// prepare data
		playersProperties = new Properties();

		// cell factory
		jugadoresLista.setCellFactory(param -> new PlayerListCell());

		// bindings
		jugadoresLista.itemsProperty().bind(playerList);

		generarButton.disableProperty()
				.bind(Bindings.isNull(jugadoresLista.getSelectionModel().selectedItemProperty()));

	}

	/**
	 * Devuelve la raiz de la escena
	 * 
	 * @return view raiz de la view
	 */

	public BorderPane getView() {
		return view;
	}

	/**
	 * metodo setter que cambia las propiedades del player
	 * 
	 * @param playersProperties recibe las propiedades del archivo properties
	 */
	public void setPlayersProperties(Properties playersProperties) {
		this.playersProperties = playersProperties;
	}

	/**
	 * metodo que lleva a la escena anterior
	 * 
	 * @param anteriorView recibe un borderPane que es el root de la escena anterior
	 */

	public void setAnteriorView(BorderPane anteriorView) {
		this.anteriorView = anteriorView;
	}

	/**
	 * metodo que lleva a la escena anterior
	 * 
	 */

	public BorderPane getAnteriorView() {
		return anteriorView;
	}

	/**
	 * metodo que carga el player guardado en el fichero Properties en caso
	 * contrario lanza una excepcion
	 * 
	 * 
	 * @throws IOException si no se encuentran jugadores.
	 * @throws Exception   si existe algun problema al cargar los datos desde el
	 *                     fichero properties
	 */

	public void cargarPlayersPropeties() {

		try {
			playersProperties.load(new FileInputStream(RootMenuController.RUTA_PLAYER_IDS));

			for (Object key : playersProperties.keySet()) {
				if (playersProperties.size() < 1) {
					mensajeLabel.setText("No se han encontrado jugadores\n previamente registrados");
				} else {
					String name = globalStat.getUserSection((String) key).getUserRank().getName();
					int points = globalStat.getUserSection((String) key).getUserRank().getValue();
					Player p = new Player(playersProperties.getProperty((String) key), name, points);
					playerList.add(p);
				}
			}
		} catch (IOException e) {
			mensajeLabel.setText("No se han encontrado jugadores\n previamente registrados");
		} catch (Exception e) {
			e.printStackTrace();
			mensajeLabel.setText("Error al cargar datos de la API\n de GlobalStats");
		}
	}

	/**
	 * metodo Setter que recibe y sustitye la referencia en memoria de la clase que
	 * apunta @see {@link GlobalStat#GlobalStat()}
	 * 
	 * @param globalStat le llega la clase GlobalStats
	 */

	public void setGlobalStat(GlobalStat globalStat) {
		this.globalStat = globalStat;
	}

	/**
	 * metodo que lleva a la escena anterior
	 * 
	 * @param event recibe el evento de raton al darle a un boton
	 */

	@FXML
	void onAtrasClickAction(MouseEvent event) {
		MonkeyBrosApp.scene.setRoot(anteriorView);
	}

	/**
	 * Metodo que recoge el elemento seleccionado de la lista de los registro de los jugadores y genera un diploma 
	 * en formato PDF con los datos usados como parametros dentro del diploma
	 * 
	 * @param event recibe el evento de raton al darle a un boton
	 */
	
	@FXML
	void onGenerarDiplomaAction(MouseEvent event) {
		try {
			// compila el informe
			JasperReport report = JasperCompileManager
					.compileReport(DiplomaMenuController.class.getResourceAsStream("/reports/DiplomaMonkeyBros.jrxml"));

			// mapa de parámetros para el informe
			Map<String, Object> parameters = new HashMap<String, Object>();

			// generar el informe (combinar el informe compilado con los datos)
			ArrayList<Player> playerArrayList = new ArrayList<Player>(
					Arrays.asList(jugadoresLista.getSelectionModel().selectedItemProperty().get()));

			JasperPrint print = JasperFillManager.fillReport(report, parameters,
					new JRBeanCollectionDataSource(playerArrayList));

			/**
			 * Exportar el informe a un fichero PDF Si no existe la carpeta "pdf", se crea
			 * para posteriormente meter los arhcivos generados allí
			 */
			if (!RootMenuController.RUTA_PDF_FOLDER.exists()) {
				RootMenuController.RUTA_PDF_FOLDER.mkdir();
			}

			JasperExportManager.exportReportToPdfFile(print,
					RootMenuController.RUTA_PDF_FOLDER.getPath() + "/Diploma Monkey.pdf");

			// Abrir el archivo PDF generado con el programa predeterminado del sistema
			try {
				Desktop.getDesktop()
						.open(new File(RootMenuController.RUTA_PDF_FOLDER.getPath() + File.separator + "Diploma Monkey.pdf"));
			} catch (IOException e) {
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setTitle("Error");
				alerta.setHeaderText("No se ha podido abrir el diploma generado.");
				alerta.setContentText(e.getMessage());
				alerta.show();
			}

		} catch (JRException e) {
			e.printStackTrace();
		}
	}
}
