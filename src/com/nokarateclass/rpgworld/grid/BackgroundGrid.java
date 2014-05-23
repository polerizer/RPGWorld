/**
 * Jun 6, 2013
 * BackgroundGrid.java
 * Daniel Pok
 * AP Java 6th
 */
package com.nokarateclass.rpgworld.grid;

import android.util.Log;

import com.nokarateclass.rpgworld.characters.CharacterActor;
import com.nokarateclass.rpgworld.ui.ImageGridView;

/**
 * @author poler_000
 *
 */
public class BackgroundGrid extends CharacterGrid {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5592380041166090258L;

	/**
	 * @param view
	 */
	public BackgroundGrid(ImageGridView view) {
		super(view, true);
		mView.setBackgroundGrid(this);
	}

	public void updateView(CharacterActor character){
		mView.changeBackground(character.getSprite(), character.getLocation().getRow(), character.getLocation().getCol());
	}
	
	public void updateAll(){
		for(int i = 0; i < getRows(); i++){
			for(int j = 0; j < getCols(); j++){
				updateView(mGrid[i][j]);
			}
		}
	}
	
	/**
	 * 
	 * @param character
	 * @param row
	 * @param col
	 */
	public boolean addCharacter(CharacterActor character, int row, int col){
		if(!isValidLocation(row, col) || mGrid[row][col] != null || character == null){
			Log.i("Character NOT added", "NOT ADDED");
			return false;
		} else{
			mGrid[row][col] = character;
			character.setGrid(this);
			character.setLocation(new Location(row, col));
			if(mView != null){
				mView.changeBackground(character.getSprite(), row, col);
				Log.i("Character Added", "Added!");
			}
			return true;
		}
	}
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @return true if the object at that location was removed, false if the location is invalid or nothing was in that position
	 */
	public boolean removeCharacter(int row, int col){
		if(!isValidLocation(row,col) || mGrid[row][col] == null){
			return false;
		} else {
			mGrid[row][col].setGrid(null);
			mGrid[row][col].setLocation(null);
			mGrid[row][col] = null;
			mView.changeBackground(null, row, col);
			return true;
		}
	}
	
	/**Similar to addCharacter, but doesn't associate grid/loc with character and can set null
	 * More raw than addCharacter, use only when addCharacter would have undesirable side effects.
	 * @param character
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean set(CharacterActor character, int row, int col){
		if(!isValidLocation(row, col)){
			return false;
		} else{
			mGrid[row][col] = character;
			if(mView != null){
				if(character == null){
					mView.changeBackground(null, row, col);
				} else {
					mView.changeBackground(character.getSprite(), row, col);
				}
			}
			return true;
		}
	}
}
