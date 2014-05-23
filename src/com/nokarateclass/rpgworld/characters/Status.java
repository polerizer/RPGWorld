/**
 * Jun 6, 2013
 * Status.java
 * Daniel Pok
 * AP Java 6th
 */
package com.nokarateclass.rpgworld.characters;

import java.io.Serializable;


/**
 * @author poler_000
 *
 */
public class Status{

	/**
	 * 
	 */
	public final static int NO_ACTION = 0, PATHING = 1, ATTACKING = 2, TALKING = 3, INTERACTING = 4;
	public int mStatus; //what the character is doing now
	public Serializable mExtra; //anything else relevant to this, i.e. Locations, etc.
	public CharacterActor mTarget; //target of the character's current action
	public Status mFuture; //what to do when the current action is complete, TODO: THIS DOES NOT GET SAVED OR RESTORED IN ZONES
	
	public Status() {
		mStatus = 0;
		mTarget = null;
		mFuture = null;
	}
	
	public Status(int status){
		mStatus = status;
		mTarget = null;
		mFuture = null;
	}
	
	public Status(int status, CharacterActor target){
		mStatus = status;
		mTarget = target;
		mFuture = null;
	}
	
	public Status(int status, CharacterActor target, Status future){
		mStatus = status;
		mTarget = target;
		mFuture = future;
	}
	
	public Status(int status, CharacterActor target, int futureStatus, CharacterActor futureTarget){
		mStatus = status;
		mTarget = target;
		mFuture = new Status(futureStatus, futureTarget);
	}
	
	//TODO add functionality?
}
