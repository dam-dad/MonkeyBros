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

	public void cargarLabels() {
		propertiesListHandler(escalarLabelClick, "escalar");
		propertiesListHandler(detrasLabelClick, "detras");
		propertiesListHandler(bajarLabelClick, "bajar");
		propertiesListHandler(delanteLabelClick, "adelante");
		propertiesListHandler(saltarLabelClick, "saltar");
		keyEventActivo = false;
	}

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

	public void propertiesListHandler(Label l, String s) {
		String nomprop = properties.getProperty(s);
		l.setText(keyEventCodeToStringName(nomprop));
	}

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

	@FXML
	void onBajarClick(MouseEvent event) {
		labelEventHandler(bajarLabelClick, keyEventHandler, "bajar");
	}

	@FXML
	void onDelanteClick(MouseEvent event) {
		labelEventHandler(delanteLabelClick, keyEventHandler, "adelante");
	}

	@FXML
	void onDetrasClick(MouseEvent event) {
		labelEventHandler(detrasLabelClick, keyEventHandler, "detras");
	}

	@FXML
	void onEscalarClick(MouseEvent event) {
		labelEventHandler(escalarLabelClick, keyEventHandler, "escalar");
	}

	@FXML
	void onSaltarClick(MouseEvent event) {
		labelEventHandler(saltarLabelClick, keyEventHandler, "saltar");
	}

}
