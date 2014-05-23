/**
 * Jun 4, 2013
 * CharacterGrid.java
 * Daniel Pok
 * AP Java 6th
 */
package com.nokarateclass.rpgworld.grid;


import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
//import com.nokarateclass.rpgworld.R;
import com.nokarateclass.rpgworld.ui.ImageGridView;
import com.nokarateclass.rpgworld.characters.AndroidCharacter;
import com.nokarateclass.rpgworld.characters.CharacterActor;
import com.nokarateclass.rpgworld.io.SettingsHolder;

/**
 * @author poler_000
 *
 */
public class CharacterGrid implements BeatTask.BeatReceptor{

	/**
	 * 
	 */
	//constants
	public static final long TIMER_DELAY = 0;
	public static final long TIMER_PERIOD = 500;
	public static final int MAX_BEAT = 4;
	
	//variables
	private int mRows; //number of rows in the grid
	private int mCols; //number of cols in the grid
	protected ImageGridView mView; //the ImageGridView that this CharacterGrid is attached to
	protected CharacterActor[][] mGrid; //the 2D array representing the characters in the grid
	private Context mContext; //context of this grid, only applicable if there's a view attached...
	private Timer mTimer; //Timer object to send beats to this object
	private BeatTask mTask; //an instance of the BeatTask
	
	/**
	 * 
	 * @param view
	 */
	public CharacterGrid(ImageGridView view){
		mView = view;
		mRows = view.getNumRows();
		mCols = view.getNumCols();
		mContext = view.getContext();
		mView.setCharacterGrid(this);
		mGrid = new CharacterActor[mRows][mCols];
	}
	
	/**for grids that will not be displayed (though theoretically they could be converted...) mostly for internal utility
	 * 
	 * @param rows
	 * @param cols
	 * @param context
	 */
	public CharacterGrid(int rows, int cols, Context context){
		mContext = context;
		mRows = rows;
		mCols = cols;
		mGrid = new CharacterActor[mRows][mCols];
	}
	
	/**
	 * 
	 * @param view
	 * @param doNotSet if this is true, does not associate the grid with the view object
	 */
	public CharacterGrid(ImageGridView view, boolean doNotSet){
		mView = view;
		mRows = view.getNumRows();
		mCols = view.getNumCols();
		mContext = view.getContext();
		if(!doNotSet) mView.setCharacterGrid(this);
		mGrid = new CharacterActor[mRows][mCols];
	}
	
	public void beginBeat(){
		beginBeat(TIMER_DELAY, TIMER_PERIOD, MAX_BEAT);
	}
	
	public void beginBeat(long delay, long period, int maxBeat){
		if(mTimer != null){
			mTimer.cancel();
		}
		mTask = makeTask(maxBeat);
		mTimer = makeTimer(mTask,delay, period);
	}
	
	public void endBeat(){
		if(mTimer != null){
			mTimer.cancel();
		}
	}
	
	public ImageGridView getView(){
		return mView;
	}
	
	public void setView(ImageGridView view){
		mView = view;
		if(view != null) mContext = view.getContext();
		else mContext = null;
	}
	
	public void updateView(CharacterActor character){
		mView.changeImage(character.getSprite(), character.getLocation().getRow(), character.getLocation().getCol());
	}
	
	public void setGrid(CharacterActor[][] grid){
		mGrid = grid;
		if(mGrid != null){
			mRows = mGrid.length;
			mCols = mGrid[0].length;
		}
	}
	
	public CharacterActor[][] getGrid(){
		return mGrid;
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
				mView.changeImage(character.getSprite(), row, col);
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
			mView.changeImage(null, row, col);
			return true;
		}
	}
	
	public boolean replaceCharacter(CharacterActor character, int row, int col){
		removeCharacter(row, col);
		return addCharacter(character, row, col);
	}
	
	public boolean move(Location from, Location to){
		if(get(from) == null || !isValidLocation(to) || get(to) != null){
			return false;
		} else {
			CharacterActor character = get(from);
			set(null, from);
			set(character, to);
			character.setLocation(to);
			return true;
		}
	}
	
	public void click(int row, int col){
		//TODO: make this real code
		if(isValidLocation(row, col)){
			Log.i("Click Registered with Grid", String.format("Loc(%d, %d) occupied by %s", row, col, mGrid[row][col]));
			if(mGrid[row][col] != null){
				mGrid[row][col].interact(null);
			} else {
				defaultClick(row, col);
			}
		} else {
			Log.i("Click Registered with Grid", String.format("Loc(%d, %d), invalid location.", row, col));
		}
	}
	
	/**This is called if there is no object in the clicked location.
	 * Override to change default behavior.
	 * @param row
	 * @param col
	 */
	public void defaultClick(int row, int col){
		addCharacter(new AndroidCharacter(mContext),row, col);
	}
	
	public boolean isValidLocation(Location loc){
		return isValidLocation(loc.getRow(),loc.getCol());
	}
	
	public boolean isValidLocation(int row, int col){
		if(mGrid == null || row < 0 || row >= mRows || col < 0 || col >= mCols){
			return false;
		} else{
			return true;
		}
	}
	
	public CharacterActor get(Location loc){
		return get(loc.getRow(), loc.getCol());
	}
	
	public CharacterActor get(int row, int col){
		if(!isValidLocation(row,col)){
			return null;
		} else {
			return mGrid[row][col];
		}
	}
	
	public boolean set(CharacterActor character, Location loc){
		return set(character, loc.getRow(), loc.getCol());
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
					mView.changeImage(null, row, col);
				} else {
					mView.changeImage(character.getSprite(), row, col);
				}
			}
			return true;
		}
	}
	
	public void refreshView(int row, int col){
		if(isValidLocation(row, col)){
			mView.refreshView(row, col);
		}
	}
	
	private Timer makeTimer(TimerTask task, long delay, long period){
		Timer result = new Timer();
		//result.schedule(task, delay, period); //use this to execute task period millis after the last task actually executes
		result.scheduleAtFixedRate(task, delay, period); //use this one if you want the timer to start tasks based at a rate irregardless of when the last one executed.
		return result;
	}

	private BeatTask makeTask(int max){
		BeatTask result = new BeatTask(max, this);
		return result;
	}
	
	private class UiBeatUpdate implements Runnable{

		private CharacterActor mCharacter;
		private int mBeat;
		
		public UiBeatUpdate(CharacterActor c, int beat){
			mCharacter = c;
			mBeat = beat;
		}
		
		@Override
		public void run() {
			Log.i("Invoking on UI Thread", String.format("Beat %d: %s", mBeat, mCharacter));
			mCharacter.act(mBeat);
			Log.i("Finished Invoking on UI Thread", mCharacter.toString());
		}
		
	}
	
	@Override
	public void beat(int beat) {
		if(mGrid == null){
			return;
		}
		
		Activity a = (Activity) mContext;
		for(int i = 0; i < mRows; i++){
			for(int j = 0; j < mCols; j++){
				if(mGrid[i][j] != null){
					Log.i("Beat Updater", mGrid[i][j].toString());
					a.runOnUiThread(new UiBeatUpdate(mGrid[i][j],beat));
				}
			}
		}
	}
	
	public Context getContext(){
		return mContext;
	}
	
	public int getRows(){
		return mRows;
	}
	
	public int getCols(){
		return mCols;
	}
	
	public SettingsHolder saveValues(){
		SettingsHolder settings = new SettingsHolder();
		settings.put("grid:rows", mRows);
		settings.put("grid:cols", mCols);
		return settings;
	}
}
