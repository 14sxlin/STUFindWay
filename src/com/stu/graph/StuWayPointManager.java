package com.stu.graph;

import java.io.Serializable;
import java.util.ArrayList;

public class StuWayPointManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private ArrayList<WayPoint> wayPointList;
	
	public StuWayPointManager() {
		wayPointList = new ArrayList<>();
	}
	
	public void addWayPoint(WayPoint wayPoint){
		wayPointList.add(wayPoint);
	}
	
	/**
	 * 获取路径点中与指定路径点直线距离最短的路径点
	 * @param wayPoint 指定的点
	 * @return
	 */
	public WayPoint findShortestWayPoint(Point point){
		if(wayPointList==null||wayPointList.size()==0)
			return null;
		int length = Point.lengthOf(point, wayPointList.get(0));
		WayPoint min = wayPointList.get(0);
		for(WayPoint temp:wayPointList)
		{
			if(Point.lengthOf(point, temp)<length)
			{
				length = Point.lengthOf(point, temp);
				min = temp;
			}
		}
		return min;
	}
	
	@Override
	public String toString() {
		String str = "";
		for(WayPoint p:wayPointList)
			str+=" " + p.toString()+"\n";
		return str;
	}

}
