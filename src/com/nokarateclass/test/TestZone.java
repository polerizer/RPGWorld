package com.nokarateclass.test;

import com.nokarateclass.rpgworld.backgrounds.GrassBackground;
import com.nokarateclass.rpgworld.backgrounds.SandBackground;
import com.nokarateclass.rpgworld.characters.CactusCharacter;
import com.nokarateclass.rpgworld.characters.HeroCharacter;
import com.nokarateclass.rpgworld.characters.MonsterCharacter;
import com.nokarateclass.rpgworld.characters.RockCharacter;
import com.nokarateclass.rpgworld.characters.TreeCharacter;
import com.nokarateclass.rpgworld.grid.BackgroundGrid;
import com.nokarateclass.rpgworld.grid.CharacterGrid;
import com.nokarateclass.rpgworld.grid.MainCharacterGrid;
//An example zone. Not for final use. Think of this as a prototype.
public class TestZone {	
	//Creates a zone.
	public static void populateZone(MainCharacterGrid grid){
		HeroCharacter hero = new HeroCharacter(grid.getContext());
		TreeCharacter tree = new TreeCharacter(grid.getContext());
		RockCharacter rock = new RockCharacter(grid.getContext());
		MonsterCharacter monster = new MonsterCharacter(grid.getContext());
		grid.setMainCharacter(hero);
		grid.addCharacter(hero, 5, 5);
		grid.addCharacter(tree, 6, 0);
		grid.addCharacter(rock, 7, 3);
		grid.addCharacter(monster, 11, 11);
		grid.addCharacter(new CactusCharacter(grid.getContext()), 0, 7);
	}
	
	public static void setBackground(BackgroundGrid grid){
		for(int i = 0; i < grid.getRows(); i++){
			for(int j = 0; j < grid.getCols(); j++){
				grid.addCharacter(new GrassBackground(grid.getContext()), i, j);
			}
		}
		
		grid.replaceCharacter(new SandBackground(grid.getContext()), 3, 3);
		grid.replaceCharacter(new SandBackground(grid.getContext()), 3, 2);
		grid.replaceCharacter(new SandBackground(grid.getContext()), 3, 4);
		grid.replaceCharacter(new SandBackground(grid.getContext()), 4, 2);
		grid.replaceCharacter(new SandBackground(grid.getContext()), 5, 2);
		grid.replaceCharacter(new SandBackground(grid.getContext()), 6, 2);
		grid.replaceCharacter(new SandBackground(grid.getContext()), 7, 2);
	}
}
