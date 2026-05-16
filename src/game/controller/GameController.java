package game.controller;

import game.engine.Game;
import game.engine.Role;
import game.engine.cells.Cell;
import game.engine.dataloader.DataLoader;
import game.view.BoardView;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class GameController {

    private Stage primaryStage;
    private Game game;

    public GameController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showBoard(String role) {
        try {
            Role playerRole = Role.valueOf(role);
            game = new Game(playerRole);

            ArrayList<Cell> loadedCells = DataLoader.readCells();
            BoardView boardView = new BoardView(loadedCells, game);

            primaryStage.setScene(boardView.getScene());
            primaryStage.show();

        } catch (IOException e) {
            System.out.println("Could not load game data: " + e.getMessage());
        }
    }

    public Game getGame() {
        return game;
    }
}