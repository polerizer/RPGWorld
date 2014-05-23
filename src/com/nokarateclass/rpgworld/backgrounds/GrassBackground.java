/**
 * Jun 7, 2013
 * GrassBackground.java
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
public class GrassBackground extends BackgroundCharacter {
	public static final int mDefaultId = CharacterFactory.GRASS;
	/**
	 * 
	 */
	private static final long serialVersionUID = 2651993642487599368L;
	protected Context mContext;
	
	/**
	 * 
	 */
	public GrassBackground(Context context) {
		super(context.getResources().getDrawable(R.drawable.grass), mDefaultId);
	}

	/**
	 * @param sprite
	 */
	public GrassBackground(Drawable sprite) {
		super(sprite, mDefaultId);
		// TODO Auto-generated constructor stub
	}

}
