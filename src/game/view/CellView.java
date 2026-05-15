package game.view;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class CellView extends StackPane {

    private int cellNumber;

    public CellView(int cellNumber) {

        this.cellNumber = cellNumber;

        createCell();
    }

    private void createCell() {

        // =========================
        // CELL BACKGROUND
        // =========================

        Rectangle background =
                new Rectangle(80, 80);

        background.setFill(Color.LIGHTGRAY);

        background.setStroke(Color.BLACK);

        // =========================
        // CELL NUMBER
        // =========================

        Label numberLabel =
                new Label(String.valueOf(cellNumber));

        numberLabel.setFont(new Font(18));

        // =========================
        // ADD COMPONENTS
        // =========================

        getChildren().addAll(
                background,
                numberLabel
        );
    }
}