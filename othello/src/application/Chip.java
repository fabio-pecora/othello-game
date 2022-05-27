package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * This is the conrete class of the chip where we crate the color and keep track of the turn
 * @author Fabio Chris and John
 *
 */
public class Chip {
    protected boolean isPlaced = false;
    protected int isWhite = 0;
    private Circle circle = new Circle(20, 20, 30);

    /**
     * toggle the chips based on the turn
     */
    public void toggle() {
        if (isWhite == 0) {
            isWhite = 1;
            circle.setFill(Color.WHITE);
        }
        else if (isWhite == 1) {
            isWhite = 0;
            circle.setFill(Color.BLACK);
        }
    }

    /**
     *  set the turn of the player based on 0 and 1
     */
    public void place(int player) {
        if (player == 1) {
            toggle();
        }
        isPlaced = true;
    }
    
    /**
     * @return the circle
     */
    public Circle getCircle() {
        return circle;
    }
}