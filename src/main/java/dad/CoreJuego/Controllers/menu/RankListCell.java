package dad.CoreJuego.Controllers.menu;

import dad.Api.GlobalStat.GlobalStat;
import io.github.fvarrui.globalstats.model.Rank;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

/**
 * Clase que crea la estructura y diseño de los datos que se guardar de @see {@link GlobalStat} en cada elemento de la vista 
 * 
 * @author David Alejandro Hernández Alonso
 *
 */


public class RankListCell extends ListCell<Rank> {

	private final Label title = new Label();
	private final Label detail = new Label();
	private final VBox layout = new VBox(title, detail);

	/**
	 * Metodo que inicia el Diseño/aspecto
	 */
	public RankListCell() {
	        super();
	        title.setStyle("-fx-font-size: 60px;");
	        detail.setStyle("-fx-font-size: 30px;");
	    }

	/**
	 * Metodo que guarda los elementos de la clase @see {@link GlobalStat} guarda en variable el nombre, id y puntos 
	 */
	
	@Override
	protected void updateItem(Rank item, boolean empty) {
		super.updateItem(item, empty);

		setText(null);

		if (empty || item == null || item.getRank() == null) {
			title.setText(null);
			detail.setText(null);
			setGraphic(null);
		} else {
			title.setText(item.getName());
			detail.setText("Puntuacion: "+(item.getValue() != null ? item.getValue()+"" : "Undefined"));
			setGraphic(layout);
		}
	}

}
