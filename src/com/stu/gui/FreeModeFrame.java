package com.stu.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.stu.database.Configuration;
import com.stu.database.ObjectTXTManager;
import com.stu.graph.Place;
import com.stu.graph.StuWayPointManager;
import com.stu.graph.WayPoint;
import com.stu.graph.WayPointManager;
import com.stu.graph.WayPointManager.PathResult;

@SuppressWarnings("serial")
public class FreeModeFrame extends JFrame {

	private MapDisplayCanvas canvas;
	private JButton findBtn;
	private ObjectTXTManager objManager;
	private StuWayPointManager stuWpManager;
	private WayPointManager algor;
	private ArrayList <WayPoint>watch;
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
	public FreeModeFrame() {
		try {
			init();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
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
		watch = new ArrayList<>();
		
		JCheckBox grey = new JCheckBox("使用灰图");
		
		grey.addActionListener(e->{
			if(grey.isSelected())
			{
				try {
					canvas.useGrey();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}else{
				try {
					canvas.useColor();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			watch.clear();
			
		});
				
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				WayPoint p = new WayPoint(e.getX(), e.getY());
				canvas.drawColorString("@"+watch.size(), p.x, p.y,Color.red,30);
				watch.add(p);
			}
		});
		
		this.setSize(1500, 1000);
		//btnPanel
		JPanel btnPanel = new JPanel();
		
		
		btnPanel.add(grey);
		
		
		findBtn = new JButton("寻路");
		findBtn.addActionListener(e->{
			FindWayAction();
		});
		btnPanel.add(findBtn);
		JButton clearBtn = new JButton("清空");
		clearBtn.addActionListener(e->{
			canvas.clearCanvas();
			watch.clear();
		});
		btnPanel.add(clearBtn);
		JButton showPathBtn = new JButton("显示路径模型");
		showPathBtn.addActionListener(e->{
			canvas.drawAvailRoute(stuWpManager.getWayPointList(), stuWpManager.getAvaliable(), Color.cyan,3);
		});
		btnPanel.add(showPathBtn);
		
		this.setTitle("自由模式");
		this.setLayout(new BorderLayout());
		this.add(canvas,"Center");
		this.add(btnPanel, "South");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void FindWayP2P(){
		if(watch.size()==2)
		{
			algor = new WayPointManager();
			try {
				algor.readRouteModel(Configuration.WAYPONINTMODELPATH);
			} catch (ClassNotFoundException | IOException e) {
				JOptionPane.showMessageDialog(this, "模型文件设置错误");
			}
			ArrayList<Integer> path = algor.calculateRouteP2P(watch.get(0), watch.get(1));
			if(path==null)
			{
				canvas.drawLineColorSize(watch.get(0), watch.get(1),Color.BLUE,3);
				return;
			}
			int nearIndex = algor.findNearPointIndex(watch.get(0));
			canvas.drawLineColorSize(watch.get(0), stuWpManager.getWayPointList().get(nearIndex),Color.BLUE,3);
			canvas.drawResultLine(stuWpManager.getWayPointList(), path);
			nearIndex = algor.findNearPointIndex(watch.get(1));
			canvas.drawLineColorSize(watch.get(1), stuWpManager.getWayPointList().get(nearIndex),Color.BLUE,3);
		}
	}
	
	private void FindWayAction(){
		if(watch.size()<2)
			return;
		if(watch.size()==2)
		{
			FindWayP2P();
		}else{
			algor = new WayPointManager();
			try {
				algor.readRouteModel(Configuration.WAYPONINTMODELPATH);
			} catch (ClassNotFoundException | IOException e) {
				JOptionPane.showMessageDialog(this, "模型文件设置错误");
			}
			WayPoint start = watch.get(0);
			watch.remove(0);
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
				
				i+=1;
				i%=colors.length;
				lineSize-=5.0f;
				
				startPlace = endPlace;
				if(i+1<rlst.order.length)
					endPlace = rlst.places.get(rlst.order[i+1]);
				else 
					endPlace = rlst.places.get(rlst.order[0]);
			}
		}
	}
	
	
 	public static void main(String[] args) {
		new FreeModeFrame().setVisible(true);
	}


}
