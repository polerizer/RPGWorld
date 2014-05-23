package com.nokarateclass.rpgworld.characters;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.nokarateclass.rpgworld.R;
import com.nokarateclass.rpgworld.editor.CharacterFactory;

public class TreeCharacter extends CharacterActor {
	public static final int mDefaultId = CharacterFactory.TREE;
	/**
	 * 
	 */
	private static final long serialVersionUID = 7424678736661979178L;

	public TreeCharacter(Context context){
		super(context.getResources().getDrawable(R.drawable.tree), mDefaultId);
	}
	
	public TreeCharacter(Drawable sprite){
		super(sprite, mDefaultId);
	}
}
