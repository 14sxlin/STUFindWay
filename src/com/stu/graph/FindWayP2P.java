package com.stu.graph;

import java.util.ArrayList;
import java.util.Stack;

public class FindWayP2P {

	public static final int INF = 9999999;
	private int[] rslt;
	private boolean[] been;
	private int[] path;
	private int[][] g ; 
	private int start;
	private int n;
	/**
	 * 无向图的构造方式
	 * @param wayPoints wayPoints中的点算的是直线距离
	 */
	public FindWayP2P(ArrayList<? extends Point> wayPoints,int start) {
		init(wayPoints.size(),start);
		g = new int[n][n];
		for( int i = 0; i<rslt.length; i++){
			rslt[i] = INF;
			been[i] = false;
		}
		for(int i =0; i<n; i++)
			for(int j = 0; j<n; j++)
			{
				g[i][j]  = INF;
			}
		for(int i =0; i<n; i++)
			for(int j = 0; j<n; j++)
			{
				g[i][j] = Point.lengthOf(wayPoints.get(i), wayPoints.get(j));
				g[j][i] = g[i][j];
			}
	}
	
	public FindWayP2P(int[][] g,int start) {
		init(g.length,start);
		this.g= g;
		
	}
	
	private void init(int n ,int start){
		this.n = n;
		this.start = start;
		rslt = new int[n];
		been = new boolean[n];
		path = new int[n];
		assert n==rslt.length;
		for( int i = 0; i<rslt.length; i++){
			rslt[i] = INF;
			been[i] = false;
			path[i] = -1;
		}
	}
	
	private int minIndex(){
		int index = -1;
		int min = INF;
		for(int i = 0; i<rslt.length ; i++)
		{
			if(!been[i]&&rslt[i]<min){
				index = i;
				min = rslt[i];
			}
		}
		return index;
	}
	
	public void findWay(){
		for(int i = 0; i<n; i++)
		{
			rslt[i] = g[start][i];
		}
		rslt[start] = 0;
		been[start] = true;
		for(int i = 1; i<n; i++)
		{
			int index = minIndex();
			for(int j = 0; j<n; j++)
			{
				
				if(g[index][j]+rslt[index]<rslt[j]&&!been[j])
				{
					rslt[j] = g[index][j]+rslt[index];
					path[j] = index;
				}
			}
			been[index] = true;
		}
	}
	
	public ArrayList<Integer> getPathList(int end){
		Stack<Integer> s = new Stack<Integer>();
		ArrayList<Integer> list = new ArrayList<>();
		list.add(start);
		int current = end;
		while(current!=start&&current!=-1)
		{
			s.push(current);
			current = path[current];
		}
		while(!s.empty())
		{
			list.add(s.pop());
		}
		return list;
	}
	
	@Override
	public String toString() {
		String str = "";
		for(int i:path)
			str+=""+i+"  ";
//		str+="   length : "+rslt[end]+"\n";
		return str;
	}

	public int[] getRslt() {
		return rslt;
	}

	public int[][] getG(){
		return g;
	}
}
