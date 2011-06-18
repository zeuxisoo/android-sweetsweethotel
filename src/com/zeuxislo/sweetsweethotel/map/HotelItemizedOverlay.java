package com.zeuxislo.sweetsweethotel.map;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class HotelItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> overlayItemList = new ArrayList<OverlayItem>();
	private Context context;
	
	public HotelItemizedOverlay(Context context, Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		
		this.context = context;
	}

	@Override
	protected OverlayItem createItem(int index) {
		return this.overlayItemList.get(index);
	}

	@Override
	public int size() {
		return this.overlayItemList.size();
	}

	@Override
	protected boolean onTap(int index) {
		OverlayItem item = this.overlayItemList.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(this.context);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	}
	
	public void addOverlay(OverlayItem overlay) {
		this.overlayItemList.add(overlay);
		this.populate();
	}

}
