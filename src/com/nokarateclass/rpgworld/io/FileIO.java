/**FileIO.java
 * Daniel Pok
 * AP Java 6th
 * May 22, 2013
 */
package com.nokarateclass.rpgworld.io;

/**
 * @author dh.dpok
 *
 */
public class FileIO {

	//variables
	String fileName;
	
	public FileIO(String file){
		fileName = file;
	}
	
	public boolean save(Object obj){
		return FileExporter.writeObjectToFile(fileName, obj);
	}
	
	public Object read(){
		return FileImporter.readObjectFromFile(fileName);
	}

	@SuppressWarnings("unchecked")
	public <T> T readType(){
		Object obj = FileImporter.readObjectFromFile(fileName);
		try{
			T result = (T) obj;//this is where the unchecked warning is suppressed
			return result;
		} catch(ClassCastException ex){
			return null;
		}
	}
}
