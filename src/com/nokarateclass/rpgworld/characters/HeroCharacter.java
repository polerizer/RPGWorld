/**
 * Jun 5, 2013
 * HeroCharacter.java
 * Daniel Pok
 * AP Java 6th
 */
package com.nokarateclass.rpgworld.characters;

import com.nokarateclass.rpgworld.R;
import com.nokarateclass.rpgworld.editor.CharacterFactory;
import com.nokarateclass.rpgworld.grid.MainCharacterGrid;
import com.nokarateclass.rpgworld.io.SettingsHolder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

/**
 * @author dh.dpok
 *
 */
public class HeroCharacter extends CharacterActor {
	public static final int mDefaultId = CharacterFactory.HERO;

	/**
	 * 
	 */
	public HeroCharacter(Context context) {
		super(context.getResources().getDrawable(R.drawable.hero), mDefaultId);
		mPlayer.setMaxHealth(100);
		mPlayer.setHealth(100);
	}

	/**
	 * @param sprite
	 */
	public HeroCharacter(Drawable sprite) {
		super(sprite, mDefaultId);
		mPlayer.setMaxHealth(100);
		mPlayer.setHealth(100);
	}
	
	@Override
	public void act(int beat){
		Log.i("Status",Integer.valueOf(mStatus.mStatus).toString());
		if(mStatus.mStatus == Status.PATHING){
			stepTowardTarget();
		}
		//if(beat == 0) mPlayer.addHealth(5);
	}
	
	@Override
	public void interact(CharacterActor character){
		removeSelfFromGrid();
	}
	
	@Override
	public void restoreValues(SettingsHolder settings){
		super.restoreValues(settings);
		if(getGrid() instanceof MainCharacterGrid){
			((MainCharacterGrid) getGrid()).setMainCharacter(this, true);
		}
	}
	
	@Override
	public void onHealthChange(int change){
		if(getGrid() != null) Toast.makeText(getGrid().getContext(), String.format("HP %d/%d (%d)", mPlayer.getHealth(), mPlayer.getMaxHealth(), change), Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onDeath(){
		if(getGrid() != null) Toast.makeText(getGrid().getContext(), "GAME OVER", Toast.LENGTH_LONG).show();
		Log.d("Player", toString() + " died" + String.format(" HP: %d/%d", mPlayer.getHealth(), mPlayer.getMaxHealth()));
		removeSelfFromGrid();
	}
	
	@Override
	public void onOverHeal(int amount){
		mPlayer.addHealth(-amount);
	}

}
