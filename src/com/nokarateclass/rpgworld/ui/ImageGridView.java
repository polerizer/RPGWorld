/**
 * Jun 3, 2013
 * ImageGridView.java
 * Daniel Pok
 * AP Java 6th
 */

package com.nokarateclass.rpgworld.ui;

import com.nokarateclass.rpgworld.R; //for reusability, this needs to be changed to the R of the application
import com.nokarateclass.rpgworld.grid.BackgroundGrid;
import com.nokarateclass.rpgworld.grid.CharacterGrid;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @author poler_000
 *
 */

public class ImageGridView extends LinearLayout{

	//variables
	private Context mContext; //application context passed from constructor
	private int mCellSize; //size in pixels of a cell, needs to be determined by size of the screen and number of row/cols
	protected final int mRows; //number of rows in this grid
	protected final int mCols; //number of cols in this grid
	private ImageView[][] grid; //a 2D array of the ImageViews in this grid
	protected CharacterGrid mCharacterGrid; //the CharacterGrid object that this view is associated with
	protected BackgroundGrid mBackgroundGrid; //the background objects, handling clicks too

	/**
	 * 
	 * @param context
	 * @param rows
	 * @param cols
	 */
	public ImageGridView(Context context, int rows, int cols, int size) {
		super(context);
		mRows = rows;
		mCols = cols;
		mCellSize = size;
		mContext = context;
		setAttributes();
		makeGrid();
	}
	
	/**
	 * @param context
	 * @param attrs
	 */
	public ImageGridView(Context context, AttributeSet attrs, int rows, int cols, int size) {
		super(context, attrs);
		mRows = rows;
		mCols = cols;
		mCellSize = size;
		mContext = context;
		makeGrid();
	}
	
	/**sets the background of the linear layout
	 *  setBackground is valid as of 
	 * 
	 * @param background the drawable to set
	 */
	public void setBackground(Drawable background){
		
		if(android.os.Build.VERSION.SDK_INT < 16){
			setBackgroundDrawable(background);
		} else {
			super.setBackground(background);			
		}

	}
	
	public  static void setBackground(View view, Drawable background){
		if(android.os.Build.VERSION.SDK_INT < 16){
			view.setBackgroundDrawable(background);
		} else {
			view.setBackground(background);			
		}
	}
	
	private void setAttributes(){
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		setPadding(0, 0, 0, 0);
		setOrientation(VERTICAL);
		
		//for testing purposes
		setBackground(mContext.getResources().getDrawable(R.drawable.gray)); //TODO: remove this in final version
		
	}
	
	private void makeGrid(){
		makeGrid(mRows, mCols, mCellSize);
	}
	
	private void makeGrid(int rows, int cols, int length){
		//clear this grid first so we can rebuild it
		removeAllViews();
		
		//remake the grid array
		grid = new ImageView[rows][cols];
		
		//make the rows
		for(int i = 0; i < rows; i++){
			LinearLayout row = makeRow(length);
			addView(row);
			//make the columns
			for(int j = 0; j < cols; j++){
				ImageView col = makeCol(row,length);
				row.addView(col);
				col.setOnClickListener(new GridClickListener(i,j, this));
				grid[i][j] = col;
			}
		}
	}
	
	/**Creates a new LinearLayout "row" of the specified height
	 * 
	 * @param size the height of the row in pixels
	 * @return LinearLayout object of specified height that wraps content
	 */
	private LinearLayout makeRow(int size){
		//create objects
		LinearLayout result = new LinearLayout(mContext); //a new LinearLayout for the new row
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, size); //LayoutParams tell the parent container how to display this one
		
		//set the LayoutParams
		params.gravity = Gravity.CENTER_HORIZONTAL; //does not resize the row but centers it in the parent LinearLayout instead
		
		//set the parameters of the LinearLayout
		result.setLayoutParams(params); //sets the layout
		result.setPadding(0, 0, 0, 0);
		result.setOrientation(HORIZONTAL);
		
		//for testing purposes
		//result.setBackground(randomColor());//TODO: remove this in final version
		
