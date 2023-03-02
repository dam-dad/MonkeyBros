package dad.CoreJuego.Controllers.menu;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import dad.CoreJuego.Elementos.main.MonkeyBrosApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * 
 * 
 * 
 * @author David Alejandro Hernández Alonso
 *
 */

public class OpcionesMenuController implements Initializable {

	// view

	@FXML
	private Label ajustesLabel;

	@FXML
	private Label atrasLabel;

	@FXML
	private Label opcionesLabel;

	@FXML
	private Label sonidoLabel;

	@FXML
	private GridPane opcionesGridPane;

	@FXML
	private StackPane contenedorOpciones;

	@FXML
	private BorderPane view;

	private BorderPane anteriorView;

	private ControlesGridPane controlesGridPane;

	private SonidoGridPane sonidoGridPane;

	private OpcionesPantallaGridPane opcionesPantallaGridPane;

	private Properties properties;

	// controller

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// https://www.youtube.com/watch?v=aOcow70vqb4
		
	}

	public void actualizarControlesInterfazProperties() {
		controlesGridPane.setProperties(properties);
		controlesGridPane.checkNullProperties();
		controlesGridPane.cargarLabels();
		opcionesLabel.setText("Ajustes");
	}

	public void actualizarSonidoProperties() {
		sonidoGridPane.setProperties(properties);
		sonidoGridPane.loadMusicaValue();
		opcionesLabel.setText("Sonido");
	}
	
	public void actualizarPantallaProperties() {
		opcionesPantallaGridPane.setProperties(properties);
		opcionesPantallaGridPane.actualizarResolucionProperty();
		opcionesLabel.setText("Tamano de Pantalla");
	}

	public OpcionesMenuController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menus/MenuOpcionesView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
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
	void onAjustesClickAction(MouseEvent event) {
		try {
			contenedorOpciones.getChildren().remove(0);
			// opcionesGridPane.getChildren().remove(4);
		} catch (IndexOutOfBoundsException e) {
		}
		try {
			controlesGridPane = new ControlesGridPane();
			actualizarControlesInterfazProperties();
			contenedorOpciones.getChildren().add(controlesGridPane);
			GridPane.setRowSpan(controlesGridPane, 5);
			GridPane.setColumnSpan(controlesGridPane, 2);
		} catch (IllegalArgumentException e) {
		}
	}

	@FXML
	void onSonidoClickAction(MouseEvent event) {
		try {
			contenedorOpciones.getChildren().remove(0);
		} catch (IndexOutOfBoundsException e) {
		}
		try {
			sonidoGridPane = new SonidoGridPane();
			actualizarSonidoProperties();
			contenedorOpciones.getChildren().add(sonidoGridPane);
			GridPane.setRowSpan(sonidoGridPane, 5);
			GridPane.setColumnSpan(sonidoGridPane, 2);
		} catch (IllegalArgumentException e) {
		}
	}
	

	@FXML
	void onPantallaClickAction(MouseEvent event) {
		try {
			contenedorOpciones.getChildren().remove(0);
		} catch (IndexOutOfBoundsException e) {
		}
		try {
			opcionesPantallaGridPane = new OpcionesPantallaGridPane();
			contenedorOpciones.getChildren().add(opcionesPantallaGridPane);
			actualizarPantallaProperties();
			GridPane.setRowSpan(opcionesPantallaGridPane, 5);
			GridPane.setColumnSpan(opcionesPantallaGridPane, 2);
		} catch (IllegalArgumentException e) {
		}
	}

	@FXML
	void onAtrasClickAction(MouseEvent event) {
		// System.out.println("a");
		// opcionesMenuController.setAnteriorView(view);
		MonkeyBrosApp.scene.setRoot(anteriorView);
	}

	public GridPane getOpcionesGridPane() {
		return opcionesGridPane;
	}

}
