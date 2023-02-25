package dad.CoreJuego.Controllers.menu;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

public class PlayerListCell extends ListCell<Player> {

	private final Label name = new Label();
	private final Label points = new Label();
	private final Label id = new Label();
	private final VBox layout = new VBox(name, points, id);

	public PlayerListCell() {
	        super();
	        name.setStyle("-fx-font-size: 40px;");
	        points.setStyle("-fx-font-size: 30px;");
	        id.setStyle("-fx-font-size: 25px;");
	    }

	@Override
	protected void updateItem(Player item, boolean empty) {
		super.updateItem(item, empty);

		setText(null);

		if (empty || item == null || item.getId() == null) {
			name.setText(null);
			points.setText(null);
			id.setText(null);
			setGraphic(null);
		} else {
			name.setText(item.getName());
			points.setText("Puntuacion: "+(item != null ? item.getPoints()+"" : "Undefined"));
			id.setText("Id: "+(item != null ? item.getId()+"" : "Undefined"));
			setGraphic(layout);
		}
	}

}
