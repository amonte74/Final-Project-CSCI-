package beatemup;

import javafx.scene.layout.Pane;

public class KillEnemy extends Enemy {
    public KillEnemy(Pane pane, double x, double y) {
        super(pane, "assets/images/Kill screen enemy.png", x, y, 2.625, Integer.MAX_VALUE, 100, 0);
    }

    @Override
    public void takeDamage(int dmg) {
        // Invincible — does nothing
    }
}
