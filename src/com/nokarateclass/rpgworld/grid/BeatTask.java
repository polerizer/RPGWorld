/**
 * Jun 6, 2013
 * BeatTask.java
 * Daniel Pok
 * AP Java 6th
 */
package com.nokarateclass.rpgworld.grid;

import java.io.Serializable;
import java.util.TimerTask;

import android.util.Log;

/**
 * @author poler_000
 * sends beats to attached grid up to max beat
 */
public class BeatTask extends TimerTask implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2661304597576684777L;
	//variables
	private int mBeat, mMaxBeat;
	private CharacterGrid mGrid;
	
	public BeatTask(int maxBeat, CharacterGrid grid){
		mMaxBeat = maxBeat;
		mBeat = 0;
		mGrid = grid;
	}
	
	@Override
	public void run() {
		Log.i("Timer Fired", String.format("Beat = %d", mBeat));
		mGrid.beat(mBeat);
		if(++mBeat >= mMaxBeat){
			mBeat = 0;
		}
	}

	/**
	 * 
	 * @author poler_000
	 *
	 */
	public interface BeatReceptor{
		
		public void beat(int beat);
		
	}
}
