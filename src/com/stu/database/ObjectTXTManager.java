package com.stu.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectTXTManager {
	private String filename = "";
	
	public ObjectTXTManager(String filename) {
		this.filename = filename;
	}
	
	public void writeObject(Serializable obj){
		File file = new File(filename);
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(obj);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("resource")
	public Object readObject() throws ClassNotFoundException, FileNotFoundException, IOException{
		File file = new File(filename);
		if(!file.exists())
			return null;
		return new ObjectInputStream(new FileInputStream(file)).readObject();
	}
}
