package com.zeuxislo.sweetsweethotel;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zeuxislo.sweetsweethotel.adapter.HotelItemAdapter;
import com.zeuxislo.sweetsweethotel.entry.AreaEntry;
import com.zeuxislo.sweetsweethotel.entry.HotelEntry;
import com.zeuxislo.sweetsweethotel.helper.UIHelper;
import com.zeuxislo.sweetsweethotel.model.HotelModel;

public class HotelList extends Activity implements OnItemClickListener, OnClickListener {

	private static final int VIEW_MAP = 1;
	
	private HotelModel hotelModel;
	private AreaEntry areaEntry;
	private int clickedPosition;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.hotel_list);
		
		this.hotelModel = new HotelModel(this);
		
		this.areaEntry = (AreaEntry) this.getIntent().getExtras().getSerializable("areaEntry");
		
		// Bind Listview
		ListView hotelListView = (ListView) this.findViewById(R.id.hotel_list);
		hotelListView.setAdapter(new HotelItemAdapter(this, this.hotelModel.findByAreaId(this.areaEntry.id)));
		hotelListView.setOnItemClickListener(this);
		
		//
		RelativeLayout headerLayout = UIHelper.loadHeaderLayout(this, (ViewGroup) this.findViewById(R.id.hotel_list_layout));
		
		// Update title
		TextView hotel_list_title = (TextView) headerLayout.findViewById(R.id.header_subject);
		hotel_list_title.setText(hotel_list_title.getText() + " - " + this.areaEntry.name);
		
		// Long Click when display context menu
		//hotelListView.setOnCreateContextMenuListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		this.clickedPosition = position;
		
		ArrayList<HotelEntry> hotelList = this.hotelModel.findByAreaId(this.areaEntry.id);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(hotelList.get(position).name);
		builder.setItems(new CharSequence[] {"查看地圖", "撥打電話"}, this);
		builder.show();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		ArrayList<HotelEntry> hotelList = this.hotelModel.findByAreaId(this.areaEntry.id);
		Intent intent;
		
		switch(which) {
			case 0:				
				Bundle bundle = new Bundle();
				bundle.putSerializable("hotelEntry", hotelList.get(this.clickedPosition));
				
				intent = new Intent(this, HotelMap.class);
				intent.putExtras(bundle);
				this.startActivityForResult(intent, VIEW_MAP);	// Supported next activity play back to me
				break;
			case 1:
				if (hotelList.get(this.clickedPosition).telephone.trim().equals("") == false) {
					Log.d("Main", "Call: " + hotelList.get(this.clickedPosition).telephone);
					
					intent = new Intent();
					intent.setAction("android.intent.action.CALL");
					intent.setData(Uri.parse("tel:" + hotelList.get(this.clickedPosition).telephone));
					this.startActivity(intent);
				}else{
					Toast.makeText(this, "沒有電話提供", Toast.LENGTH_LONG).show();
				}
				break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch(requestCode){
			case VIEW_MAP:
				Toast.makeText(this, "目前位置: " + this.areaEntry.name, Toast.LENGTH_LONG).show();
				break;
		}
	}
	
	/* Long Click
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {		
		if (view.getId() == R.id.hotel_list) {
			ArrayList<HotelEntry> hotelList = this.hotelModel.findByAreaId(this.areaEntry.id);
			
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
			menu.setHeaderTitle(hotelList.get(info.position).name);
			
			menu.add(0, 1, 0, "查看地圖");
			menu.add(0, 2, 0, "撥打電話");
		}
	}
	
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		ArrayList<HotelEntry> hotelList = this.hotelModel.findByAreaId(this.areaEntry.id);
		
		switch(item.getItemId()) {
			case 1:
				
				break;
			case 2:
				if (hotelList.get(info.position).telephone.trim().equals("") == false) {
					Log.d("Main", "Call: " + hotelList.get(info.position).telephone);
					
					Intent intent = new Intent();
					intent.setAction("android.intent.action.CALL");
					intent.setData(Uri.parse("tel:" + hotelList.get(info.position).telephone));
					this.startActivity(intent);
				}
				break;
		}
		return true;
	}
	*/
}
