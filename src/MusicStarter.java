import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

//Based this class on Kyle Scovill's project https://github.com/kscovill/BellChoir/blob/master/src/Player.javapublic 
class MusicStarter implements Runnable {
	public boolean playTime = false;
	public List<BellNote> b;
	public Note note;
	private final Tone t;
	

	public final Thread thread;
	
	/**
	 * Must be passes the song, note and type
	 * @param song
	 * @param n
	 * @param a
	 */
	public MusicStarter(Tone t, List<BellNote> song, Note n) {
		thread = new Thread(this);
		this.t = t;
		this.b = song;
		this.playTime = true;
		this.note = n;
		thread.start();
	}

	/**
	 * makes it not time to play and notifies everyone
	 */
	public synchronized void release() {
		playTime = false;
		notify();
	}

	/**
	 * runs when the thread starts while it is time to run, it checks if it is time
	 * to play if it is then it makes sure then it plays the song then releases
	 * their resource
	 */
	@Override
	public synchronized void run() {
				while (playTime) {
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
					}
				}
			}

	/**
	 * Stops threads
	 */
	public void StopThread() {
		try {
			release();
			thread.join();
		} catch (InterruptedException e) {
			System.err.println(thread.getName() + " stop malfunction");
		}
		
	}
	
	/**
	 * Calls the ChosenNote method
	 */
	public synchronized void Play() {
		t.playChosenNote(b, note);
	}
}