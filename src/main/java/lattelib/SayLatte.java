package lattelib;

import java.io.IOException;

/**
 * text to speech
 * @author cforster
 *
 */
public class SayLatte {
	public static void main(String[] args) {
		SayLatte ds = new SayLatte();
		ds.setVoice(AGNES);
		ds.say("Hello Dalton Objects");
	}
	
	private String voice;

	public static final String AGNES = "Agnes";			   //en_US    # Isn't it nice to have a computer that will talk to you?<br>
	public static final String ALBERT = "Albert";		   //en_US    #  I have a frog in my throat. No, I mean a real frog!<br>
	public static final String ALEX = "Alex";			   //en_US    # Most people recognize me by my voice.<br>
	public static final String BAD_NEWS = "Bad News";      //en_US    # The light you see at the end of the tunnel is the headlamp of a fast approaching train.<br>
	public static final String BAHH = "Bahh";              //en_US    # Do not pull the wool over my eyes.<br>
	public static final String BELLS = "Bells";            //en_US    # Time flies when you are having fun.<br>
	public static final String BOING = "Boing";            //en_US    # Spring has sprung, fall has fell, winter's here and it's colder than usual.<br>
	public static final String BRUCE = "Bruce";            //en_US    # I sure like being inside this fancy computer<br>
	public static final String BUBBLES = "Bubbles";        //en_US    # Pull the plug! I'm drowning!<br>
	public static final String CELLOS = "Cellos";          //en_US    # Doo da doo da dum dee dee doodly doo dum dum dum doo da doo da doo da doo da doo da doo da doo<br>
	public static final String DERANGED = "Deranged";      //en_US    # I need to go on a really long vacation.<br>
	public static final String FRED = "Fred";              //en_US    # I sure like being inside this fancy computer<br>
	public static final String GOOD_NEWS = "Good News";    //en_US    # Congratulations you just won the sweepstakes and you don't have to pay income tax again.<br>
	public static final String HYSTERICAL = "Hysterical";  //en_US    # Please stop tickling me!<br>
	public static final String JUNOR = "Junior";           //en_US    # My favorite food is pizza.<br>
	public static final String KATHY = "Kathy";            //en_US    # Isn't it nice to have a computer that will talk to you?<br>
	public static final String PIPE_ORGAN = "Pipe Organ";  //en_US    # We must rejoice in this morbid voice.<br>
	public static final String PRINCESS = "Princess";      //en_US    # When I grow up I'm going to be a scientist.<br>
	public static final String RALPH = "Ralph";            //en_US    # The sum of the squares of the legs of a right triangle is equal to the square of the hypotenuse.<br>
	public static final String TRINOIDS = "Trinoids";      //en_US    # We cannot communicate with these carbon units.<br>
	public static final String VICKI = "Vicki";            //en_US    # Isn't it nice to have a computer that will talk to you?<br>
	public static final String VICTORIA = "Victoria";      //en_US    # Isn't it nice to have a computer that will talk to you?<br>
	public static final String WHISPER = "Whisper";        //en_US    # Pssssst, hey you, Yeah you, Who do ya think I'm talking to, the mouse?<br>
	public static final String ZARVOX = "Zarvox";          //en_US    # That looks like a peaceful planet.<br>


	public SayLatte() {
		voice = FRED; //default
	}

	/**
	 * use the mac tts generator to speak
	 */
	public void say(String s)
	{
		try {
			Runtime.getRuntime().exec("say -v " + voice + " \"" + s + "\"");
		} catch (IOException e) {
			System.err.println("say failed");
			e.printStackTrace();
		}
	}

	/**
	 * set voice
	 * Agnes               en_US    # Isn't it nice to have a computer that will talk to you?<br>
	 * Albert              en_US    #  I have a frog in my throat. No, I mean a real frog!<br>
	 * Alex                en_US    # Most people recognize me by my voice.<br>
	 * Bad News            en_US    # The light you see at the end of the tunnel is the headlamp of a fast approaching train.<br>
	 * Bahh                en_US    # Do not pull the wool over my eyes.<br>
	 * Bells               en_US    # Time flies when you are having fun.<br>
	 * Boing               en_US    # Spring has sprung, fall has fell, winter's here and it's colder than usual.<br>
	 * Bruce               en_US    # I sure like being inside this fancy computer<br>
	 * Bubbles             en_US    # Pull the plug! I'm drowning!<br>
	 * Cellos              en_US    # Doo da doo da dum dee dee doodly doo dum dum dum doo da doo da doo da doo da doo da doo da doo<br>
	 * Deranged            en_US    # I need to go on a really long vacation.<br>
	 * Fred                en_US    # I sure like being inside this fancy computer<br>
	 * Good News           en_US    # Congratulations you just won the sweepstakes and you don't have to pay income tax again.<br>
	 * Hysterical          en_US    # Please stop tickling me!<br>
	 * Junior              en_US    # My favorite food is pizza.<br>
	 * Kathy               en_US    # Isn't it nice to have a computer that will talk to you?<br>
	 * Pipe Organ          en_US    # We must rejoice in this morbid voice.<br>
	 * Princess            en_US    # When I grow up I'm going to be a scientist.<br>
	 * Ralph               en_US    # The sum of the squares of the legs of a right triangle is equal to the square of the hypotenuse.<br>
	 * Trinoids            en_US    # We cannot communicate with these carbon units.<br>
	 * Vicki               en_US    # Isn't it nice to have a computer that will talk to you?<br>
	 * Victoria            en_US    # Isn't it nice to have a computer that will talk to you?<br>
	 * Whisper             en_US    # Pssssst, hey you, Yeah you, Who do ya think I'm talking to, the mouse?<br>
	 * Zarvox              en_US    # That looks like a peaceful planet.<br>
	 */
	public void setVoice(String voice) {
		this.voice = voice;
	}

	/**
	 * wait a given number of milliseconds
	 * @param millis the amount of time to wait
	 */
	public void wait(int millis)
	{
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
