package com.stu.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.PopupMenuUI;

import com.stu.database.ObjectTXTManager;
import com.stu.graph.StuPlaceManager;
import com.stu.graph.StuWayPointManager;
import com.stu.graph.WayPoint;

public class WayPointManagePanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private static final int WAYPOINT_MODE = 3;
	private static final int PLACE_MODE = 4;
	private String stuPlaceFile = "",stuWayPointFile = "";
	private int mode = WAYPOINT_MODE;
	private int count = 0;				//	当前标注在地图中的路径点的数目
	private int init = 0;				
	
	
	private StuPlaceManager stuPlaceManager;
	private StuWayPointManager stuWpManager;
	private ObjectTXTManager objTxtManager1,objTxtManager2;
	
	private JLabel msgLabel;
	private JButton addBtn,saveBtn,clearBtn,loadBtn,showRouteBtn,alterRouteBtn;
	private JButton addBtn1,saveBtn1,clearBtn1,loadBtn1,alterRouteBtn1;
	private DefaultListModel<String> listModel;
	private MapDisplayCanvas canvas;

	private JRadioButton wayPointRadio,placeRadio;
	private AvaliableDialog avaDialog;		//表示一个点可以直接直线达到哪些点
	private JList<String> wayPointList;
	private JPopupMenu pop;
	private String menuItem_str[]={"删除"};
	private JMenuItem menuItem[];
	
	private WayPoint lastSelected;
	private ActionListener menuItemActionListener;
	
	private final static String 
				CLEARMAP = "清空地图",
				ADD = "添加",
				SAVE = "保存",
				LOAD = "读取",
				ALTER = "修改",
				SHOWROUTE = "显示路径";
				
	/**
	 * 初始化组件和事件		
	 */
	public WayPointManagePanel() {
		stuWpManager = new StuWayPointManager();
		stuPlaceManager = new StuPlaceManager();
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
				if(mode==PLACE_MODE)
				{
					String name = JOptionPane.showInputDialog("地点名称");
					if(name==null||name.trim().equals(""))
					{
						JOptionPane.showMessageDialog(WayPointManagePanel.this, "请输入地点名称");
						return;
					}
					WayPoint wp = new WayPoint(e.getX(), e.getY());
					wp.setName(name);
					canvas.drawColorString("@"+name, e.getX(), e.getY(), Color.darkGray);
					stuPlaceManager.addPlace(wp);
					
				}else if(mode==WAYPOINT_MODE)
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
		
		JPanel waypointPanel  = new JPanel();
		JPanel placePanel  = new JPanel();
		JPanel bottomPanel  = new JPanel();
		bottomPanel.add(msgLabel);
		
		clearBtn = new JButton(CLEARMAP);
		addBtn = new JButton(ADD);
		saveBtn = new JButton(SAVE);
		loadBtn = new JButton(LOAD);
		showRouteBtn = new JButton(SHOWROUTE);
		alterRouteBtn = new JButton(ALTER);
		
		ButtonGroup waypointGroup = new ButtonGroup();
		addBtn.addActionListener(this);
		saveBtn.addActionListener(this);
		clearBtn.addActionListener(this);
		loadBtn.addActionListener(this);
		showRouteBtn.addActionListener(this);
		alterRouteBtn.addActionListener(this);
		
		waypointPanel.add(clearBtn);
		waypointPanel.add(addBtn);
		waypointPanel.add(saveBtn);
		waypointPanel.add(loadBtn);
		waypointPanel.add(alterRouteBtn);
		waypointPanel.add(showRouteBtn);
		waypointPanel.setBorder(BorderFactory.createTitledBorder("路径点管理"));
		
		waypointGroup.add(addBtn);
		waypointGroup.add(saveBtn);
		waypointGroup.add(loadBtn);
		waypointGroup.add(clearBtn);
		waypointGroup.add(showRouteBtn);
		waypointGroup.add(alterRouteBtn);
		
		
		
		clearBtn1 = new JButton(CLEARMAP);
		addBtn1 = new JButton(ADD);
		saveBtn1 = new JButton(SAVE);
		loadBtn1 = new JButton(LOAD);
		alterRouteBtn1 = new JButton(ALTER);
		
		ButtonGroup placeGroup = new ButtonGroup();
		addBtn1.addActionListener(this);
		saveBtn1.addActionListener(this);
		clearBtn1.addActionListener(this);
		loadBtn1.addActionListener(this);
		alterRouteBtn1.addActionListener(this);
		
		placePanel.add(clearBtn1);
		placePanel.add(addBtn1);
		placePanel.add(saveBtn1);
		placePanel.add(loadBtn1);
		placePanel.add(alterRouteBtn1);
		placePanel.setBorder(BorderFactory.createTitledBorder("地点管理"));
		
		placeGroup.add(addBtn1);
		placeGroup.add(saveBtn1);
		placeGroup.add(loadBtn1);
		placeGroup.add(clearBtn1);
		placeGroup.add(alterRouteBtn1);
		
		
		bottomPanel.add(waypointPanel);
		bottomPanel.add(placePanel);
		
		wayPointRadio = new JRadioButton("路径点");
		placeRadio = new JRadioButton("地点");
		ButtonGroup group = new ButtonGroup();
		group.add(wayPointRadio);
		group.add(placeRadio);
		
		bottomPanel.add(wayPointRadio);
		bottomPanel.add(placeRadio);
		
		wayPointRadio.addActionListener(e->{
			if(wayPointRadio.isSelected())
			{
				mode = WAYPOINT_MODE;
				Enumeration<AbstractButton> e1 = placeGroup.getElements();
				while(e1.hasMoreElements())
					e1.nextElement().setEnabled(false);
				Enumeration<AbstractButton> e2 = waypointGroup.getElements();
				while(e2.hasMoreElements())
					e2.nextElement().setEnabled(true);
				if(init==0)
					return;
				try{
					listModel.removeAllElements();
					for(WayPoint p:stuWpManager.getWayPointList())
						listModel.addElement(p.toString());
					wayPointList.setModel(listModel);
					count = listModel.getSize();
					canvas.setPoints(stuWpManager.getWayPointList());
					canvas.repaint();
				}catch(Exception ex){
					JOptionPane.showMessageDialog(this, "请点击读取");
				}
			}
		});
		placeRadio.addActionListener(e->{
			if(placeRadio.isSelected())
			{
				mode = PLACE_MODE;
				Enumeration<AbstractButton> e1 = waypointGroup.getElements();
				while(e1.hasMoreElements())
					e1.nextElement().setEnabled(false);
				Enumeration<AbstractButton> e2 = placeGroup.getElements();
				while(e2.hasMoreElements())
					e2.nextElement().setEnabled(true);
			}
			if(init==0)
				return;
			try{
				listModel.removeAllElements();
				for(WayPoint p:stuPlaceManager.getPlaces())
					listModel.addElement(p.toString());
				wayPointList.setModel(listModel);
				count = listModel.getSize();
				canvas.setPoints(stuPlaceManager.getPlaces());
				canvas.repaint();
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this, "请点击读取");
			}
		});
		
		wayPointRadio.setSelected(true);
		init++;
		mode = WAYPOINT_MODE;
		Enumeration<AbstractButton> e1 = placeGroup.getElements();
		while(e1.hasMoreElements())
			e1.nextElement().setEnabled(false);
		Enumeration<AbstractButton> e2 = waypointGroup.getElements();
		while(e2.hasMoreElements())
			e2.nextElement().setEnabled(true);
		
		// TODO
