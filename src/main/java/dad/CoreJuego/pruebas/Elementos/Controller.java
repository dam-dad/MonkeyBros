package dad.CoreJuego.pruebas.Elementos;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.jbox2d.common.Vec2;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class Controller implements Initializable {

	boolean derecha, izquierda, arriba, abajo;

	// logic

	private GamePrueba game;

	// view

	@FXML
	private BorderPane view;

	@FXML
	private Canvas canvas;

	public Controller() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		game = new GamePrueba(canvas);
		// game.fpsProperty().addListener((o, ov, nv) -> System.out.println(nv +
		// "fps"));
		game.start();
		gestionEventos();

	}

	public BorderPane getView() {
		return view;
	}

	private void gestionEventos() {
		/* implementar para pruebas con el mapa */

		canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent evento) {
				switch (evento.getCode().toString()) {
				case "RIGHT":
					derecha = true;
					mover();
					break;
				case "LEFT":
					izquierda = true;
					mover();
					break;
				case "UP":
					arriba = true;
					mover();
					break;
				case "DOWN":
					abajo = true;
					mover();
					break;
				}

			}
		});

		canvas.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent evento) {
				switch (evento.getCode().toString()) {
				case "RIGHT":
					derecha = false;
					parar();
					break;
				case "LEFT":
					izquierda = false;
					parar();
					break;
				case "UP":
					arriba = false;
					parar();
					break;
				case "DOWN":
					abajo = false;
					parar();
					break;
				}
			}
		});

	}

	public void mover() {

		// game.getWorld().getWorldF();

		if (derecha)
			// System.out.println(game.getWorld().getWorldF().getGravity().y);
			game.getWorld().getWorldF().setGravity(new Vec2(200f, game.getWorld().getWorldF().getGravity().y));

		if (izquierda)
			game.getWorld().getWorldF().setGravity(new Vec2(-200f, game.getWorld().getWorldF().getGravity().y));

		if (arriba)
			game.getWorld().getWorldF().setGravity(new Vec2(game.getWorld().getWorldF().getGravity().x, -100f));

		if (abajo)
			game.getWorld().getWorldF().setGravity(new Vec2(game.getWorld().getWorldF().getGravity().x, 100f));

	}

	public void parar() {
		//game.getCp().getBodyF().setLinearVelocity(new Vec2(0.0f,1f));
		game.getCp().getBodyF().setAngularVelocity(-0.01f);
		System.out.println(game.getCp().getBodyF().getLinearVelocity());
		game.getWorld().getWorldF().setGravity(new Vec2(0f, 10f));
	}
}
