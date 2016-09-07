package com.stu.database;

import java.io.File;
import java.io.IOException;

public class Configuration{
	
	//Ä¬ÈÏÖµ
	public static final String STUJPGPATH = "/pic/stu.jpg";
	public static final String STUGRAYIMAGEPATH = "/pic/stu_grey.png";
	public static final String STUPNGPATH = "/pic/stu.png";
	public static final String PLACEMODELPATH = "placemodel.data";
	public static final String WAYPONINTMODELPATH = "waypointmodel.data";
	
	public static void check() throws IOException{
		File f1 = new File(PLACEMODELPATH);
		if(!f1.exists())
			f1.createNewFile();
		f1 = new File(WAYPONINTMODELPATH);
		if(!f1.exists())
			f1.createNewFile();
	}
	
}
