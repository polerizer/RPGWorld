/**
 * Jun 10, 2013
 * EditorGrid.java
 * Daniel Pok
 * AP Java 6th
 */
package com.nokarateclass.rpgworld.editor;

import android.content.Context;
import android.util.Log;

import com.nokarateclass.rpgworld.backgrounds.BackgroundCharacter;
import com.nokarateclass.rpgworld.characters.CharacterActor;
import com.nokarateclass.rpgworld.characters.HeroCharacter;
import com.nokarateclass.rpgworld.grid.BackgroundGrid;
import com.nokarateclass.rpgworld.grid.CharacterGrid;
import com.nokarateclass.rpgworld.grid.MainCharacterGrid;
import com.nokarateclass.rpgworld.ui.ImageGridView;

/**
 * @author poler_000
 *
 */
public class EditorGrid extends ImageGridView {

	//constants
	public final static int MODE_NONE = 0, MODE_CHARACTER = 1, MODE_BACKGROUND = 2, MODE_SIM = 3;
	public final static int NO_ID = -1;
	
	//variables
	private int mMode;
	private int mId;
	private CharacterFactory mFac;
	
	/**
	 * @param context
	 * @param rows
	 * @param cols
	 * @param size
	 */
	public EditorGrid(Context context, int rows, int cols, int size) {
		super(context, rows, cols, size);
		setMode(MODE_NONE);
		setCharacterId(NO_ID);
		mFac = new CharacterFactory(getContext());
	}

	@Override
	public void click(int row, int col){
		if(mCharacterGrid == null || mBackgroundGrid == null || row < 0 || row >= mRows || col < 0 || col >= mCols){
			Log.w("Click Intercepted by View But Can't Forward", String.format("Loc(%d,%d) to Grid %s",row, col, mCharacterGrid));
			return;
		} else {
			if(mMode == MODE_CHARACTER){
				Log.i("Mode Character", String.format("Loc(%d,%d) to Grid %s",row, col, mCharacterGrid));
				//remove anything in that space
				if(mCharacterGrid.get(row, col) != null){
					mCharacterGrid.removeCharacter(row, col);
				} else {
					//try adding the new item
					CharacterActor character = mFac.makeCharacter(mId);
					if(character != null){
						mCharacterGrid.replaceCharacter(character, row, col);
						if(character instanceof HeroCharacter && mCharacterGrid instanceof MainCharacterGrid){
							Log.i("Setting Main Character", character.toString());
							((MainCharacterGrid) mCharacterGrid).setMainCharacter(character, true);
						}
					}
				}
			} else if(mMode == MODE_BACKGROUND){
				Log.i("Mode Background", String.format("Loc(%d,%d) to Grid %s",row, col, mBackgroundGrid));
				//remove anything in that space
//				if(mBackgroundGrid.get(row, col) != null){
//					mBackgroundGrid.removeCharacter(row, col);
//				} else {
					//try adding the new item
					BackgroundCharacter character = mFac.makeBackground(mId);
					if(character != null){
						mBackgroundGrid.replaceCharacter(character, row, col);
					}
//				}

			} else if(mMode == MODE_SIM){
				Log.i("Mode Sim", String.format("Loc(%d,%d) to Grid %s",row, col, mCharacterGrid));
				mCharacterGrid.click(row, col);
			} else {
				Log.i("Mode None", String.format("Loc(%d,%d)",row, col));
				//if it's mode none, then do nothing.
			}
		}
	}

	/**
	 * @return the mMode
	 */
	public int getMode() {
		return mMode;
	}

	/**
	 * @param mMode the mMode to set
	 */
	public void setMode(int mMode) {
		this.mMode = mMode;
	}

	/**
	 * @return the mId
	 */
	public int getCharacterId() {
		return mId;
	}

	/**
	 * @param mId the mId to set
	 */
	public void setCharacterId(int mId) {
		this.mId = mId;
	}
	
}
