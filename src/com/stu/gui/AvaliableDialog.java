package com.stu.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.stu.graph.WayPoint;

public class AvaliableDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int ADD_MODE = 0;
	private static final int ALTER_MODE = 1;
	private int self = -1;
	
	public static boolean finish = false;
	public static int [][] aval;
	
	private int mode = ADD_MODE;
	
	private JButton okBtn,cancelBtn;
	private JCheckBox []checkBoxes;
	private JCheckBox [][] ck1;
	
	private JPanel centerPanel,bottomPanel;
	
	private PropertyChangeSupport surpport;
	
	/**
	 * 
	 * @param self 自身在List中的序号
	 * @param pointList 保存了所有点的List
	 */
	public AvaliableDialog(int self,ArrayList<WayPoint> pointList) {
		mode = ADD_MODE;
		this.self = self;
		surpport = new PropertyChangeSupport(this);
		
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
	
	public AvaliableDialog(int[][] origin_ava) {
		surpport = new PropertyChangeSupport(this);
		mode = ALTER_MODE;
		int n = origin_ava.length;
		ck1 = new JCheckBox[n][n];
		aval = new int[n][n];
		centerPanel = new JPanel(new GridLayout(n+1, n+1));
		centerPanel.add(new JLabel(""));
		for(int i = 1; i<=n; i++)
			centerPanel.add(new JLabel(""+(i-1)));
		bottomPanel = new JPanel();
		for(int i = 0; i<n ; i++)
		{
			centerPanel.add(new JLabel(""));
			ck1[i] = new JCheckBox[n];
			for(int j = 0; j<ck1[i].length; j++ )
			{
				ck1[i][j] = new JCheckBox("");
				aval[i][j] = 0;
				if(i>=j)
				{
					ck1[i][j].setEnabled(false);
					if(i==j)
						centerPanel.add(new JLabel(""+(i)));
					else
						centerPanel.add(new JLabel(""));
				}else
				{
					centerPanel.add(ck1[i][j]);
				}
				if(origin_ava[i][j]==1)
				{
					ck1[i][j].setSelected(true);
				}
			}
		}
		
		cancelBtn = new JButton("取消");
		okBtn = new JButton("确定");
		
		cancelBtn.addActionListener(this);
		okBtn.addActionListener(this);
		
		bottomPanel.add(okBtn);
		bottomPanel.add(cancelBtn);
		
		this.setLayout(new BorderLayout());
		this.add(centerPanel, "Center");
		this.add(bottomPanel, "South");
		
		this.setSize(n*20, n*20+80);
		this.setLocationRelativeTo(null);
		this.setTitle("选择可以到达的点");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("确定"))
		{
			switch(mode){
				case ADD_MODE:{
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
					break;
				}
				case ALTER_MODE:{
					for(int i = 0; i<ck1.length; i++)
					{
						for(int j = i+1; j<ck1[i].length;j++)
						{
							if(ck1[i][j].isSelected())
							{
								aval[j][i] = 1;
								aval[i][j] = 1;
							}
							else{
								aval[j][i] =  0;
								aval[i][j] =  0;
							}
						}
					}
					firePropertyChange("finish", false, true);
					this.dispose();
					break;
				}
			}
		}
		else
		{
			finish = false;
			this.dispose();
		}
		
	}

	
	public void addListener(PropertyChangeListener listener){
		surpport.addPropertyChangeListener(listener);
	}
	
	public boolean isFinish() {
		return finish;
	}

	

	public static void setFinish(boolean finish) {
		AvaliableDialog.finish = finish;
	}

	public int[][] getAval() {
		return aval;
	}
	

}
