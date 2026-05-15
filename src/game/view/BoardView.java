package game.view;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class BoardView {

    private Scene scene;

    private GridPane board;

    private CellView[][] cells;
    
    private String role;

    public BoardView(String role) {
    	
    	this.role= role;
    	
        cells = new CellView[10][10];

        createBoard();
    }

    private void createBoard() {

        board = new GridPane();

        int cellNumber = 0;

        for (int row = 9; row >= 0; row--) {

            // zigzag rows
            if ((9 - row) % 2 == 0) {

                // LEFT TO RIGHT

                for (int col = 0; col < 10; col++) {

                    CellView cell =
                            new CellView(cellNumber);

                    cells[row][col] = cell;

                    board.add(cell, col, row);

                    cellNumber++;
                }
            }

            else {

                // RIGHT TO LEFT

                for (int col = 9; col >= 0; col--) {

                    CellView cell =
                            new CellView(cellNumber);

                    cells[row][col] = cell;

                    board.add(cell, col, row);

                    cellNumber++;
                }
            }
        }

        scene = new Scene(board, 900, 900);
    }

    public Scene getScene() {

        return scene;
    }
}