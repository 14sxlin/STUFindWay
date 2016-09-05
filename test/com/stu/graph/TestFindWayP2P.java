package com.stu.graph;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.stu.database.ObjectTXTManager;

public class TestFindWayP2P {
	private final int INF = FindWayP2P.INF;
	private FindWayP2P p2p ;
	@Before
	public void setUp() throws Exception {
		int[][] map = {
				{ INF,   100,  30, INF,  10},
				{ INF,   INF, INF, INF, INF},
				{ INF,    60, INF,  60, INF},
				{ INF,    10, INF, INF, INF},
				{ INF,   INF, INF,  50, INF}
		};
		p2p = new FindWayP2P(map, 0);
	}

	
	
	@Test
	public void testFindWay1() {
		p2p.findWay();
//		System.out.println(p2p.toString());
		ArrayList<Integer> list = p2p.getPathList(3);
		Integer[] tempList = new Integer[list.size()];
		tempList = (Integer[]) list.toArray(new Integer[list.size()]);
//		for(int i: list)
//		{
//			System.out.print(i+"  ");
//		}
		assertArrayEquals(new Integer[]{0,4,3},tempList );
	}
	
	@Test
	public void testFindWay2() {
		ObjectTXTManager manager = new ObjectTXTManager("waypointmodel.data");
		StuWayPointManager wpManager = (StuWayPointManager) manager.readObject();
//		System.out.println(wpManager.toString());
		p2p = new FindWayP2P(wpManager.getDis(), 0);
		p2p.findWay();
//		System.out.println(p2p.getPathList(13));
//		System.out.println(p2p.getPathList(15));
	}
	
	@Test
	public void testBug(){
		ObjectTXTManager manager = new ObjectTXTManager("waypointmodel.data");
		StuWayPointManager wpManager = (StuWayPointManager) manager.readObject();
//		System.out.println(wpManager.toString());
		p2p = new FindWayP2P(wpManager.getDis(), 1);
		p2p.findWay();
//		System.out.println(wpManager.getWayPointList().toString());
//		System.out.println(p2p.getPathList(2));
	}
	
	@Test
	public void testBug0_16(){
		ObjectTXTManager manager = new ObjectTXTManager("waypointmodel.data");
		StuWayPointManager wpManager = (StuWayPointManager) manager.readObject();
//		System.out.println(wpManager.toString());
		wpManager.calculateDis();
		p2p = new FindWayP2P(wpManager.getDis(), 0);
		p2p.findWay();
//		System.out.println(wpManager.getWayPointList().toString());
//		System.out.println(p2p.getPathList(16));
	}

	
	
}
