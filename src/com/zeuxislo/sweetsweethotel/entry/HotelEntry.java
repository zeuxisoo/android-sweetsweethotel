package com.zeuxislo.sweetsweethotel.entry;

import java.io.Serializable;

public class HotelEntry implements Serializable {

	public int id;
	public int area_id;
	public String name;
	public String address;
	public String telephone;
	public String lat;
	public String lng;
	
	public HotelEntry(int id, int area_id, String name, String address, String telephone, String lat, String lng) {
		this.id = id;
		this.area_id = area_id;
		this.name = name;
		this.address = address;
		this.telephone = telephone;
		this.lat = lat;
		this.lng = lng;
	}
	
}
