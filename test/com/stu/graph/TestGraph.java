package com.stu.graph;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestGraph {
	private FindWayPoints mygraph ;
	private int [][][]g=

		{
				{
					{0, 30,  6,  4},
					{30, 0,  5,  4},
					{6,  5,  0, 20},
					{4,  4,  20, 0}
				},
				{
					{0,5,1,1},
					{5,0,2,3},
					{1,2,0,9},
					{1,3,9,0}
				},
				{
					{0,5,1,8},
					{5,0,2,3},
					{1,2,0,9},
					{8,3,9,0}
				}
		};
	
	private int[][] exp =
		{
				{0,2,1,3},
				{0,2,3,1},
				{0,2,3,1}
		};
			
	@Before
	public void setup(){
		
	}
	
	@Test
	@Ignore
	public void testLegthOf() {
		Point p1 = new Point(0 , 0);
		Point p2 = new Point(0 , 0);
		assertEquals(0, Point.lengthOf(p1, p2));
		
		p1 = new Point(0 , 1);
		p2 = new Point(0 , 2);
		assertEquals(1, Point.lengthOf(p1, p2));
		
		p1 = new Point(0 , 3);
		p2 = new Point(1 , 6);
		assertEquals(3, Point.lengthOf(p1, p2));
	}
	
	@Test
	@Ignore
	public void testConstruct(){
		System.out.println(mygraph.toString());
		for(int i = 0; i<mygraph.getN()  ; i++)
			assertEquals(mygraph.getG()[i][i], 0);
	}
	
	@Test
	public void testFindWay(){
		int[][] rlst = new int[g.length][5];
		for(int i = 0;i < g.length;i++)
		{
			mygraph = new FindWayPoints(g[i]);
			mygraph.findWay(1);
			rlst[i] = mygraph.getShortestOrder();
		}
		for(int i = 0; i<exp.length;i++)
		{	
			for(int j = 0; j<exp[i].length;j++)
			{
				System.out.print(""+exp[i][j]+"  ");
				assertEquals(exp[i][j], rlst[i][j]);
			}
			System.out.println();
		}
	}

}
