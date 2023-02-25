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

	public GridPane getView() {
		return view;
	}

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

	private void setWindowResolution(int w, int h) {
		MonkeyBrosApp.primaryStage.setWidth(w);
		MonkeyBrosApp.primaryStage.setHeight(h);
		MonkeyBrosApp.primaryStage.centerOnScreen();
	}

	public void actualizarResolucionProperty() {
		if (properties.getProperty("resolucion") != null && selectedResolucion.get() != null
				&& !properties.getProperty("resolucion").equals("" + selectedResolucion.get())) {
			// Si ha cambiado se guarda
			properties.setProperty("resolucion", selectedResolucion.get().name());

			//System.out.println(selectedResolucion.get() + "test");
		} else {
			properties.setProperty("resolucion", "res1080x720p");
			selectedResolucion.set(Resolucion.res1080x720p);
			resolucionesListView.getSelectionModel().select(Resolucion.res1080x720p);
		}
		guardarProperties();
	}

	public void guardarProperties() {
		try {
			properties.store(new FileOutputStream(RootMenuController.RUTAFULL), "");
		} catch (IOException e) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error");
			alerta.setHeaderText("No se ha podido guardar la resolución de pantalla.");
			alerta.setContentText(e.getMessage());
			alerta.show();
		}
	}
}
