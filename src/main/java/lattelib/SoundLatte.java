package lattelib;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import javazoom.jl.player.Player;
/**
 * play tones based on a generated waveform
 * @author cforster
 *
 */

public class SoundLatte {
	public static void main(String[] args){
//		SoundLatte.playFile("joss.mp3");
		
		//vibrato:
		//		SoundLatte ds = new SoundLatte();
		//		ds.start();
		//		ds.setWaveform(Waveform.SINE);
		//		for (int i = 100; i >0; i--) {
		//			ds.setVolume(.9, 20*100/i);
		//			ds.setFrequency(443, 20*100/i);
		//			ds.setVolume(.8, 20*100/i);
		//			ds.setFrequency(437, 20*100/i);
		//		}


		SoundLatte ds = new SoundLatte();

		ds.start();
		ds.setFrequency(300);
		ds.setFrequency(1200, 5000);
//		ds.setWaveform(Waveform.TRIANGLE);
//		ds.sustain(2000);
//		ds.setWaveform(Waveform.SINE);
//		ds.sustain(2000);
//		ds.setWaveform(Waveform.SQUARE);
//		ds.sustain(2000);
//		ds.setWaveform(Waveform.SAWTOOTH);
		ds.sustain(2000);


		ds.stop();

		//		List<SoundLatte> ds = new ArrayList<SoundLatte>();
		//
		//		for (int i = 0; i < 4; i++) {
		//			ds.add(new SoundLatte());
		//		}
		//		
		//		int i = 440;
		//		for (SoundLatte d : ds) {
		//			d.setFrequency(i);
		//			d.setVolume(.3);
		//			d.setWaveform(Waveform.SAWTOOTH);
		//			i*=1.25;
		//			d.start();
		//			d.sustain(500);
		//		}
		//		
		//		SoundLatte ad = new SoundLatte();
		//		ad.setFrequency(700);
		//		ad.setVolume(1);
		//		for (int j = 0; j < 10; j++) {	
		//			ad.adsr(j*100);
		//		}


		//		ds.get(0).sustain(2000);
		//
		//		for (SoundLatte d : ds) {
		//			d.stop();
		//		}
		//			
		//		ds.start();
		//		ds.setWaveform(Waveform.SQUARE);
		//		for (double i = 0; i < 1.0; i+=.1) {
		//			ds.setVolume(i);
		//			ds.sustain(500);
		//		}
		//		ds.setVolume(.6);
		//		ds.sustain(2000);
		//
		//		ds.sustain(2000);
		//		ds.setWaveform(Waveform.SAWTOOTH);
		//		ds.sustain(2000);
		//		ds.setFrequency(200,2000);
		//		ds.setFrequency(700, 1000);
		//		ds.setFrequency(400);
		//		ds.sustain(3000);
		//		ds.stop();
//				ds.setWaveform(Waveform.SQUARE);
//				for (int i = 0; i < 10; i++) {
//					ds.setFrequency(440+i*30);
//					ds.adsr();
//					ds.sustain(500);
//				}

	}

    // play the MP3 file to the sound card
    public static void playFile(String filename) {
        final Player player;
        
    	try {
            FileInputStream fis     = new FileInputStream(filename);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
        }
        catch (Exception e) {
            System.out.println("Problem playing file " + filename);
            System.out.println(e);
            return;
        }

        // run in new thread to play in background
        new Thread() {
            public void run() {
                try { player.play(); }
                catch (Exception e) { System.out.println(e); }
            }
        }.start();
    }
	
	
	/**
	 * various types of waveforms for sound generation
	 * @author cforster
	 */
	public enum Waveform {
		SINE, SQUARE, SAWTOOTH, TRIANGLE
	}
	public final int SAMPLERATE = 44100;

	private SoundRunner sr;
	private Waveform form = Waveform.SINE;
	private boolean stop = false;
	private double frequency = 440;
	private double volume = 0.7;

