package game.view;

import game.model.BoardLayout;
import game.model.CellType;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Visual representation of one board cell.
 *
 * Colors:
 *   SCARER door        – steel blue   #4A90D9
 *   LAUGHER door       – soft green   #5CB85C
 *   Monster cell       – crimson red  #C0392B
 *   Card cell          – deep coral   #E67E22
 *   Conveyor belt      – teal         #1ABC9C
 *   Contamination sock – amber/orange #F39C12
 *   Normal             – warm cream   #F5F0E8
 *
 * Each cell shows:
 *   - Its index number (top-left, small)
 *   - A short type label (center, medium)
 */
public class CellView extends StackPane {

    // ---------------------------------------------------------------
    // Color palette
    // ---------------------------------------------------------------
    private static final Color COLOR_DOOR_SCARER  = Color.web("#4A90D9"); // blue
    private static final Color COLOR_DOOR_LAUGHER = Color.web("#5CB85C"); // green
    private static final Color COLOR_MONSTER       = Color.web("#C0392B"); // red
    private static final Color COLOR_CARD          = Color.web("#E67E22"); // orange
    private static final Color COLOR_CONVEYOR      = Color.web("#1ABC9C"); // teal
    private static final Color COLOR_SOCK          = Color.web("#F39C12"); // amber
    private static final Color COLOR_NORMAL        = Color.web("#F5F0E8"); // cream

    private static final Color COLOR_BORDER        = Color.web("#7F7F7F");
    private static final Color COLOR_TEXT_DARK     = Color.web("#1A1A1A");
    private static final Color COLOR_TEXT_LIGHT    = Color.web("#FFFFFF");

    // ---------------------------------------------------------------
    // Cell dimensions
    // ---------------------------------------------------------------
    private static final double CELL_SIZE = 72;

    // ---------------------------------------------------------------
    // State
    // ---------------------------------------------------------------
    private final int cellNumber;
    private final CellType cellType;

    // Labels that may need updating at runtime
    private final Label monsterLabel;  // shows monster name on monster cells
    private final Label statusLabel;   // shows "activated" / player tokens etc.
    private final Label energyLabel;   // shows door energy on door cells

    // ---------------------------------------------------------------
    // Constructor
    // ---------------------------------------------------------------
    public CellView(int cellNumber) {
        this.cellNumber = cellNumber;
        this.cellType   = BoardLayout.typeOf(cellNumber);
        this.monsterLabel = new Label();
        this.statusLabel  = new Label();
        this.energyLabel  = new Label();
        buildCell();
    }

    // ---------------------------------------------------------------
    // Build
    // ---------------------------------------------------------------
    private void buildCell() {
        // --- background rectangle ---
        Rectangle bg = new Rectangle(CELL_SIZE, CELL_SIZE);
        bg.setFill(fillColor());
        bg.setStroke(COLOR_BORDER);
        bg.setStrokeWidth(0.8);
        bg.setArcWidth(4);
        bg.setArcHeight(4);

        // --- index label (top-left corner) ---
        Label indexLabel = new Label(String.valueOf(cellNumber));
        indexLabel.setFont(Font.font("System", FontWeight.BOLD, 10));
        indexLabel.setTextFill(textColor());
        indexLabel.setOpacity(0.75);
        StackPane.setAlignment(indexLabel, Pos.TOP_LEFT);
        indexLabel.setTranslateX(3);
        indexLabel.setTranslateY(2);

        // --- type abbreviation (center) ---
        Label typeLabel = new Label(typeAbbrev());
        typeLabel.setFont(Font.font("System", FontWeight.NORMAL, 9));
        typeLabel.setTextFill(textColor());
        typeLabel.setWrapText(true);
        typeLabel.setAlignment(Pos.CENTER);
        typeLabel.setMaxWidth(CELL_SIZE - 6);

        // --- monster name (shown when a stationed monster lives here) ---
        monsterLabel.setFont(Font.font("System", FontWeight.BOLD, 8));
        monsterLabel.setTextFill(textColor());
        monsterLabel.setWrapText(true);
        monsterLabel.setAlignment(Pos.CENTER);
        monsterLabel.setMaxWidth(CELL_SIZE - 6);

        // --- status (activated door, monster tokens, etc.) ---
        statusLabel.setFont(Font.font("System", FontWeight.NORMAL, 8));
        statusLabel.setTextFill(textColor());
        StackPane.setAlignment(statusLabel, Pos.BOTTOM_CENTER);
        statusLabel.setTranslateY(-2);

        // --- energy value (door cells only, bottom-right corner) ---
        energyLabel.setFont(Font.font("System", FontWeight.BOLD, 9));
        energyLabel.setTextFill(textColor());
        energyLabel.setOpacity(0.9);
        StackPane.setAlignment(energyLabel, Pos.BOTTOM_RIGHT);
        energyLabel.setTranslateX(-3);
        energyLabel.setTranslateY(-2);

        // --- stack them ---
        VBox center = new VBox(1, typeLabel, monsterLabel);
        center.setAlignment(Pos.CENTER);
        center.setMaxWidth(CELL_SIZE - 4);

        getChildren().addAll(bg, indexLabel, center, statusLabel, energyLabel);
        setPrefSize(CELL_SIZE, CELL_SIZE);
        setMaxSize(CELL_SIZE, CELL_SIZE);
        setMinSize(CELL_SIZE, CELL_SIZE);
    }

