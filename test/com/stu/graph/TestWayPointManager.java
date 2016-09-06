package com.stu.graph;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TestWayPointManager {

	WayPointManager wpManager;
	ArrayList<Point> watchPoints;
	@Before
	public void setUp() throws Exception {
		watchPoints = new ArrayList<>();
		watchPoints.add(new Point(233,356));
		watchPoints.add(new Point(400,680));
		watchPoints.add(new Point(600,200));
		
		wpManager = new WayPointManager(
				new Point(100, 100),	//1 
				new Point(700,800), watchPoints);//8
		wpManager.readRouteModel("waypointmodel.data");
	}

	@Test
	public void test() {
		WayPointManager.PathResult rslt = wpManager.calcculateRouteWith(watchPoints);
		ArrayList<Point> exp = new ArrayList<>(watchPoints);
		exp.add(0,new Point(100,100));
		exp.add(0,new Point(700,800));
		assertEquals(exp.size(), rslt.places.size());
		System.out.println(rslt.places);
		for(ArrayList<Integer>path : rslt.paths)
			System.out.println(path);
	}

}
