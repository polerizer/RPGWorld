/**
 * Jun 4, 2013
 * Character.java
 * Daniel Pok
 * AP Java 6th
 */
package com.nokarateclass.rpgworld.characters;

import java.io.Serializable;
import java.util.ArrayList;

import com.nokarateclass.rpgworld.editor.CharacterFactory;
import com.nokarateclass.rpgworld.grid.CharacterGrid;
import com.nokarateclass.rpgworld.grid.Location;
import com.nokarateclass.rpgworld.io.SettingsHolder;

import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * @author poler_000
 *
 */
public class CharacterActor {

	/**
	 * 
	 */
	//variables
	public final Integer mId; //id of the character or background type (i.e. HERO, ROCK, SAND, etc.)
	private Drawable mSprite; //the picture associated with the character (just one for now...)
	private CharacterGrid mGrid; //the grid this character is in
	private Location mLoc; //the location that this actor occupies in the grid
	private Location mTarget; //location to route towards
	protected Status mStatus; //this character's status
	protected Player mPlayer; //the character's stats
	
	/**
	 * 
	 */
	public CharacterActor(){
		mId = CharacterFactory.DEFAULT_CHARACTER;
		mStatus = new Status(Status.NO_ACTION, null);
		mPlayer = new Player(this);
	}
	
	public CharacterActor(int id){
		mId = id;
		mStatus = new Status(Status.NO_ACTION, null);
		mPlayer = new Player(this);
	}
	
	/**
	 * 
	 * @param sprite
	 */
	public CharacterActor(Drawable sprite, int id){
		mId = id;
		mSprite = sprite;
		mStatus = new Status(Status.NO_ACTION, null);
		mPlayer = new Player(this);
	}
	
	public void setSprite(Drawable sprite){
		mSprite = sprite;
		mGrid.updateView(this);
	}
	
	public Drawable getSprite(){
		return mSprite;
	}
	
	public void setGrid(CharacterGrid grid){
		mGrid = grid;
	}
	
	public CharacterGrid getGrid(){
		return mGrid;
	}
	
	public void setLocation(int row, int col){
		mLoc = new Location(row, col);
	}
	
	public void setLocation(Location loc){
		mLoc = loc;
	}
	
	public Location getLocation(){
		return mLoc;
	}
	
	public void turn(){
		if(mLoc.getDirection() != 0){
			switch(mLoc.getDirection()){
			case 0: 
			}
		}
	}
	
	public boolean removeSelfFromGrid(){
		if(mGrid == null || mLoc == null){
			return false;
		} else{
			return mGrid.removeCharacter(mLoc.getRow(), mLoc.getCol());
		}
	}
	
	public boolean putSelfInGrid(CharacterGrid grid, int row, int col){
		if(grid == null){
			return false;
		} else {
			setGrid(grid);
			return mGrid.addCharacter(this, row, col);
		}
		
	}
	
	public boolean moveTo(int row, int col){
		if(mGrid == null){
			return false;
		} else {
			int dir = mLoc.getDirection();
			return mGrid.move(mLoc, new Location(row, col, dir));
		}
	}
	
	public boolean moveTo(Location loc){
		if(mGrid == null){
			return false;
		} else {
			return mGrid.move(mLoc, loc);
		}
	}

	public void act(int beat){
		
	}
	
	public void interact(CharacterActor character){
		
	}
	
	public ArrayList<CharacterActor> lineOfSight(int range){
		ArrayList<CharacterActor> result = new ArrayList<CharacterActor>();
		
		if(mLoc == null || mLoc.getDirection() == Location.NO_DIRECTION){
			return result;
		}
		
		Location loc = new Location(mLoc.getRow(), mLoc.getCol(), mLoc.getDirection());
		for(int i = 0; i < range; i++){
			loc = loc.getLocationAhead();
			if(mGrid.isValidLocation(loc)){
				if(mGrid.get(loc) != null){
					result.add(mGrid.get(loc));
				}
			} else {
				return result;
			}
		}
		
		return result;
	}
	
