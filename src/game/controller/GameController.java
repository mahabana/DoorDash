package game.controller;

import game.view.BoardView;
import javafx.stage.Stage;

public class GameController {

    private Stage primaryStage;

    public GameController(Stage primaryStage) {

        this.primaryStage = primaryStage;
    }

    // =========================
    // SHOW GAME BOARD
    // =========================

    public void showBoard() {

        BoardView boardView =
                new BoardView();

        primaryStage.setScene(
                boardView.getScene()
        );

        primaryStage.show();
    }
}