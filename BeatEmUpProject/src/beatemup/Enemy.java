package beatemup;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class Enemy {
    protected ImageView sprite;
    protected double x, y;
    protected double speed;
    protected int health;
    protected int damage;
    protected int scoreValue;
    protected boolean alive = true;

    public Enemy(Pane pane, String gifPath, double x, double y, double speed, int health, int damage, int scoreValue) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = health;
        this.damage = damage;
        this.scoreValue = scoreValue;

        Image image = new Image("file:" + gifPath);
        sprite = new ImageView(image);
        sprite.setFitWidth(64);
        sprite.setFitHeight(64);
        sprite.setX(x);
        sprite.setY(y);
        pane.getChildren().add(sprite);
    }

    public void update(double playerX, double playerY) {
        if (!alive) return;

        double dx = playerX - x;
        double dy = playerY - y;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist > 0) {
            x += (dx / dist) * speed;
            y += (dy / dist) * speed;
        }

        sprite.setX(x);
        sprite.setY(y);
    }

    public void takeDamage(int dmg) {
        health -= dmg;
        if (health <= 0) {
            alive = false;
            sprite.setVisible(false);
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public ImageView getSprite() {
        return sprite;
    }

    public int getDamage() {
        return damage;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
