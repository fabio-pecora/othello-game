package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

/**
 * Date:05/16/2022
 * This is the main class of our othello game, this mostly creates UI of the game and place chips
 * @author Fabio Chris and John
 * @version 1.0
 */
public class Main extends Application {

	public boolean isBlack = true;
	public int colIndex;
	public int rowIndex;
    public GridPane pane = new GridPane();

  
    /**
     * This method crates all the UI of the game and start everything (board, cells, etc..)
     */
    public void start(Stage primaryStage) throws Exception{
    	
        Board board = new Board(pane);
        BorderPane root = new BorderPane();

        //Set game pane in center and set background color
        pane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setAlignment(Pos.CENTER);

        //Create menu bar
        final Menu menu1 = new Menu("File");
        MenuBar menuBar = new MenuBar();

        MenuItem saveMenuItem = new MenuItem("Save");
        saveMenuItem.setOnAction(e -> {
            try {
     
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //exit function to quit the application
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(actionEvent -> {
            try {
  
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            Platform.exit();
        });

        menu1.getItems().addAll(saveMenuItem, exitMenuItem);
        menuBar.getMenus().add(menu1);

      //Create side tab to right of GridPane
        Text title = new Text();
        title.setText("Player Scores");
        title.setFont(Font.font("Century Gothic", FontWeight.BOLD, 40));
        title.setFill(Color.DARKORANGE);
      

        //Setup for black score
        Text bT = new Text();
        bT.setText("Black Player:");
        bT.setFont(Font.font("Century Gothic", FontWeight.BOLD, 36));
        bT.setFill(Color.BLACK);

        Circle bC = new Circle(20);
        bC.setFill(Color.BLACK);

        Text bCount = new Text();
        bCount.setText("Count = ");
        bCount.setFont(Font.font("Century Gothic", FontWeight.BOLD, 36));
        bCount.setFill(Color.BLACK);

        int blackScore = board.scoreB;
        Text bScore = new Text();
        bScore.setText(Integer.toString(blackScore));
        bScore.setFont(Font.font("Century Gothic", FontWeight.BOLD, 36));
        bScore.setFill(Color.BLACK);

        HBox bRow = new HBox(10, bC, bCount, bScore);
        VBox black = new VBox(30, title, bT, bRow);
        black.setAlignment(Pos.BASELINE_LEFT);

        //Setup for white score
        Text wT = new Text();
        wT.setText("White Player:");
        wT.setFont(Font.font("Century Gothic", FontWeight.BOLD, 36));
        wT.setFill(Color.WHITE);

        Circle wC = new Circle(20);
        wC.setFill(Color.WHITE);

        Text wCount = new Text();
        wCount.setText("Count = ");
        wCount.setFont(Font.font("Century Gothic", FontWeight.BOLD, 36));
        wCount.setFill(Color.WHITE);

        int whiteScore = board.scoreW;
        Text wScore = new Text();
        wScore.setText(Integer.toString(whiteScore));
        wScore.setFont(Font.font("Century Gothic", FontWeight.BOLD, 36));
        wScore.setFill(Color.WHITE);

        HBox wRow = new HBox(10, wC, wCount, wScore);
        VBox white = new VBox(30, wT, wRow);
        white.setAlignment(Pos.BASELINE_LEFT);

        final Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            try {
              
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

       

        HBox hb = new HBox();
        hb.getChildren().addAll( saveButton);
        hb.setSpacing(5);

        Text developers = new Text();
        developers.setText("Developers:");
        developers.setFont(Font.font("Century Gothic", FontWeight.BOLD, 35));
        developers.setFill(Color.DARKORANGE);
        
        Text fabio = new Text();
        fabio.setText("Fabio Pecora");
        fabio.setFont(Font.font("Century Gothic", FontWeight.BOLD, 25));
        fabio.setFill(Color.BLACK);

        Text chris = new Text();
        chris.setText("Christopher Hui");
        chris.setFont(Font.font("Century Gothic", FontWeight.BOLD, 25));
        chris.setFill(Color.BLACK);
        
        Text john = new Text();
        john.setText("John Mayuga");
        john.setFont(Font.font("Century Gothic", FontWeight.BOLD, 25));
        john.setFill(Color.BLACK);

        Text course = new Text();
        course.setText("COURSE: CSC330");
        course.setFont(Font.font("Century Gothic", FontWeight.BOLD, 20));
        course.setFill(Color.WHITE);

        VBox mentions = new VBox(40, developers, fabio, chris, john, course);
        mentions.setAlignment(Pos.BASELINE_LEFT);

        VBox sideTab = new VBox(15, black, white);
        sideTab.setMinWidth(300);
        sideTab.setAlignment(Pos.TOP_CENTER);
        sideTab.setPadding(new Insets(15, 20, 50, 20));
        sideTab.setBackground(new Background(new BackgroundFill(Color.BROWN, CornerRadii.EMPTY, Insets.EMPTY)));

        VBox secondTab = new VBox(15, mentions);
        secondTab.setMinWidth(300);
        secondTab.setAlignment(Pos.TOP_CENTER);
        secondTab.setPadding(new Insets(15, 20, 50, 20));
        secondTab.setBackground(new Background(new BackgroundFill(Color.BROWN, CornerRadii.EMPTY, Insets.EMPTY)));

        //Add all panes to scene
        root.setCenter(pane);
        root.setTop(menuBar);
        root.setLeft(sideTab);
        root.setRight(secondTab);
        Scene scene = new Scene(root, 1300, 750);
        
        int c = 0;
        
       
    	while(c < 1) {
			pane.setOnMouseClicked(e -> {
				// after the user click the mouse
				colIndex = (int) Math.round(e.getX()) / 90;
	            rowIndex = (int) Math.round(e.getY()) / 90;
	            board.placement(pane, colIndex, rowIndex);
	            int bS = count(blackScore) - 2;
	            int wS = count(whiteScore) - 2;

	            for (int i = 0; i < 8; i++) {
	                for (int j = 0; j < 8; j++) {
	                    if (board.pieces.get(i).get(j).isPlaced && board.pieces.get(i).get(j).isWhite == 1)  wS++;
	                    else if (board.pieces.get(i).get(j).isPlaced && board.pieces.get(i).get(j).isWhite == 0) bS++; 
	                }
	            
	            bScore.setText(Integer.toString(bS));
	            wScore.setText(Integer.toString(wS));
	        
	            }
	            boolean whiteIsPlaced = false;
	           
            	while(whiteIsPlaced == false) {
            		Random ran = new Random();
    	            colIndex = ran.nextInt(8);
    	            rowIndex = ran.nextInt(8); 
    	            if(board.isIllegal(colIndex, rowIndex) == 0) {
    	            	board.placement(pane, colIndex, rowIndex);
    	            	whiteIsPlaced = true;
    	            
    	            }
	            }
            	bS = count(blackScore) - 2;
	            wS = count(whiteScore) - 2;

	            for (int i = 0; i < 8; i++) {
	                for (int j = 0; j < 8; j++) {
	                    if (board.pieces.get(i).get(j).isPlaced && board.pieces.get(i).get(j).isWhite == 1)  wS++;
	                    else if (board.pieces.get(i).get(j).isPlaced && board.pieces.get(i).get(j).isWhite == 0) bS++; 
	                }
	            
	            bScore.setText(Integer.toString(bS));
	            wScore.setText(Integer.toString(wS));
	        
	            }
			});
			/////////////////////////////////////////////////////////////////////////////
			//                                                                         //          _______
			//                                                                         //         | _   _ |
			// professor, if you are reading this, I HATE JAVAFX!!!! Guess who I am....//        /  O   O  \
			//                                                                         //       |     U     |
			//                                                                         //       |   ______  |
            /////////////////////////////////////////////////////////////////////////////       |  '      ' |
			                                                             //                      \_________/
    		c++;
    	}
        
        //Setup stage
        primaryStage.setTitle("Othello");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * launching
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * 
     * @return the count
     */
    public int count(int n){
        return n++;
    }
}