package dad.CoreJuego.Controllers.menu;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import dad.CoreJuego.Elementos.main.MonkeyBrosApp;
import javafx.animation.PauseTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 * 
 * Clase que carga las teclas que gestiona las propiedades del movimiento del personaje
 * 
 * @author David Alejandro Hernández Alonso
 *
 */

public class ControlesGridPane extends GridPane implements Initializable {

	// model

	IntegerProperty num = new SimpleIntegerProperty(0);

	// view

//	@FXML
//	private Label ajustesLabel;

	@FXML
	private Label bajarLabel;

	@FXML
	private Label bajarLabelClick;

	@FXML
	private Label delanteLabelClick;

	@FXML
	private Label detrasLabel;

	@FXML
	private Label detrasLabelClick;

	@FXML
	private Label escalarLabel;

	@FXML
	private Label escalarLabelClick;

	@FXML
	private Label opcionesLabel;

	@FXML
	private Label saltarLabel;

	@FXML
	private Label saltarLabelClick;

	@FXML
	private GridPane view;

	// datos

	private EventHandler<KeyEvent> keyEventHandler;

	private Properties properties;

	private List<Label> listaKeyControlLabels;

	private boolean keyEventActivo = false;
	
	private boolean seguir;
	
	HashMap<String, String> patrones;

