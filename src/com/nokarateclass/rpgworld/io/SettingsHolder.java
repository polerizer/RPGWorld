/**
 * Jun 11, 2013
 * SettingsHolder.java
 * Daniel Pok
 * AP Java 6th
 */
package com.nokarateclass.rpgworld.io;

import java.util.HashMap;
import java.util.Map;

/**
 * @author poler_000
 *
 */
public class SettingsHolder extends HashMap<String, Object>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8571068923732304861L;

	/**
	 * 
	 */
	public SettingsHolder() {
		super();
	}
	
	public SettingsHolder(int initialCapacity){
		super(initialCapacity);
	}

	public SettingsHolder(int initialCapacity, float loadFactor){
		super(initialCapacity, loadFactor);
	}
	
	public SettingsHolder(Map<? extends String, ? extends Object> m){
		super(m);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getType(String key){
		Object obj = get(key);
		if(obj == null) return null;
		try{
			return (T) obj;
		} catch(Exception ex){
			return null;
		}
	}
	
}
