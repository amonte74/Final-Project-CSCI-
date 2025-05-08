package beatemup;

import javafx.scene.layout.Pane;

public class SmallEnemy extends Enemy {
    public SmallEnemy(Pane pane, double x, double y) {
        super(pane, "assets/gifs/small_enemy.gif", x, y, 2.5, 20, 5, 2);
    }
}