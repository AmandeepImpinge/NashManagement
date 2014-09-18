package com.runtwo.kmlclasses;

import java.util.ArrayList;

import android.util.Log;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class NavigationSaxHandler extends DefaultHandler {

	// ===========================================================
	// Fields
	// ===========================================================

	private boolean in_kmltag = false;
	private boolean in_placemarktag = false;
	private boolean in_nametag = false;
	private boolean in_descriptiontag = false;
	private boolean in_geometrycollectiontag = false;
	private boolean in_linestringtag = false;
	private boolean in_pointtag = false;
	private boolean in_coordinatestag = false;

	private StringBuffer buffer;

	private Placemark myplacemark;
	private ArrayList<String> coordinateList = new ArrayList<String>();
	String coordinates = "";

	private NavigationDataSet navigationDataSet = new NavigationDataSet();

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public NavigationDataSet getParsedData() {
		// Log.e("get parsed data","called");
		navigationDataSet.getCurrentPlacemark().setCoordinates(
				buffer.toString().trim());
		return this.navigationDataSet;
	}

	public ArrayList<String> getParsedCoordinatesList() {
		return coordinateList;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	@Override
	public void startDocument() throws SAXException {
		// Log.e("Doc","start");
		myplacemark = new Placemark();
		this.navigationDataSet = new NavigationDataSet();
	}

	@Override
	public void endDocument() throws SAXException {
		// Nothing to do
		// Log.e("Doc","end");
	}

	/**
	 * Gets be called on opening tags like: <tag> Can provide attribute(s), when
	 * xml was like: <tag attribute="attributeValue">
	 */
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {

		/*
		 * if (localName.equals("kml")) { this.in_kmltag = true; } else if
		 * (localName.equals("Placemark")) { this.in_placemarktag = true;
		 * navigationDataSet.setCurrentPlacemark(new Placemark()); myplacemark =
		 * new Placemark(); } else if (localName.equals("name")) {
		 * this.in_nametag = true; } else if (localName.equals("description")) {
		 * this.in_descriptiontag = true; } else if
		 * (localName.equals("GeometryCollection")) {
		 * this.in_geometrycollectiontag = true; } else if
		 * (localName.equals("LineString")) { this.in_linestringtag = true; }
		 * else if (localName.equals("point")) { this.in_pointtag = true; } else
		 */
		if (localName.equals("coordinates")) {
			navigationDataSet.setCurrentPlacemark(new Placemark());
			myplacemark = new Placemark();
			buffer = new StringBuffer();
			this.in_coordinatestag = true;
		}
	}

	/**
	 * Gets be called on closing tags like: </tag>
	 */
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		/*
		 * if (localName.equals("kml")) { this.in_kmltag = false; } else if
		 * (localName.equals("Placemark")) { this.in_placemarktag = false;
		 */

		/*
		 * if
		 * ("Route".equals(navigationDataSet.getCurrentPlacemark().getTitle().
		 * contains("kilometer")))
		 * navigationDataSet.setRoutePlacemark(navigationDataSet
		 * .getCurrentPlacemark()); else
		 * navigationDataSet.addCurrentPlacemark();
		 */

		/*
		 * } else if (localName.equals("name")) { this.in_nametag = false; }
		 * else if (localName.equals("description")) { this.in_descriptiontag =
		 * false; } else if (localName.equals("GeometryCollection")) {
		 * this.in_geometrycollectiontag = false; } else if
		 * (localName.equals("LineString")) { this.in_linestringtag = false; }
		 * else if (localName.equals("point")) { this.in_pointtag = false; }
		 * else
		 */
		if (localName.equals("coordinates")) {
			this.in_coordinatestag = false;
		}
	}

	/**
	 * Gets be called on the following structure: <tag>characters</tag>
	 */
	@Override
	public void characters(char ch[], int start, int length) {
		/*
		 * if(this.in_nametag){ if
		 * (navigationDataSet.getCurrentPlacemark()==null)
		 * navigationDataSet.setCurrentPlacemark(new Placemark()); String ti =
		 * new String(ch,start,length);
		 * 
		 * navigationDataSet.getCurrentPlacemark().setTitle(ti.trim());
		 * myplacemark.setTitle(ti.trim()); }else
		 */
		if (this.in_coordinatestag) {
			// if (navigationDataSet.getCurrentPlacemark()==null)
			// navigationDataSet.setCurrentPlacemark(new Placemark());
			// navigationDataSet.getCurrentPlacemark().setCoordinates(new
			// String(ch, start, length));
			coordinates = new String(ch, start, length);
			//Log.d("out","coo : "+coordinates);
			if (coordinates.trim().length() > 0) {
				// navigationDataSet.getCurrentPlacemark().setCoordinates(coordinates.trim());
				// buffer.append(coordinates.trim());
				// myplacemark.setCoordinates(coordinates.trim());

				// navigationDataSet.addCurrentPlacemark(myplacemark);

				coordinateList.add(coordinates);

				/*if (coordinateList.size() > 232 && coordinateList.size() < 238) {
					Log.e("Ch len",""+ch.length+" : "+new String(ch));
					int pos = (coordinateList.size() - 1);
					Log.e("data at", "pos " + pos + " : " + coordinates);
				}*/
			}
		}
	}
}