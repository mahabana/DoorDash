package game.view;

import game.engine.Constants;
import game.engine.Game;
import game.engine.cells.Cell;
import game.engine.cells.DoorCell;
import game.engine.monsters.Monster;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

public class BoardView {

    private Scene scene;
    private final CellView[] cells = new CellView[100];

    // Token colors
    private static final Color PLAYER_COLOR   = Color.web("#FFD700"); // gold
    private static final Color OPPONENT_COLOR = Color.web("#FF4444"); // red

    public BoardView(ArrayList<Cell> loadedCells, Game game) {
        buildBoard();
        applyDoorEnergies(loadedCells);
        initializeMonsters(game);
        initializeStationedMonsters(game);
    }

    // ---------------------------------------------------------------
    // Build grid
    // ---------------------------------------------------------------
    private void buildBoard() {
        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);
        grid.setPadding(new Insets(8));
        grid.setStyle("-fx-background-color: #2C2C2C;");

        for (int boardRow = 0; boardRow < 10; boardRow++) {
            int visualRow = 9 - boardRow;
            boolean leftToRight = (boardRow % 2 == 0);
            int baseIndex = boardRow * 10;

            for (int col = 0; col < 10; col++) {
                int cellNumber = leftToRight
                        ? baseIndex + col
                        : baseIndex + (9 - col);

                CellView cell = new CellView(cellNumber);
                cells[cellNumber] = cell;
                grid.add(cell, col, visualRow);
            }
        }

        VBox root = new VBox(8, buildLegend(), grid);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(12));
        root.setStyle("-fx-background-color: #1A1A1A;");

        scene = new Scene(root);
    }

    // ---------------------------------------------------------------
    // Place player and opponent tokens at cell 0 on startup
    // ---------------------------------------------------------------
    private void initializeMonsters(Game game) {
        if (game == null) return;

        Monster player   = game.getPlayer();
        Monster opponent = game.getOpponent();

        // Both start at position 0 — show name + energy on cell 0
        String playerText   = "P:" + player.getName()   + "\n⚡" + player.getEnergy();
        String opponentText = "O:" + opponent.getName() + "\n⚡" + opponent.getEnergy();

        // Cell 0 holds both — stack them
        cells[0].setStatus(playerText + " | " + opponentText);
    }

    // ---------------------------------------------------------------
    // Show stationed monsters on their monster cells
    // ---------------------------------------------------------------
    private void initializeStationedMonsters(Game game) {
        if (game == null) return;

        for (Monster m : game.getAllMonsters()) {
            int pos = m.getPosition();
            if (pos >= 0 && pos < 100 && cells[pos] != null) {
                cells[pos].setStationedMonster(m.getName());
            }
        }
    }

    // ---------------------------------------------------------------
    // Wire door energies from the loaded model cells
    // ---------------------------------------------------------------
    private void applyDoorEnergies(ArrayList<Cell> loadedCells) {
        if (loadedCells == null) return;

        // loadedCells contains DoorCell objects in board order.
        // Door cells sit at odd board indices: 1, 3, 5, ... 99.
        // We match them positionally: loadedCells.get(0) → board index 1,
        //                             loadedCells.get(1) → board index 3, etc.
        int doorIndex = 0;
        for (int boardIndex = 1; boardIndex < Constants.BOARD_SIZE; boardIndex += 2) {
            if (doorIndex >= loadedCells.size()) break;
            Cell c = loadedCells.get(doorIndex);
            if (c instanceof DoorCell) {
                cells[boardIndex].setDoorEnergy(((DoorCell) c).getEnergy());
            }
            doorIndex++;
        }
    }

    // ---------------------------------------------------------------
    // Legend
    // ---------------------------------------------------------------
    private HBox buildLegend() {
        HBox legend = new HBox(16);
        legend.setAlignment(Pos.CENTER);
        legend.setPadding(new Insets(6, 10, 6, 10));
        legend.setStyle("-fx-background-color: #2C2C2C; -fx-background-radius: 6;");

        legend.getChildren().addAll(
                legendItem("#4A90D9", "Scarer door"),
                legendItem("#5CB85C", "Laugher door"),
                legendItem("#C0392B", "Monster cell"),
                legendItem("#E67E22", "Card cell"),
                legendItem("#1ABC9C", "Conveyor belt"),
                legendItem("#F39C12", "Sock"),
                legendItem("#F5F0E8", "Normal")
        );
        return legend;
    }

    private HBox legendItem(String hex, String label) {
        Rectangle swatch = new Rectangle(14, 14);
        swatch.setFill(Color.web(hex));
        swatch.setStroke(Color.web("#7F7F7F"));
        swatch.setStrokeWidth(0.5);
        swatch.setArcWidth(3);
        swatch.setArcHeight(3);

        Label text = new Label(label);
        text.setFont(Font.font("System", FontWeight.NORMAL, 11));
        text.setTextFill(Color.web("#CCCCCC"));

        HBox item = new HBox(5, swatch, text);
        item.setAlignment(Pos.CENTER_LEFT);
        return item;
    }

    // ---------------------------------------------------------------
    // Runtime update API
    // ---------------------------------------------------------------

    /**
     * Mark a door cell as activated (exhausted) — dims it visually.
     */
    public void setDoorActivated(int cellIndex, boolean activated) {
        if (cells[cellIndex] != null) {
            cells[cellIndex].setActivated(activated);
        }
    }

    /**
     * Show which monster is stationed at a monster cell.
     */
    public void setStationedMonster(int cellIndex, String monsterName) {
        if (cells[cellIndex] != null) {
            cells[cellIndex].setStationedMonster(monsterName);
        }
    }

    /**
     * Show a status string on any cell (e.g. player token "P1").
     */
    public void setCellStatus(int cellIndex, String status) {
        if (cells[cellIndex] != null) {
            cells[cellIndex].setStatus(status);
        }
    }

    /**
     * Move a player token: clear the old cell and mark the new one.
     * token = "P1" or "P2" — append if both are on the same cell.
     */
    public void moveToken(int fromIndex, int toIndex, String token) {
        if (fromIndex >= 0 && cells[fromIndex] != null) {
            String current = cells[fromIndex].getCellNumber() + ""; // placeholder
            cells[fromIndex].setStatus("");
        }
        if (cells[toIndex] != null) {
            cells[toIndex].setStatus(token);
        }
    }

    // ---------------------------------------------------------------
    // Getters
    // ---------------------------------------------------------------
    public Scene getScene()           { return scene; }
    public CellView getCell(int index){ return cells[index]; }
}