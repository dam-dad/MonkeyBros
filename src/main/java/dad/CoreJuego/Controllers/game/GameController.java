package dad.CoreJuego.Controllers.game;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.Api.GlobalStat.GlobalStat;
import dad.CoreJuego.Elementos.MonkeyGame;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class GameController implements Initializable {

	// logic

	private MonkeyGame game;
	
	private GlobalStat globalStats;
	
	// view
	
	@FXML
	private Label labelVida;

	@FXML
	private BorderPane view;

	@FXML
	private Canvas canvas;

	public GameController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Game/GameView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		game = new MonkeyGame(canvas);
		
		// bindings
		game.vidasProperty().addListener((o, ov, nv) -> {
			labelVida.setText("Vidas: " + nv);
		});
		
		//set values
		labelVida.setText("Vidas : " + game.vidasProperty().getValue());
		
		
		game.start();
	}
	
	public BorderPane getView() {
		return view;
	}
	
	public void setGlobalStats(GlobalStat globalStats) {
		this.globalStats = globalStats;
		game.setGlobalStats(globalStats);
	}
}
