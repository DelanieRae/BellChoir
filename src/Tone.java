import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
// Based most of the class of off Kyle's https://github.com/kscovill/BellChoir/blob/master/src/Tone.java
public class Tone {
	private static MusicStarter[] m;
	final static List<BellNote> song = new ArrayList<>();
	int NUM_PLAYER=15;

	/**
	 * The main method where the threads are created
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
	        
	        final AudioFormat af =
	                new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, false);
	            Tone t = new Tone();
	            t.musicTone(af);    
	            if(args[0]!=null) {
	            loadSong(args[0]);
	            }else{
	            	System.out.println("Invalid song");
	            	System.exit(1);
	            }
                
	            m = new MusicStarter[15];
	            int j = 0;
	            for (Note n : Note.values()) {
	            	m[j] = new MusicStarter(t, song, n);
	            	j++;

	            }
	      
	        t.playSong(song);
	        
	        for(int i = 0; i<15; i++) {
	        	m[i].StopThread();
	        }
	    }
	
	/**
	 * Separates the lines read in from the file and 
	 * makes sure each is in the list of notes and lengths
	 * Then starts the song
	 * @param filename
	 * @return
	 */

    private  static final List<BellNote> loadSong(String filename) {
    	final File file = new File(filename);
        if (file.exists()) {
            try (FileReader fileReader = new FileReader(file);
                 BufferedReader br = new BufferedReader(fileReader)) {
                String line = null;
                while ((line = br.readLine()) != null) {
                	
                	//8 lines below Received help from Joe Ikehara
                    String[] music = line.split(" ");
                    String m = music[0];
                    String l = music[1];
                    if(music.length != 2) {
                    	System.out.println("Incorrect Format for line of music");
                    }
                    
                    m = parseNote(m);
                    l = parseLength(l);
    	            
                    song.add(new BellNote(Note.valueOf(m), NoteLength.valueOf(l)));
    	            
    	            
                }
            } catch (IOException ignored) {}
        } else {
            System.err.println("File '" + filename + "' not found");
        }
        return song;
        }
    
    /**
     * Checks to make sure the given note is in the list of 
     * accepted notes
     * @param note
     * @return
     */
        private static String parseNote(String note) {
            // If you give me garbage, I'll give it back
            if (note == null) {
                System.out.println("Note Null Need input");
            }

            switch (note.toUpperCase().trim()) {
            case "REST":
            	note= "REST";
            	break;
            case "A4":
            	note= "A4";
            	break;
           case "A4S":
        	   note= "A4S";
           	   break;
            case "B4":
            	note= "B4";
            	break;
            case "C4":
            	note= "C4";
            	break;
            case "C4S":
            	note= "C4S";
            	break;
            case "D4":
            	note= "D4";
            	break;
            case "D4S":
            	note= "D4S";
            	break;
            case "E4":
            	note= "E4";
            	break;
            case "F4":
            	note= "F4";
            	break;
            case "F4S":
            	note= "F4S";
            	break;
            case "G4":
            	note= "G4";
            	break;
            case "G4S":
            	note= "G4S";
            	break;
            case "A5":
            	note= "A5";
            	break;

            default:
            	System.out.println("Invalid Notes");
            	note = "INVALID";
            	System.exit(1);
            	break;
            }
			return note;
        }
        
        /**
         * Checks to see if the given length matches
         * the accepted lengths
         * @param length
         * @return
         */
        private static String parseLength(String length) {
        	
            switch(length.toUpperCase().trim()) {
            case "1":
                length="WHOLE";
                break;
            case "2":
                length ="HALF";
                break;
            case "4":
            	length ="QUARTER";
                break;
            case "8":
            	length ="EIGTH";
                break;
            
            default:
            	length ="INVALID";
            	System.out.println("Invalid length of note.");
            	System.exit(1);
            	break;
          }
            return length;
        }
   

    private AudioFormat af;

    /**
     * Gives the thread the AudioFormat
     * @param af
     */
    public void musicTone(AudioFormat af) {
        this.af = af;
    }
    
    /**
     * Got from Kyle's code:https://github.com/kscovill/BellChoir/blob/master/src/Tone.java
     * Opens the lines for the song. If the song isn't empty
     * then it calls the assignNote method
     * @param song
     * @throws LineUnavailableException
     */

