package beatemup;

import javafx.scene.layout.Pane;

public class BigEnemy extends Enemy {
    public BigEnemy(Pane pane, double x, double y) {
        super(pane, "assets/gifs/big_enemy.gif", x, y, 2.0, 100, 25, 10);
    }
}
