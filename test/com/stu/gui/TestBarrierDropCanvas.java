package com.stu.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.stu.database.ObjectTXTManager;

@SuppressWarnings("serial")
public class TestBarrierDropCanvas extends JFrame {

	private BarrierDropCanvas canvas ;
	private ObjectTXTManager manager ;
	public TestBarrierDropCanvas() {
		
		canvas = new BarrierDropCanvas();
		manager = new ObjectTXTManager("test.data");
		this.add(canvas,"Center");
		this.setSize(canvas.getImwidth(),canvas.getImheight());
		
		canvas.setMode(MapDisplayCanvas.MODE_SETSTARTPOINT);
		
		JButton finishBtn = new JButton("完成");
		finishBtn.addActionListener(e->{
			System.out.println(canvas.getBarrierGenerator().toString());
		});
		JButton writeBtn = new JButton("保存到文件");
		writeBtn.addActionListener(e->{
			manager.writeObject(canvas.getBarrierGenerator());
		});
		JPanel btnPanel = new JPanel();
		btnPanel.add(finishBtn);
		btnPanel.add(writeBtn);
		this.add(btnPanel,"South");
	}
	
	public static void main(String[] args) {
		new TestBarrierDropCanvas().setVisible(true);
	}
}
