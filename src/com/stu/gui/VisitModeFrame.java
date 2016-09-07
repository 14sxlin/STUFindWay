package com.stu.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.stu.database.Configuration;
import com.stu.database.ObjectTXTManager;
import com.stu.graph.Place;
import com.stu.graph.StuPlaceManager;
import com.stu.graph.StuWayPointManager;
import com.stu.graph.WayPoint;
import com.stu.graph.WayPointManager;
import com.stu.graph.WayPointManager.PathResult;

@SuppressWarnings("serial")
public class VisitModeFrame extends JFrame {

	private MapDisplayCanvas canvas;
	private JComboBox<String> startCom,endCom;
	private JButton findBtn;
	private JPanel watchPointPanel;
	private ObjectTXTManager objManager;
	private StuWayPointManager stuWpManager;
	private StuPlaceManager stuPlaceManager;
	private WayPointManager algor;
	private WayPoint start,end;
	private int startIndex = -1,endIndex = -1;
	private ArrayList<WayPoint> watch;
	private JCheckBox cks[];
	private boolean visitMode = false;
	
	private Color[] colors={
			Color.black,
			Color.orange,
			Color.GREEN,
			Color.blue,
			Color.red,
			Color.CYAN,
			Color.DARK_GRAY,
			Color.magenta,
			Color.PINK
	};
	public VisitModeFrame() {
		try {
			init();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		startCom.addItem("--选择起点--");
		endCom.addItem("--选择终点--");
		for(WayPoint p : stuPlaceManager.getPlaces())
		{
			startCom.addItem(p.getName());
			endCom.addItem(p.getName());
		}
	}
	
	/**
	 * 初始化,读取路径点模型
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	private void init() throws ClassNotFoundException, FileNotFoundException, IOException{
		canvas = new MapDisplayCanvas(1500,1000);
		objManager = new ObjectTXTManager(Configuration.WAYPONINTMODELPATH);
		stuWpManager = (StuWayPointManager) objManager.readObject();
		objManager = new ObjectTXTManager(Configuration.PLACEMODELPATH);
		stuPlaceManager = (StuPlaceManager) objManager.readObject();
		watchPointPanel = new JPanel(new GridBagLayout());
		watch = new ArrayList<>();
		
		
		GridBagConstraints con1 = new GridBagConstraints();
		con1.gridwidth = GridBagConstraints.REMAINDER;
		con1.fill = GridBagConstraints.HORIZONTAL;
		con1.anchor = GridBagConstraints.NORTH;
		con1.weighty = 0.1;
		con1.weightx = 0.5;
		cks = new JCheckBox[stuPlaceManager.getPlaces().size()];
		int i = 0;
		for(WayPoint p : stuPlaceManager.getPlaces())
		{
			cks[i]= new JCheckBox(p.getName());
			cks[i].addActionListener(event->{
				canvas.clearCanvas();
				end = null;
				drawSE();
				visitMode = false;
				for(int j = 0 ;j<cks.length ; j++)
				{
					if(cks[j].isSelected())
					{
						WayPoint point = stuPlaceManager.getPlaces().get(j);
						canvas.drawColorString(point.getName(), point.x, point.y, Color.RED);
						visitMode = true;
					}
				}
				if(visitMode)
				{
					endCom.setSelectedIndex(0);
					endCom.setEnabled(false);
				}else{
					endCom.setEnabled(true);
				}
			});
			watchPointPanel.add(cks[i],con1);
			i++;
		}
		con1.weighty = 0.9;
		watchPointPanel.add(new JLabel(""), con1);
		
		
		this.setSize(1500, 1000);
		//btnPanel
		JPanel btnPanel = new JPanel();
		findBtn = new JButton("寻路");
		findBtn.addActionListener(e->{
			FindWayAction();
		});
		btnPanel.add(findBtn);
		JButton clearBtn = new JButton("清空");
		clearBtn.addActionListener(e->{
			canvas.clear();
			startCom.setSelectedIndex(0);
			endCom.setSelectedIndex(0);
			start = end = null;
			for(int j = 0; j<cks.length; j++)
			{
				cks[j].setSelected(false);
			}
		});
		btnPanel.add(clearBtn);
		JButton showPathBtn = new JButton("显示路径模型");
		showPathBtn.addActionListener(e->{
			canvas.drawAvailRoute(stuWpManager.getWayPointList(), stuWpManager.getAvaliable(), Color.cyan,3);
		});
		btnPanel.add(showPathBtn);
		
		//rightPanel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridBagLayout());
		GridBagConstraints con ;
		
		
		con = new GridBagConstraints();
		con.fill = GridBagConstraints.HORIZONTAL;
//		con.anchor = GridBagConstraints.NORTHEAST;
		con.insets.top = 10;
		con.insets.left = 5;
		con.insets.right = 5;
		con.weighty = 0.01;
		
		con.weightx = 0.1;
		rightPanel.add(new JLabel("起点:"),con);
		
		con.weightx = 0.8;
		con.gridwidth = GridBagConstraints.REMAINDER;
		rightPanel.add(startCom = new JComboBox<>(),con);
		
		con.gridwidth = 1;
		con.weightx = 0.1;
		rightPanel.add(new JLabel("终点:"),con);
		
		con.weightx = 0.8;
		con.gridwidth = GridBagConstraints.REMAINDER;
		rightPanel.add(endCom = new JComboBox<>(),con);
		
		con.gridwidth = 1;
		con.weightx = 0.1;
		rightPanel.add(new JLabel("点击选择参观点:"),con);
		
		con.weightx = 0.8;
		con.gridwidth = GridBagConstraints.REMAINDER;
		
		JCheckBox grey = new JCheckBox("使用灰图");
		
		grey.addActionListener(e->{
			if(grey.isSelected())
			{
				try {
					canvas.useGrey();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else{
				try {
					canvas.useColor();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			startCom.setSelectedIndex(0);
			endCom.setSelectedIndex(0);
			start = end = null;
			for(int j = 0; j<cks.length; j++)
			{
				cks[j].setSelected(false);
			}
			
		});
		rightPanel.add(grey,con);
		
		con.weighty = 1.0;
		con.fill = GridBagConstraints.BOTH;
		rightPanel.add(new JScrollPane(watchPointPanel),con);
		
		con.weighty = 0.0;
		rightPanel.add(btnPanel,con);
		
		
		//注意,如果将canvas放在panel上面,图片就会显示不出来
		//splitPanel
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
				canvas, 
				rightPanel
				);
		split.setDividerLocation(1200);
		
		startCom.addItemListener(e->{
			if(canvas.getGraphics()==null)
				return;
			int index = startCom.getSelectedIndex();
			if (index==0) {
				return;
			}
			canvas.clearCanvas();
			if(startIndex!=-1)
			{	
				cks[startIndex].setSelected(false);
				cks[startIndex].setEnabled(true);
			}
			startIndex = index-1;
			start = stuPlaceManager.getPlaces().get(index-1);
			drawSE();
			drawWatch();
			if (start != null && end!=null) {
				FindS2E();
			}
		});
		endCom.addItemListener(e->{
			if(canvas.getGraphics()==null)
				return;
			int index = endCom.getSelectedIndex();
			if (index==0) {
				return;
			}
			canvas.clearCanvas();
			if(endIndex!=-1)
				cks[endIndex].setEnabled(true);
			endIndex = index - 1;
			end = stuPlaceManager.getPlaces().get(index-1);
			drawSE();
			drawWatch();
			if (start != null && end!=null) {
				FindS2E();
			}
		});
		
		this.setTitle("参观模式");
		this.setLayout(new BorderLayout());
		this.add(split,"Center");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void FindS2E(){
		if(start != null && end != null)
		{
			algor = new WayPointManager();
			try {
				algor.readRouteModel(Configuration.WAYPONINTMODELPATH);
			} catch (ClassNotFoundException | IOException e) {
				JOptionPane.showMessageDialog(this, "模型文件设置错误");
			}
			ArrayList<Integer> path = algor.calculateRouteP2P(start, end);
			if(path==null)
			{
				canvas.drawLineColorSize(start, end,Color.BLUE,3);
				return;
			}
			int nearIndex = algor.findNearPointIndex(start);
			canvas.drawLineColorSize(start, stuWpManager.getWayPointList().get(nearIndex),Color.BLUE,3);
			canvas.drawResultLine(stuWpManager.getWayPointList(), path);
			nearIndex = algor.findNearPointIndex(end);
			canvas.drawLineColorSize(end, stuWpManager.getWayPointList().get(nearIndex),Color.BLUE,3);
		}else{
			JOptionPane.showMessageDialog(this, "请选择起终点");
		}
	}
	
	private void FindWayAction(){
		
		watch.clear();
		for(int i = 0; i<cks.length ;  i++)
		{
			if(cks[i].isSelected())
			{
				watch.add(stuPlaceManager.getPlaces().get(i));
			}
		}

		
		if(watch.size()==0)
		{
			FindS2E();
		}else{
			if(start == null)
			{
				JOptionPane.showMessageDialog(this, "选择起点");
				return;
			}
			
			algor = new WayPointManager();
			try {
				algor.readRouteModel(Configuration.WAYPONINTMODELPATH);
			} catch (ClassNotFoundException | IOException e) {
				JOptionPane.showMessageDialog(this, "模型文件设置错误");
			}
			PathResult rlst = algor.calcculateRouteWith(start,watch);
			int len = rlst.paths.length;
			float lineSize = len*5.0f;
			int i = 0;
			Place startPlace = rlst.places.get(rlst.order[0]);
			Place endPlace = rlst.places.get(rlst.order[1]);
			for(ArrayList<Integer> path:rlst.paths)
			{
				canvas.drawLineColorSize(
						startPlace,
						startPlace.getLinkPoint(), 
						colors[i], lineSize);
				
				canvas.drawLineColorSize(
						endPlace,
						endPlace.getLinkPoint(), 
						colors[i], lineSize);
				
				canvas.drawResultLine(
						stuWpManager.getWayPointList(), 
						path,
						colors[i], 
						lineSize);
				
				
//				canvas.drawColorString(
//						""+i,
//						place.x+10*len, place.y+10*len, 
//						colors[i],30);
				i+=1;
				i%=colors.length;
				lineSize-=5.0f;
				
				startPlace = endPlace;
				if(i+1<rlst.paths.length)
					endPlace = rlst.places.get(rlst.order[i+1]);
				else 
					endPlace = rlst.places.get(rlst.order[0]);
			}
//			Place place = rlst.places.get(i-1);
//			canvas.drawLineColorSize(
//					place,
//					place.getLinkPoint(), 
//					colors[i], lineSize);
		}
	}
	
	private void drawSE(){
		
		if(start!=null)
		{
			canvas.drawColorString("S"+start.getName(), start.x, start.y, Color.RED);
			cks[startIndex].setEnabled(false);
		}
		
		if (end != null) 
		{
			cks[endIndex].setEnabled(false);
			canvas.drawColorString("E"+end.getName(), end.x, end.y, Color.RED);
		}
	
	}
	
	private void drawWatch(){
		for(int j = 0 ;j<cks.length ; j++)
		{
			if(cks[j].isSelected())
			{
				WayPoint point = stuPlaceManager.getPlaces().get(j);
				canvas.drawColorString(point.getName(), point.x, point.y, Color.RED);
				visitMode = true;
			}
		}
	}
	
 	public static void main(String[] args) {
		new VisitModeFrame().setVisible(true);
	}


}
