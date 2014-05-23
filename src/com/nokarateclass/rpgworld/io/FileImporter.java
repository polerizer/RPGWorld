/**FileImporter.java
 * Daniel Pok
 * AP Java 6th
 * May 24, 2013
 */
package com.nokarateclass.rpgworld.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @author dh.dpok
 *
 */
public class FileImporter {
	//variables
	private String fileName;
	private FileInputStream fileIn;
	private ObjectInputStream oIn;
	boolean ready = false;
	
	//Constructor
	public FileImporter(String file){
		fileName = file;
		try {
			fileIn = new FileInputStream(file);
			oIn = new ObjectInputStream(fileIn);
			ready = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Could not find file \'" + file + "\'.");
			ready = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Could not open file \'" + file + "\'.");
			ready = false;
		}
	}
	
	//getFileName
	public String getFileName(){
		return fileName;
	}
	
	//isReady
	public boolean isReady(){
		return ready;
	}
	
	//Closes the streams associated with this object
	public void close(){
		ready = false;
		try{
			oIn.close();
		} catch(IOException ex){}
		finally{
			oIn = null;
			fileIn = null;
			fileName = "";
		}
	}
	
	//Opens a file, returns ready state
	public boolean open(String file){
		close();
		fileName = file;
		try {
			fileIn = new FileInputStream(file);
			oIn = new ObjectInputStream(fileIn);
			ready = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Could not find file \'" + file + "\'.");
			ready = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Could not open file \'" + file + "\'.");
			ready = false;
		}
		return isReady();
	}
	
	public Object readObject(){
		try {
			return oIn.readObject();
		} catch (IOException e) {
			System.err.println("Could not read file \'" + fileName + "\'.");
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			System.err.println("Could not read object from \'" + fileName + "\'.");
			return null;
		}
	}
	
	public static Object readObjectFromFile(String file){
		FileImporter in = new FileImporter(file);
		if(in.isReady()){
			Object obj = in.readObject();
			in.close();
			return obj;
		} else {
			return null;
		}
	}
		
}
