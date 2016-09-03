package com.stu.graph;

import java.util.Stack;
@Deprecated
public class FindWayP2PWithBarrier {
	private final int UP = 0,DOWN = 1,LEFT =2,RIGHT = 3;
	public static int MAX_DISTANCE = 1000;
	private Point start,end;
	private char [][] map;
	private int [][] shortestDis;
	private int area = 5;			//搜索路径的范围 默认是0
	private int upBound,bottomBound,leftBound,rightBound;  //上下左右的边界
	private Stack<Point> wayStack;
	/**
	 * 
	 * @param start
	 * @param end
	 * @param map * 号表示不能通过,也就是障碍
	 */
	public FindWayP2PWithBarrier(Point start,Point end,char[][] map) {
		wayStack = new Stack<>();
		this.start = start;
		this.end = end;
		this.map = map;
		shortestDis = new int[map.length][map.length];
		for(int i = 0; i<shortestDis.length ; i++)
			for(int j = 0; j<shortestDis[i].length ; j++)
			{
				shortestDis[i][j] = MAX_DISTANCE ;
			}
	}
	
	private void initBounds(int area){
		upBound = (start.y-area>0)?start.y-area:0;
		bottomBound =(end.y+area<map[0].length)?end.y+area:map[0].length;
		bottomBound-=1;
		leftBound = (start.x-area>0)?start.x-area:0;
		rightBound = (end.x+area<map.length)?end.x+area:map.length;
		rightBound -=1;
		System.out.println("up:"+upBound+"  bottom: "+bottomBound+"  left:"+leftBound+"  right:"+rightBound);;
	}
	/**
	 * 移动路径点
	 * @param type 上下左右移动
	 * @return true 表示到达了终点
	 */
	private boolean moveAndPush(int type,Point currentPoint)
	{
		Point next_point = new Point(currentPoint.x, currentPoint.y);
		switch(type)
		{
			case UP:{
				next_point.y -= 1;
				if(next_point.y<upBound)
					return false;
				break;
			}
			case DOWN:{
				next_point.y += 1;
				if(next_point.y>bottomBound)
					return false;
				break;
			}
			case LEFT:{
				next_point.x -= 1;
				if(next_point.x<leftBound)
					return false;
				}
				break;
			case RIGHT:{
				next_point.x+=1;
				if(next_point.x>rightBound)
					return false;
				break;
			}
			
		}
		if(map[next_point.x][next_point.y]!='*'
				&&
		(shortestDis[next_point.x][next_point.y]==MAX_DISTANCE))
		{
			shortestDis[next_point.x][next_point.y] = shortestDis[currentPoint.x][currentPoint.y]+1;
			if(next_point.x==end.x&&next_point.y==next_point.y)
				return true;
			wayStack.push(next_point);
		}
//		System.out.println(toString());
		return false;
	}
	
	public boolean findWay2Point(){
		initBounds(area);
		shortestDis[start.x][start.y] = 0;
		wayStack.push(start);
		while(!wayStack.isEmpty())
		{
			Point currentPoint = wayStack.pop();
			for(int i = 0; i<4; i++)
			{	
				if(moveAndPush(i, currentPoint))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		String str = "";
		for(int i=0; i<shortestDis.length;i++)
		{	for(int j = 0; j<shortestDis[i].length; j++)
				if(shortestDis[i][j]==MAX_DISTANCE)
					str+="* "; 
				else 
					str+=""+shortestDis[i][j]+" "; 
			str+="\n";
		}
		return str;
	}

	/*
	 * setter  getter 方法
	 */
	public int[][] getShortestDis() {
		return shortestDis;
	}

	public void setShortestDis(int[][] shortestDis) {
		this.shortestDis = shortestDis;
	}
	
	
	
}
