package com.stu.gui;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
	private int count = 0;				//	��ǰ��ע�ڵ�ͼ�е�·�������Ŀ
	private StuWayPointManager stuWpManager;
	private ObjectTXTManager objTxtManager;
	
	private JLabel msgLabel;
	private JButton addBtn,saveBtn,clearBtn,loadBtn;
	private JList<String> wayPointList;
	private DefaultListModel<String> listModel;
	private MapDisplayCanvas canvas;
//	private Point currentPoint ;
//	private WayPoint currentWayPoint ;
	private Place currentPlace ;
	private JRadioButton wayPointRadio,placeRadio;
	
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
				/*	msgLabel.setText("ѡ��ص�");
					currentWayPoint = new WayPoint(e.getX(), e.getY());
					currentPlace = new Place(e.getX(), e.getY());
					String name = JOptionPane.showInputDialog("�ص�����:");
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
					msgLabel.setText("������ӵص�");
				}else if(mode==MapDisplayCanvas.MODE_ADDPOINTS)
				{
					canvas.drawBigStirng("@"+count, e.getX(), e.getY());
					WayPoint wp = new WayPoint(e.getX(), e.getY());
					wp.setName(""+count);
					stuWpManager.addWayPoint(wp);
					count++;
				}
					
			}
				
		});
		JPanel btnPanel  = new JPanel();
		btnPanel.add(msgLabel);
		addBtn = new JButton("���·����");
		saveBtn = new JButton("����");
		clearBtn = new JButton("�����������");
		loadBtn = new JButton("��ȡ·����");
		
		addBtn.addActionListener(this);
		saveBtn.addActionListener(this);
		clearBtn.addActionListener(this);
		loadBtn.addActionListener(this);
		
		btnPanel.add(addBtn);
		btnPanel.add(saveBtn);
		btnPanel.add(loadBtn);
		btnPanel.add(clearBtn);
		
		wayPointRadio = new JRadioButton("·����");
		placeRadio = new JRadioButton("�ص�");
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
	 * ָ���ļ���
	 * @param fileName �ļ���
	 * @param readFile �Ƿ��ָ�����ļ��ж�ȡ����
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
			case "���·����":{
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
				}
				break;
			}
			case "����":{
				int choose = JOptionPane.showConfirmDialog(this, "ȷ��Ҫ������? ���Ḳ��ԭ��������,�����ñ���");
				if(choose!=JOptionPane.OK_OPTION)
					return;
				if(objTxtManager == null)
				{
					objTxtManager = new ObjectTXTManager(fileName);
					objTxtManager.writeObject(stuWpManager);
				}else{
					objTxtManager.writeObject(stuWpManager);
				}
				break;
			}
			case "�����������":{
				stuWpManager.clearWayPoint();
				listModel.removeAllElements();
				canvas.clear();
				count = 0;
				break;
			}
			case "��ȡ·����":{
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