	public ArrayList<CharacterActor> lineOfSight(int range, int direction){
		ArrayList<CharacterActor> result = new ArrayList<CharacterActor>();
		
		if(mLoc == null) return result;
		if(direction == Location.NO_DIRECTION){
			return result;
		}
		
		Location loc = new Location(mLoc.getRow(), mLoc.getCol(), direction);
		for(int i = 0; i < range; i++){
			loc = loc.getLocationInDirection(direction);
			//Log.i("lineOfSight", String.format("Checking (%d, %d)", loc.getRow(), loc.getCol()));
			if(mGrid.isValidLocation(loc)){
				if(mGrid.get(loc) != null){
					result.add(mGrid.get(loc));
				}
			} else {
				//Log.i("lineOfSight", "Stopping: Reached invalid location");
				return result;
			}
		}
		
		//Log.i("lineOfSight", "Stopping: Reached max range");
		return result;
	}
	
	public ArrayList<CharacterActor> fourWayLineOfSight(int range){
		ArrayList<CharacterActor> result = new ArrayList<CharacterActor>();
		result.addAll(lineOfSight(range, Location.NORTH));
		result.addAll(lineOfSight(range, Location.SOUTH));
		result.addAll(lineOfSight(range, Location.EAST));
		result.addAll(lineOfSight(range, Location.WEST));
		return result;
	}
	
	public CharacterActor lineOfSight(){
		
		if(mLoc == null || mLoc.getDirection() == Location.NO_DIRECTION){
			return null;
		}
		
		Location loc = mLoc;
		while(true){
			loc = loc.getLocationAhead();
			if(mGrid.isValidLocation(loc)){
				if(mGrid.get(loc) != null){
					return mGrid.get(loc);
				}
			} else {
				return null;
			}
		}
	}
	
	public ArrayList<CharacterActor> getCharactersInRange(int range){
		ArrayList<CharacterActor> result = new ArrayList<CharacterActor>();
		if(mLoc == null) return result;
		
		for(int i = mLoc.getRow() - range; i <= mLoc.getRow() + range; i++){
			for(int j = mLoc.getCol() - range; j <= mLoc.getCol() + range; j++){
				if(!(i == mLoc.getRow() && j == mLoc.getCol()) && mGrid.isValidLocation(i, j)){
					if(mGrid.get(i, j) != null){
						result.add(mGrid.get(i, j));
					}
				}
			}	
		}
		
		return result;
	}
	
	public int getDirectionTowards(Location loc){
		return 0;//TODO: implement
	}
	
	public ArrayList<CharacterActor> getAdjacentCharacters(){
		return getCharactersInRange(1);
	}
	
	public boolean canSee(CharacterActor character){
		return lineOfSight() == character;
	}
	
	public boolean isInRange(CharacterActor character, int range){
		for(CharacterActor i:getCharactersInRange(range)){
			if(i == character) return true;
		}
		return false;
	}
	
	public boolean isAdjacent(CharacterActor character){
		for(CharacterActor i:getAdjacentCharacters()){
			if(i == character) return true;
		}
		return false;
	}
	
	public Location getNearestEmptyLocation(int range){
		
		for(int k = 1; k <= range; k++){
			for(Location i: mLoc.getLocationsAtRange(k)){
				if(mGrid.isValidLocation(i) && mGrid.get(i) == null){
					return i;
				}
			}
		}
		return null;

	}
	
	public void setTarget(Location loc){
		Log.i("Setting Target", String.format("(%d,%d)", loc.getRow(), loc.getCol()));
		mTarget = loc;
		mStatus.mStatus = Status.PATHING;
		mStatus.mFuture = new Status(Status.NO_ACTION, null);
	}
	
	public void stepTowardTarget(){
		Location step = getLocationToward(mTarget);
		if(step != null){
			moveTo(step);
		} else {
			Log.i("Cannot Move Towards Target", "Status: " + mStatus.mStatus);
		}
	}
	
