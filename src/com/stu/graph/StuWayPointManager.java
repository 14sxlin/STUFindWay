package com.stu.graph;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * @author LinSixin
 * Stu地图上面的路径点的模型管理
 */
public class StuWayPointManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int INF = FindWayP2P.INF;
	private int dis[][];
	private int available[][];
	
	private ArrayList<WayPoint> wayPointList;
	
	/**
	 * @param avaliable 表示要不要计算距离  1 表示计算, 0表示别计算
	 */
	public StuWayPointManager(int[][] available) {
		this();
		this.available = available;
	}
	
	public StuWayPointManager() {
		wayPointList = new ArrayList<>();
		int n  = wayPointList.size();
		this.available  = new int[n][n];
	}
	
	public void addWayPoint(WayPoint p){
		wayPointList.add(p);
	}
	
	public void removeWayPoint(int index){
		wayPointList.remove(index);
	}
	
	/**
	 * 还是要用available[][] ,要在构造函数中指定
	 */
	public void calculateDis(){
		calculateDis(this.available);
	}
	
	/**
	 * 使用当前的available[][] 指定
	 * @param avaliable
	 */
	public void calculateDis(int[][] avaliable){
		this.available = avaliable;
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

	/**
	 * 通过距离矩阵获取可到达矩阵
	 * @return 由01组成的可到达矩阵,0表示不可到达,1表示可以到达
	 */
	public int[][] getAvaliMatrix(){
		int n = dis.length;
		int[][] temp = new int[n][n];
		for(int i = 0; i<n; i++)
			for(int j = 0; j<n; j++)
			{
				if(dis[i][j]<INF)
					temp[i][j] = 1;
				else 
					temp[i][j] = 0;
			}
		return temp;
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

	
	public int[][] getAvaliable() {
		return available;
	}

	public void setAvaliable(int[][] avaliable) {
		this.available = avaliable;
	}
	
	
}
