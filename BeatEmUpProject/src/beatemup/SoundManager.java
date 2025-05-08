package beatemup;

import javafx.scene.media.AudioClip;

public class SoundManager {
    public static void play(String filename) {
        try {
            AudioClip clip = new AudioClip(new java.io.File("assets/sounds/" + filename).toURI().toString());
            clip.play();
        } catch (Exception e) {
            System.out.println("Error playing sound: " + filename);
        }
    }
}
