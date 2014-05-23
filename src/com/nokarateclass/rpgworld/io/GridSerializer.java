/**
 * Jun 11, 2013
 * GridSerializer.java
 * Daniel Pok
 * AP Java 6th
 */
package com.nokarateclass.rpgworld.io;

import java.util.ArrayList;

import android.content.Context;

import com.nokarateclass.rpgworld.characters.CharacterActor;
import com.nokarateclass.rpgworld.editor.CharacterFactory;
import com.nokarateclass.rpgworld.grid.CharacterGrid;
import com.nokarateclass.rpgworld.grid.Location;

/**
 * @author poler_000
 * this class handles writing grids to arraylists of settingsholders and also creates matrices of backgroudns and characters from those arraylists
 */
public class GridSerializer {

	//variables
	private Context mContext;
	private CharacterFactory mFac;
	
	public GridSerializer(Context context){
		mContext = context;
		mFac = new CharacterFactory(mContext);
	}
	
	public static ArrayList<SettingsHolder> getSettings(CharacterGrid grid){
		if(grid == null) return null;
		ArrayList<SettingsHolder> result = new ArrayList<SettingsHolder>();
		
		result.add(grid.saveValues());
		
		for(int i = 0; i < grid.getRows(); i++){
			for(int j = 0; j < grid.getCols(); j++){
				CharacterActor temp = grid.get(i, j);
				if(temp != null){
					result.add(temp.saveValues());
				}
			}
		}
		
		return result;
	}
	
	/** adds the characters in the arraylist to the grid
	 * 
	 * @param settings
	 * @param grid
	 */
	public void loadGrid(ArrayList<SettingsHolder> settings, CharacterGrid grid){
		if(settings == null || settings.isEmpty() || !settings.get(0).containsKey("grid:rows") || !settings.get(0).containsKey("grid:cols") || grid == null) return;
		
		ArrayList<CharacterActor> characters = new ArrayList<CharacterActor>();
		ArrayList<SettingsHolder> params = new ArrayList<SettingsHolder>();
		
		for(int i = 1; i < settings.size(); i++){
			if(settings.get(i).containsKey("character:id")){
				int id = settings.get(i).<Integer>getType("character:id");
				CharacterActor character = mFac.makeCharacter(id);
				if(character != null){
					characters.add(character);
					params.add(settings.get(i));
					if(settings.get(i).containsKey("character:location")){
						Location loc = settings.get(i).<Location>getType("character:location");
						character.putSelfInGrid(grid, loc.getRow(), loc.getCol());
					}
				}
			}
		}
		
		for(int i = 0; i < characters.size(); i++){
			characters.get(i).restoreValues(params.get(i));
		}
	}
	
	/** returns just the matrix portion of the grid object
	 * 
	 * @param settings
	 * @return
	 */
	public CharacterActor[][] getGrid(ArrayList<SettingsHolder> settings){
		if(settings == null || settings.isEmpty() || !settings.get(0).containsKey("grid:rows") || !settings.get(0).containsKey("grid:cols")) return null;
		
		CharacterGrid grid = new CharacterGrid(settings.get(0).<Integer>getType("grid:rows"),settings.get(0).<Integer>getType("grid:cols"),mContext);
		
		ArrayList<CharacterActor> characters = new ArrayList<CharacterActor>();
		ArrayList<SettingsHolder> params = new ArrayList<SettingsHolder>();
		
		for(int i = 1; i < settings.size(); i++){
			if(settings.get(i).containsKey("character:id")){
				int id = settings.get(i).<Integer>getType("character:id");
				CharacterActor character = mFac.makeCharacter(id);
				if(character != null){
					characters.add(character);
					params.add(settings.get(i));
					if(settings.get(i).containsKey("character:location")){
						Location loc = settings.get(i).<Location>getType("character:location");
						character.putSelfInGrid(grid, loc.getRow(), loc.getCol());
					}
				}
			}
		}
		
		for(int i = 0; i < characters.size(); i++){
			characters.get(i).restoreValues(params.get(i));
		}
		
		return grid.getGrid();
	}
}
