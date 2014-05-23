/**
 * Jun 6, 2013
 * BackgroundCharacter.java
 * Daniel Pok
 * AP Java 6th
 */
package com.nokarateclass.rpgworld.backgrounds;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.nokarateclass.rpgworld.characters.CharacterActor;
import com.nokarateclass.rpgworld.editor.CharacterFactory;

/**
 * @author poler_000
 *
 */
public class BackgroundCharacter extends CharacterActor {
	public static final int mDefaultId = CharacterFactory.DEFAULT_BACKGROUND;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1372588999344089793L;
	protected Context mContext;
	/**
	 * 
	 */
	public BackgroundCharacter(Context context) {
		super(mDefaultId);
		mContext = context;
	}

	public BackgroundCharacter(){
		super(mDefaultId);
	}
	
	public BackgroundCharacter(int id){
		super(id);
	}
	
	/**
	 * @param sprite
	 */
	public BackgroundCharacter(Drawable sprite) {
		super(sprite, mDefaultId);
	}
	
	public BackgroundCharacter(Drawable sprite, int id){
		super(sprite, id);
	}
	
	public void interact(CharacterActor character){
		//do nothing
	}
}
