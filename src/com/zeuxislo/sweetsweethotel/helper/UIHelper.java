package com.zeuxislo.sweetsweethotel.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zeuxislo.sweetsweethotel.R;

public class UIHelper {

	public static RelativeLayout loadHeaderLayout(Context context, ViewGroup viewGroup) {
		RelativeLayout headerLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.header, null);
		viewGroup.addView(headerLayout, 0);
		
		return headerLayout;
	}
	
}
