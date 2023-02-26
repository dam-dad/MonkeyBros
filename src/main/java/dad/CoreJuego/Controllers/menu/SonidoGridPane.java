package dad.CoreJuego.Controllers.menu;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import dad.CoreJuego.Elementos.main.MonkeyBrosApp;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;



public class SonidoGridPane extends GridPane implements Initializable {


	// view

	@FXML
	private Label musicaLabel;

	@FXML
	private Label opcionesLabel;

	@FXML
	private Label porcentajeMusicaLabel;

	@FXML
	private Label porcentajeVolumenLabel;

	@FXML
	private GridPane view;

	@FXML
	private Slider volumenSlider;

	@FXML
	private Slider musicaSlider;

	@FXML
	private Label volumenLabel;

	// datos

	private Properties properties = new Properties();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// bindings

		porcentajeVolumenLabel.textProperty().bind(Bindings.format("%.0f", volumenSlider.valueProperty()).concat("%"));
		porcentajeMusicaLabel.textProperty().bind(Bindings.format("%.0f", musicaSlider.valueProperty()).concat("%"));

		// listeners

		// Los bindings no actualizan constantemente el volumen a medida que se arrastra
		// el slider, por ello es necesario el uso de listeners
		musicaSlider.valueProperty().addListener((o, ov, nv) -> {
			
			MonkeyBrosApp.mediaPlayerMusica.setVolume((double) nv.intValue() / 100);
		});

		// TODO
//		volumenSlider.valueProperty().addListener((o, ov, nv) -> {
//			System.out.println(nv.intValue());
//			MonkeyBrosApp.mediaPlayerMusica.setVolume((double) nv.intValue() / 100);
//		});
	}

	public SonidoGridPane() {
		super();

		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/fxml/Menus/Componentes/SonidoGridPaneView.fxml"));
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

	public void guardarProperties() {
		try {
			properties.store(new FileOutputStream(RootMenuController.RUTAFULL), "");
		} catch (IOException e) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error");
			alerta.setHeaderText("No se ha podido guardar los controles del teclado.");
			alerta.setContentText(e.getMessage());
			alerta.show();
		}
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void loadVolumenValue() {
		int numVolum = 0;
		try {
			numVolum = Integer.parseInt(properties.getProperty("volumenAmount"));
		} catch (Exception e) {
		}
		if (numVolum != 0/* && properties.getProperty("volumenAmount") != null */) {
			volumenSlider.valueProperty().set(numVolum); // valor por defecto
		} else {
			volumenSlider.valueProperty().set(100);
			actualizarProperty("volumenAmount", volumenSlider.valueProperty().intValue());
		}
	}

	public void loadMusicaValue() {
		int numMusic = 0;
		try {
			numMusic = Integer.parseInt(properties.getProperty("musicaAmount"));
		} catch (Exception e) {
		}
		if (numMusic != 0) {
			musicaSlider.valueProperty().set(numMusic); // valor por defecto
		} else {
			musicaSlider.valueProperty().set(100);
			actualizarProperty("musicaAmount", volumenSlider.valueProperty().intValue());
		}
	}

	@FXML
	void onVolumenDragExit(MouseEvent event) {
		if (properties.getProperty("volumenAmount") == null) { // por si cambian el archivo manualmente
			properties.setProperty("volumenAmount", "100");
		}
		actualizarProperty("volumenAmount", volumenSlider.valueProperty().intValue());
	}

	@FXML
	void onMusicaDragExit(MouseEvent event) {
		if (properties.getProperty("musicaAmount") == null) {
			properties.setProperty("musicaAmount", "100");
		}
		actualizarProperty("musicaAmount", musicaSlider.valueProperty().intValue());
	}

	public void actualizarProperty(String s, int i) {
		if (properties.getProperty(s) != null && !properties.getProperty(s).equals("" + i)) {
			// Si ha cambiado se guarda
			properties.setProperty(s, "" + i);
		} else {
			properties.setProperty(s, "" + 100);
		}
		guardarProperties();
	}

}
