package com.nokarateclass.rpgworld.characters;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.nokarateclass.rpgworld.R;
import com.nokarateclass.rpgworld.editor.CharacterFactory;

public class RockCharacter extends CharacterActor {
	public static final int mDefaultId = CharacterFactory.ROCK;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8773857871079596112L;

	public RockCharacter(Context context){
		super(context.getResources().getDrawable(R.drawable.rock), mDefaultId);
	}

	public RockCharacter(Drawable sprite){
		super(sprite, mDefaultId);
	}
}
