package com.zeuxislo.sweetsweethotel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;
import android.widget.ZoomControls;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.zeuxislo.sweetsweethotel.entry.HotelEntry;
import com.zeuxislo.sweetsweethotel.map.HotelItemizedOverlay;

public class HotelMap extends MapActivity implements OnClickListener, LocationListener, OnCheckedChangeListener {

	private MapView mapView;
	private MapController mapController;
	private Location myLocation;
	private HotelEntry hotelEntry;
	private GeoPoint hotelGeoPoint;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		this.hotelEntry = (HotelEntry) this.getIntent().getExtras().get("hotelEntry");

		if (this.hotelEntry.lat != null && this.hotelEntry.lng != null) {
			setContentView(R.layout.hotel_map);

			// update 1000ms, not consider location change, use Network provider not GPS because fast
			LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);

			this.mapView = (MapView) this.findViewById(R.id.hotel_map);
			this.mapController = this.mapView.getController();

			this.hotelGeoPoint = new GeoPoint(
				(int) (Double.parseDouble(this.hotelEntry.lat) * 1000000), 
				(int) (Double.parseDouble(this.hotelEntry.lng) * 1000000)
			);

			//this.mapView.setBuiltInZoomControls(true);
			this.mapController.setZoom(17);
			this.mapController.setCenter(this.hotelGeoPoint);
			this.mapController.animateTo(this.hotelGeoPoint);

			ZoomControls zoomControls = (ZoomControls) findViewById(R.id.hotel_map_zoomcontrols);
	        zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
	                @Override
					public void onClick(View v) {
						mapController.zoomIn();
					}
	        });
	        zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mapController.zoomOut();
					}
	        });

			this.labelHotel();
			
			ToggleButton view_hotel = (ToggleButton) this.findViewById(R.id.hotel_map_view_hotel);
			ToggleButton view_me = (ToggleButton) this.findViewById(R.id.hotel_map_view_me);
			
			view_hotel.setOnCheckedChangeListener(this);
			view_me.setOnCheckedChangeListener(this);
		}else{
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setMessage("沒有任何位置信息");
			alertDialog.setButton("OK", this);
			alertDialog.show();
		}
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		this.setResult(RESULT_OK);
		this.finish();
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onLocationChanged(Location location) {
		this.myLocation = location;
		this.labelLocation();	
	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}
	
	@Override
	public void onCheckedChanged(CompoundButton button, boolean isChecked) {
		this.labelLocation();
	}
	
	private void labelLocation() {
		if (this.mapView != null) {
			this.mapView.getOverlays().clear();
			this.labelMe();
			this.labelHotel();
			this.mapView.invalidate();
		}
	}
	
	private void labelHotel() {
		ToggleButton view_hotel = (ToggleButton) this.findViewById(R.id.hotel_map_view_hotel);

		if (view_hotel.isChecked()) {
			HotelItemizedOverlay myItemizedOverlay = new HotelItemizedOverlay(this, this.getResources().getDrawable(R.drawable.ic_map_marker_hotel));
			myItemizedOverlay.addOverlay(new OverlayItem(this.hotelGeoPoint, this.hotelEntry.name, this.hotelEntry.address));
			this.mapView.getOverlays().add(myItemizedOverlay);
		}
	}
	
	private void labelMe() {
		ToggleButton view_me = (ToggleButton) this.findViewById(R.id.hotel_map_view_me);

		if (view_me.isChecked()) {
			if (this.myLocation != null) {
				GeoPoint geoPoint = new GeoPoint(
					(int)(this.myLocation.getLatitude() * 1E6),
					(int)(this.myLocation.getLongitude() * 1E6)
				);
				
				HotelItemizedOverlay myItemizedOverlay = new HotelItemizedOverlay(this, this.getResources().getDrawable(R.drawable.ic_map_marker_me));
				myItemizedOverlay.addOverlay(new OverlayItem(geoPoint, "位置", "我的位置"));
				this.mapView.getOverlays().add(myItemizedOverlay);
			}
		}
	}

}
