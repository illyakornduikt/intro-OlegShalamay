package main;


import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Sound {
    Clip clip;
    File soundUrl[] = new File[30];
    

    public Sound() {
    
        soundUrl[0] = new File("/Users/drake/Desktop/javagame/res/sound/BlueBoyAdventure.wav");
        soundUrl[1] = new File("/Users/drake/Desktop/javagame/res/sound/coin.wav");
        soundUrl[2] = new File("/Users/drake/Desktop/javagame/res/sound/fanfare.wav");
        soundUrl[3] = new File("/Users/drake/Desktop/javagame/res/sound/powerup.wav");
        soundUrl[4] = new File("/Users/drake/Desktop/javagame/res/sound/unlock.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundUrl[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

            // checkVolume();
        } catch (Exception e) {
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

}
