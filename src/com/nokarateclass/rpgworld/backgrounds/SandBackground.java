/**
 * Jun 7, 2013
 * SandBackground.java
 * Daniel Pok
 * AP Java 6th
 */
package com.nokarateclass.rpgworld.backgrounds;

import com.nokarateclass.rpgworld.R;
import com.nokarateclass.rpgworld.editor.CharacterFactory;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * @author poler_000
 *
 */
public class SandBackground extends BackgroundCharacter {
	public static final int mDefaultId = CharacterFactory.SAND;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4209122443250542587L;

	/**
	 * @param context
	 */
	public SandBackground(Context context) {
		super(context.getResources().getDrawable(R.drawable.sand), mDefaultId);
	}

	/**
	 * @param sprite
	 */
	public SandBackground(Drawable sprite) {
		super(sprite, mDefaultId);
		// TODO Auto-generated constructor stub
	}

}