	/**
	 * Metodo que carga la vista y guarda la llave/relacion con la tecla de accion
	 * 
	 */
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listaKeyControlLabels = view.getChildren().stream().filter(node -> node instanceof Label)
				.map(node -> (Label) node).filter(label -> "keyControl".equals(label.getId()))
				.collect(Collectors.toList());
		patrones = new HashMap<>();
		patrones.put("saltar", " ");
		patrones.put("adelante", "d");
		patrones.put("escalar", "w");
		patrones.put("detras", "a");
		patrones.put("bajar", "s");
	}

	/**
	 * Metodo que carga el nombre del valor de las propiedades del fichero en los labels
	 */
	
	public void cargarLabels() {
		propertiesListHandler(escalarLabelClick, "escalar");
		propertiesListHandler(detrasLabelClick, "detras");
		propertiesListHandler(bajarLabelClick, "bajar");
		propertiesListHandler(delanteLabelClick, "adelante");
		propertiesListHandler(saltarLabelClick, "saltar");
		keyEventActivo = false;
	}

	/**
	 * metodo que recibe una tecla y el devuelve el valor numerica de la tecla
	 * 
	 * @param s recibe un String que es la letra pulsada
	 * @return el valor numerico necesario para guardar en el fichero de propiedades
	 */
	
	public static String keyEventCodeToStringName(String s) {
		int num = 0;
		String nombre = "Undefined";
		try {
			num = Integer.parseInt(s);
			nombre = java.awt.event.KeyEvent.getKeyText(num);
		} catch (NumberFormatException e) {
		}
		return nombre;
	}

	/**
	 * Metodo que cambia el texto del label ingresado por el valor textual que recibe de la pulsacion
	 * 
	 * @param l recibe un label existente de la vista
	 * @param s recibe el valor numerico de la tecla en formato de String
	 */
	
	public void propertiesListHandler(Label l, String s) {
		String nomprop = properties.getProperty(s);
		l.setText(keyEventCodeToStringName(nomprop));
	}

	/**
	 * metodo que rellena los nulls con un valor por defecto
	 */
	
	public void checkNullProperties() {
		// Si una property está vacía se le da su valor por defecto
		patrones.forEach((k, v)-> {
			if (properties.getProperty(k) == null) {
				properties.setProperty(k,
						"" + java.awt.event.KeyEvent.getExtendedKeyCodeForChar(v.charAt(0)));
			}
		});	
		guardarProperties();
	}

	/**
	 * Metodo que gestiona que asigna un nuevo valor a las propiedades de los controles del teclado y cambia el 
	 * texto del label ingresado por el valor de la propiedad del nombre ingresado
	 * 
	 * @param l recibe un label existente de la vista
	 * @param evH recibe un listener que espera los cliks de los labels
	 * @param nomProp recibe el nombre de una propiedad del fichero propiedades
	 */
	
	public void labelEventHandler(Label l, EventHandler<KeyEvent> evH, String nomProp) {
		if (!keyEventActivo) {
			keyEventActivo = true;
			String labelName = l.getText();
			l.setText("...");

			// checkNullProperties();

			EventHandler<KeyEvent> eventHandler = new EventHandler<KeyEvent>() {
				PauseTransition pause = new PauseTransition(Duration.seconds(1));

				@Override
				public void handle(KeyEvent event) {
					int numAux = 0;
					seguir = true;
					try {
						numAux = Integer.parseInt(properties.getProperty(nomProp));
					} catch (NumberFormatException e) {
					}

					num.set(event.getCode().getCode());
					
					patrones.forEach((k, v) ->{
						if(properties.getProperty(k).equals("" + num.get())) {
							seguir = false;
						}
					});
					
					String keyEventToString = java.awt.event.KeyEvent.getKeyText(num.get());
					if (seguir && num.get() != numAux && num.get() != 0 && numAux != 0) { // Si no es indefinido
						l.setText(keyEventToString);
						properties.setProperty(nomProp, "" + num.get());
						guardarProperties();
						
					} else {
						l.setText(labelName);

						for (int i = 0; i < listaKeyControlLabels.size(); i++) {
							if (listaKeyControlLabels.get(i).getText().equals(keyEventToString)) {
								listaKeyControlLabels.get(i).getStyleClass().add("invalido");
							}
						}

						l.getStyleClass().add("invalido");
						pause.setOnFinished(e -> {
							for (int i = 0; i < listaKeyControlLabels.size(); i++) {
								if (listaKeyControlLabels.get(i).getText().equals(keyEventToString)) {
									listaKeyControlLabels.get(i).getStyleClass().remove("invalido");
								}
							}
							l.getStyleClass().remove("invalido");
						});
						pause.play();
					}

					MonkeyBrosApp.scene.removeEventFilter(KeyEvent.KEY_RELEASED, this);
					keyEventActivo = false;
				}
			};
			evH = eventHandler;
			MonkeyBrosApp.scene.addEventFilter(KeyEvent.KEY_RELEASED, evH);
		}
	}

	/**
	 * Constructor que carga la vista 
	 */
	
	public ControlesGridPane() {
		super();

		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/fxml/Menus/Componentes/ControlesGridPaneView.fxml"));
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

	/**
	 * Metodo que al invocarse guarda la propiedades 
	 * 
	 * @throws lanza un aviso de que no se ha podido guardar las propiedades
	 */
	
	public void guardarProperties() {
		try {
			properties.store(new FileOutputStream(RootMenuController.RUTAFULL), "");
			MonkeyBrosApp.properties = properties;
		} catch (IOException e) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error");
			alerta.setHeaderText("No se ha podido guardar los controles del teclado.");
			alerta.setContentText(e.getMessage());
			alerta.show();
		}
	}

	/**
	 * Metodo que recibe un fichero propiedades
	 * 
	 * @param properties recibe el fichero de propiedades
	 */
	
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * Metodo que recibe un evento por teclado al clickar encima del label para gestionar su nueva definicion 
	 * 
	 * @param event recibe el evento de raton al darle a un boton
	 */
	
	@FXML
	void onBajarClick(MouseEvent event) {
		labelEventHandler(bajarLabelClick, keyEventHandler, "bajar");
	}

	/**
	 * Metodo que recibe un evento por teclado al clickar encima del label para gestionar su nueva definicion 
	 * 
	 * @param event recibe el evento de raton al darle a un boton
	 */
	
	@FXML
	void onDelanteClick(MouseEvent event) {
		labelEventHandler(delanteLabelClick, keyEventHandler, "adelante");
	}

	/**
	 * Metodo que recibe un evento por teclado al clickar encima del label para gestionar su nueva definicion 
	 * 
	 * @param event recibe el evento de raton al darle a un boton
	 */
	
	@FXML
	void onDetrasClick(MouseEvent event) {
		labelEventHandler(detrasLabelClick, keyEventHandler, "detras");
	}

	/**
	 * Metodo que recibe un evento por teclado al clickar encima del label para gestionar su nueva definicion 
	 * 
	 * @param event recibe el evento de raton al darle a un boton
	 */
	
	@FXML
	void onEscalarClick(MouseEvent event) {
		labelEventHandler(escalarLabelClick, keyEventHandler, "escalar");
	}

	/**
	 * Metodo que recibe un evento por teclado al clickar encima del label para gestionar su nueva definicion 
	 * 
	 * @param event recibe el evento de raton al darle a un boton
	 */
	
	@FXML
	void onSaltarClick(MouseEvent event) {
		labelEventHandler(saltarLabelClick, keyEventHandler, "saltar");
	}

}