//		JPanel rightPanel = new JPanel();
//		rightPanel.setLayout(new BorderLayout());
//		rightPanel.add(wayPointList, "Center");
//		
//		JPanel rightBottomPanel = new JPanel();
		
		
		JSplitPane split = 
				new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, canvas,wayPointList );
		split.setDividerLocation(canvas.getImwidth()-200);
		
		menuItemActionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = wayPointList.getSelectedIndex();
				if(e.getActionCommand()=="删除")
				{
					if(mode==WAYPOINT_MODE)
					{
						listModel.remove(selectedIndex);
						stuWpManager.removeWayPoint(selectedIndex);
						int n = stuWpManager.getAvaliable().length;
						int[][] oldava = stuWpManager.getAvaliable();
						int[][] newava = new int[n][n];
						int traceI = 0,traceJ = 0;
						for(int i = 0;i<n;i++)
						{
							System.out.print(traceI+"("+i+"):  ");
							traceJ = i;
							if(i==selectedIndex)
							{	
								System.out.println();
								continue;
							}
							for(int j = i; j<n ; j++)
							{	System.out.print(traceJ+"("+j+")  ");
								if(j==selectedIndex)
								{
									continue;
								}
								newava[traceI][traceJ] = oldava[i][j];
								newava[traceJ][traceI] = oldava[i][j];
								traceJ++;
								
							}
							System.out.println();
							traceI++;
							
						}
						com.stu.graph.Point.printMatrix(oldava);
						System.out.println("------------------");
						com.stu.graph.Point.printMatrix(newava);
						System.out.println("------------------");
						stuWpManager.setAvaliable(newava);
						canvas.setPoints(stuWpManager.getWayPointList());
						canvas.repaint();
						
					}else if(mode==PLACE_MODE)
					{
						listModel.remove(selectedIndex);
						stuPlaceManager.removePlace(selectedIndex);
						canvas.setPoints(stuWpManager.getWayPointList());
						canvas.repaint();
					}
				}
			}
		};
		
		
		pop = new JPopupMenu();
		menuItem = new JMenuItem[menuItem_str.length];
		for(int i = 0; i<menuItem.length; i++)
		{	menuItem[i] = new JMenuItem(menuItem_str[i]);
			menuItem[i].addActionListener(menuItemActionListener);
			pop.add(menuItem[i]);
		}
		
		
		wayPointList.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				wayPointList.setSelectedIndex(wayPointList.locationToIndex(new Point(e.getX(), e.getY())));
				if(lastSelected!=null)
					canvas.drawColorString("@"+lastSelected.getName(), lastSelected.x, lastSelected.y, Color.red);
				WayPoint selected = selectedItem(wayPointList.getSelectedIndex());
				assert selected!=null;
				canvas.drawColorString("@"+selected.getName(), selected.x, selected.y, Color.blue);
				lastSelected = selected;
				
				if (e.getButton()==MouseEvent.BUTTON3) {
					pop.show(e.getComponent(), e.getX(), e.getY());
				}
			}
			
		});
		this.setLayout(new BorderLayout());
		this.add(split, "Center");
		this.add(bottomPanel, "South");
	}
	
	/**
	 * 指定文件名
	 * @param fileName 文件名
	 * @param readFile 是否从指定的文件中读取数据
	 */
	public WayPointManagePanel(String stuWayPointFile,boolean readFile) {
		this();
		this.stuWayPointFile = stuWayPointFile;
		objTxtManager1 = new ObjectTXTManager(stuWayPointFile);
		listModel = new DefaultListModel<>();
		if(readFile)
		{	
			try {
				stuWpManager = (StuWayPointManager) objTxtManager1.readObject();
				for(WayPoint p:stuWpManager.getWayPointList())
					listModel.addElement(p.toString());
				canvas.setPoints(stuWpManager.getWayPointList());
				count = listModel.getSize();
			} catch (ClassNotFoundException | IOException e) {
				JOptionPane.showMessageDialog(this, "文件错误");
				return;
			}
		}
		wayPointList.setModel(listModel);
	}

	/**
	 * 
	 * @param wayPointFile
	 * @param placeFile
	 * @param readFile
	 */
	public WayPointManagePanel(String wayPointFile,String placeFile,boolean readFile) {
		this(wayPointFile,readFile);
		this.stuPlaceFile = placeFile;
		objTxtManager2 = new ObjectTXTManager(placeFile);
		if(readFile)
		{
				try {
					stuPlaceManager = (StuPlaceManager) objTxtManager2.readObject();
				} catch (ClassNotFoundException | IOException e) {
					JOptionPane.showMessageDialog(this, "文件错误");
					return;
				}
		}
	}
	
	public boolean checkFileExist(int mode){
		if(mode==PLACE_MODE)
		{
			if(stuPlaceFile==null||stuPlaceFile.trim().equals(""))
			{
				String filename = JOptionPane.showInputDialog("保存的文件名");
				if(filename==null||filename.trim().equals(""))
					return false;
				objTxtManager2 = new ObjectTXTManager(filename);
			}
		}else if(mode==WAYPOINT_MODE)
		{
			if(stuWayPointFile==null||stuWayPointFile.trim().equals(""))
			{
				String filename = JOptionPane.showInputDialog("保存的文件名");
				if(filename==null||filename.trim().equals(""))
					return false;
				objTxtManager1 = new ObjectTXTManager(filename);
			}
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
			case ADD:{
				AddAction();
				break;
			}
			case SAVE:{
				SaveAction();
				break;
			}
			case CLEARMAP:{
				stuWpManager.clearWayPoint();
				listModel.removeAllElements();
				canvas.clear();
				count = 0;
				break;
			}
			case LOAD:{
				LoadAction();
				break;
			}
			case SHOWROUTE:{
				if(!canvas
						.drawAvailRoute(stuWpManager.getWayPointList(), 
								stuWpManager.getAvaliable()))
					JOptionPane.showMessageDialog(this, "请先读取数据");
				break;
			}
			case ALTER:{
				if(stuWpManager.getWayPointList().size()!=stuWpManager.getAvaliable().length)
				{
					JOptionPane.showMessageDialog(this, "请先读取数据");
					return;
				}
				avaDialog = new AvaliableDialog(stuWpManager.getAvaliable());
				avaDialog.addPropertyChangeListener(new PropertyChangeListener() {
					
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						canvas.repaint();
						int [][]ava = avaDialog.getAval();
						stuWpManager.setAvaliable(ava);
						canvas.drawAvailRoute(
								stuWpManager.getWayPointList(), 
								stuWpManager.getAvaliable());
					}
				});
				break;
			}
			case "":{
				break;
			}
		}
		
	}

	private void LoadAction() {
		if(mode==WAYPOINT_MODE)
		{
			if(!checkFileExist(WAYPOINT_MODE))
				return;
				try {
					stuWpManager = (StuWayPointManager) objTxtManager1.readObject();
				} catch (ClassNotFoundException | IOException e1) {
					JOptionPane.showMessageDialog(this, "文件错误");
					return;
				}
			listModel.removeAllElements();
			for(WayPoint p:stuWpManager.getWayPointList())
				listModel.addElement(p.toString());
			wayPointList.setModel(listModel);
			count = listModel.getSize();
			canvas.setPoints(stuWpManager.getWayPointList());
			canvas.repaint();
			
		}else if(mode==PLACE_MODE){
			if(!checkFileExist(PLACE_MODE))
				return;
			listModel.removeAllElements();
			try {
				stuPlaceManager = (StuPlaceManager) objTxtManager2.readObject();
			} catch (ClassNotFoundException | IOException e1) {
				JOptionPane.showMessageDialog(this, "文件错误");
				return;
			}
			for(WayPoint p:stuPlaceManager.getPlaces())
				listModel.addElement(p.toString());
			wayPointList.setModel(listModel);
			count = listModel.getSize();
			canvas.setPoints(stuPlaceManager.getPlaces());
			canvas.repaint();
		}
	}

	private void SaveAction() {
		if(mode==WAYPOINT_MODE)
		{
			int choose = JOptionPane.showConfirmDialog(this, "确定要保存吗? 将会覆盖原来的数据,请做好备份");
			if(choose!=JOptionPane.OK_OPTION)
				return;
			if(!checkFileExist(WAYPOINT_MODE))
				return;
			if(objTxtManager1 == null)
			{
				objTxtManager1 = new ObjectTXTManager(stuWayPointFile);
			}
			stuWpManager.calculateDis();
			objTxtManager1.writeObject(stuWpManager);
		}
		else if(mode==PLACE_MODE)
		{
			int choose = JOptionPane.showConfirmDialog(this, "确定要保存吗? 将会覆盖原来的数据,请做好备份");
			if(choose!=JOptionPane.OK_OPTION)
				return;
			if(!checkFileExist(PLACE_MODE))
				return;
			if(objTxtManager2 == null)
			{
				objTxtManager2 = new ObjectTXTManager(stuPlaceFile);
			}
			objTxtManager2.writeObject(stuPlaceManager);
		}
	}

	private void AddAction(){
		if(mode==PLACE_MODE)
		{	
			listModel.removeAllElements();
			for(WayPoint p:stuPlaceManager.getPlaces())
			{
				if(listModel==null)
					listModel = new DefaultListModel<>();
				listModel.addElement(p.toString());
				wayPointList.setModel(listModel);
			}
			
		}else if(mode==WAYPOINT_MODE)
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
		}
	}

	private WayPoint selectedItem(int index){
		if(mode==WAYPOINT_MODE)
		{
			return stuWpManager.getWayPointList().get(index);
		}
		else if(mode==PLACE_MODE)
		{
			return stuPlaceManager.getPlaces().get(index);
		}
		return null;
	}
}
