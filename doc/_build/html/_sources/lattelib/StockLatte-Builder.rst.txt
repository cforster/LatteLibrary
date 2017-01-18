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

StockLatte.Builder
==================

.. java:package:: lattelib
   :noindex:

.. java:type:: @AutoValue.Builder public static abstract class Builder
   :outertype: StockLatte

Methods
-------
build
^^^^^

.. java:method:: abstract StockLatte build()
   :outertype: StockLatte.Builder

change
^^^^^^

.. java:method:: abstract Builder change(double value)
   :outertype: StockLatte.Builder

changepercent
^^^^^^^^^^^^^

.. java:method:: abstract Builder changepercent(double value)
   :outertype: StockLatte.Builder

changepercentytd
^^^^^^^^^^^^^^^^

.. java:method:: abstract Builder changepercentytd(double value)
   :outertype: StockLatte.Builder

changeytd
^^^^^^^^^

.. java:method:: abstract Builder changeytd(double value)
   :outertype: StockLatte.Builder

high
^^^^

.. java:method:: abstract Builder high(double value)
   :outertype: StockLatte.Builder

lastprice
^^^^^^^^^

.. java:method:: abstract Builder lastprice(double value)
   :outertype: StockLatte.Builder

low
^^^

.. java:method:: abstract Builder low(double value)
   :outertype: StockLatte.Builder

marketcap
^^^^^^^^^

.. java:method:: abstract Builder marketcap(long value)
   :outertype: StockLatte.Builder

name
^^^^

.. java:method:: abstract Builder name(String value)
   :outertype: StockLatte.Builder

open
^^^^

.. java:method:: abstract Builder open(double value)
   :outertype: StockLatte.Builder

symbol
^^^^^^

.. java:method:: abstract Builder symbol(String value)
   :outertype: StockLatte.Builder

timestamp
^^^^^^^^^

.. java:method:: abstract Builder timestamp(String value)
   :outertype: StockLatte.Builder

volume
^^^^^^

.. java:method:: abstract Builder volume(int value)
   :outertype: StockLatte.Builder

