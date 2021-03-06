package com.stu.graph;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.stu.database.ObjectTXTManager;

public class TestStuWayPointManager {

	private final String fileName = "waypoint_test.data";
	private StuWayPointManager stuwp ; 
	private String exp;
	private ObjectTXTManager txtManager ;
	private int[][] available = {
		   //0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15
			{0,1,0,0,0,0,0,0,0,0, 1, 0, 0, 0, 0, 0},//0
			{0,0,1,0,0,0,1,0,0,0, 0, 0, 0, 0, 0, 0},//1
			{0,0,0,1,0,0,0,0,0,0, 0, 0, 0, 0, 0, 0},//2
			{0,0,0,0,1,0,0,0,0,0, 0, 0, 0, 0, 0, 0},//3
			{0,0,0,0,0,1,0,0,0,0, 0, 0, 0, 0, 0, 0},//4
			{0,0,0,0,0,0,1,0,0,0, 0, 1, 0, 0, 0, 0},//5
			{0,0,0,0,0,0,0,0,0,1, 0, 0, 0, 0, 0, 0},//6
			{0,0,0,0,0,0,0,0,1,1, 0, 1, 1, 1, 0, 0},//7
			{0,0,0,0,0,0,0,0,0,0, 1, 0, 0, 0, 0, 0},//8
			{0,0,0,0,0,0,0,0,0,0, 1, 0, 0, 0, 0, 0},//9
			{0,0,0,0,0,0,0,0,0,0, 0, 0, 0, 0, 0, 0},//10
			{0,0,0,0,0,0,0,0,0,0, 0, 0, 0, 0, 0, 1},//11
			{0,0,0,0,0,0,0,0,0,0, 0, 0, 0, 1, 1, 0},//12
			{0,0,0,0,0,0,0,0,0,0, 0, 0, 0, 0, 1, 0},//13
			{0,0,0,0,0,0,0,0,0,0, 0, 0, 0, 0, 0, 1},//14
			{0,0,0,0,0,0,0,0,0,0, 0, 0, 0, 0, 0, 0},//15
	};
	
	@Before
	public void setUp() throws Exception {
		txtManager = new ObjectTXTManager(fileName);
		stuwp = new StuWayPointManager(available);
		WayPoint p1 = new WayPoint(1, 1, "hello");
		WayPoint p2 = new WayPoint(2, 2, "the");
		WayPoint p3 = new WayPoint(3, 3, "new");
		WayPoint p4 = new WayPoint(4, 4, "world");
		stuwp.addWayPoint(p1);
		stuwp.addWayPoint(p2);
		stuwp.addWayPoint(p3);
		stuwp.addWayPoint(p4);
		exp = stuwp.toString();
		
		for(int i = 0; i<available.length; i++)
		{
			for(int j = i; j<available.length; j++)
			{
				available[j][i] = available[i][j] ; 
			}
		}
//		for(int i = 0; i<avaliable.length; i++)
//		{
//			for(int j = 0; j<avaliable.length; j++)
//			{
//				System.out.print(available[i][j]+"  "); ; 
//			}
//			System.out.println();
//		}
	}

	@Test
	@Ignore
	public void testSaveStuWayPointManager() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtManager.writeObject(stuwp);
		StuWayPointManager readData = (StuWayPointManager)txtManager.readObject();
		System.out.println(exp);
		assertEquals(exp, readData.toString());
	}
	
	@Test
	@Ignore
	public void ��������() throws ClassNotFoundException, FileNotFoundException, IOException{
		txtManager = new ObjectTXTManager("waypointmodel.data");
		stuwp = (StuWayPointManager) txtManager.readObject();
		stuwp.calculateDis();
		System.out.println(stuwp.toString());
//		txtManager.writeObject(stuwp);
		
	}
	
	@Test
	@Ignore
	public void testFindShortestIndex() throws ClassNotFoundException, FileNotFoundException, IOException{
		txtManager = new ObjectTXTManager("waypointmodel.data");
		stuwp = (StuWayPointManager) txtManager.readObject();
		int start = stuwp.findShortestWayPoint(new Point(590,400));
		assertEquals(1, start);
	}

}
