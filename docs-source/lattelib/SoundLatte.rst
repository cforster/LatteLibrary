.. java:import:: java.io BufferedInputStream

.. java:import:: java.io FileInputStream

.. java:import:: javax.sound.sampled AudioFormat

.. java:import:: javax.sound.sampled AudioSystem

.. java:import:: javax.sound.sampled DataLine

.. java:import:: javax.sound.sampled LineUnavailableException

.. java:import:: javax.sound.sampled SourceDataLine

.. java:import:: javazoom.jl.player Player

SoundLatte
==========

.. java:package:: lattelib
   :noindex:

.. java:type:: public class SoundLatte

   play tones based on a generated waveform

   :author: cforster

Fields
------
SAMPLERATE
^^^^^^^^^^

.. java:field:: public final int SAMPLERATE
   :outertype: SoundLatte

Methods
-------
adsr
^^^^

.. java:method:: public void adsr(long millis)
   :outertype: SoundLatte

adsr
^^^^

.. java:method:: public void adsr(long amillis, double a, long dmillis, double d, long smillis, double s, long rmillis)
   :outertype: SoundLatte

main
^^^^

.. java:method:: public static void main(String[] args)
   :outertype: SoundLatte

playFile
^^^^^^^^

.. java:method:: public static void playFile(String filename)
   :outertype: SoundLatte

setFrequency
^^^^^^^^^^^^

.. java:method:: public void setFrequency(double frequency)
   :outertype: SoundLatte

setFrequency
^^^^^^^^^^^^

.. java:method:: public void setFrequency(int targetfrequency, long millis)
   :outertype: SoundLatte

setVolume
^^^^^^^^^

.. java:method:: public void setVolume(double volume)
   :outertype: SoundLatte

setVolume
^^^^^^^^^

.. java:method:: public void setVolume(double volume, long millis)
   :outertype: SoundLatte

setWaveform
^^^^^^^^^^^

.. java:method:: public void setWaveform(Waveform form)
   :outertype: SoundLatte

start
^^^^^

.. java:method:: public void start()
   :outertype: SoundLatte

stop
^^^^

.. java:method:: public void stop()
   :outertype: SoundLatte

sustain
^^^^^^^

.. java:method:: public void sustain(long millis)
   :outertype: SoundLatte

