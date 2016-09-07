package com.stu.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class TestGRidBagLayout extends JFrame {

	public TestGRidBagLayout() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		
		con.fill = GridBagConstraints.BOTH;
		con.weightx = 0.05;
		
		this.add(new JButton("1"),con);
		
		con.weightx = 0.05;
		con.gridwidth = GridBagConstraints.REMAINDER;
		this.add(new JButton("2"),con);
		
		con.weightx = 0.01;
//		con.fill = GridBagConstraints.NONE;
		con.gridwidth = 1;
		this.add(new JButton("3"),con);
		
		con.weightx = 0.1;
		con.gridwidth = 0;
//		con.anchor = GridBagConstraints.ABOVE_BASELINE;//中间对齐
		this.add(new JButton("4"),con);
		
		con.weighty = 0;
		con.weightx = 0.01;
		con.gridwidth = 1;
//		con.anchor = GridBagConstraints.BASELINE;//上对齐
		this.add(new JButton("5"),con);
		
		con.gridwidth = 2;
		con.weightx = 0.1;
//		con.anchor = GridBagConstraints.BELOW_BASELINE;//上对齐
		this.add(new JButton("6"),con);
		
//		this.add(new JButton("7"),con);
//		
//		this.add(new JButton("8"),con);
		
		
		this.setLocationRelativeTo(null);
		this.setSize(500, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new TestGRidBagLayout();
	}

}
