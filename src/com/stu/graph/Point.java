package com.stu.graph;

import java.io.Serializable;

/**
 * 简单的使用int类型就好
 * @author LinSixin
 *
 */
public class Point implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int x,y;
	public Point(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point scaled(int scale){
		if(scale>0)
			return new Point(x*scale, y*scale);
		else 
			return new Point(-x/scale,-y/scale);
	}
	
	public static int lengthOf(Point p1,Point p2)
	{
		int x = Math.abs(p1.x-p2.x);
		int y = Math.abs(p1.y-p2.y);
		int rlst = (int)Math.sqrt(x*x+y*y);
		return rlst;
	}
	
	@Override
	public String toString() {
		return "P( "+x+" , "+y+" )";
	}
	
	public static void printMatrix(int[][] matrix){
		for(int i = 0; i<matrix.length; i++)
		{	for(int j = 0; j<matrix[i].length; j++)
			{
				System.out.print(""+matrix[i][j]+" ");
			}
			System.out.println();
		}
	}
}
