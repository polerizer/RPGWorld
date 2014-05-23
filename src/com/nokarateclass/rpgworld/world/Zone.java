/**
 * Jun 8, 2013
 * Zone.java
 * Daniel Pok
 * AP Java 6th
 */
package com.nokarateclass.rpgworld.world;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;

import com.nokarateclass.rpgworld.characters.CharacterActor;
import com.nokarateclass.rpgworld.grid.BackgroundGrid;
import com.nokarateclass.rpgworld.grid.CharacterGrid;
import com.nokarateclass.rpgworld.io.GridSerializer;
import com.nokarateclass.rpgworld.io.SettingsHolder;

/**
 * @author poler_000
 *
 */
public class Zone implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6510398155068837988L;
	public ArrayList<SettingsHolder> mCharacters;//holds serialized info about the characters, first element in arraylist is the CharacterGrid's settings
	public ArrayList<SettingsHolder> mBackgrounds;//holds serialized info about the backgrounds, first element in arraylist is the BackgroundGrid's settings
	public CharacterActor[][] mCGrid; //must be null to serialize!
	public CharacterActor[][] mBGrid; //must be null to serialize!
	private String mId; //the unique id string of this Zone object. Cannot be added to a region without one
	protected String zoneNorth, zoneSouth, zoneEast, zoneWest; //the ids of the zones to the top, bottom, right and left of this one. id only b/c that's more serializable
															   //the region object is responsible for finding the correct zone
	
	public Zone() {
		
	}
	
	public Zone(CharacterGrid cGrid, BackgroundGrid bGrid){
		save(cGrid, bGrid);
	}
	
	public Zone(String id){
		mId = id;
	}

	public String getId(){
		return mId;
	}
	
	public void setId(String id){
		mId = id;
	}
	
	public void save(CharacterGrid cGrid, BackgroundGrid bGrid){
		mCharacters = GridSerializer.getSettings(cGrid);
		mBackgrounds = GridSerializer.getSettings(bGrid);
	}
	
	public void prepareForSerialization(){
		mCGrid = null;
		mBGrid = null;
	}
	
	public CharacterActor[][] makeCharacterMatrix(Context context){
		GridSerializer serial = new GridSerializer(context);
		mCGrid = serial.getGrid(mCharacters);
		return mCGrid;
	}
	
	public CharacterActor[][] makeBackgroundMatrix(Context context){
		GridSerializer serial = new GridSerializer(context);
		mBGrid = serial.getGrid(mBackgrounds);
		return mBGrid;
	}
	
	public CharacterGrid loadCharacterGrid(Context context, CharacterGrid grid){
		GridSerializer serial = new GridSerializer(context);
		serial.loadGrid(mCharacters, grid);
		mCGrid = grid.getGrid();
		return grid;
	}
	
	public CharacterGrid loadBackgroundGrid(Context context, BackgroundGrid grid){
		GridSerializer serial = new GridSerializer(context);
		serial.loadGrid(mBackgrounds, grid);
		mBGrid = grid.getGrid();
		return grid;
	}
}
