package game.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InstructionsView {

    private static final Color COL_BG          = Color.web("#0f1117");
    private static final Color COL_CARD        = Color.web("#1a1f2e");
    private static final Color COL_CARD_BORDER = Color.web("#2e3650");
    private static final Color COL_TITLE       = Color.web("#ffa657");  // amber
    private static final Color COL_SECTION     = Color.web("#79c0ff");  // sky-blue
    private static final Color COL_TEXT        = Color.web("#cdd9e5");  // light grey
    private static final Color COL_MUTED       = Color.web("#768390");  // dim grey
    private static final Color COL_SCARER      = Color.web("#58a6ff");  // blue
    private static final Color COL_LAUGHER     = Color.web("#3fb950");  // green
    private static final Color COL_DOOR        = Color.web("#f0883e");  // orange

    private static final Font FONT_TITLE   = Font.font("Georgia", FontWeight.BOLD,   22);
    private static final Font FONT_SECTION = Font.font("Verdana", FontWeight.BOLD,   13);
    private static final Font FONT_LABEL   = Font.font("Verdana", FontWeight.BOLD,   12);
    private static final Font FONT_BODY    = Font.font("Verdana", FontWeight.NORMAL, 12);
    private static final Font FONT_FLAVOUR = Font.font("Georgia", FontPosture.ITALIC, 12);

    private final Stage owner;

    public InstructionsView(Stage owner) {
        this.owner = owner;
    }

    public void show() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        stage.setTitle("How to Play  ·  DoorDasH");
        stage.setResizable(false);

        VBox content = buildContent(stage);

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setStyle(
            "-fx-background-color: #0f1117;" +
            "-fx-background: #0f1117;"
        );

        Scene scene = new Scene(scroll, 720, 660);
        stage.setScene(scene);
        stage.show();
    }

    private VBox buildContent(Stage stage) {
        VBox root = new VBox(18);
        root.setPadding(new Insets(30, 36, 30, 36));
        root.setStyle("-fx-background-color: #0f1117;");

        root.getChildren().addAll(
            buildHeader(),
            buildObjectiveBanner(),
            buildSectionCard("🎲  Turn Sequence",   COL_SECTION,  buildTurnRows()),
            buildSectionCard("🚪  Cell Types",       COL_DOOR,     buildCellRows()),
            buildSectionCard("🃏  Cards",            COL_LAUGHER,  buildCardRows()),
            buildSectionCard("👾  Monster Types",    COL_SCARER,   buildMonsterRows()),
            buildSectionCard("🏆  How to Win",       COL_TITLE,    buildWinRows()),
            buildCloseButton(stage)
        );

        return root;
    }

    private VBox buildHeader() {
        Label title = label(
            "DoorDasH: Scare vs Laugh Touchdown",
            FONT_TITLE, COL_TITLE
        );
        title.setTextAlignment(TextAlignment.CENTER);
        title.setMaxWidth(Double.MAX_VALUE);
        title.setAlignment(Pos.CENTER);
        title.setWrapText(true);

        Label flavour = label(
            "\"We scare because we care.\"  vs  \"We laugh, that's our path.\"",
            FONT_FLAVOUR, COL_MUTED
        );
        flavour.setTextAlignment(TextAlignment.CENTER);
        flavour.setMaxWidth(Double.MAX_VALUE);
        flavour.setAlignment(Pos.CENTER);

        // Two-colour divider (blue = Scarer | green = Laugher)
        Region leftBar  = new Region();
        Region rightBar = new Region();
        HBox.setHgrow(leftBar,  Priority.ALWAYS);
        HBox.setHgrow(rightBar, Priority.ALWAYS);
        leftBar.setPrefHeight(3);
        leftBar.setStyle("-fx-background-color: #58a6ff;");
        rightBar.setPrefHeight(3);
        rightBar.setStyle("-fx-background-color: #3fb950;");
        HBox divider = new HBox(leftBar, rightBar);
        divider.setMaxWidth(Double.MAX_VALUE);

        VBox header = new VBox(8, title, flavour, divider);
        header.setAlignment(Pos.CENTER);
        return header;
    }
    
    private HBox buildObjectiveBanner() {
        Label emoji = new Label("🎯");
        emoji.setFont(Font.font(26));

        Label text = label(
            "Be the first monster to land exactly on cell 99 (Boo's Door) while " +
            "carrying at least 1 000 energy.  Laughers harvest laughter " +
            "(10× more powerful than screams), Scarers rely on good old-fashioned frights.",
            FONT_BODY, COL_TEXT
        );
        text.setWrapText(true);
        HBox.setHgrow(text, Priority.ALWAYS);

        HBox banner = new HBox(14, emoji, text);
        banner.setAlignment(Pos.CENTER_LEFT);
        banner.setPadding(new Insets(16));
        banner.setStyle(
            "-fx-background-color: #1a2a1a;" +
            "-fx-border-color: #3fb950;" +
            "-fx-border-width: 1.5;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;"
        );
        return banner;
    }

    private String[][] buildTurnRows() {
        return new String[][] {
            {"1. Powerup Phase",
             "Optional. Before rolling you may spend 500 energy to activate your " +
             "monster's unique powerup. Skip this phase if you don't want to or " +
             "can't afford it."},
            {"2. Dice Roll",
             "Roll a standard 6-sided die (1–6). The result is how many cells " +
             "you move forward this turn."},
            {"3. Movement",
             "Move forward by the rolled amount. If your destination is already " +
             "occupied by your opponent the move is invalid — stay put and roll again."},
            {"4. Cell Action",
             "Once you land, the cell's special effect triggers automatically: " +
             "Door energy, Card draw, Transport (Conveyor Belt / Contamination Sock), " +
             "Monster Cell interaction, or nothing (Normal Cell)."},
            {"5. End of Turn",
             "The board updates, all status-effect durations tick down, and the win " +
             "condition is checked. The turn then passes to your opponent."},
        };
    }

    private String[][] buildCellRows() {
        return new String[][] {
            {"Scarer Doors ×25\n(blue cells)",
             "Placed at every odd-numbered cell. Role match → you and your entire team " +
             "gain the door's energy. Mismatch → your whole team loses that energy " +
             "(a shield blocks the penalty). Each door activates on first landing " +
             "and is exhausted forever after."},
            {"Laugher Doors ×25\n(green cells)",
             "Same rules as Scarer Doors but trigger for the LAUGHER role. The two " +
             "types alternate across all 50 odd cells (1, 3, 5 … 99)."},
            {"Monster Cells ×6\n(purple cells)",
             "Fixed positions on the board. Each holds one of the 6 unselected " +
             "monsters. Same role as you → free powerup activation. Opposite role → " +
             "if you have LESS energy than the stationed monster, your energies swap."},
            {"Card Cells ×10\n(red cells)",
             "Draw the top card from the shuffled pile and immediately apply its " +
             "effect. The card is then discarded. Moving due to a card does NOT " +
             "trigger the new cell's effect."},
            {"Conveyor Belts ×5\n(teal cells)",
             "Snap you forward by the cell's fixed offset. The cell you land on " +
             "after the transport does NOT activate."},
            {"Contamination Socks ×5\n(orange cells)",
             "Send you backward by the cell's fixed offset AND drain 100 energy. " +
             "A shield blocks the energy drain but NOT the backward move."},
            {"Normal Cells ×24\n(yellow cells)",
             "Nothing special happens. Take a moment to plan your strategy."},
        };
    }

    private String[][] buildCardRows() {
        return new String[][] {
            {"Position Swap ×4",
             "Swap positions with your opponent — only works if you are currently " +
             "behind them. No effect if you are ahead."},
            {"Contamination Code ×2",
             "Bad luck: YOU are sent all the way back to cell 0."},
            {"2319 Alert ×3",
             "Good news: your OPPONENT is sent all the way back to cell 0."},
            {"Small Snatcher ×3",
             "Steal 50 energy from your opponent. Blocked entirely by their shield."},
            {"Sneaky Thief ×2",
             "Steal 100 energy from your opponent. Blocked entirely by their shield."},
            {"Mega Drain ×1",
             "Steal 150 energy from your opponent. Blocked entirely by their shield."},
            {"Super Shield ×5",
             "Block the next negative energy effect for your entire team. " +
             "Removes the opponent's shield first if they already have one."},
            {"Mind Scramble ×3",
             "Both players swap roles (SCARER ↔ LAUGHER) for 2 turns, " +
             "reversing door interactions during that time."},
            {"Total Confusion ×2",
             "Same as Mind Scramble but the role swap lasts 3 turns."},
        };
    }

    private String[][] buildMonsterRows() {
        return new String[][] {
            {"Dasher\n(Mike, Fungus)",
             "Passive — Lightning Movement: dice roll is doubled (2× speed).\n" +
             "Powerup — Momentum Rush: move at 3× speed for the next 3 turns " +
             "instead of the usual 2×."},
            {"Dynamo\n(Sulley, Yeti)",
             "Passive — Energy Amplification: ALL energy gains AND losses are " +
             "doubled — the good and the bad!\n" +
             "Powerup — Energy Freeze: your opponent skips their entire next turn."},
            {"Multitasker\n(Celia, Roz)",
             "Passive — Movement-Energy: dice roll is halved (½ speed) but every " +
             "energy change receives a +200 bonus.\n" +
             "Powerup — Focus Mode: move at normal (un-halved) speed for 2 turns; " +
             "the +200 energy bonus is kept throughout."},
            {"Schemer\n(Randall, Waternoose)",
             "Passive — Energy Manipulation: every energy change (gain or loss) " +
             "gets a flat +10 bonus.\n" +
             "Powerup — Chain Attack: steal 10 energy from EVERY other monster on " +
             "the board simultaneously. This bypasses shields."},
        };
    }

    private String[][] buildWinRows() {
        return new String[][] {
            {"Land on Cell 99",
             "Your monster must reach Boo's Door — the very last cell on the board."},
            {"Carry 1 000+ Energy",
             "You must have at least 1 000 energy in your canister at the moment " +
             "you arrive on cell 99."},
            {"Both Required",
             "Both conditions must be true at the same time. If either is missing, " +
             "play continues until someone satisfies both simultaneously."},
        };
    }

    private VBox buildSectionCard(String heading, Color accentColor, String[][] rows) {
        Label headingLabel = label(heading, FONT_SECTION, accentColor);

        Region line = new Region();
        line.setPrefHeight(1.5);
        line.setMaxWidth(Double.MAX_VALUE);
        line.setStyle("-fx-background-color: " + toHex(accentColor.deriveColor(0, 1, 1, 0.45)) + ";");

        VBox rowsBox = new VBox(10);
        rowsBox.setPadding(new Insets(10, 0, 0, 0));
        for (String[] row : rows) {
            rowsBox.getChildren().add(buildRow(row[0], row[1], accentColor));
        }

        VBox card = new VBox(8, headingLabel, line, rowsBox);
        card.setPadding(new Insets(16));
        card.setStyle(
            "-fx-background-color: " + toHex(COL_CARD) + ";" +
            "-fx-border-color: " + toHex(COL_CARD_BORDER) + ";" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;"
        );
        return card;
    }

    private HBox buildRow(String labelText, String descText, Color dotColor) {
        Circle dot = new Circle(5);
        dot.setFill(dotColor);

        Label lbl = label(labelText, FONT_LABEL, COL_TITLE);
        lbl.setMinWidth(160);
        lbl.setMaxWidth(160);
        lbl.setWrapText(true);

        Label desc = label(descText, FONT_BODY, COL_TEXT);
        desc.setWrapText(true);
        HBox.setHgrow(desc, Priority.ALWAYS);

        HBox row = new HBox(10, dot, lbl, desc);
        row.setAlignment(Pos.TOP_LEFT);
        return row;
    }

    private HBox buildCloseButton(Stage stage) {
        Button btn = new Button("Got it — Let's Play!");
        btn.setFont(FONT_LABEL);
        btn.setTextFill(Color.web("#0f1117"));
        btn.setPadding(new Insets(10, 32, 10, 32));
        btn.setCursor(javafx.scene.Cursor.HAND);

        String base  = "-fx-background-color: #58a6ff; -fx-background-radius: 6;";
        String hover = "-fx-background-color: #79c0ff; -fx-background-radius: 6;";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e  -> btn.setStyle(base));
        btn.setOnAction(e -> stage.close());

        HBox wrapper = new HBox(btn);
        wrapper.setAlignment(Pos.CENTER);
        wrapper.setPadding(new Insets(6, 0, 0, 0));
        return wrapper;
    }

    private Label label(String text, Font font, Color color) {
        Label lbl = new Label(text);
        lbl.setFont(font);
        lbl.setTextFill(color);
        return lbl;
    }

    private String toHex(Color c) {
        return String.format("#%02x%02x%02x",
            (int) (c.getRed()   * 255),
            (int) (c.getGreen() * 255),
            (int) (c.getBlue()  * 255));
    }
}