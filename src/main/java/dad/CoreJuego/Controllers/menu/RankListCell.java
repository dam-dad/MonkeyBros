package dad.CoreJuego.Controllers.menu;

import io.github.fvarrui.globalstats.model.Rank;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

public class RankListCell extends ListCell<Rank> {

	private final Label title = new Label();
	private final Label detail = new Label();
	private final VBox layout = new VBox(title, detail);

	public RankListCell() {
	        super();
	        title.setStyle("-fx-font-size: 60px;");
	        detail.setStyle("-fx-font-size: 30px;");
	    }

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
