/**
 * Jun 4, 2013
 * MainCharacterGrid.java
 * Daniel Pok
 * AP Java 6th
 */
package com.nokarateclass.rpgworld.grid;

import android.util.Log;

//import com.nokarateclass.rpgworld.characters.AndroidCharacter;
import com.nokarateclass.rpgworld.characters.CharacterActor;
import com.nokarateclass.rpgworld.characters.HeroCharacter;
import com.nokarateclass.rpgworld.ui.ImageGridView;

/**
 * @author poler_000
 *
 */
public class MainCharacterGrid extends CharacterGrid {

	private CharacterActor mCharacter;
	
	/**
	 * @param view
	 */
	public MainCharacterGrid(ImageGridView view) {
		super(view);
	}

	public void defaultClick(int row, int col){
		if(mCharacter != null && (mCharacter.getGrid() == null || mCharacter.getLocation() == null)){
			mCharacter = null;
		}
		if(mCharacter == null){
			addCharacter(new HeroCharacter(getContext()),row, col);
			mCharacter = get(row,col);
			mCharacter.getLocation().setDirection(Location.NORTH);
		} else {
			mCharacter.setTarget(new Location(row, col));
		}
	}
	
	public void click(int row, int col){
		//TODO: make this real code
		if(isValidLocation(row, col)){
			Log.i("Click Registered with Grid", String.format("Loc(%d, %d) occupied by %s", row, col, mGrid[row][col]));
			if(mGrid[row][col] != null){
				mGrid[row][col].interact(mCharacter);
			} else {
				defaultClick(row, col);
			}
		} else {
			Log.i("Click Registered with Grid", String.format("Loc(%d, %d), invalid location.", row, col));
		}
	}
	
	public boolean removeCharacter(int row, int col){
		if(isValidLocation(row, col) && get(row, col) == mCharacter) mCharacter = null;
		return super.removeCharacter(row, col);
	}
	
	public void setMainCharacter(CharacterActor character){
		mCharacter = character;
	}
	
	public void setMainCharacter(CharacterActor character, boolean replace){
		if(replace){
			if(mCharacter != null){
				Log.i("Removing Main Character", mCharacter.toString());
				mCharacter.removeSelfFromGrid();
				Log.i("Adding Main Character", character.toString());
				mCharacter = character;
			} else {
				Log.i("Adding Main Character", character.toString());
				mCharacter = character;
			}
		} else{
			Log.i("Adding Without Replace Main Character", character.toString());
			mCharacter = character;			
		}
	}
	
	public CharacterActor getMainCharacter(){
		return mCharacter;
	}
}
