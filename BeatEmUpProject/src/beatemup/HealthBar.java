package beatemup;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class HealthBar {
    private Label label;
    private int currentHealth;
    private int maxHealth;

    public HealthBar(Pane parent, int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;

        label = new Label("Health: " + currentHealth + "/" + maxHealth);
        label.setFont(new Font("Consolas", 20));
        label.setTextFill(Color.WHITE);
        label.setLayoutX(10);
        label.setLayoutY(10);
        parent.getChildren().add(label);
    }

    public void update(int currentHealth) {
        this.currentHealth = Math.max(0, currentHealth);
        label.setText("Health: " + this.currentHealth + "/" + maxHealth);
    }

    public void setVisible(boolean visible) {
        label.setVisible(visible);
    }
}
