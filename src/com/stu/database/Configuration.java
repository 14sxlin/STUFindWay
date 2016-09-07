package com.stu.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Configuration{
	
	public static final String BACKUPPLACE = "/backup/placemodel.data";
	public static final String BACKWAYPOINT = "/backup/waypointmodel.data";
	
	public static final String STUJPGPATH = "/pic/stu.jpg";
	public static final String STUGRAYIMAGEPATH = "/pic/stu_grey.png";
	public static final String STUPNGPATH = "/pic/stu.png";
	public static final String PLACEMODELPATH = "placemodel.data";
	public static final String WAYPONINTMODELPATH = "waypointmodel.data";
	
	public static void check(InputStream place,InputStream waypoint) throws IOException{
		File f1 = new File(PLACEMODELPATH);
		
		if(!f1.exists())
		{
			f1.createNewFile();
			FileOutputStream outputStream = new FileOutputStream(f1);
			byte[] b = new byte[place.available()];
			outputStream.write(b);
			outputStream.flush();
			outputStream.close();
		}else{
			System.out.println("load placemodel @ "+f1.getAbsolutePath());
		}
		
		f1 = new File(WAYPONINTMODELPATH);
		if(!f1.exists())
		{
			f1.createNewFile();
			FileOutputStream outputStream = new FileOutputStream(f1);
			byte[] b = new byte[waypoint.available()];
			waypoint.read(b);
			outputStream.write(b);
			outputStream.flush();
			outputStream.close();
		}else{
			System.out.println("load waypointmodel @ "+f1.getAbsolutePath());
		}
	}
	
}
