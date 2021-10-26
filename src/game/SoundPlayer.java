package game;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {
	/**
	 * Baseado no código da página: https://noobtuts.com/java/play-sounds
	 **/
	private HashMap<String, String> soundMap;

	public SoundPlayer(HashMap<String, String> sounds) {
		soundMap = sounds;
	}

	public synchronized void play(String sound) {
		String soundFile = soundMap.get(sound);
		if (soundFile != null) {
			new Thread(new Runnable() {
				public void run() {
					try {
						Clip clip = AudioSystem.getClip();
						AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(soundFile));
						clip.open(inputStream);
						clip.start();
						TimeUnit.SECONDS.sleep(2);
						clip.close();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}).start();
		}

	}
}