    // ---------------------------------------------------------------
    // Color helpers
    // ---------------------------------------------------------------
    private Color fillColor() {
        if (cellType == CellType.DOOR_SCARER)        return COLOR_DOOR_SCARER;
        if (cellType == CellType.DOOR_LAUGHER)       return COLOR_DOOR_LAUGHER;
        if (cellType == CellType.MONSTER)            return COLOR_MONSTER;
        if (cellType == CellType.CARD)               return COLOR_CARD;
        if (cellType == CellType.CONVEYOR_BELT)      return COLOR_CONVEYOR;
        if (cellType == CellType.CONTAMINATION_SOCK) return COLOR_SOCK;
        return COLOR_NORMAL;
    }

    private Color textColor() {
        if (cellType == CellType.NORMAL) return COLOR_TEXT_DARK;
        return COLOR_TEXT_LIGHT;
    }

    private String typeAbbrev() {
        if (cellType == CellType.DOOR_SCARER)        return "SCARE";
        if (cellType == CellType.DOOR_LAUGHER)       return "LAUGH";
        if (cellType == CellType.MONSTER)            return "MON";
        if (cellType == CellType.CARD)               return "CARD";
        if (cellType == CellType.CONVEYOR_BELT)      return "BELT";
        if (cellType == CellType.CONTAMINATION_SOCK) return "SOCK";
        return "";
    }

    // ---------------------------------------------------------------
    // Runtime update API (called by BoardView when game state changes)
    // ---------------------------------------------------------------

    /**
     * Show the name of the monster stationed on this monster cell.
     * Pass null to clear.
     */
    public void setStationedMonster(String name) {
        monsterLabel.setText(name != null ? name : "");
    }

    /**
     * Show a small status string at the bottom of the cell.
     * Examples: "✓ used", "P1", "P1 P2"
     */
    public void setStatus(String status) {
        statusLabel.setText(status != null ? status : "");
    }

    /**
     * Dim an activated (exhausted) door cell to make it visually distinct.
     */
    public void setActivated(boolean activated) {
        if (cellType == CellType.DOOR_SCARER || cellType == CellType.DOOR_LAUGHER) {
            setOpacity(activated ? 0.45 : 1.0);
        }
    }

    /**
     * Display the door's energy value (bottom-right corner).
     * Only meaningful on DOOR_SCARER / DOOR_LAUGHER cells.
     * Call this once after the board is loaded from CSV.
     */
    public void setDoorEnergy(int energy) {
        if (cellType == CellType.DOOR_SCARER || cellType == CellType.DOOR_LAUGHER) {
            energyLabel.setText(String.valueOf(energy));
        }
    }

    // ---------------------------------------------------------------
    // Getters
    // ---------------------------------------------------------------
    public int getCellNumber() { return cellNumber; }
    public CellType getCellType() { return cellType; }
}