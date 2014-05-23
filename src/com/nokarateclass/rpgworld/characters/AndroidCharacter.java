/**
 * Jun 4, 2013
 * AndroidCharacter.java
 * Daniel Pok
 * AP Java 6th
 */
package com.nokarateclass.rpgworld.characters;

import com.nokarateclass.rpgworld.R;
import com.nokarateclass.rpgworld.editor.CharacterFactory;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * @author poler_000
 *
 */

public class AndroidCharacter extends CharacterActor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6608174748788795234L;
	public static final int mDefaultId = CharacterFactory.ANDROID;
	/**
	 * 
	 */
	public AndroidCharacter(Context context) {
		super(context.getResources().getDrawable(R.drawable.ic_launcher), mDefaultId);
	}
	
	public AndroidCharacter(Drawable sprite){
		super(sprite,mDefaultId);
	}
	
	@Override
	public void interact(CharacterActor character){
		removeSelfFromGrid();
	}

}
