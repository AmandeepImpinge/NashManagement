package com.runtwo.fragmentactivities;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.runtwo.kmlclasses.MapService;
import com.runtwo.kmlclasses.NavigationDataSet;
import com.runtwo.main.Globals;
import com.runtwo.main.R;

public class RunUsaFragmentActivity extends FragmentActivity{
	
	GoogleMap mMap;
	boolean firstTime;
	double loclat = 0.0,loclng =0.0; 
	ProgressBar milesBar;
	String filename = "run_across_america_1st_1000"; 
	String filename2 = "run_across_america_2nd_1000";
	String filename3 = "run_across_america_3rd_1000";
	String filename4 = "run_across_america_4th_1000";
	String filename5 = "run_across_america_5th_1000";
	String filename6 = "run_across_america_6th_1000";
	String routeFileNames[] = {filename,filename2,filename3,filename4,filename5,filename6};
	NavigationDataSet routeDataSet;
	ProgressDialog pd;
	Polyline route;
	PolylineOptions routePolyOptions;
	int routecolor;
	Marker startMarker,endMarker;
	double startLat;
	double startLng;
	double endLat;
	double endLng;
	ArrayList<String> coordinatelist = new ArrayList<String>();
	ArrayList<LatLng> allLatLngs;
	LatLngBounds.Builder boundbuilder;
	SupportMapFragment mf;
	int currentRoute = 1;
	RelativeLayout mapContainer;
	LinearLayout map_cover;
	View actionBar;
	Globals global;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		/*setContentView(R.layout.common_fragment_activity_lay);
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Fragment f = new RunUsaFragment();
		ft.replace(R.id.contentintab,f);
		ft.commit();*/
		
		setContentView(R.layout.run_usa_frag_lay);
		
		global = (Globals) getApplicationContext();
		
		actionBar = findViewById(R.id.action_bar);
		((TextView)actionBar.findViewById(R.id.user_name)).setText(""+global.getUserName());
		
		firstTime = true;
		routecolor = getResources().getColor(R.color.route_line_color);
		
		milesBar = (ProgressBar)findViewById(R.id.miles_bar);
		mapContainer = (RelativeLayout)findViewById(R.id.map_container);
		map_cover = (LinearLayout)findViewById(R.id.map_cover);
		setUpMap();
		
		new RouteLoader().execute("");
		
	}
	
	
	public void setUpMap(){
		try {
			mf =(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map_usa);
			mMap = mf.getMap();
			mf.onResume();             
			mMap.setMyLocationEnabled(true);
			
			mMap.setOnMyLocationChangeListener(new OnMyLocationChangeListener() {
				public void onMyLocationChange(Location loc) {
					loclat = loc.getLatitude();
					loclng = loc.getLongitude();
					
					if(firstTime){
						firstTime = false;
						try {
							//mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loclat, loclng),17));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class RouteLoader extends AsyncTask<String,Integer,String>{
		ArrayList<String> tempCoodList = new ArrayList<String>();
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(RunUsaFragmentActivity.this).show(RunUsaFragmentActivity.this,"","Loading Route...");
		}
		
		@Override
		protected String doInBackground(String... params) {
			try {
				for(int i=0;i<routeFileNames.length;i++){
					tempCoodList = MapService.getNavigationArrayList(routeFileNames[i],RunUsaFragmentActivity.this);
					coordinatelist.addAll(tempCoodList);
				}
				if(coordinatelist.size() > 0){
					//create polyoptions while progress dialog is shown
					generatePolylineForMap();
				}else{
					Log.e("size","zero");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			pd.dismiss();
			//Add polyline to map after dialog is dismissed
			addRouteToMap();
		}
	} 
	
	private void generatePolylineForMap(){
		
		routePolyOptions = new PolylineOptions();
		allLatLngs = new ArrayList<LatLng>();
		boundbuilder = new LatLngBounds.Builder();
		
		int upto = coordinatelist.size();
		
		
		
		for(int i=0;i<upto;i++){
			//String cood = placemarkList.get(i).getCoordinates();
			String cood = coordinatelist.get(i);
			if(cood.length() > 0){
				try {
					String[] data = cood.split(",");
					//Log.e("cood",""+cood);
					double lat = Double.parseDouble(data[1]);
					double lng = Double.parseDouble(data[0]);
					if(i == 0){
						startLat = lat;
						startLng = lng;
					}else if(i == (upto-1)){
						endLat = lat;
						endLng = lng;
					}
					
					if((""+lat).length() > 6 && (""+lng).length() > 6){
						//routePolyOptions.add(new LatLng(lat,lng));
						LatLng ltlg = new LatLng(lat, lng);
						allLatLngs.add(ltlg);
						boundbuilder.include(ltlg);
					}
					
				} catch (Exception e) {
					Log.e("i",""+i);
					e.printStackTrace();
				}
			}
		}
	 	//Log.e("lats lngs",""+startLat+","+startLng+" : "+endLat+","+endLng);
		if(allLatLngs.size() > 0){
			routePolyOptions.addAll(allLatLngs);
			routePolyOptions.color(routecolor).width(4);
		}
	}
	
	private void addRouteToMap(){
		
		if(startMarker != null){
			startMarker.remove();
		}
		if(endMarker != null){
			endMarker.remove();
		}
		if(route != null){
			route.remove();
		}
		try {
			int size = routePolyOptions.getPoints().size();
			Log.e("adding","route points : "+size);
			route = mMap.addPolyline(routePolyOptions);
			
			startMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.start_route_marker)).position(new LatLng(startLat,startLng)));
			endMarker =  mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.stop_route_marker)).position(new LatLng(endLat,endLng)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundbuilder.build(),25));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
}