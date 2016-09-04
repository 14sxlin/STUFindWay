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
		JButton findBtn = new JButton("��·");
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
		JButton clearBtn = new JButton("���");
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
	 * ��ʼ��,��ȡ·����ģ��
	 * ���Ը���ֱ��ʹ��
	 */
	public void init(){
		canvas = new MapDisplayCanvas(1500,1000);
		objManager = new ObjectTXTManager("waypointmodel.data");
		stuWpManager = (StuWayPointManager) objManager.readObject();
		
		this.add(canvas,"Center");
		this.setSize(canvas.getImwidth(),canvas.getImheight());
		
		JPanel btnPanel = new JPanel();
		JButton findBtn = new JButton("��·");
		findBtn.addActionListener(e->{
		});
		btnPanel.add(findBtn);
		JButton clearBtn = new JButton("���");
		clearBtn.addActionListener(e->{
			canvas.clear();
		});
		btnPanel.add(clearBtn);
		this.add(btnPanel, "South");
	}
	
	
	/**
	 * ��ʼ����ģʽ,ѡ�������յ�,Ȼ����Ƴ����·��
	 * �����������Copy��ȥ��Ҫ�ĵط�ֱ��ʹ��
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
						canvas.drawBigStirng("S", e.getX(), e.getY());
						canvas.setMode(MapDisplayCanvas.MODE_SETENDPOINT);
						int start  = stuWpManager.findShortestWayPoint(new Point(e.getX(),e.getY()));
						System.out.println("start = "+start+" (x,y) = "+e.getX()+"  "+e.getY());
						canvas.drawBigLine(new Point(e.getX(),e.getY()), stuWpManager.getWayPointList().get(start));
						p2p = new FindWayP2P(stuWpManager.getDis(), start);
						break;
					}
					case MapDisplayCanvas.MODE_SETENDPOINT:{
						canvas.drawBigStirng("E", e.getX(), e.getY());
						int end = stuWpManager.findShortestWayPoint(new Point(e.getX(),e.getY()));
						p2p.findWay();
						System.out.println("end = "+end);
						ArrayList<Integer> path = p2p.getPathList(end);
						canvas.drawBigLine(new Point(e.getX(),e.getY()), stuWpManager.getWayPointList().get(end));
						System.out.println("path = "+path.toString());
						canvas.drawResultLine(stuWpManager.getWayPointList(), path);
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
