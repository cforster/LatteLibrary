.. java:import:: com.google.auto.value AutoValue

.. java:import:: org.json JSONArray

.. java:import:: org.json JSONException

.. java:import:: org.json JSONObject

.. java:import:: org.json JSONTokener

.. java:import:: org.jsoup Jsoup

.. java:import:: org.jsoup.nodes Document

.. java:import:: org.jsoup.nodes Element

.. java:import:: org.jsoup.select Elements

.. java:import:: java.net URL

.. java:import:: java.net URLEncoder

.. java:import:: java.util ArrayList

.. java:import:: java.util List

.. java:import:: java.util Random

.. java:import:: java.util Scanner

WordLatte
=========

.. java:package:: lattelib
   :noindex:

.. java:type:: @AutoValue public abstract class WordLatte

   Created by charlie on 8/8/16.

Methods
-------
definition
^^^^^^^^^^

.. java:method:: public String definition(int i)
   :outertype: WordLatte

definition
^^^^^^^^^^

.. java:method:: public String definition()
   :outertype: WordLatte

definitionCount
^^^^^^^^^^^^^^^

.. java:method:: public int definitionCount()
   :outertype: WordLatte

definitions
^^^^^^^^^^^

.. java:method:: abstract List<String> definitions()
   :outertype: WordLatte

etymology
^^^^^^^^^

.. java:method:: public abstract String etymology()
   :outertype: WordLatte

example
^^^^^^^

.. java:method:: public String example(int i)
   :outertype: WordLatte

example
^^^^^^^

.. java:method:: public String example()
   :outertype: WordLatte

exampleCount
^^^^^^^^^^^^

.. java:method:: public int exampleCount()
   :outertype: WordLatte

examples
^^^^^^^^

.. java:method:: abstract List<String> examples()
   :outertype: WordLatte

fetch
^^^^^

.. java:method:: public static WordLatte fetch()
   :outertype: WordLatte

fetch
^^^^^

.. java:method:: public static WordLatte fetch(String word)
   :outertype: WordLatte

partofspeech
^^^^^^^^^^^^

.. java:method:: public abstract String partofspeech()
   :outertype: WordLatte

word
^^^^

.. java:method:: public abstract String word()
   :outertype: WordLatte

