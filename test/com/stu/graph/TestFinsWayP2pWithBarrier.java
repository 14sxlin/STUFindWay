package com.stu.graph;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
@Deprecated
public class TestFinsWayP2pWithBarrier {

	private FindWayP2PWithBarrier findway1;
	private Point s1 = new Point(0,1);
	private Point e1 = new Point(6, 5);
	
	private char [][] map1 = {
			{'*','0','0','0','*','*'},
			{'*','*','*','0','0','0'},
			{'0','0','0','0','0','0'},
			{'*','0','*','*','*','*'},
			{'0','0','*','*','*','*'},
			{'*','0','0','0','0','0'},
			{'*','*','*','*','*','0'}
	};
	
	@Before
	public void setup(){
		findway1 = new FindWayP2PWithBarrier(s1, e1, map1);
	}
	
	@Test
	public void test() {
		findway1.findWay2Point();
		System.out.println(findway1.toString());
		assertEquals(14, findway1.getShortestDis()[e1.x][e1.y]);
	}

}
