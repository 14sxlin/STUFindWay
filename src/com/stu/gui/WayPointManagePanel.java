package com.stu.gui;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.stu.database.ObjectTXTManager;
import com.stu.graph.Place;
import com.stu.graph.Point;
import com.stu.graph.StuWayPointManager;
import com.stu.graph.WayPoint;

public class WayPointManagePanel extends JPanel implements ActionListener,ChangeListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int ORIGIN_MODE = 3;
	private static final int LINK_MODE = 4;
	private static final int WAIT_MODE = 5;
	private String fileName;
	private int mode = ORIGIN_MODE;
	private int count = 0;				//	当前标注在地图中的路径点的数目
	private StuWayPointManager stuWpManager;
	private ObjectTXTManager objTxtManager;
	
	private JLabel msgLabel;
	private JButton addBtn,saveBtn,clearBtn,loadBtn,showRouteBtn;
	private JButton alterRouteBtn;
	private JList<String> wayPointList;
	private DefaultListModel<String> listModel;
	private MapDisplayCanvas canvas;
//	private Point currentPoint ;
//	private WayPoint currentWayPoint ;
	private Place currentPlace ;
	private JRadioButton wayPointRadio,placeRadio;
	private AvaliableDialog avaDialog;		//表示一个点可以直接直线达到哪些点
	
	public WayPointManagePanel() {
		stuWpManager = new StuWayPointManager();
		msgLabel = new JLabel("");
		wayPointList = new JList<>();
		canvas = new MapDisplayCanvas(1500,1000);
		canvas.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(mode==ORIGIN_MODE)
				{//TODO to finish
				/*	msgLabel.setText("选择地点");
					currentWayPoint = new WayPoint(e.getX(), e.getY());
					currentPlace = new Place(e.getX(), e.getY());
					String name = JOptionPane.showInputDialog("地点名称:");
					System.out.println("name = "+name);
					if(name.trim().equals(""))
						return;
					canvas.drawRedStirng("O", e.getX(), e.getY());
					currentWayPoint.setName(name);
					mode = LINK_MODE;*/
				}else if(mode==LINK_MODE)
				{
					canvas.drawBigStirng("L", e.getX(), e.getY());
					currentPlace.setLinkPoint(new Point( e.getX(), e.getY()));
					mode = WAIT_MODE;
						
				}else if(mode==WAIT_MODE)
				{
					msgLabel.setText("请点击添加地点");
				}else if(mode==MapDisplayCanvas.MODE_ADDPOINTS)
				{
					canvas.drawBigStirng("@"+count, e.getX(), e.getY());
					WayPoint wp = new WayPoint(e.getX(), e.getY());
					wp.setName(""+count);
					stuWpManager.addWayPoint(wp);
					avaDialog = new AvaliableDialog(count, stuWpManager.getWayPointList(),stuWpManager.getAvaliable());
					avaDialog.setVisible(true);
					count++;
				}
					
			}
				
		});
		JPanel btnPanel  = new JPanel();
		btnPanel.add(msgLabel);
		addBtn = new JButton("添加路径点");
		saveBtn = new JButton("保存");
		clearBtn = new JButton("清空所有数据");
		loadBtn = new JButton("读取路径点");
		showRouteBtn = new JButton("显示路线");
		alterRouteBtn = new JButton("修改路线");
		
		
		addBtn.addActionListener(this);
		saveBtn.addActionListener(this);
		clearBtn.addActionListener(this);
		loadBtn.addActionListener(this);
		showRouteBtn.addActionListener(this);
		alterRouteBtn.addActionListener(this);
		
		btnPanel.add(addBtn);
		btnPanel.add(saveBtn);
		btnPanel.add(loadBtn);
		btnPanel.add(clearBtn);
		btnPanel.add(showRouteBtn);
		btnPanel.add(alterRouteBtn);
		
		
		wayPointRadio = new JRadioButton("路径点");
		placeRadio = new JRadioButton("地点");
		placeRadio.setEnabled(false);//TODO 
		ButtonGroup group = new ButtonGroup();
		group.add(wayPointRadio);
		group.add(placeRadio);
		
		btnPanel.add(wayPointRadio);
		btnPanel.add(placeRadio);
		wayPointRadio.addChangeListener(e->{
			mode = MapDisplayCanvas.MODE_ADDPOINTS;
		});
		placeRadio.addChangeListener(e->{
			mode = ORIGIN_MODE;
		});
		wayPointRadio.setSelected(true);
		
		