	public Location getLocationToward(Location loc){
		if(loc == null) return null;
		
		int dRows = loc.getRow() - mLoc.getRow();
		int dCols = loc.getCol() - mLoc.getCol();
		Log.i("Offset", String.format("dX: %d, dY: %d", dRows, dCols));
		
		if(dRows == 0 && dCols == 0){
			//TODO code to complete routing 
			Log.i("Finished Pathing", String.format("From(%d, %d)",mLoc.getRow(), mLoc.getCol()));
			mTarget = null;
			if(mStatus.mFuture == null){
				mStatus.mStatus = Status.NO_ACTION;
			} else {
			mStatus = mStatus.mFuture;				
			}
			return null;
		}
		
		if(Math.abs(dRows) < Math.abs(dCols)){
			//move cols first
			if(dCols > 0){
				//columns is positive
				//location is valid
				if(mGrid.isValidLocation(mLoc.getRow(), mLoc.getCol() + 1) && mGrid.get(mLoc.getRow(), mLoc.getCol() + 1) == null){
					Log.i("Moving East", String.format("From(%d, %d) To(%d,%d)",mLoc.getRow(), mLoc.getCol(), mLoc.getRow(), mLoc.getCol() + 1));
					return new Location(mLoc.getRow(), mLoc.getCol() + 1, mLoc.getDirection());
				} else {
					//move rows instead
					if(dRows > 0){
						if(mGrid.isValidLocation(mLoc.getRow() + 1, mLoc.getCol()) && mGrid.get(mLoc.getRow() + 1, mLoc.getCol()) == null){
							Log.i("Moving South", String.format("From(%d, %d) To(%d,%d)",mLoc.getRow(), mLoc.getCol(), mLoc.getRow() + 1, mLoc.getCol()));
							return new Location(mLoc.getRow() + 1, mLoc.getCol(), mLoc.getDirection());
						}
					} else {
						if(mGrid.isValidLocation(mLoc.getRow() - 1, mLoc.getCol()) && mGrid.get(mLoc.getRow() - 1, mLoc.getCol()) == null){
							Log.i("Moving North", String.format("From(%d, %d) To(%d,%d)",mLoc.getRow(), mLoc.getCol(), mLoc.getRow() - 1, mLoc.getCol()));
							return new Location(mLoc.getRow() - 1, mLoc.getCol(), mLoc.getDirection());
						}
					}
				}

			} else {
				//columns is negative
				if(mGrid.isValidLocation(mLoc.getRow(), mLoc.getCol() - 1) && mGrid.get(mLoc.getRow(), mLoc.getCol() - 1) == null){
					Log.i("Moving West", String.format("From(%d, %d) To(%d,%d)",mLoc.getRow(), mLoc.getCol(), mLoc.getRow(), mLoc.getCol() - 1));
					return new Location(mLoc.getRow(), mLoc.getCol() - 1, mLoc.getDirection());
				} else {
					//move rows instead
					if(dRows > 0){
						if(mGrid.isValidLocation(mLoc.getRow() + 1, mLoc.getCol()) && mGrid.get(mLoc.getRow() + 1, mLoc.getCol()) == null){
							Log.i("Moving South", String.format("From(%d, %d) To(%d,%d)",mLoc.getRow(), mLoc.getCol(), mLoc.getRow() + 1, mLoc.getCol()));
							return new Location(mLoc.getRow() + 1, mLoc.getCol(), mLoc.getDirection());
						}
					} else {
						if(mGrid.isValidLocation(mLoc.getRow() - 1, mLoc.getCol()) && mGrid.get(mLoc.getRow() - 1, mLoc.getCol()) == null){
							Log.i("Moving North", String.format("From(%d, %d) To(%d,%d)",mLoc.getRow(), mLoc.getCol(), mLoc.getRow() - 1, mLoc.getCol()));
							return new Location(mLoc.getRow() - 1, mLoc.getCol(), mLoc.getDirection());
						}
					}
				}
			}
		} else{
			//move rows first
			if(dRows > 0){
				//location is valid
				if(mGrid.isValidLocation(mLoc.getRow() + 1, mLoc.getCol()) && mGrid.get(mLoc.getRow() + 1, mLoc.getCol()) == null){
					Log.i("Moving South", String.format("From(%d, %d) To(%d,%d)",mLoc.getRow(), mLoc.getCol(), mLoc.getRow() + 1, mLoc.getCol()));
					return new Location(mLoc.getRow() + 1, mLoc.getCol(), mLoc.getDirection());
				} else {
					//move cols instead
					if(dCols > 0){
						//location is valid
						if(mGrid.isValidLocation(mLoc.getRow(), mLoc.getCol() + 1) && mGrid.get(mLoc.getRow(), mLoc.getCol() + 1) == null){
							Log.i("Moving East", String.format("From(%d, %d) To(%d,%d)",mLoc.getRow(), mLoc.getCol(), mLoc.getRow(), mLoc.getCol() + 1));
							return new Location(mLoc.getRow(), mLoc.getCol() + 1, mLoc.getDirection());
						}
					} else {
						if(mGrid.isValidLocation(mLoc.getRow(), mLoc.getCol() - 1) && mGrid.get(mLoc.getRow(), mLoc.getCol() - 1) == null){
							Log.i("Moving West", String.format("From(%d, %d) To(%d,%d)",mLoc.getRow(), mLoc.getCol(), mLoc.getRow(), mLoc.getCol() - 1));
							return new Location(mLoc.getRow(), mLoc.getCol() - 1, mLoc.getDirection());
						}
					}
				}

			} else {
				if(mGrid.isValidLocation(mLoc.getRow() - 1, mLoc.getCol()) && mGrid.get(mLoc.getRow() - 1, mLoc.getCol()) == null){
					Log.i("Moving North", String.format("From(%d, %d) To(%d,%d)",mLoc.getRow(), mLoc.getCol(), mLoc.getRow() - 1, mLoc.getCol()));
					return new Location(mLoc.getRow() - 1, mLoc.getCol(), mLoc.getDirection());
				} else {
					//move cols instead
					if(dCols > 0){
						//location is valid
						if(mGrid.isValidLocation(mLoc.getRow(), mLoc.getCol() + 1) && mGrid.get(mLoc.getRow(), mLoc.getCol() + 1) == null){
							Log.i("Moving East", String.format("From(%d, %d) To(%d,%d)",mLoc.getRow(), mLoc.getCol(), mLoc.getRow(), mLoc.getCol() + 1));
							return new Location(mLoc.getRow(), mLoc.getCol() + 1, mLoc.getDirection());
						}
					} else {
						if(mGrid.isValidLocation(mLoc.getRow(), mLoc.getCol() - 1) && mGrid.get(mLoc.getRow(), mLoc.getCol() - 1) == null){
							Log.i("Moving West", String.format("From(%d, %d) To(%d,%d)",mLoc.getRow(), mLoc.getCol(), mLoc.getRow(), mLoc.getCol() - 1));
							return new Location(mLoc.getRow(), mLoc.getCol() - 1, mLoc.getDirection());
						}
					}
				}
			}
		}
		
		//TODO complete routing with failure
		Log.i("Cannot Move", String.format("From(%d, %d)",mLoc.getRow(), mLoc.getCol()));
		mTarget = null;
		return null;
	}
	
