package models;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class ShipCell extends ListCell<Ship> {
    int size = 0;

    public ShipCell(int size){
        super();
        this.size = size;
    }

    @Override
    protected void updateItem(Ship ship, boolean b) {
        super.updateItem(ship, b);

        if(b || ship == null){
            setText(null);
            setGraphic(null);
        } else {
//            setText(ship.getName());

            Label label = new Label();
            label.setText(ship.getName());
            VBox vBox = new VBox();
            HBox hull = new HBox();
            for (int i = 0; i < ship.getSize(); i++) {
                Rectangle rect = new Rectangle(size, size);
                rect.getStyleClass().addAll("boardCell","ship");
                rect.setId(ship.getName().toLowerCase());
//                stack.getChildren().add(rect);
//                stack.setId(ship.getName().toLowerCase());
                hull.getChildren().add(rect);
            }
            hull.setAlignment(Pos.CENTER);
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(hull);
//            vBox.setId("carrier");
//            this.setId("listView");
            this.setAlignment(Pos.CENTER);
            setGraphic(vBox);
        }
    }
}
