package com.zeuxislo.sweetsweethotel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zeuxislo.sweetsweethotel.entry.AreaEntry;
import com.zeuxislo.sweetsweethotel.helper.UIHelper;
import com.zeuxislo.sweetsweethotel.lib.quickaction.ActionItem;
import com.zeuxislo.sweetsweethotel.lib.quickaction.QuickAction;
import com.zeuxislo.sweetsweethotel.model.AreaModel;

public class Main extends Activity implements OnItemClickListener {
	
	private AreaModel areaModel;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		this.areaModel = new AreaModel(this);

		ListView areaListView = (ListView) this.findViewById(R.id.main_area_list);
		areaListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.areaModel.findAllByNameArray()));
		areaListView.setOnItemClickListener(this);

		RelativeLayout headerLayout = UIHelper.loadHeaderLayout(this, (ViewGroup) this.findViewById(R.id.main_layout));
		ImageView action = (ImageView) headerLayout.findViewById(R.id.header_action);
		
		final ActionItem addActionItem = new ActionItem();
		addActionItem.setTitle("收藏");
		addActionItem.setIcon(this.getResources().getDrawable(R.drawable.ic_quickaction_bookmark));

		final ActionItem acceptActionItem = new ActionItem();
		acceptActionItem.setTitle("設定");
		acceptActionItem.setIcon(this.getResources().getDrawable(R.drawable.ic_quickaction_setting));
		
		action.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				QuickAction quickAction = new QuickAction(view);
				
				quickAction.addActionItem(addActionItem);
				quickAction.addActionItem(acceptActionItem);
				quickAction.show();
				
				return false;
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		AreaEntry areaEntry = this.areaModel.findAll().get(position);
		
		Bundle bundle = new Bundle();
		bundle.putSerializable("areaEntry", areaEntry);
		
		Intent intent = new Intent(this, HotelList.class);
		intent.putExtras(bundle);
		
		this.startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
		menu.add(0, 1, 0, "關於").setIcon(android.R.drawable.ic_menu_info_details);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId()){
			case 1:
				Toast.makeText(this, "Author: Zeuxis.Lo", Toast.LENGTH_LONG).show();
				break;
		}
		return true;
	}

}