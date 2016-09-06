package com.stu.gui;

import javax.swing.JFrame;

public class TestWayPointManagerPanel extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	WayPointManagePanel wpPanel;
	
	public TestWayPointManagerPanel() {
		wpPanel = new WayPointManagePanel();
		this.add(wpPanel);
		this.setSize(1500, 1000);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	public TestWayPointManagerPanel(String fileName1,String fileName2) {
		wpPanel = new WayPointManagePanel(fileName1,fileName2,true);
		this.add(wpPanel);
		this.setSize(1500, 1000);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new TestWayPointManagerPanel("waypointmodel.data","placemodel.data").setVisible(true);
	}

}
