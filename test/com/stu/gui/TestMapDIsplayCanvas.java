package com.stu.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.stu.database.ObjectTXTManager;
import com.stu.graph.BarrierGenerator;
import com.stu.graph.FindWayP2P;
import com.stu.graph.FindWayPoints;
import com.stu.graph.Point;
import com.stu.graph.StuWayPointManager;

@SuppressWarnings({ "serial", "deprecation" })
public class TestMapDIsplayCanvas extends JFrame {
	
	private MapDisplayCanvas canvas ;
	private FindWayPoints findWay ;
	private ObjectTXTManager objManager;
	private FindWayP2P p2p ;
	private Point clickStart,clickEnd;	//点击的起点,终点
	private int start,end;				//路径中的起点,终点
	
	private StuWayPointManager stuWpManager;
	
	@Deprecated
	public TestMapDIsplayCanvas(int mode) {
		
		canvas = new MapDisplayCanvas(1500,1000);
//		canvas = new MapDisplayCanvas();
		objManager = new ObjectTXTManager("savedMap.data");
		char[][] map = ((BarrierGenerator)objManager.readObject()).getMap();
		this.add(new JScrollPane(canvas),"Center");
		this.setSize(canvas.getImwidth(),canvas.getImheight());
		
		
		canvas.setMode(MapDisplayCanvas.MODE_SETSTARTPOINT);
		
		JPanel btnPanel = new JPanel();
		JButton findBtn = new JButton("找路");
		findBtn.addActionListener(e->{
			findWay = new FindWayPoints(canvas.getWay(),map,-10);
			System.out.println(findWay.toString());
			findWay.findWay(1);
			int[] rslt = findWay.getShortestOrder();
			for(int i:rslt)
				System.out.print(i+"  ");
			canvas.drawResult(rslt);
		});
		btnPanel.add(findBtn);
		JButton clearBtn = new JButton("清空");
		clearBtn.addActionListener(e->{
			canvas.clear();
		});
		btnPanel.add(clearBtn);
		this.add(btnPanel, "South");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	public TestMapDIsplayCanvas() {
		init();
		startEndMode();
	}
	
	/**
	 * 初始化,读取路径点模型
	 * 可以复制直接使用
	 */
	public void init(){
		canvas = new MapDisplayCanvas(1500,1000);
		objManager = new ObjectTXTManager("waypointmodel.data");
		stuWpManager = (StuWayPointManager) objManager.readObject();
		
		this.add(canvas,"Center");
		this.setSize(canvas.getImwidth(),canvas.getImheight());
		
		JPanel btnPanel = new JPanel();
		
		
		JButton clearBtn = new JButton("清空");
		clearBtn.addActionListener(e->{
			canvas.clear();
		});
		btnPanel.add(clearBtn);
		
		
		JButton showPathBtn = new JButton("显示路径模型");
		showPathBtn.addActionListener(e->{
			canvas.drawAvailRoute(stuWpManager.getWayPointList(), stuWpManager.getAvaliable());
		});
		btnPanel.add(showPathBtn);
		this.add(btnPanel, "South");
		
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	/**
	 * 开始结束模式,选择起点和终点,然后绘制出最短路径
	 * 这个方法可以Copy过去想要的地方直接使用
	 */
	public void startEndMode(){
		canvas.setMode(MapDisplayCanvas.MODE_SETSTARTPOINT);
		canvas.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				switch(canvas.getMode())
				{
					case MapDisplayCanvas.MODE_SETSTARTPOINT:{
						clickStart = new Point(e.getX(), e.getY());
						canvas.drawBigStirng("S", e.getX(), e.getY());
						canvas.setMode(MapDisplayCanvas.MODE_SETENDPOINT);
						start  = stuWpManager.findShortestWayPoint(new Point(e.getX(),e.getY()));
						System.out.println("start = "+start+" (x,y) = "+e.getX()+"  "+e.getY());
						p2p = new FindWayP2P(stuWpManager.getDis(), start);
						break;
					}
					case MapDisplayCanvas.MODE_SETENDPOINT:{
						clickEnd = new Point(e.getX(), e.getY());
						canvas.drawBigStirng("E", e.getX(), e.getY());
						if(Point.lengthOf(clickStart, clickEnd)<100)
						{
							canvas.drawBigLine(clickStart, clickEnd);
							canvas.setMode(MapDisplayCanvas.MODE_SETSTARTPOINT);
							return;
						}
						
						end = stuWpManager.findShortestWayPoint(new Point(e.getX(),e.getY()));
						p2p.findWay();
						System.out.println("end = "+end);
						
						ArrayList<Integer> path = p2p.getPathList(end);
						canvas.drawBigLine(clickStart, stuWpManager.getWayPointList().get(start));
						canvas.drawBigLine(new Point(e.getX(),e.getY()), stuWpManager.getWayPointList().get(end));
						canvas.drawResultLine(stuWpManager.getWayPointList(), path);
						
						System.out.println("path = "+path.toString());
						canvas.setMode(MapDisplayCanvas.MODE_SETSTARTPOINT);
						break;
					}
					
				}
				
			}
		});
		
	}
	
	
	
	public static void main(String[] args) {
		new TestMapDIsplayCanvas().setVisible(true);
	}
}
