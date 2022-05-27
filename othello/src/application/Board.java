package application;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;

/**
 * This is the actual board of the game that check for all the position available etc..
 * @author Fabio Chris and John
 *
 */
public class Board implements validPosition {
    
    public int player = 0;
    public ArrayList<Circle> possibleMoves = new ArrayList<>();
    public int scoreB = 0;
    public int scoreW = 0;
    //array of pieces
    public ArrayList<ArrayList<Chip>> pieces = new ArrayList<>(8);
    public boolean started = false;
    public int gameOverCounter = 0;
    public ArrayList<ArrayList<Chip>> getPieces(){
    	return pieces;
    }

    /**
     * Placing the first chips in the default positions
     */
    public void start(GridPane pane) {
    	// adding the default chips in the middle positions
        placement(pane, 3, 3);
        placement(pane, 3, 4);
        placement(pane, 4, 4);
        placement(pane, 4, 3);
        // the game started
        started = true;
        // we want to show the allowed positions. if the player click different place of the 
        // board nothing will happen
        showLegal(pane);
    }

    // placing
    /**
     *  Placing the chips and looking for turn chips of different color
     * We have the board, the row and the column as parameters 
     */
    public void placement(GridPane pane, int cI, int rI) {

        if (started) {
        	// only place a chip if the position is allowed
            if (isIllegal(cI, rI) == 0) {
                // checking for all the cells around the piece
                for (int i = cI - 1; i < cI + 2; i++) {     
                    for (int j = rI - 1; j < rI + 2; j++) {
                        int diffX = i - cI;                 
                        int diffY = j - rI;
                        int c = 1;
                        for (int i2 = 1; i2 < 8; i2++) {
                            int dx = cI + (diffX * i2);   
                            int dy = rI + (diffY * i2);
                            // check for the boundaries, if the next chip is out of bounds we break out of this loop
                            if (dx > 7 || dx < 0 || dy > 7 || dy < 0)  {    
                                c = 1;
                                break;
                            }
                            // understand how many pieces to toggle
                            if (pieces.get(dx).get(dy).isPlaced) {
                                if (pieces.get(dx).get(dy).isWhite != player) {
                                    c++;
                                } else { break; }
                            } else {
                                c = 1;
                                break;
                            }
                        }
                        // turn the pieces
                        for (int c2 = 1; c2 < c; c2++) {
                            pieces.get(cI + (diffX * c2)).get(rI + (diffY * c2)).toggle();
                        }
                    }
                }
                pieces.get(cI).get(rI).place(player);
                pane.add(pieces.get(cI).get(rI).getCircle(), cI, rI);
                // controlling who's turn is, changing the player integer every time
                if (player == 0) player = 1; 
                else if (player == 1) player = 0; 
                countScore();
                showLegal(pane);
            }

        } else {
            pieces.get(cI).get(rI).place(player);
            pane.add(pieces.get(cI).get(rI).getCircle(), cI, rI);
            if (player == 0) player = 1; 
            else if (player == 1) player = 0;
            countScore();
        }
    }
    
    /**
     * Filling the board with chips
     */
    public Board(GridPane pane) throws InterruptedException {      
    	// here we are filling all the board with invisible chips so that we can activate them under some conditions 
        Thread fillThread = new Thread("fill"){
     
            public void run(){
                for (int i = 0; i < 8; i++) {
                    pieces.add(new ArrayList<>());  // fills with arrays
                    for (int j = 0; j < 8; j++) {
                        pieces.get(i).add(new Chip());  // fills array with pieces
                    }
                }
            }
        };

        Thread boardCreationThread = new Thread("boardCreation"){
            @Override
            public void run(){
                // creating the board adding lines to create rows and columns 
                pane.setGridLinesVisible(true);
                for (int i = 0; i < 8; i++) {
                    ColumnConstraints colConst = new ColumnConstraints();
                    colConst.setPercentWidth(100.0 / 8);
                    colConst.setHalignment(HPos.CENTER);
                    pane.getColumnConstraints().add(colConst);

                    RowConstraints rowConst = new RowConstraints();
                    rowConst.setPercentHeight(100.0 / 8);
                    rowConst.setValignment(VPos.CENTER);
                    pane.getRowConstraints().add(rowConst);
                }
            }
        };

        fillThread.start();
        boardCreationThread.start();

        fillThread.join();
        boardCreationThread.join();

        start(pane);
    }
    

    /**
     * checking for legal positions
     */
    public int isIllegal(int cI, int rI) {
    	
        if (pieces.get(cI).get(rI).isPlaced) return 1; // check if piece already exists

        // check surroundings for any of opposite color
        for (int i = cI - 1; i < cI + 2; i++) {
            for (int j = rI - 1; j < rI + 2; j++) {
                if (i <= 7 && i >= 0 && j <= 7 && j >= 0 ) { 
                    if (pieces.get(i).get(j).isPlaced && pieces.get(i).get(j).isWhite != player) {
                        int diffX = i - cI;
                        int diffY = j - rI;

                        // check all in a line in that direction
                        for (int x = 2; x < 8; x++) {
                            int dx = cI + (diffX * x);
                            int dy = rI + (diffY * x);
                            if (dx > 7 || dx < 0 || dy > 7 || dy < 0) {
                                x = 8;
                            } else if (!pieces.get(dx).get(dy).isPlaced) {
                                x = 8;
                            } else if (pieces.get(dx).get(dy).isPlaced && pieces.get(dx).get(dy).isWhite == player) {
                                return 0;
                            }
                        }

                    }
                }
            }
        }
        return 1;
    }
    
    /**
     * show the legal positions
     */
    public void showLegal(GridPane pane) {

        int moves = 0;
        //clear
        possibleMoves.forEach((n) -> pane.getChildren().remove(n));
        possibleMoves.clear();
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
            	// if is legal, we place a circle to show that it is a legal move 
                if(isIllegal(i, j) == 0) {
                    moves++;
                    Circle circle = new Circle(30, 20, 30);
                    circle.setFill(Color.GRAY);
                    circle.setStroke(Color.DARKRED);
                    circle.setStrokeWidth(3);
                    circle.setOpacity(0.2);
                    possibleMoves.add(circle);
                    pane.add(circle, i, j);
                }
            }
        }
        
        if (moves == 0) {
            if (player == 0) player = 1; 
            else if (player == 1) player = 0; 
            gameOverCounter++;

            //we want to end the game if no moves left 2 times in a row
            if (gameOverCounter == 2) {
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println("Game Over!");
                System.out.println();
                countScore();
                // displaying the name of the winner or if there is no winner, choose both of them as winners 
                if (scoreB > scoreW) {
                	HumanPlayer fabio = new HumanPlayer("Fabio", "40");
                    System.out.print(fabio.getName());
                } else if (scoreW > scoreB) {
                	ComputerPlayer computer = new ComputerPlayer("Computer", "30");
                    System.out.print(computer.getName());
                } else {
                    System.out.print("Both of you");
                }
                System.out.println(" Wins!");
            } else {
                System.out.println("The player has to play again, no moves for the opponent");
                showLegal(pane);
            }
        } else gameOverCounter = 0;
    }

    
    // keep tracking of the count score 
    /**
     * keep tracking of the count
     */
    public void countScore() {
    	// set both to zero
        scoreW = 0;
        scoreB = 0;
        // increase each of them if a chip turn to their color
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces.get(i).get(j).isPlaced && pieces.get(i).get(j).isWhite == 1) scoreW++; 
                else if (pieces.get(i).get(j).isPlaced && pieces.get(i).get(j).isWhite == 0) scoreB++;
            }
        }
    }
}