		//return the result
		return result;
	}
	
	/**Adds a properly sized ImageView to the specified LinearLayout
	 * 
	 * @param size the width of the cell in pixels (cell will match parent height, should be square)
	 * @return ImageView object of specified size created inside the layout
	 */
	private ImageView makeCol(LinearLayout layout, int size){
		//create objects
		ImageView result = new ImageView(mContext);
		LayoutParams params = new LayoutParams(size, LayoutParams.MATCH_PARENT); //LayoutParams tell the parent container how to display this one
		
		//set the LayoutParams
		params.gravity = Gravity.CENTER; //does not resize the cell but centers it in the parent LinearLayout instead
		
		//set the parameters of the ImageView
		result.setLayoutParams(params); //sets the layout
		result.setPadding(0, 0, 0, 0);
		
		//for testing purposes
		//result.setBackground(randomColor()); //TODO: remove this in final version
		
		//return the result
		return result;
	}
	
	/**For debugging only! Gets a random color drawable.
	 * 
	 * @return a random colored drawable
	 */
	public Drawable randomColor(){
		int[] colors = new int[]{R.drawable.black,R.drawable.white,R.drawable.red,R.drawable.blue,R.drawable.green,
				R.drawable.purple,R.drawable.yellow,R.drawable.orange}; //gray is taken out b/c it's the bg color
		return mContext.getResources().getDrawable(colors[(int) (Math.random()*colors.length)]);
	}
	
	/**
	 * 
	 * @param image
	 * @param row
	 * @param col
	 */
	public void changeImage(Drawable image, int row, int col){
		//check the image, valid row/col, and that there is an ImageView in the position indicated
		if(row >= 0 && row < mRows && col >= 0 && col < mCols && grid[row][col] != null){
			//update the picture
			grid[row][col].setImageDrawable(image);
			grid[row][col].invalidate();
		}
	} //TODO: error checking
	
	/**
	 * 
	 * @param image
	 * @param row
	 * @param col
	 */
	public void changeBackground(Drawable image, int row, int col){
		//check the image, valid row/col, and that there is an ImageView in the position indicated
		if(row >= 0 && row < mRows && col >= 0 && col < mCols && grid[row][col] != null){
			//update the picture
			setBackground(grid[row][col],image);
			grid[row][col].invalidate();
		}
	} //TODO: error checking
	
	public void refreshView(int row, int col){
		if(row >= 0 && row < mRows && col >= 0 && col < mCols && grid[row][col] != null){
			//update the picture
			grid[row][col].invalidate();
		}
	}
	
	/**
	 * 
	 * @return the number of rows in this ImageGridView
	 */
	public int getNumRows(){
		return mRows;
	}
	
	/**
	 * 
	 * @return the number of cols in this ImageGridView
	 */
	public int getNumCols(){
		return mCols;
	}
	
	public void setCharacterGrid(CharacterGrid characterGrid){
		Log.d("Registering Grid", characterGrid.toString());
		mCharacterGrid = characterGrid;
		if(mCharacterGrid != null){
			mCharacterGrid.setView(this);
		}
	}
	
	public CharacterGrid getCharacterGrid(){
		return mCharacterGrid;
	}
	
	/**
	 * @return the mBackgroundGrid
	 */
	public BackgroundGrid getBackgroundGrid() {
		return mBackgroundGrid;
	}
	

	/**
	 * @param mBackgroundGrid the mBackgroundGrid to set
	 */
	public void setBackgroundGrid(BackgroundGrid mBackgroundGrid) {
		this.mBackgroundGrid = mBackgroundGrid;
		if(mBackgroundGrid != null){
			mBackgroundGrid.setView(this);
		}
	}

	public void click(int row, int col){
		if(mCharacterGrid == null || row < 0 || row >= mRows || col < 0 || col >= mCols){
			Log.w("Click Intercepted by View But Can't Forward", String.format("Loc(%d,%d) to Grid %s",row, col, mCharacterGrid));
			return;
		} else {
			mCharacterGrid.click(row, col);
		}
	}

}
