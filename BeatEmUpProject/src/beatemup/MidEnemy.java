package beatemup;

import javafx.scene.layout.Pane;

public class MidEnemy extends Enemy {
    public MidEnemy(Pane pane, double x, double y) {
        super(pane, "assets/gifs/mid_enemy.gif", x, y, 2.3, 50, 10, 5);
    }
}
