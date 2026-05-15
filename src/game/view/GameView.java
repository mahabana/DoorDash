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

        Label title =
                new Label("DoorDasH");
        Label roleLabel =
                new Label("Selected Role: NONE");

        Button scarerButton =
                new Button("SCARER");

        scarerButton.setOnAction(e -> {

            selectedRole = "SCARER";

            roleLabel.setText(
                    "Selected Role: SCARER"
            );
        });

     
        Button laugherButton =
                new Button("LAUGHER");

        laugherButton.setOnAction(e -> {

            selectedRole = "LAUGHER";

            roleLabel.setText(
                    "Selected Role: LAUGHER"
            );
        });

     

        Button startButton =
                new Button("START GAME");

        startButton.setOnAction(e -> {

          

            if (selectedRole.equals("NONE")) {

                roleLabel.setText(
                        "Choose a role first!"
                );

                return;
            }

            // open board

            controller.showBoard(selectedRole);
        });

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