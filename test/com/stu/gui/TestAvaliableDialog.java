package com.stu.gui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.stu.database.ObjectTXTManager;
import com.stu.graph.StuWayPointManager;
import com.stu.graph.WayPoint;
import com.stu.gui.AvaliableDialog;

public class TestAvaliableDialog {

	private AvaliableDialog dialog;
	private StuWayPointManager stuwp ; 
	private ObjectTXTManager txtManager ;
	private int [][] ava ;
	public TestAvaliableDialog() {
		txtManager = new ObjectTXTManager("waypointmodel.data");
		stuwp = (StuWayPointManager) txtManager.readObject();
		stuwp.getWayPointList().add(new WayPoint(90, 90, "newPoint(16)"));
		dialog = new AvaliableDialog(16, stuwp.getWayPointList(),stuwp.getAvaliable());
		dialog.setVisible(true);
		while(!dialog.isFinish())
		{
			System.out.println("waiting");
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
	public static void main(String[] args) {
		new TestAvaliableDialog();
	}

}