	public void onDeath(){
		 
	 }
	
	public void onHealthChange(int change){
		 
	 }
	
	public void onMaxHealthChange(int change){
		 
	 }
	 
	 public void onOverHeal(int amount){
		 
	 }
	
	public SettingsHolder saveValues() {
		SettingsHolder settings = new SettingsHolder();
		settings.put("character:id", mId); //must be handled by reconstructor since the object is created by this value
		if(mLoc != null) settings.put("character:location", mLoc);
		settings.put("character:status:status", mStatus.mStatus);
		if(mStatus.mTarget != null) settings.put("character:status:targetlocation", mStatus.mTarget.mLoc);
		if(mStatus.mExtra != null) settings.put("charater:status:extra", mStatus.mExtra);
		//Note that future statuses are not saved as they would create a chain of up to infinite length
		if(mTarget != null) settings.put("character:target", mTarget);
		if(mPlayer != null)settings.putAll(mPlayer.saveValues());
		return settings;
	}
	
	public void restoreValues(SettingsHolder settings){
		if(settings == null) return;
		
		if(settings.containsKey("character:location")) mLoc = settings.<Location>getType("character:location");
		if(settings.containsKey("character:status:status")) mStatus.mStatus = settings.<Integer>getType("character:status:status");
		if(settings.containsKey("character:status:extra")) mStatus.mExtra = settings.<Serializable>getType("character:status:extra");
		if(settings.containsKey("character:status:targetlocation") && mGrid != null){
			mStatus.mTarget = mGrid.get(settings.<Location>getType("character:status:targetlocation"));
		}
		if(settings.containsKey("character:target")) mTarget = settings.<Location>getType("character:target");
		if(mPlayer != null) mPlayer.restoreValues(settings);
	}
}
