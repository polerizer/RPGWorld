/**
 * Jun 10, 2013
 * MonsterCharacter.java
 * Daniel Pok
 * AP Java 6th
 */
package com.nokarateclass.rpgworld.characters;

import java.util.*;

import com.nokarateclass.rpgworld.R;
import com.nokarateclass.rpgworld.editor.CharacterFactory;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * @author poler_000
 *
 */
public class MonsterCharacter extends CharacterActor {

	public static final int mDefaultId = CharacterFactory.MONSTER;


	/**
	 * 
	 */
	public MonsterCharacter(Context context) {
		super(context.getResources().getDrawable(R.drawable.monster), mDefaultId);
				mPlayer.setMaxHealth(50);
				mPlayer.setHealth(50);
	}

	/**
	 * @param sprite
	 */
	public MonsterCharacter(Drawable sprite) {
		super(sprite, mDefaultId);
		mPlayer.setMaxHealth(50);
		mPlayer.setHealth(50);
	}
	public void interact(CharacterActor character){
		if(isAdjacent(character)){
			mPlayer.addHealth(-10);
		}
	}
	@Override
	public void act(int beat){
		//Log.i("Monster", "Getting characters in range(5)");
		ArrayList<CharacterActor> inRange = fourWayLineOfSight(5);
		//Log.i("Monster", inRange.toString());
		for(int i = 0; i < inRange.size(); i++){
			if(inRange.get(i).mId == CharacterFactory.HERO){
				Log.i("Monster", "Found Hero: " + inRange.get(i).toString());
				setTarget(inRange.get(i).getLocation());
				stepTowardTarget();
			}
		}

		if(beat == 0){
			ArrayList<CharacterActor> adjacent = getAdjacentCharacters();
			//Log.i("Monster", adjacent.toString());
			for(int i = 0; i < adjacent.size(); i++){
				if(adjacent.get(i).mId == CharacterFactory.HERO){
					adjacent.get(i).mPlayer.addHealth(-10);
				} if(adjacent.get(i).mId == CharacterFactory.MONSTER){
					adjacent.get(i).mPlayer.addHealth(-5);
				}
			}	
		}	
	}
	
	@Override
	public void onDeath(){
		Log.d("Player", toString() + " died" + String.format(" HP: %d/%d", mPlayer.getHealth(), mPlayer.getMaxHealth()));
		for(CharacterActor i: getAdjacentCharacters()){
			i.mPlayer.addHealth(20);
		}
		Log.d("Player", "Remove status: " + toString() + " " + removeSelfFromGrid());
	}
	
}


