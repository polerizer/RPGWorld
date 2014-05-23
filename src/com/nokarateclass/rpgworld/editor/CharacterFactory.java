/**
 * Jun 9, 2013
 * CharacterFactory.java
 * Daniel Pok
 * AP Java 6th
 */
package com.nokarateclass.rpgworld.editor;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.nokarateclass.rpgworld.R;
import com.nokarateclass.rpgworld.backgrounds.*;
import com.nokarateclass.rpgworld.characters.*;

/**
 * @author poler_000
 *
 */
public class CharacterFactory {

	//constants
	public final static int DEFAULT_CHARACTER = 10000, HERO = 10001, MONSTER = 10002, ANDROID = 10003, ROCK = 10004, CACTUS = 10005, TREE = 10006;
	public final static int DEFAULT_BACKGROUND = 20000, SAND = 20001, GRASS = 20002;
	
	//variables
	private Context mContext;
	
	/**
	 * 
	 */
	public CharacterFactory(Context context) {
		mContext = context;
	}
	
	public Drawable getDrawable(int id){
		return mContext.getResources().getDrawable(id);
	}
	
	public CharacterActor makeCharacter(int id){
		switch(id){
		case DEFAULT_CHARACTER:{
			return new CharacterActor();
		}
		case HERO:{
			return new HeroCharacter(getDrawable(R.drawable.hero));
		}
		case MONSTER:{
			return new MonsterCharacter(getDrawable(R.drawable.monster));
		}
		case ANDROID:{
			return new AndroidCharacter(getDrawable(R.drawable.ic_launcher));
		}
		case ROCK:{
			return new RockCharacter(getDrawable(R.drawable.rock));
		}
		case CACTUS: {
			return new CactusCharacter(getDrawable(R.drawable.cactus));
		}
		case TREE: {
			return new TreeCharacter(getDrawable(R.drawable.tree));
		}
		case DEFAULT_BACKGROUND:{
			return new BackgroundCharacter();
		}
		case SAND:{
			return new SandBackground(getDrawable(R.drawable.sand));
		}
		case GRASS:{
			return new GrassBackground(getDrawable(R.drawable.grass));
		}
		default:{
			return null;
		}
		}
	}
	
	public BackgroundCharacter makeBackground(int id){
		switch(id){
		case DEFAULT_BACKGROUND:{
			return new BackgroundCharacter();
		}
		case SAND:{
			return new SandBackground(getDrawable(R.drawable.sand));
		}
		case GRASS:{
			return new GrassBackground(getDrawable(R.drawable.grass));
		}
		default:{
			return null;
		}
		}
	}
}