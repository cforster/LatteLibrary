.. java:import:: com.google.auto.value AutoValue

.. java:import:: org.json JSONObject

.. java:import:: org.json JSONTokener

.. java:import:: java.io IOException

.. java:import:: java.io InputStreamReader

.. java:import:: java.net MalformedURLException

.. java:import:: java.net URL

ImageLatte
==========

.. java:package:: lattelib
   :noindex:

.. java:type:: @AutoValue public abstract class ImageLatte

   Created by charlie on 8/9/16.

Methods
-------
fetchGiphy
^^^^^^^^^^

.. java:method:: public static ImageLatte fetchGiphy(String ref)
   :outertype: ImageLatte

toString
^^^^^^^^

.. java:method:: public String toString()
   :outertype: ImageLatte

url
^^^

.. java:method:: abstract String url()
   :outertype: ImageLatte

