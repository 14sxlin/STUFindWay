package com.stu.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.stu.database.ObjectTXTManager;
import com.stu.graph.BarrierGenerator;
import com.stu.graph.FindWayPoints;

@SuppressWarnings("serial")
public class TestMapDIsplayCanvas extends JFrame {
	
	private MapDisplayCanvas canvas ;
	private FindWayPoints findWay ;
	private ObjectTXTManager objManager;
	private char[][] map ;
	public TestMapDIsplayCanvas() {
		
		canvas = new MapDisplayCanvas(1500,1000);
//		canvas = new MapDisplayCanvas();
		objManager = new ObjectTXTManager("savedMap.data");
		map = ((BarrierGenerator)objManager.readObject()).getMap();
		this.add(new JScrollPane(canvas),"Center");
		this.setSize(canvas.getImwidth(),canvas.getImheight());
		
//		ArrayList<Point> points = new ArrayList<>();
//		for(int i = 0; i<300; i+=10)
//			for(int j = 10 ; j<500; j+=20)
//			{
//				points.add(new Point(i,j));
//			}
//		canvas.setPoints(points);
		
//		canvas.setMode(MapDisplayCanvas.MODE_ADDPOINTS);
		
		canvas.setMode(MapDisplayCanvas.MODE_SETSTARTPOINT);
		
		JPanel btnPanel = new JPanel();
		JButton findBtn = new JButton("ÕÒÂ·");
		findBtn.addActionListener(e->{
			findWay = new FindWayPoints(canvas.getWay(),map,-10);
//			findWay = new FindWayPoints(canvas.getWay());
			System.out.println(findWay.toString());
			findWay.findWay(1);
			int[] rslt = findWay.getShortestOrder();
			for(int i:rslt)
				System.out.print(i+"  ");
			canvas.drawResult(rslt);
		});
		btnPanel.add(findBtn);
		JButton clearBtn = new JButton("Çå¿Õ");
		clearBtn.addActionListener(e->{
			canvas.clear();
		});
		btnPanel.add(clearBtn);
		this.add(btnPanel, "South");
	}
	
	public static void main(String[] args) {
		new TestMapDIsplayCanvas().setVisible(true);
	}
}
