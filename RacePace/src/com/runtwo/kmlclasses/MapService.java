package com.runtwo.kmlclasses;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.content.Context;
import android.util.Log;

public class MapService {

	public static final int MODE_ANY = 0;
	public static final int MODE_CAR = 1;
	public static final int MODE_WALKING = 2;

	public static String inputStreamToString(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	public static NavigationDataSet calculateRoute(Double startLat,
			Double startLng, Double targetLat, Double targetLng, int mode) {
		return calculateRoute(startLat + "," + startLng, targetLat + ","
				+ targetLng, mode);
	}

	public static NavigationDataSet calculateRoute(String startCoords,
			String targetCoords, int mode) {
		String urlPedestrianMode = "http://maps.google.com/maps?" + "saddr="
				+ startCoords + "&daddr=" + targetCoords + "&sll="
				+ startCoords + "&dirflg=w&hl=en&ie=UTF8&z=14&output=kml";

		Log.d("Kml Tag", "urlPedestrianMode: " + urlPedestrianMode);

		String urlCarMode = "http://maps.google.com/maps?" + "saddr="
				+ startCoords + "&daddr=" + targetCoords + "&sll="
				+ startCoords + "&hl=en&ie=UTF8&z=14&output=kml";

		Log.d("Kml Tag", "urlCarMode: " + urlCarMode);

		NavigationDataSet navSet = null;
		// for mode_any: try pedestrian route calculation first, if it fails,
		// fall back to car route
		/*if (mode == MODE_ANY || mode == MODE_WALKING)
			navSet = MapService.getNavigationDataSet(urlPedestrianMode);
		if (mode == MODE_ANY && navSet == null || mode == MODE_CAR)
			navSet = MapService.getNavigationDataSet(urlCarMode);*/
		return navSet;
	}

	/**
	 * Retrieve navigation data set from either remote URL or String
	 * 
	 * @param url
	 * @return navigation set
	 */
	public static NavigationDataSet getNavigationDataSet(String url,Context c) {

		// urlString = "http://192.168.1.100:80/test.kml";
		Log.d("Kml Tag", "urlString -->> " + url);
		NavigationDataSet navigationDataSet = null;
		try {
			/*
			 * final URL aUrl = new URL(url); final URLConnection conn =
			 * aUrl.openConnection(); conn.setReadTimeout(15 * 1000); // timeout
			 * for reading the google maps data: 15 secs conn.connect();
			 */

			/* Get a SAXParser from the SAXPArserFactory. */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();

			/* Get the XMLReader of the SAXParser we created. */
			XMLReader xr = sp.getXMLReader();

			/* Create a new ContentHandler and apply it to the XML-Reader */
			NavigationSaxHandler navSax2Handler = new NavigationSaxHandler();
			xr.setContentHandler(navSax2Handler);

			/* Parse the xml-data from our URL. */
			
			xr.parse(new InputSource(c.getAssets().open(url)));

			/* Our NavigationSaxHandler now provides the parsed data to us. */
			navigationDataSet = navSax2Handler.getParsedData();

			/* Set the result to be displayed in our GUI. */
			//Log.d("Kml Tag","navigationDataSet: " + navigationDataSet.toString());

		} catch (Exception e) {
			// Log.e(myapp.APP, "error with kml xml", e);
			navigationDataSet = null;
		}

		return navigationDataSet;
	}
	
	
	/**
	 * Retrieve navigation data set from either remote URL or String
	 * 
	 * @param url
	 * @return navigation set
	 */
	public static ArrayList<String> getNavigationArrayList(String url,Context c) {

		// urlString = "http://192.168.1.100:80/test.kml";
		Log.d("Kml Tag", "urlString -->> " + url);
		ArrayList<String> coodList;
		try {
			/*
			 * final URL aUrl = new URL(url); final URLConnection conn =
			 * aUrl.openConnection(); conn.setReadTimeout(15 * 1000); // timeout
			 * for reading the google maps data: 15 secs conn.connect();
			 */

			/* Get a SAXParser from the SAXPArserFactory. */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();

			/* Get the XMLReader of the SAXParser we created. */
			XMLReader xr = sp.getXMLReader();

			/* Create a new ContentHandler and apply it to the XML-Reader */
			NavigationSaxHandler navSax2Handler = new NavigationSaxHandler();
			xr.setContentHandler(navSax2Handler);

			/* Parse the xml-data from our URL. */
			
			xr.parse(new InputSource(c.getAssets().open(url)));

			/* Our NavigationSaxHandler now provides the parsed data to us. */
			coodList = navSax2Handler.getParsedCoordinatesList();

			/* Set the result to be displayed in our GUI. */
			//Log.d("Kml Tag","navigationDataSet: " + navigationDataSet.toString());

		} catch (Exception e) {
			// Log.e(myapp.APP, "error with kml xml", e);
			coodList = new ArrayList<String>();
		}

		return coodList;
	}

}