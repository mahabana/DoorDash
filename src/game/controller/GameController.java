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

    public void showBoard(String selectedRole) {

        BoardView boardView =
                new BoardView(selectedRole);

        primaryStage.setScene(
                boardView.getScene()
        );

        primaryStage.show();
    }
}