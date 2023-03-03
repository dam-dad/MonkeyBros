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
 * clase que carga la vista del menu de opciones y gestiona las propiedades de la aplicacion
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
		
	}

	/**
	 * Metodo que actualiza las propiedades del componente del Teclado
	 * 
	 */
	
	public void actualizarControlesProperties() {
		controlesGridPane.setProperties(properties);
		controlesGridPane.checkNullProperties();
		controlesGridPane.cargarLabels();
		opcionesLabel.setText("Ajustes");
	}

	/**
	 * Metodo que actualiza las propiedades del componente del Sonido
	 */
	
	public void actualizarSonidoProperties() {
		sonidoGridPane.setProperties(properties);
		sonidoGridPane.loadMusicaValue();
		opcionesLabel.setText("Sonido");
	}
	
	/**
	 * Metodo que actualiza las propiedades del componente de la pantalla
	 */
	
	public void actualizarPantallaProperties() {
		opcionesPantallaGridPane.setProperties(properties);
		opcionesPantallaGridPane.actualizarResolucionProperty();
		opcionesLabel.setText("Tamano de Pantalla");
	}

	/**
	 * Constructor que carga la vista   
	 * 
	 */
	
	public OpcionesMenuController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menus/MenuOpcionesView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que recibe el fichero propiedades
	 * 
	 * @param properties
	 */
	
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * metodo que devuelve la escena anterior
	 * 
	 * @return la raiz de la escena
	 */
	
	public BorderPane getAnteriorView() {
		return anteriorView;
	}

	/**
	 * metodo que devuelve la escena anterior
	 * 
	 * @return la raiz de la escena
	 */
	
	public void setAnteriorView(BorderPane anteriorView) {
		this.anteriorView = anteriorView;
	}

	/**
	 * metodo que devuelve la escena
	 * 
	 * @return la raiz de la escena
	 */
	
	public BorderPane getView() {
		return view;
	}

	/**
	 * Metodo que muestra la vista del controlador de los controles de teclado en el centro del menu de opciones
	 * 
	 * @param event recibe el evento de raton al darle a un boton
	 */
	
	@FXML
	void onAjustesClickAction(MouseEvent event) {
		try {
			contenedorOpciones.getChildren().remove(0);
		} catch (IndexOutOfBoundsException e) {
		}
		try {
			controlesGridPane = new ControlesGridPane();
			actualizarControlesProperties();
			contenedorOpciones.getChildren().add(controlesGridPane);
			GridPane.setRowSpan(controlesGridPane, 5);
			GridPane.setColumnSpan(controlesGridPane, 2);
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Metodo que muestra la vista del controlador de sonido en el centro del menu de sonido
	 * 
	 * @param event recibe el evento de raton al darle a un boton
	 */
	
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
	
	/**
	 * Metodo que muestra la vista del controlador de la pantalla en el centro del menu de pantalla
	 * 
	 * @param event recibe el evento de raton al darle a un boton
	 */
	
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

	/**
	 * Metodo que cambia la vista al menu principal
	 * 
	 * @param event recibe el evento de raton al darle a un boton
	 */
	
	@FXML
	void onAtrasClickAction(MouseEvent event) {
		MonkeyBrosApp.scene.setRoot(anteriorView);
	}

	public GridPane getOpcionesGridPane() {
		return opcionesGridPane;
	}

}
