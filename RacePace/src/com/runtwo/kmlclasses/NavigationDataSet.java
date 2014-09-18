package com.runtwo.kmlclasses;
import java.util.ArrayList;
import java.util.Iterator;

import android.util.Log;


public class NavigationDataSet { 

private ArrayList<Placemark> placemarks = new ArrayList<Placemark>();
private Placemark currentPlacemark;
private Placemark routePlacemark;

public String toString() {
    String s= "";
    for (Iterator<Placemark> iter=placemarks.iterator();iter.hasNext();) {
        Placemark p = (Placemark)iter.next();
        s += p.getTitle() + "\n" + p.getDescription() + "\n" +p.getCoordinates()+ "\n\n";
    }
    return s;
}

public void addCurrentPlacemark(Placemark mark) {
	//placemarks.add(currentPlacemark);
	placemarks.add(mark);
}

public ArrayList<Placemark> getPlacemarks() {
    return placemarks;
}

public void setPlacemarks(ArrayList<Placemark> placemarks) {
    this.placemarks = placemarks;
}

public Placemark getCurrentPlacemark() {
    return currentPlacemark;
}

public void setCurrentPlacemark(Placemark currentPlacemark) {
    this.currentPlacemark = currentPlacemark;
}

public Placemark getRoutePlacemark() {
    return routePlacemark;
}

public void setRoutePlacemark(Placemark routePlacemark) {
    this.routePlacemark = routePlacemark;
}

}