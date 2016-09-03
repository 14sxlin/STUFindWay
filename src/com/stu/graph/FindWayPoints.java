package com.stu.graph;

import java.util.ArrayList;

public class FindWayPoints {

	public static final int MAXCOST = 1000;
	private int g[][];
	private int currentDis = 0;
	private int shortestDis = MAXCOST;	
	private int order[];
	private int shortestOrder[];
	private int n;	
	
	
	
	public FindWayPoints(int[][] g) {
		n = g.length;
		order = new int[n];
		shortestOrder = new int[n];
		for( int i = 0; i< n ;i++)
		{
			order[i] = i;
			shortestOrder[i] = i;
		}
		this.g= g;
		
	}
	
	public FindWayPoints(ArrayList<Point> points)
	{
		n = points.size();
		g = new int[n][n];
		order = new int[n];
		shortestOrder = new int[n];
		
		for( int i = 1; i< n ;i++)
		{
			order[i] = i;
			shortestOrder[i] = i;
		}
		
		for(int i = 0; i < n; i++)
			for(int j = i; j < n; j++)
			{
				if(i==j)
					g[i][j] = 0;
				else 
				{
					g[i][j] = Point.lengthOf(points.get(i), points.get(j));
					g[j][i] = g[i][j];
				}
			}
		
	}
	
	@Deprecated
	public FindWayPoints(ArrayList<Point> points,char[][] map,int scale) {
		n = points.size();
		g = new int[n][n];
		order = new int[n];
		shortestOrder = new int[n];
		
		for( int i = 1; i< n ;i++)
		{
			order[i] = i;
			shortestOrder[i] = i;
		}
		
		for(int i = 0; i < n; i++)
			for(int j = i; j < n; j++)
			{
				if(i==j)
					g[i][j] = 0;
				else 
				{
					int x = points.get(j).scaled(-10).x,
						y = points.get(j).scaled(-10).y;
					FindWayP2PWithBarrier findLength  = 
							new FindWayP2PWithBarrier(points.get(i).scaled(-10), points.get(j).scaled(-10),map);
					if(findLength.findWay2Point()
							&&findLength.getShortestDis()[x][y]!=FindWayP2PWithBarrier.MAX_DISTANCE)
						g[i][j] = findLength.getShortestDis()[x][y];
					else 
						g[i][j] = Point.lengthOf(points.get(i), points.get(j))*10;
					g[j][i] = g[i][j];
				}
			}
	}
	
	public void findWay(int t){
		
		if(t==n){
			if(g[order[n-2]][order[n-1]]<MAXCOST&&g[order[n-1]][order[1]]<MAXCOST
					&&(shortestDis==MAXCOST||currentDis+g[order[n-2]][order[n-1]]+g[order[n-1]][1]<shortestDis))
			{
				shortestDis = currentDis+g[order[n-2]][order[n-1]]+g[order[n-1]][1];
				for( int i = 0 ; i < n ; i++)
					shortestOrder[i] = order[i];
			}
		}
		else{
			for(int j = t; j < n; j++)
			{
				//是否可以进入子树
				if(g[order[t-1]][order[j]]<MAXCOST
				     &&
				       (shortestDis==MAXCOST||currentDis+g[order[t-1]][order[j]]<shortestDis))
				{
					swap(t,j);
					currentDis += g[order[t-1]][order[j]];
					findWay(t+1);
					currentDis -= g[order[t-1]][order[j]];
					swap(t,j);
				}
					
			}
		}
		
	}
	

	private void swap(int x,int y){
		int temp = order[x];
		order[x] = order[y];
		order[y] = temp;
	}
	
	
	
	@Override
	public String toString(){
		String gstr  = "";
		for(int i = 0; i<g.length; i++)
		{
			for(int j = 0; j<g[i].length; j++)
			{
				gstr+="  "+g[i][j];
			}
			gstr+="\n";
		}
		return gstr;
	}

	public int[] getShortestOrder() {
		// TODO Auto-generated method stub
		return shortestOrder;
	}

	public int getN() {
		// TODO Auto-generated method stub
		return n;
	}

	public int[][] getG() {
		// TODO Auto-generated method stub
		return g;
	}
	
}
