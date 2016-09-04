package com.stu.graph;

import java.io.Serializable;
import java.util.ArrayList;

public class StuWayPointManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int INF = 99999999;
	private int dis[][];
	
	private ArrayList<WayPoint> wayPointList;
	
	public StuWayPointManager() {
		wayPointList = new ArrayList<>();
	}
	
	public void addWayPoint(WayPoint p){
		wayPointList.add(p);
	}
	
	/**
	 * 
	 * @param avaliable 表示要不要计算距离  1 表示计算, 0表示别计算
	 */
	public void calculateDis(int[][] avaliable){
		int n = avaliable.length;
		dis = new int[n][n];
		for(int i = 0 ; i<n; i++)
			for(int j = 0; j<n; j++)
			{
				if(avaliable[i][j]==1)
				{
					dis[i][j] = Point.lengthOf(wayPointList.get(i), wayPointList.get(j));
				}else{
					dis[i][j] = INF ;
				}
			}
	}
	
	/**
	 * 获取路径点中与指定路径点直线距离最短的路径点
	 * @param point 指定的点
	 * @return 最短距离的点的序号
	 */
	public int findShortestWayPoint(Point point){
		if(wayPointList==null||wayPointList.size()==0)
			return -1;
		int length = Point.lengthOf(point, wayPointList.get(0));
		int min = 0;
		int count = 0;
		for(;count<wayPointList.size();)
		{
			if(Point.lengthOf(point, wayPointList.get(count))<length)
			{
				length = Point.lengthOf(point, wayPointList.get(count));
				min = count;
			}
			count++;
		}
		return min;
	}
	
	public void clearWayPoint(){
		wayPointList.clear();
	}
	
	@Override
	public String toString() {
		String str = "";
		for(WayPoint p:wayPointList)
			str+=" " + p.toString()+"\n";
		if(dis!=null)
			for(int i = 0; i<dis.length; i++)
				{
					for(int j = 0; j<dis.length; j++)
					{
						if(dis[i][j]==INF)
							System.out.print("INF  "); 
						else
							System.out.print(dis[i][j]+"  "); ; 
					}
					System.out.println();
				}
		return str;
	}

	public ArrayList<WayPoint> getWayPointList() {
		return wayPointList;
	}

	public void setWayPointList(ArrayList<WayPoint> wayPointList) {
		this.wayPointList = wayPointList;
	}

	public int[][] getDis() {
		return dis;
	}

	public void setDis(int[][] dis) {
		this.dis = dis;
	}

	
}
