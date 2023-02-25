package dad.CoreJuego.Controllers.menu;

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.Api.GlobalStat.GlobalStat;
import dad.CoreJuego.Elementos.main.MonkeyBrosApp;
import io.github.fvarrui.globalstats.model.Rank;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class HighscoreMenuController implements Initializable {
	
	// model
	private ListProperty<Rank> listaScores = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	// view
	@FXML
	private StackPane contenedorScores;

	@FXML
	private Label highscoreLabel;
	
	@FXML
	private Label mensajeLabel;

	@FXML
	private ListView<Rank> highscoreLista;

	@FXML
	private GridPane scoresGridPane;

	@FXML
	private BorderPane view;

	private BorderPane anteriorView;
	
	// GlobalStats
	
	private GlobalStat globalStat;
	
	// controller
	
	DiplomaMenuController diplomaMenuController;
	
	public HighscoreMenuController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menus/MenuHighscoreView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// bindings
		highscoreLista.itemsProperty().bind(listaScores);
		
		// cell factory
		highscoreLista.setCellFactory(param -> new RankListCell());
		
		try {
			globalStat = new GlobalStat();
			//globalStat.createUser("Esto es otro test poniendo un nombre largo a ver que pasa");
			//globalStat.getUserStadistic("63f88e1f5f5e88076e8b457e");
			//globalStat.updateUser("63f88e1f5f5e88076e8b457e");
			//globalStat.resetUserScore("63f88e1f5f5e88076e8b457e");
			listaScores.setAll(globalStat.getLeaderBoard());
		} catch (SocketException e) {
			//e.printStackTrace();
			mensajeLabel.setText("La API de globalstats no funciona\n en estos momentos.");
			//listaScores
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public BorderPane getView() {
		return view;
	}

	public void setAnteriorView(BorderPane anteriorView) {
		this.anteriorView = anteriorView;
	}

	public BorderPane getAnteriorView() {
		return anteriorView;
	}

	@FXML
	void onAtrasClickAction(MouseEvent event) {
		MonkeyBrosApp.scene.setRoot(anteriorView);
	}
	
	@FXML
	void onDiplomaMenuClickAction(MouseEvent event) {
		diplomaMenuController = new DiplomaMenuController();
		diplomaMenuController.setAnteriorView(view);
		diplomaMenuController.setGlobalStat(globalStat);
		diplomaMenuController.cargarPlayersPropeties();
		MonkeyBrosApp.scene.setRoot(diplomaMenuController.getView());
	}
	
	
}
