package com.stu.graph;

public class Place extends Point {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Point linkPoint;
	
	public Place(int x, int y) {
		super(x, y);
	}
	
	public Place(int x, int y,Point linkPoint,String name) {
		super(x, y);
		this.linkPoint = linkPoint;
	}

	/*
	 * setter  getter
	 */
	public Point getLinkPoint() {
		return linkPoint;
	}

	public void setLinkPoint(Point linkPoint) {
		this.linkPoint = linkPoint;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	

}
