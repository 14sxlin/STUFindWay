package com.stu.graph;

public class WayPoint extends Point {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	
	public WayPoint(int x, int y,String name) {
		super(x, y);
		this.name = name;
	}
	
	public WayPoint(int x, int y) {
		super(x, y);
	}
	
	public WayPoint(Point p) {
		super(p.x, p.y);
	}

	@Override
	public String toString() {
		return "P_"+name+"( "+x+" , "+y+" )";
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
