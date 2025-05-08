package beatemup;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameManager {
    private Pane gamePane;
    private Player player;
    private List<Enemy> enemies = new ArrayList<>();
    private Label scoreLabel;
    private HealthBar healthBar;
    private boolean isGameOver = false;
    private boolean killScreenActive = false;
    private int score = 0;

    public GameManager() {
        gamePane = new Pane();
        gamePane.setPrefSize(800, 600);

        player = new Player(gamePane);
        healthBar = new HealthBar(gamePane, 100);

        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(new Font("Consolas", 24));
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setLayoutX(650);
        scoreLabel.setLayoutY(10);
        gamePane.getChildren().add(scoreLabel);

        gamePane.setOnKeyPressed(e -> {
            player.handleKeyPressed(e.getCode());
            if (e.getCode().toString().equals("Z")) {
                player.attack(enemies);
            }
        });

        gamePane.setOnKeyReleased(e -> player.handleKeyReleased(e.getCode()));
        gamePane.setFocusTraversable(true);
    }

    public Scene getScene() {
        return new Scene(gamePane, 800, 600);
    }

    public Pane getGamePane() {
        return gamePane;
    }

    public void startGameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            long lastSpawnTime = 0;

            @Override
            public void handle(long now) {
                if (isGameOver) return;

                player.update();
                healthBar.update(player.getHealth());

                if (player.getHealth() <= 0) {
                    showGameOverScreen();
                    return;
                }

                Iterator<Enemy> iter = enemies.iterator();
                while (iter.hasNext()) {
                    Enemy e = iter.next();
                    if (!e.isAlive()) {
                        score += e.getScoreValue();
                        updateScore();
                        gamePane.getChildren().remove(e.getSprite());
                        iter.remove();
                        continue;
                    }
                    e.update(player.getX(), player.getY());
                    if (e.getSprite().getBoundsInParent().intersects(player.getSprite().getBoundsInParent())) {
                        player.damage(e.getDamage());
                    }
                }

                if (!killScreenActive && score >= 9999) {
                    triggerKillScreen();
                }

                if (!killScreenActive && now - lastSpawnTime > 2_000_000_000L) {
                    spawnEnemies();
                    lastSpawnTime = now;
                }
            }
        };
        timer.start();
    }

    private void spawnEnemies() {
        Random rand = new Random();
        double x = rand.nextInt(700);
        double y = rand.nextInt(500);
        if (score < 500) {
            enemies.add(new SmallEnemy(gamePane, x, y));
        } else if (score < 1500) {
            enemies.add(new MidEnemy(gamePane, x, y));
        } else {
            enemies.add(new BigEnemy(gamePane, x, y));
        }
    }

    private void updateScore() {
        scoreLabel.setText("Score: " + score);
    }

    private void triggerKillScreen() {
        killScreenActive = true;
        for (Enemy e : enemies) {
            gamePane.getChildren().remove(e.getSprite());
        }
        enemies.clear();

        gamePane.setStyle("-fx-background-color: #330033;");
        KillEnemy weegee = new KillEnemy(gamePane, 0, 0);
        enemies.add(weegee);
    }

    private void showGameOverScreen() {
        isGameOver = true;
        HighScoreManager.saveScore(score);

        Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.setFont(new Font("Consolas", 48));
        gameOverLabel.setTextFill(Color.RED);
        gameOverLabel.setLayoutX(250);
        gameOverLabel.setLayoutY(250);

        gamePane.getChildren().add(gameOverLabel);

        Timeline backToStart = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            MainGameApp.setScene("start");
        }));
        backToStart.play();
    }
}