//		JPanel rightPanel = new JPanel();
//		rightPanel.setLayout(new BorderLayout());
//		rightPanel.add(wayPointList, "Center");
//		
//		JPanel rightBottomPanel = new JPanel();
		
		
		JSplitPane split = 
				new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, canvas,wayPointList );
		split.setDividerLocation(canvas.getImwidth()-200);
		
		this.setLayout(new BorderLayout());
		this.add(split, "Center");
		this.add(btnPanel, "South");
	}
	
	/**
	 * 指定文件名
	 * @param fileName 文件名
	 * @param readFile 是否从指定的文件中读取数据
	 */
	public WayPointManagePanel(String fileName,boolean readFile) {
		this();
		this.fileName = fileName;
		objTxtManager = new ObjectTXTManager(fileName);
		listModel = new DefaultListModel<>();
		if(readFile)
		{	
			
			stuWpManager = (StuWayPointManager) objTxtManager.readObject();
			for(WayPoint p:stuWpManager.getWayPointList())
				listModel.addElement(p.toString());
			canvas.setPoints(stuWpManager.getWayPointList());
			count = listModel.getSize();
		}
		wayPointList.setModel(listModel);
	}

	
	public WayPointManagePanel(LayoutManager arg0) {
		super(arg0);
	}

	public WayPointManagePanel(boolean arg0) {
		super(arg0);
	}

	public WayPointManagePanel(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
			case "添加路径点":{
				if(mode==WAIT_MODE)
				{	/*//TODO 
					stuWpManager.addWayPoint(currentWayPoint);
					if(listModel==null)
						listModel = new DefaultListModel<>();
					listModel.addElement(currentPlace.toString());
					wayPointList.setModel(listModel);*/
				}else if(mode==MapDisplayCanvas.MODE_ADDPOINTS)
				{
					listModel.removeAllElements();
					for(WayPoint p:stuWpManager.getWayPointList())
					{
						if(listModel==null)
							listModel = new DefaultListModel<>();
						listModel.addElement(p.toString());
						wayPointList.setModel(listModel);
					}
					int [][]ava = avaDialog.getAval();
					if(AvaliableDialog.finish)
						stuWpManager.setAvaliable(ava);
					
//					//TODO delete this
//					for(int i = 0; i<ava.length; i++)
//					{
//						for(int j = 0; j<ava.length; j++)
//						{
//							System.out.print(""+ava[i][j]+"  ");
//						}
//						System.out.println();
//					}
				}
				break;
			}
			case "保存":{
				int choose = JOptionPane.showConfirmDialog(this, "确定要保存吗? 将会覆盖原来的数据,请做好备份");
				if(choose!=JOptionPane.OK_OPTION)
					return;
				if(objTxtManager == null)
				{
					objTxtManager = new ObjectTXTManager(fileName);
				}
				stuWpManager.calculateDis();
				objTxtManager.writeObject(stuWpManager);
				break;
			}
			case "清空所有数据":{
				stuWpManager.clearWayPoint();
				listModel.removeAllElements();
				canvas.clear();
				count = 0;
				break;
			}
			case "读取路径点":{
				listModel.removeAllElements();
				stuWpManager = (StuWayPointManager) objTxtManager.readObject();
				for(WayPoint p:stuWpManager.getWayPointList())
					listModel.addElement(p.toString());
				wayPointList.setModel(listModel);
				count = listModel.getSize();
				canvas.setPoints(stuWpManager.getWayPointList());
				canvas.repaint();
				break;
			}
			case "显示路线":{
				canvas.drawAvailRoute(stuWpManager.getWayPointList(), stuWpManager.getAvaliable());
				break;
			}
			case "修改路线":{
				avaDialog = new AvaliableDialog(stuWpManager.getAvaliable());
				avaDialog.addPropertyChangeListener(new PropertyChangeListener() {
					
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						int [][]ava = avaDialog.getAval();
						stuWpManager.setAvaliable(ava);
						canvas.clear();
					}
				});
				break;
			}
			case "":{
				break;
			}
		}
		
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

}
