package dad.CoreJuego.Controllers.menu;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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

	public DiplomaMenuController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menus/MenuDiplomaView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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

	public BorderPane getView() {
		return view;
	}

	public void setPlayersProperties(Properties playersProperties) {
		this.playersProperties = playersProperties;
	}

	public void setAnteriorView(BorderPane anteriorView) {
		this.anteriorView = anteriorView;
	}

	public BorderPane getAnteriorView() {
		return anteriorView;
	}

	public void cargarPlayersPropeties() {

		// globalStat.getUserStadistic();

		// playersProperties.propertyNames()
		try {
			playersProperties.load(new FileInputStream(RootMenuController.RUTA_PLAYER_IDS));
//			
//			List<String> listaP = playersProperties. .values().stream()
//					.map(obj ->{
//						String p = (String) obj;
//						System.out.println(p);
//						return p;
//					}).collect(Collectors.toList());
//				System.out.println(listaP.size());

			for (Object key : playersProperties.keySet()) {
//				System.out.println(key + "=" + playersProperties.getProperty((String) key));
				if (playersProperties.size() < 1) {
					mensajeLabel.setText("No se han encontrado jugadores\n previamente registrados");
				} else {
					String name = globalStat.getUserSection((String) key).getUserRank().getName();
					int points = globalStat.getUserSection((String) key).getUserRank().getValue();
					Player p = new Player((String) key, name, points);
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

	public void setGlobalStat(GlobalStat globalStat) {
		this.globalStat = globalStat;
	}

	@FXML
	void onAtrasClickAction(MouseEvent event) {
		MonkeyBrosApp.scene.setRoot(anteriorView);
	}

	@FXML
	void onGenerarDiplomaAction(MouseEvent event) {
//		Section section = globalStat.getUserSection("63f88b555f5e88fd6d8b4574");
		try {
			// compila el informe
			JasperReport report = JasperCompileManager
					.compileReport(DiplomaMenuController.class.getResourceAsStream("/reports/DiplomaMonkeyBros.jrxml"));
			
			// mapa de parámetros para el informe
			Map<String, Object> parameters = new HashMap<String, Object>();
			
			// generar el informe (combinar el informe compilado con los datos) 
			ArrayList<Player> playerArrayList = new ArrayList<Player>(Arrays.asList(jugadoresLista.getSelectionModel().selectedItemProperty().get()));
	        JasperPrint print  = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(playerArrayList));
	        
	        
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
