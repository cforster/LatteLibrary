.. java:import:: java.io IOException

SayLatte
========

.. java:package:: lattelib
   :noindex:

.. java:type:: public class SayLatte

   text to speech

   :author: cforster

Fields
------
AGNES
^^^^^

.. java:field:: public static final String AGNES
   :outertype: SayLatte

ALBERT
^^^^^^

.. java:field:: public static final String ALBERT
   :outertype: SayLatte

ALEX
^^^^

.. java:field:: public static final String ALEX
   :outertype: SayLatte

BAD_NEWS
^^^^^^^^

.. java:field:: public static final String BAD_NEWS
   :outertype: SayLatte

BAHH
^^^^

.. java:field:: public static final String BAHH
   :outertype: SayLatte

BELLS
^^^^^

.. java:field:: public static final String BELLS
   :outertype: SayLatte

BOING
^^^^^

.. java:field:: public static final String BOING
   :outertype: SayLatte

BRUCE
^^^^^

.. java:field:: public static final String BRUCE
   :outertype: SayLatte

BUBBLES
^^^^^^^

.. java:field:: public static final String BUBBLES
   :outertype: SayLatte

CELLOS
^^^^^^

.. java:field:: public static final String CELLOS
   :outertype: SayLatte

DERANGED
^^^^^^^^

.. java:field:: public static final String DERANGED
   :outertype: SayLatte

FRED
^^^^

.. java:field:: public static final String FRED
   :outertype: SayLatte

GOOD_NEWS
^^^^^^^^^

.. java:field:: public static final String GOOD_NEWS
   :outertype: SayLatte

HYSTERICAL
^^^^^^^^^^

.. java:field:: public static final String HYSTERICAL
   :outertype: SayLatte

JUNOR
^^^^^

.. java:field:: public static final String JUNOR
   :outertype: SayLatte

KATHY
^^^^^

.. java:field:: public static final String KATHY
   :outertype: SayLatte

PIPE_ORGAN
^^^^^^^^^^

.. java:field:: public static final String PIPE_ORGAN
   :outertype: SayLatte

PRINCESS
^^^^^^^^

.. java:field:: public static final String PRINCESS
   :outertype: SayLatte

RALPH
^^^^^

.. java:field:: public static final String RALPH
   :outertype: SayLatte

TRINOIDS
^^^^^^^^

.. java:field:: public static final String TRINOIDS
   :outertype: SayLatte

VICKI
^^^^^

.. java:field:: public static final String VICKI
   :outertype: SayLatte

VICTORIA
^^^^^^^^

.. java:field:: public static final String VICTORIA
   :outertype: SayLatte

WHISPER
^^^^^^^

.. java:field:: public static final String WHISPER
   :outertype: SayLatte

ZARVOX
^^^^^^

.. java:field:: public static final String ZARVOX
   :outertype: SayLatte

Constructors
------------
SayLatte
^^^^^^^^

.. java:constructor:: public SayLatte()
   :outertype: SayLatte

Methods
-------
main
^^^^

.. java:method:: public static void main(String[] args)
   :outertype: SayLatte

say
^^^

.. java:method:: public void say(String s)
   :outertype: SayLatte

   use the mac tts generator to speak

setVoice
^^^^^^^^

.. java:method:: public void setVoice(String voice)
   :outertype: SayLatte

   set voice Agnes en_US # Isn't it nice to have a computer that will talk to you? Albert en_US # I have a frog in my throat. No, I mean a real frog! Alex en_US # Most people recognize me by my voice. Bad News en_US # The light you see at the end of the tunnel is the headlamp of a fast approaching train. Bahh en_US # Do not pull the wool over my eyes. Bells en_US # Time flies when you are having fun. Boing en_US # Spring has sprung, fall has fell, winter's here and it's colder than usual. Bruce en_US # I sure like being inside this fancy computer Bubbles en_US # Pull the plug! I'm drowning! Cellos en_US # Doo da doo da dum dee dee doodly doo dum dum dum doo da doo da doo da doo da doo da doo da doo Deranged en_US # I need to go on a really long vacation. Fred en_US # I sure like being inside this fancy computer Good News en_US # Congratulations you just won the sweepstakes and you don't have to pay income tax again. Hysterical en_US # Please stop tickling me! Junior en_US # My favorite food is pizza. Kathy en_US # Isn't it nice to have a computer that will talk to you? Pipe Organ en_US # We must rejoice in this morbid voice. Princess en_US # When I grow up I'm going to be a scientist. Ralph en_US # The sum of the squares of the legs of a right triangle is equal to the square of the hypotenuse. Trinoids en_US # We cannot communicate with these carbon units. Vicki en_US # Isn't it nice to have a computer that will talk to you? Victoria en_US # Isn't it nice to have a computer that will talk to you? Whisper en_US # Pssssst, hey you, Yeah you, Who do ya think I'm talking to, the mouse? Zarvox en_US # That looks like a peaceful planet.

wait
^^^^

.. java:method:: public void wait(int millis)
   :outertype: SayLatte

   wait a given number of milliseconds

   :param millis: the amount of time to wait

