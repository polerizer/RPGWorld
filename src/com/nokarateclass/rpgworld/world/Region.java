/**
 * Jun 8, 2013
 * Region.java
 * Daniel Pok
 * AP Java 6th
 */
package com.nokarateclass.rpgworld.world;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author poler_000
 *
 */
public class Region implements Serializable{

	private String mId; //the 
	private HashMap<String,Zone> mZones; //a hashmap mapping zone ids to their objects
	
	public Region() {
		
	}
	
	public String getId(){
		return mId;
	}
	
	public void setId(String id){
		mId = id;
	}

	public boolean addZone(Zone zone){
		if(zone.getId().isEmpty()) return false;
		else {
			mZones.put(zone.getId(), zone);
			return true;
		}
	}
	
	public Zone getZone(String id){
		return mZones.get(id);
	}
	
	
}