	public void sustain(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void adsr(long millis) {


		adsr((long)(0.02*millis), .8, (long)(0.15*millis), .6, (long)(0.5*millis), .5, (long)(0.33*millis));
	}

	public void adsr(long amillis, double a, long dmillis, double d, long smillis, double s, long rmillis) {
		start();
		setVolume(0);
		setVolume(a, amillis);
		setVolume(d, dmillis);
		setVolume(s, smillis);
		setVolume(0, rmillis);	
		stop();
	}

	public void setVolume(double volume) {
		volume = Math.max(volume, 0.0);
		volume = Math.min(volume, 1.0);
		this.volume = volume;
		if(sr!=null) sr.setVolume(volume);
	}

	public void setVolume(double volume, long millis) {
		//if it's not running, set the frequency and wait millis:
		if(sr==null) {
			this.setVolume(volume);
			try {
				Thread.sleep(millis);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		//this is the normal operation for this function:
		double delta = (volume-this.volume)/millis;
		for (int i = 0; i < millis; i++) {
			setVolume(this.volume+delta);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setWaveform(Waveform form) {
		this.form = form;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
		if(sr!=null) sr.setFrequency(frequency);
	}

	public void setFrequency(int targetfrequency, long millis) {
		//if it's not running, set the frequency and wait millis:
		if(sr==null) {
			this.setFrequency(targetfrequency);
			try {
				Thread.sleep(millis);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		//this is the normal operation for this function:
		double delta = (targetfrequency-sr.getFrequency())/ (double)millis;
		for (int i = 0; i < millis; i++) {
			sr.changeFrequency(delta);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	public void start() {
		stop = false;
		sr = new SoundRunner();
		sr.setFrequency(frequency);
		sr.start();
		sr.setVolume(volume);
	}

	public void stop() {
		sr.setVolume(0);
		sustain(50);
		stop = true;
	}

	private class SoundRunner extends Thread {
		private double frequency=440;
		private double volume = 0.0;
		private double newvolume = volume;
		SourceDataLine line;
		double t;



		public synchronized void setVolume(double volume) {
			this.newvolume = volume;
		}

		public synchronized void setFrequency(double frequency) {
			t*=this.frequency/frequency;
			this.frequency = frequency;
		}

		public void changeFrequency(double delta) {
			this.setFrequency(frequency+delta);
		}

		public double getFrequency()
		{
			return frequency;
		}

		public SoundRunner() {
			AudioFormat format = new AudioFormat(SAMPLERATE, 16, 1, true, true);
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format ); // format is an AudioFormat object
			if (!AudioSystem.isLineSupported(info)) {
				System.err.println("sound failure");
			}

			// Obtain and open the line.
			try {
				line = (SourceDataLine) AudioSystem.getLine(info);
				line.open(format);
			} catch (LineUnavailableException ex) {
				System.err.println("sound failure");
			}
		}

		public synchronized void fillBuffer(byte[] b) {
			double volumedelta = (newvolume-volume)/b.length;

			for (int i = 0; i < b.length; i+=2) {
				short sample=0;
				double pos = t++*frequency/(double)SAMPLERATE;
				switch(form) {
				case SAWTOOTH:
					sample = (short)(65535*(pos%1.0)-32768);
					break;
				case SINE:
					sample = (short)(32767*Math.sin(Math.PI *2 * pos));
					break;
				case SQUARE:
					sample = (short)(pos%1.0<0.5?32767:-32768);
					break;
				case TRIANGLE: 
					if(pos%1.0<0.5) sample = (short)(65535*(2*(pos%.5))-32768);
					else sample = (short)(-65535*(2*(pos%0.5))+32768);
					break;
				default:
					break;
				}

				sample*=Math.pow(volume+volumedelta*i, 3.5);

				b[i] = (byte)(sample >> 8);
				b[i+1] = (byte)(sample % 256);
				//				System.out.println(t + ":" +sample + " " + b[i] + " " + b[i+1]);
			}
			volume = newvolume;
		}

		@Override
		public void run() {
			byte[] b = new byte[1024];
			t = 0;

			line.start();
			while(!stop)
			{
				fillBuffer(b);
				line.write(b, 0, b.length);
				if(line.getBufferSize()-line.available()>2*b.length) {
					try {
						Thread.sleep((long)(b.length*1000/SAMPLERATE/2));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			//			//to fade out:
			//			fillBuffer(b);
			//			line.write(b, 0, b.length);
		}

	}

	//play
	//stop
	//setwaveform
	//set pitch
	//set pitch, time
	//volume





}
