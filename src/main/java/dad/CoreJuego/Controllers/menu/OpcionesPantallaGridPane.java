package dad.CoreJuego.Controllers.menu;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.ResourceBundle;

import dad.CoreJuego.Elementos.main.MonkeyBrosApp;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

/**
 * 
 * Clase que nos permite elegir y guardar
 * 
 * @author David Alejandro Hernández Alonso
 *
 */

public class OpcionesPantallaGridPane extends GridPane implements Initializable {

	// model

	private ListProperty<Resolucion> resolucionesListProperty = new SimpleListProperty<>(
			FXCollections.observableArrayList());
	private ObjectProperty<Resolucion> selectedResolucion = new SimpleObjectProperty<>();
	// view

	@FXML
	private Label p1080Label;

	@FXML
	private Label p480Label;

	@FXML
	private Label p720Label;

	@FXML
	private Label tituloLabel;

	@FXML
	private ListView<Resolucion> resolucionesListView;

	@FXML
	private GridPane view;

	Properties properties;

	HashMap<String, String> patrones;

	/**
	 * Metodo que inicia un array con todas las resoluciones
	 */

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Dimension dimensiones = Toolkit.getDefaultToolkit().getScreenSize();
		resolucionesListView.itemsProperty().bind(resolucionesListProperty);
		Resolucion[] resArray = {};
		for (int i = 0; i < Resolucion.values().length; i++) {
			resArray = Arrays.copyOf(resArray, resArray.length + 1);
			// Mostrar solo las resoluciones que quepan en el monitor
			if (Resolucion.values()[i].getWidth() <= dimensiones.getWidth()
					&& Resolucion.values()[i].getHeight() <= dimensiones.getHeight()) {
				resolucionesListProperty.add((Resolucion.values()[i]));
			}
		}
	}

	/**
	 * Constructor que carga la vista
	 * 
	 */
	
	public OpcionesPantallaGridPane() {
		super();

		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/fxml/Menus/Componentes/OpcionesPantallaGridPaneView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Metodo que devueleve la raiz de la vista
	 * 
	 * @return view retorna el GridPane
	 */

	public GridPane getView() {
		return view;
	}

	/**
	 * Metodo que recibe el fichero
	 * 
	 * @param properties recibe el fichero de propiedades
	 */

	public void setProperties(Properties properties) {
		this.properties = properties;

		for (Resolucion res : Resolucion.values()) {
			if (res.name().equals(properties.getProperty("resolucion"))) {
				selectedResolucion.set(res);
				resolucionesListView.getSelectionModel().select(res);
				break;
			}
		}

		resolucionesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Resolucion>() {

			@Override
			public void changed(ObservableValue<? extends Resolucion> observable, Resolucion oldValue,
					Resolucion newValue) {
				selectedResolucion.set(newValue);
				actualizarResolucionProperty();
				setWindowResolution(newValue.getWidth(), newValue.getHeight());

			}
		});
	}

	/**
	 * Metodo que recibe la recibe la resolucion
	 * 
	 * @param w recibe el alto
	 * @param h recibe el ancho
	 */

	private void setWindowResolution(int w, int h) {
		MonkeyBrosApp.primaryStage.setWidth(w);
		MonkeyBrosApp.primaryStage.setHeight(h);
		MonkeyBrosApp.primaryStage.centerOnScreen();
	}

	/**
	 * Metodo que reescala la ventana
	 * 
	 */

	public void actualizarResolucionProperty() {
		if (properties.getProperty("resolucion") != null && selectedResolucion.get() != null
				&& !properties.getProperty("resolucion").equals("" + selectedResolucion.get())) {
			// Si ha cambiado se guarda
			properties.setProperty("resolucion", selectedResolucion.get().name());
		} else {
			properties.setProperty("resolucion", "res1080x720p");
			selectedResolucion.set(Resolucion.res1080x720p);
			resolucionesListView.getSelectionModel().select(Resolucion.res1080x720p);
		}
		guardarProperties();
	}

	/**
	 * Metodo que guarda la propiedades en el fichero
	 * 
	 */
	
	public void guardarProperties() {
		try {
			properties.store(new FileOutputStream(RootMenuController.RUTAFULL), "");
			MonkeyBrosApp.properties = properties;
		} catch (IOException e) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error");
			alerta.setHeaderText("No se ha podido guardar la resolución de pantalla.");
			alerta.setContentText(e.getMessage());
			alerta.show();
		}
	}
}
