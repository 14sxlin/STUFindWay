package com.stu.graph;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestBarrierGenrator {
	private BarrierGenerator bg = new BarrierGenerator(3, 3);
	private char exp[][] =
		{
				{'0','*','0'},
				{'0','0','0'},
				{'*','0','*'}
		};	
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testdropBarrier() {
		bg.dropBarrier(0,1);
		bg.dropBarrier(2,0);
		bg.dropBarrier(2,2);
		System.out.println(bg.toString());
		assertArrayEquals(exp, bg.getMap());
	}
	
	@Test
	public void testScale(){
		Point p = new Point(10,20);
		Point p1 = p.scaled(10);
		Point p2 = p.scaled(-10);
		assertEquals(p1.x, 100);
		assertEquals(p1.y, 200);
		assertEquals(p2.x, 1);
		assertEquals(p2.y, 2);
	}

}
