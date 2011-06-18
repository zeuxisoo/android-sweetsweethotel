package com.zeuxislo.sweetsweethotel.entry;

import java.io.Serializable;

public class AreaEntry implements Serializable {

	public int id;
	public String name;
	
	public AreaEntry(int id, String name) {
		this.id = id;
		this.name = name;
	}	
}
