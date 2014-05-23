/**
 * Jun 4, 2013
 * Location.java
 * Daniel Pok
 * AP Java 6th
 */
package com.nokarateclass.rpgworld.grid;

import java.io.Serializable;
import java.util.ArrayList;

import com.nokarateclass.rpgworld.characters.CharacterActor;

/**
 * @author poler_000
 *
 */
public class Location implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2351105194629568884L;

	//constants
	public static final int NO_DIRECTION = 0, NORTH = 1, EAST = 2, SOUTH = 3, WEST = 4;
	
	//variables
	private int mRow, mCol, mDirection;
	
	/**
	 * 
	 */
	public Location(int row, int col) {
		mRow = row;
		mCol = col;
		mDirection = NO_DIRECTION;
	}
	
	/**
	 * 
	 */
	public Location(int row, int col, int direction) {
		mRow = row;
		mCol = col;
		mDirection = direction;
	}
	
	public void setRow(int row){
		mRow = row;
	}
	
	public int getRow(){
		return mRow;
	}
	
	public void setCol(int col){
		mCol = col;
	}
	
	public int getCol(){
		return mCol;
	}

	public void setDirection(int direction){
		mDirection = direction;
	}
	
	public int getDirection(){
		return mDirection;
	}
	
	public Location getLocationAhead(){
		return getLocationInDirection(mDirection);
	}
	
	public Location getLocationInDirection(int direction){
		Location result = new Location(mRow, mCol, mDirection);
		int dx = 0, dy = 0;
		
		switch(direction){
			case NORTH: dx = -1;
				break;
			case EAST: dy = 1;
				break;
			case SOUTH: dx = 1;
				break;
			case WEST: dy = -1;
				break;
			default: break;
		}
		result.setRow(result.getRow() + dx);
		result.setCol(result.getCol() + dy);
		
		return result;
	}
	
	public static Location getLocationAhead(Location loc){
		Location result = new Location(loc.getRow(), loc.getCol(), loc.getDirection());
		result.getLocationAhead();
		return result;
	}
	
	public static Location getLocationInDirection(Location loc, int direction){
		Location result = new Location(loc.getRow(), loc.getCol(), loc.getDirection());
		result.getLocationInDirection(direction);
		return result;
	}
	
	public ArrayList<Location> getLocationsAtRange(int range){
		ArrayList<Location> result = new ArrayList<Location>();
		
		for(int i = mCol - range + 1; i < mCol + range; i++){
			result.add(new Location(i, mRow - range));
			result.add(new Location(i, mRow + range));
		}		
		
		for(int i = mRow - range; i <= mRow + range; i++){
			result.add(new Location(i, mCol - range));
			result.add(new Location(i, mCol + range));
		}
		
		return result;
	}
}
