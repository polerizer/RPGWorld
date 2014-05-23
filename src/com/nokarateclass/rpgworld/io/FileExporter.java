/**FileExporter.java
 * Daniel Pok
 * AP Java 6th
 * May 24, 2013
 */
package com.nokarateclass.rpgworld.io;

import java.io.Externalizable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author dh.dpok
 *
 */
public class FileExporter {
	//variables
	private String fileName;
	private FileOutputStream fileOut;
	private ObjectOutputStream oOut;
	boolean ready = false;
	
	//Constructor
	public FileExporter(String file){
		fileName = file;
		try {
			fileOut = new FileOutputStream(file);
			oOut = new ObjectOutputStream(fileOut);
			ready = true;
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
			oOut.close();
		} catch(IOException ex){}
		finally{
			oOut = null;
			fileOut = null;
			fileName = "";
		}
	}
	
	//Opens a file, returns ready state
	public boolean open(String file){
		close();
		fileName = file;
		try {
			fileOut = new FileOutputStream(file);
			oOut = new ObjectOutputStream(fileOut);
			ready = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Could not open file \'" + file + "\'.");
			ready = false;
		}
		return isReady();
	}
	
	public boolean writeObject(Object obj){
		if(!(obj instanceof Serializable || obj instanceof Externalizable)){
			System.err.println("The object cannot be written to the stream because it is not serializable or externalizable.");
		}
		try {
			//System.out.println("Stream is " + (isReady()? "ready" : "not ready"));
			oOut.writeObject(obj);
			oOut.flush();
			return true;
		} catch (IOException e) {
			System.err.println("Could not write file \'" + fileName + "\'.");
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean writeObjectToFile(String file, Object obj){
		FileExporter out = new FileExporter(file);
		if(out.isReady()){
			Boolean result = out.writeObject(obj);
			out.close();
			return result;
		} else {
			return false;
		}
	}
}
