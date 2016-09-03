package com.stu.graph;

import java.io.Serializable;
@Deprecated
public class BarrierGenerator implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int width,height;
	private char [][] map;
	
	public BarrierGenerator(int width, int height) {
		this.width = width;
		this.height = height;
		map = new char[width][height];
		for(int i = 0; i<width ; i++)
			for( int j = 0; j<height ; j++)
			{
				map[i][j] = '0';
			}
	}
	
	public void dropBarrier(int x,int y){
		if(constrain(x, y))
			map[x][y] = '*';
	}
	
	private boolean constrain(int x,int y)
	{
		if(x<0||x>width)
			return false;
		if(y<0||y>height)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		String str = "";
		for(int i = 0; i<height; i++)
		{	
			for(int j = 0; j<width; j++)
			{
				str+=""+map[j][i];
//				if(map[j][i]=='*')
//					str+=" ("+j+","+i+")";
			}
			str+="\n";
		}
		return str;
	}
	
	
	/*
	 *  下面是是  getter setter
	 */

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public char[][] getMap() {
		return map;
	}

	public void setMap(char[][] map) {
		this.map = map;
	}
	
	
}
