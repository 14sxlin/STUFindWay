package com.stu.graph;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.stu.database.ObjectTXTManager;

public class TestStuWayPointManager {

	private final String fileName = "waypoint_test.data";
	private StuWayPointManager stuwp ; 
	private String exp;
	private ObjectTXTManager txtManager ;
	
	@Before
	public void setUp() throws Exception {
		txtManager = new ObjectTXTManager(fileName);
		stuwp = new StuWayPointManager();
		WayPoint p1 = new WayPoint(1, 1, "hello");
		WayPoint p2 = new WayPoint(2, 2, "the");
		WayPoint p3 = new WayPoint(3, 3, "new");
		WayPoint p4 = new WayPoint(4, 4, "world");
		stuwp.addWayPoint(p1);
		stuwp.addWayPoint(p2);
		stuwp.addWayPoint(p3);
		stuwp.addWayPoint(p4);
		exp = stuwp.toString();
	}

	@Test
	public void testSaveStuWayPointManager() {
		txtManager.writeObject(stuwp);
		StuWayPointManager readData = (StuWayPointManager)txtManager.readObject();
		System.out.println(exp);
		assertEquals(exp, readData.toString());
	}

}
