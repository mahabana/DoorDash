package game.view;

import game.controller.GameController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameView {

    private Stage primaryStage;

    private GameController controller;

    private String selectedRole = "NONE";

    public GameView(Stage primaryStage) {

        this.primaryStage = primaryStage;

        controller =
                new GameController(primaryStage);
    }

    public void showStartScreen() {

        // =========================
        // TITLE
        // =========================

        Label title =
                new Label("DoorDasH");

        // =========================
        // ROLE LABEL
        // =========================

        Label roleLabel =
                new Label("Selected Role: NONE");

        // =========================
        // SCARER BUTTON
        // =========================

        Button scarerButton =
                new Button("SCARER");

        scarerButton.setOnAction(e -> {

            selectedRole = "SCARER";

            roleLabel.setText(
                    "Selected Role: SCARER"
            );
        });

        // =========================
        // LAUGHER BUTTON
        // =========================

        Button laugherButton =
                new Button("LAUGHER");

        laugherButton.setOnAction(e -> {

            selectedRole = "LAUGHER";

            roleLabel.setText(
                    "Selected Role: LAUGHER"
            );
        });

        // =========================
        // START GAME BUTTON
        // =========================

        Button startButton =
                new Button("START GAME");

        startButton.setOnAction(e -> {

            // validation

            if (selectedRole.equals("NONE")) {

                roleLabel.setText(
                        "Choose a role first!"
                );

                return;
            }

            // open board

            controller.showBoard(selectedRole);
        });
        
        Button instructionsButton = new Button("How to Play");
        instructionsButton.setOnAction(e -> new InstructionsView(primaryStage).show());

        // =========================
        // ROOT
        // =========================

        VBox root = new VBox();

        root.setSpacing(20);

        root.setAlignment(Pos.CENTER);

        root.getChildren().addAll(
                title,
                roleLabel,
                scarerButton,
                laugherButton,
                instructionsButton,
                startButton
        );

        // =========================
        // SCENE
        // =========================

        Scene scene =
                new Scene(root, 700, 500);

        primaryStage.setScene(scene);

        primaryStage.setTitle("DoorDasH");

        primaryStage.show();
    }
}