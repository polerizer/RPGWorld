/**
 * Jun 7, 2013
 * CactusCharacter.java
 * Daniel Pok
 * AP Java 6th
 */
package com.nokarateclass.rpgworld.characters;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.nokarateclass.rpgworld.R;
import com.nokarateclass.rpgworld.editor.CharacterFactory;


/**
 * @author poler_000
 *
 */
public class CactusCharacter extends CharacterActor {
	public static final int mDefaultId = CharacterFactory.CACTUS;
	/**
	 * 
	 */
	private static final long serialVersionUID = 9140316178486566293L;

	/**
	 * @param context
	 */
	public CactusCharacter(Context context) {
		super(context.getResources().getDrawable(R.drawable.cactus), mDefaultId);
	}

	/**
	 * @param sprite
	 */
	public CactusCharacter(Drawable sprite) {
		super(sprite, mDefaultId);
		// TODO Auto-generated constructor stub
	}
}
