/**
 * Jun 13, 2013
 * Player.java
 * Daniel Pok
 * AP Java 6th
 */
package com.nokarateclass.rpgworld.characters;

import android.util.Log;

import com.nokarateclass.rpgworld.io.SettingsHolder;

/**
 * @author poler_000
 *
 */
public class Player {

	//variables
	private int mHealth;
	private int mMaxHealth;
	private CharacterActor mCharacter;
	
	/*CharacterActor will support the following callback methods:
	 * onDeath()					called when the hp drops =< 0
	 * onHealthChange(int change)	called when the health changes, includes the amount it changed by
	 * onMaxHealthChange(int change)ditto for MaxHealth
	 * onOverHeal(int amount)		called when hp exceeds maxHP, amount is how much it exceeded max by
	 */
	
	public Player() {
		
	}
	
	public Player(CharacterActor character){
		mCharacter = character;
	}
	
	public int getHealth(){
		return mHealth;
	}
	
	public synchronized void setHealth(int health){
		int change = health - mHealth;
		int overHeal = health - mMaxHealth;
		mHealth = health;
		Log.d("Player", "SET HEALTH(" + health + "): " + mCharacter.toString() + String.format(" HP: %d/%d, change: %d, overheal: %d", mHealth, mMaxHealth, change, overHeal));
		if(change != 0 && mCharacter != null){
			Log.d("Player", "ON HEALTH CHANGE: " + mCharacter.toString() + String.format(" HP: %d/%d, change: %d, overheal: %d", mHealth, mMaxHealth, change, overHeal));
			mCharacter.onHealthChange(change);
		}
		if(overHeal > 0 && mCharacter != null){
			Log.d("Player", "ON OVERHEAL: " + mCharacter.toString() + String.format(" HP: %d/%d, change: %d, overheal: %d", mHealth, mMaxHealth, change, overHeal));
			mCharacter.onOverHeal(overHeal);
		}
		if(mHealth <= 0 && mCharacter != null){
			Log.d("Player", "ON DEATH: " + mCharacter.toString() + String.format(" HP: %d/%d, change: %d, overheal: %d", mHealth, mMaxHealth, change, overHeal));
			mCharacter.onDeath();
			Log.d("Player", "ON DEATH COMPLETE: " + mCharacter.toString() + String.format(" HP: %d/%d, change: %d, overheal: %d", mHealth, mMaxHealth, change, overHeal));
		}
		
	}
	
	public void addHealth(int add){
		Log.d("Player", "ADDING HEALTH(" + add + "): " + mCharacter.toString() + String.format(" HP: %d/%d", mHealth, mMaxHealth));
		setHealth(mHealth + add);
	}
	
	public int getMaxHealth(){
		return mMaxHealth;
	}
	
	public synchronized void setMaxHealth(int health){
		int change = health - mMaxHealth;
		mMaxHealth = health;
		if(health != 0 && mCharacter != null){
			Log.d("Player", "ON MAX HEALTH CHANGE: " + mCharacter.toString() + String.format(" HP: %d/%d, max health change: %d", mHealth, mMaxHealth, change));
			mCharacter.onMaxHealthChange(change);
		}

	}
	
	public void addMaxHealth(int add){
		setMaxHealth(mMaxHealth + add);
	}
	
	public SettingsHolder saveValues(){
		SettingsHolder settings = new SettingsHolder();
		
		settings.put("player:health", mHealth);
		settings.put("player:maxhealth", mMaxHealth);
		
		return settings;
	}
	
	public void restoreValues(SettingsHolder settings){
		if(settings.containsKey("player:health")) mHealth = settings.<Integer>getType("player:health");
		if(settings.containsKey("player:maxhealth")) mMaxHealth = settings.<Integer>getType("player:maxhealth");
	}

}