    synchronized void playSong(List<BellNote> song) throws LineUnavailableException {
        try (final SourceDataLine line = AudioSystem.getSourceDataLine(af)) {
            line.open();
            line.start();

            while (!song.isEmpty()) {
				try {
					assignNote(song);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            line.drain();
        }
    }
    
    /**
     * assigns each thread a note.
     * @param b
     * @throws InterruptedException
     */
    public synchronized void assignNote(List<BellNote> b) throws InterruptedException{
    	Note n = b.get(0).note;
    	if(n== Note.A4) {
    		m[1].Play();
    	}
    	if(n== Note.A4S) {
    		m[2].Play();
    	}
    	if(n== Note.B4) {
    		m[3].Play();
    	}
    	if(n== Note.C4) {
    		m[4].Play();
    	}
    	if(n== Note.C4S) {
    		m[5].Play();
    	}
    	if(n== Note.D4) {
    		m[6].Play();
    	}
    	if(n== Note.D4S) {
    		m[7].Play();
    	}
    	if(n== Note.E4) {
    		m[8].Play();
    	}
    	if(n== Note.F4) {
    		m[9].Play();
    	}
    	if(n== Note.F4S) {
    		m[10].Play();
    	}
    	if(n== Note.G4) {
    		m[11].Play();
    	}
    	if(n== Note.G4S) {
    		m[12].Play();
    	}
    	if(n== Note.A5) {
    		m[13].Play();
    	}
    	if(n== Note.REST) {
    		m[0].Play();
    	}

		}
    /**
     *Got this from Kyle's code: https://github.com/kscovill/BellChoir/blob/master/src/Tone.java 
     *Plays the current chosen note
     * @param b
     * @param note
     */
public synchronized void playChosenNote(List<BellNote> b, Note note) {
		
		try (final SourceDataLine line = AudioSystem.getSourceDataLine(af)) {
			line.open();
			line.start();
			playNote(line, new BellNote(note, b.remove(0).length));
			notify();
			line.drain();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Play note and length acquired in previous method.
	 * 
	 * @param line
	 * @param bn
	 */
	public synchronized void playNote(SourceDataLine line, BellNote bn) {

		final int ms = Math.min(bn.length.timeMs(), Note.MEASURE_LENGTH_SEC * 1000);
		final int length = Note.SAMPLE_RATE * ms / 1000;
		line.write(bn.note.sample(), 0, length);
		line.write(Note.REST.sample(), 0, 100);
	}
    }
//Written by Nate
class BellNote {
    final Note note;
    final NoteLength length;

    /**
     * sets the note and length for the thread
     * @param note
     * @param length
     */
    BellNote(Note note, NoteLength length) {
        this.note = note;
        this.length = length;
    }
}

/**
 * list of accepted note lengths
 * @author Nate
 *
 */
enum NoteLength {
	INVALID(0),
    WHOLE(1.0f),
    HALF(0.5f),
    QUARTER(0.25f),
    EIGTH(0.125f);

    private final int timeMs;

    private NoteLength(float length) {
        timeMs = (int)(length * Note.MEASURE_LENGTH_SEC * 1000);
    }

    public int timeMs() {
        return timeMs;
    }
}
/**
 * creates the list of accepted notes
 * @author Nate
 *
 */
enum Note {
    // REST Must be the first 'Note'
	INVALID,
    REST,
    A4,
    A4S,
    B4,
    C4,
    C4S,
    D4,
    D4S,
    E4,
    F4,
    F4S,
    G4,
    G4S,
    A5;

    public static final int SAMPLE_RATE = 48 * 1024; // ~48KHz
    public static final int MEASURE_LENGTH_SEC = 1;

    // Circumference of a circle divided by # of samples
    private static final double step_alpha = (2.0d * Math.PI) / SAMPLE_RATE;

    private final double FREQUENCY_A_HZ = 440.0d;
    private final double MAX_VOLUME = 127.0d;

    private final byte[] sinSample = new byte[MEASURE_LENGTH_SEC * SAMPLE_RATE];

    private Note() {
        int n = this.ordinal();
        if (n > 0) {
            // Calculate the frequency!
            final double halfStepUpFromA = n - 1;
            final double exp = halfStepUpFromA / 12.0d;
            final double freq = FREQUENCY_A_HZ * Math.pow(2.0d, exp);

            // Create sinusoidal data sample for the desired frequency
            final double sinStep = freq * step_alpha;
            for (int i = 0; i < sinSample.length; i++) {
                sinSample[i] = (byte)(Math.sin(i * sinStep) * MAX_VOLUME);
            }
        }
    }

    /**
     * creates sample music
     * @return
     */
    public byte[] sample() {
        return sinSample;
    }
}