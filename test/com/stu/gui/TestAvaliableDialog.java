package com.stu.gui;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.stu.database.ObjectTXTManager;
import com.stu.graph.StuWayPointManager;
import com.stu.graph.WayPoint;

public class TestAvaliableDialog {

	private AvaliableDialog dialog;
	private StuWayPointManager stuwp ; 
	private ObjectTXTManager txtManager ;
	private int [][] ava ;
	public TestAvaliableDialog() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtManager = new ObjectTXTManager("waypointmodel.data");
		stuwp = (StuWayPointManager) txtManager.readObject();
		stuwp.getWayPointList().add(new WayPoint(90, 90, "newPoint(16)"));
		dialog = new AvaliableDialog(16, stuwp.getWayPointList(),stuwp.getAvaliable());
		dialog.setVisible(true);
		while(!dialog.isFinish())
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		ava = dialog.getAval();
		System.out.println("Finish");
		for(int i = 0; i<ava.length; i++)
		{
			for(int j = 0; j<ava.length; j++)
			{
				System.out.print(""+ava[i][j]+"  ");
			}
			System.out.println();
		}
	}
	
	public TestAvaliableDialog(int nothing) throws ClassNotFoundException, FileNotFoundException, IOException{
		txtManager = new ObjectTXTManager("waypointmodel.data");
		stuwp = (StuWayPointManager) txtManager.readObject();
		dialog = new AvaliableDialog(stuwp.getAvaliable());
		while(!dialog.isFinish())
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		ava = AvaliableDialog.aval;
		System.out.println("Finish");
		for(int i = 0; i<ava.length; i++)
		{
			for(int j = 0; j<ava.length; j++)
			{
				System.out.print(""+ava[i][j]+"  ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException, IOException {
//		new TestAvaliableDialog();
		new TestAvaliableDialog(1);
		
	}

}
