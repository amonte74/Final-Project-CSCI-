package beatemup;

import java.io.*;

public class HighScoreManager {
    private static final String FILE = "highscore.dat";

    public static int getHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            return Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            return 0;
        }
    }

    public static void saveScore(int score) {
        int currentHigh = getHighScore();
        if (score > currentHigh) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE))) {
                writer.write(String.valueOf(score));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
