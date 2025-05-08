package beatemup;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player {
    private ImageView sprite;
    private double x = 400, y = 300;
    private double speed = 2.5;
    private Set<KeyCode> keysPressed = new HashSet<>();
    private int health = 100;
    private int comboStep = 0;
    private long lastAttackTime = 0;
    private boolean isJumping = false;

    public Player(Pane pane) {
        Image idleGif = new Image("file:assets/gifs/player_idle.gif");
        sprite = new ImageView(idleGif);
        sprite.setFitWidth(64);
        sprite.setFitHeight(64);
        sprite.setX(x);
        sprite.setY(y);
        pane.getChildren().add(sprite);
    }

    public void update() {
        double dx = 0, dy = 0;

        if (keysPressed.contains(KeyCode.LEFT)) dx -= speed;
        if (keysPressed.contains(KeyCode.RIGHT)) dx += speed;
        if (keysPressed.contains(KeyCode.UP)) dy -= speed;
        if (keysPressed.contains(KeyCode.DOWN)) dy += speed;

        x += dx;
        y += dy;

        sprite.setX(x);
        sprite.setY(y);
    }

    public void handleKeyPressed(KeyCode code) {
        keysPressed.add(code);
        if (code == KeyCode.X && !isJumping) jump();
    }

    public void handleKeyReleased(KeyCode code) {
        keysPressed.remove(code);
    }

    public void attack(List<Enemy> enemies) {
        long now = System.currentTimeMillis();
        if (now - lastAttackTime < 300) return;

        comboStep = (now - lastAttackTime < 500) ? comboStep + 1 : 1;
        int damage = switch (comboStep) {
            case 1 -> 10;
            case 2 -> 20;
            case 3 -> {
                comboStep = 0;
                yield 30;
            }
            default -> 10;
        };

        for (Enemy e : enemies) {
            if (sprite.getBoundsInParent().intersects(e.getSprite().getBoundsInParent())) {
                e.takeDamage(damage);
            }
        }
        lastAttackTime = now;
    }

    public void jump() {
        isJumping = true;
        sprite.setTranslateY(-30);
        Timeline fall = new Timeline(new KeyFrame(Duration.millis(500), e -> {
            sprite.setTranslateY(0);
            isJumping = false;
        }));
        fall.play();
    }

    public boolean isJumping() {
        return isJumping;
    }

    public int getHealth() {
        return health;
    }

    public void damage(int amount) {
        health -= amount;
        if (health < 0) health = 0;
    }

    public ImageView getSprite() {
        return sprite;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
