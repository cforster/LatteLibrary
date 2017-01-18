.. java:import:: com.google.auto.value AutoValue

.. java:import:: org.json JSONObject

.. java:import:: org.json JSONTokener

.. java:import:: org.jsoup Jsoup

.. java:import:: org.jsoup.nodes Document

.. java:import:: org.jsoup.select Elements

.. java:import:: java.io File

.. java:import:: java.io FileNotFoundException

.. java:import:: java.io IOException

.. java:import:: java.io InputStreamReader

.. java:import:: java.net URL

.. java:import:: java.net URLEncoder

StockLatte
==========

.. java:package:: lattelib
   :noindex:

.. java:type:: @AutoValue public abstract class StockLatte

   Created by charlie on 8/7/16.

Methods
-------
change
^^^^^^

.. java:method:: public abstract double change()
   :outertype: StockLatte

changepercent
^^^^^^^^^^^^^

.. java:method:: public abstract double changepercent()
   :outertype: StockLatte

changepercentytd
^^^^^^^^^^^^^^^^

.. java:method:: public abstract double changepercentytd()
   :outertype: StockLatte

changeytd
^^^^^^^^^

.. java:method:: public abstract double changeytd()
   :outertype: StockLatte

fetch
^^^^^

.. java:method:: static StockLatte fetch()
   :outertype: StockLatte

fetch
^^^^^

.. java:method:: static StockLatte fetch(String symbol)
   :outertype: StockLatte

high
^^^^

.. java:method:: public abstract double high()
   :outertype: StockLatte

lastprice
^^^^^^^^^

.. java:method:: public abstract double lastprice()
   :outertype: StockLatte

low
^^^

.. java:method:: public abstract double low()
   :outertype: StockLatte

marketcap
^^^^^^^^^

.. java:method:: public abstract long marketcap()
   :outertype: StockLatte

name
^^^^

.. java:method:: public abstract String name()
   :outertype: StockLatte

open
^^^^

.. java:method:: public abstract double open()
   :outertype: StockLatte

symbol
^^^^^^

.. java:method:: public abstract String symbol()
   :outertype: StockLatte

timestamp
^^^^^^^^^

.. java:method:: public abstract String timestamp()
   :outertype: StockLatte

volume
^^^^^^

.. java:method:: public abstract int volume()
   :outertype: StockLatte

