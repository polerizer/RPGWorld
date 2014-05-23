/**
 * Jun 4, 2013
 * GridClickListener.java
 * Daniel Pok
 * AP Java 6th
 */
package com.nokarateclass.rpgworld.ui;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author poler_000
 *
 */
public class GridClickListener implements OnClickListener {

	//variables
	private final int mRow, mCol; //the row and col of this listener's object
	private final ImageGridView mView; //the ImageGridView the listener's object is in
	
	public GridClickListener(int row, int col, ImageGridView view){
		mRow = row;
		mCol = col;
		mView = view;
	}
	
	/**
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		Log.i("Grid Clicked", String.format("Row: %d Col: %d", mRow, mCol));
		if(mView != null){
			mView.click(mRow, mCol);
		}
	}

}
