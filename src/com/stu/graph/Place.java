package com.stu.graph;

public class Place extends Point {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Point linkPoint;
	private int modelIndex;
	
	public Place(int x, int y) {
		super(x, y);
	}
	
	public Place(int x, int y,Point linkPoint) {
		super(x, y);
		this.linkPoint = linkPoint;
	}
	
	public Place(Point originPoint,Point linkPoint) {
		this(originPoint.x, originPoint.y,linkPoint);
	}
	public Place(Point originPoint,Point linkPoint,int modelIndex) {
		this(originPoint.x, originPoint.y,linkPoint);
		this.modelIndex = modelIndex;
	}
	
	public Place(Point originPoint,Point linkPoint,String name) {
		this(originPoint.x, originPoint.y,linkPoint);
		this.name = name;
	}

	@Override
	public String toString() {
		return ""+modelIndex+"("+x+","+y+")->("+linkPoint.x+","+linkPoint.y+")";
	}

	/*
	 * setter  getter
	 */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Point getLinkPoint() {
		return linkPoint;
	}

	public void setLinkPoint(Point linkPoint) {
		this.linkPoint = linkPoint;
	}

	public int getModelIndex() {
		return modelIndex;
	}

	public void setModelIndex(int modelIndex) {
		this.modelIndex = modelIndex;
	}
	

}
