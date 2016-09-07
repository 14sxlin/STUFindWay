package com.stu.graph;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestWayPointManager {

	WayPointManager wpManager;
	ArrayList<Point> watchPoints;
	WayPoint startPoint;
	@Before
	public void setUp() throws Exception {
		watchPoints = new ArrayList<>();
//		watchPoints.add(new Point(233,356));
//		watchPoints.add(new Point(400,680));
//		watchPoints.add(new Point(600,200));
		
//		wpManager = new WayPointManager(
//				new Point(100, 100),	//1 
//				new Point(700,800), watchPoints);//8
		startPoint = new WayPoint(644, 415);
		wpManager = new WayPointManager();
		wpManager.readRouteModel("waypointmodel.data");
	}

	@Test
	@Ignore
	public void testWithWacthPoint() {
		WayPointManager.PathResult rslt = wpManager.calcculateRouteWith(startPoint,watchPoints);
		ArrayList<Point> exp = new ArrayList<>(watchPoints);
		exp.add(0,new Point(100,100));
		exp.add(0,new Point(700,800));
		assertEquals(exp.size(), rslt.places.size());
		System.out.println(rslt.places);
		for(ArrayList<Integer>path : rslt.paths)
			System.out.println(path);
	}
	
	/*
	 * P_图书馆( 857 , 800 )
		[30, 26]
		P_校医院( 473 , 549 )
	 */
	@Test
	public void testP2P() throws ClassNotFoundException, FileNotFoundException, IOException {
		ArrayList<Integer> rArrayList = wpManager.calculateRouteP2P(new Point(857, 800), new Point(473, 549));
		Integer rlst[] = (Integer[]) rArrayList.toArray(new Integer[rArrayList.size()]);
		assertArrayEquals(new Integer[]{30, 29, 8, 10, 26},rlst);
	}

}
