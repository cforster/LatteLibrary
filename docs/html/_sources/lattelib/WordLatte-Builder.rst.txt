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

WordLatte.Builder
=================

.. java:package:: lattelib
   :noindex:

.. java:type:: @AutoValue.Builder public static abstract class Builder
   :outertype: WordLatte

Methods
-------
build
^^^^^

.. java:method:: abstract WordLatte build()
   :outertype: WordLatte.Builder

definitions
^^^^^^^^^^^

.. java:method:: abstract Builder definitions(List<String> value)
   :outertype: WordLatte.Builder

etymology
^^^^^^^^^

.. java:method:: abstract Builder etymology(String value)
   :outertype: WordLatte.Builder

examples
^^^^^^^^

.. java:method:: abstract Builder examples(List<String> value)
   :outertype: WordLatte.Builder

partofspeech
^^^^^^^^^^^^

.. java:method:: abstract Builder partofspeech(String value)
   :outertype: WordLatte.Builder

word
^^^^

.. java:method:: abstract Builder word(String value)
   :outertype: WordLatte.Builder

