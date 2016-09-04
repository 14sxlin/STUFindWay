package com.stu.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.stu.graph.WayPoint;

public class AvaliableDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int self = -1;
	
	private boolean finish = false;
	private JButton okBtn,cancelBtn;
	private JCheckBox []checkBoxes;
	private JPanel centerPanel,bottomPanel;
	private int [][] aval;
	
	/**
	 * 
	 * @param self 自身在List中的序号
	 * @param pointList 保存了所有点的List
	 */
	public AvaliableDialog(int self,ArrayList<WayPoint> pointList) {
		this.self = self;
		int n = pointList.size();
		checkBoxes = new JCheckBox[n];
		aval = new int[n][n];
		centerPanel = new JPanel();
		bottomPanel = new JPanel();
		for(int i = 0; i<n ; i++)
		{
			checkBoxes[i] = new JCheckBox(pointList.get(i).getName());
			centerPanel.add(checkBoxes[i]);
			for(int j = 0; j<n; j++ )
			{
				aval[i][j] = 0;
			}
		}
		checkBoxes[self].setEnabled(false);
		
		cancelBtn = new JButton("取消");
		okBtn = new JButton("确定");
		
		cancelBtn.addActionListener(this);
		okBtn.addActionListener(this);
		
		bottomPanel.add(okBtn);
		bottomPanel.add(cancelBtn);
		
		this.setLayout(new BorderLayout());
		this.add(centerPanel, "Center");
		this.add(bottomPanel, "South");
		
		this.setSize(300, 300);
		this.setLocationRelativeTo(null);
		this.setTitle("选择可以到达的点");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	/**
	 *
	 * @param self 自身在List中的序号
	 * @param pointList 保存了所有点的List
	 * @param origin_ava 先前已经有过的可到达矩阵
	 */
	public AvaliableDialog(int self,ArrayList<WayPoint> pointList,int[][] origin_ava) {
		this(self,pointList);
		int n = origin_ava.length;
		for(int  i = 0; i<n; i++)
			for(int j = 0; j<n; j++)
			{
				aval[i][j] = origin_ava[i][j];
			}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("确定"))
		{
			for(int i = 0; i<checkBoxes.length; i++)
			{
				if(checkBoxes[i].isSelected())
				{
					aval[self][i] = 1;
					aval[i][self] = 1;
				}
				else{
					aval[self][i] =  0;
					aval[i][self] =  0;
				}
			}
			finish = true;
			this.dispose();
		}
		else
		{
			this.dispose();
		}
		
	}

	public boolean isFinish() {
		return finish;
	}


	public int[][] getAval() {
		return aval;
	}
	

}
