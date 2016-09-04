package com.stu.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.stu.graph.BarrierGenerator;

@SuppressWarnings("serial")
@Deprecated
public class BarrierDropCanvas extends MapDisplayCanvas {
	public static final int MODE_DROPBARRIER = 3;
	
	private BarrierGenerator barrierGenerator;
	private MouseListener ba_clickListener;
	private int minX = getImwidth()+1,
				minY = getImheight()+1,
				maxX = 0,
				maxY = 0;
	
	public BarrierDropCanvas(String pathInJar) {
		super(pathInJar);
		barrierGenerator = new BarrierGenerator(-10, -10);
		this.removeMouseListener(clickListener);
		this.addMouseListener(ba_clickListener = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("x  = "+e.getX()+" y = "+e.getY());
				getGraphics().drawString("*", e.getX(), e.getY());
				calBarrierRange(e.getX(), e.getY());
				drawBarrier();
				for(int i = minX; i<=maxX; i++)
					for(int j = minY; j<=maxY; j++)
						barrierGenerator.dropBarrier(i/10, j/10);
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("x  = "+e.getX()+" y = "+e.getY());
				getGraphics().drawString("*", e.getX(), e.getY());
				initBarrierRange();
				calBarrierRange(e.getX(), e.getY());
				
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
				//TODO
			}
		});
	}

	public BarrierDropCanvas() {
		this(STUJPG_PATH);
		
	}
	
	private void initBarrierRange(){
		minX = getImwidth()+1;
		minY = getImheight()+1;
		maxX = 0;
		maxY = 0;
	}
	private void calBarrierRange(int x,int y){
		minX = x<minX?x:minX;
		minY = y<minY?y:minY;
		maxX = x>maxX?x:maxX;
		maxY = y>maxY?y:maxY;
		System.out.println(""+minX+" "+maxX+"  "+minY+"  "+maxY);
	}
	
	public void drawBarrier(){
		for(int i = minX; i<=maxX; i++)
			for(int j = minY; j<=maxY; j++)
			{
				getGraphics().drawString("*", i, j);
			}
	}

	
	/*
	 * 下面是getter 和 setter 方法
	 */
	public BarrierGenerator getBarrierGenerator() {
		return barrierGenerator;
	}

	public void setBarrierGenerator(BarrierGenerator barrierGenerator) {
		this.barrierGenerator = barrierGenerator;
	}

	public MouseListener getBa_clickListener() {
		return ba_clickListener;
	}

	public void setBa_clickListener(MouseListener ba_clickListener) {
		this.ba_clickListener = ba_clickListener;
	}

	public int getMinX() {
		return minX;
	}

	public void setMinX(int minX) {
		this.minX = minX;
	}

	public int getMinY() {
		return minY;
	}

	public void setMinY(int minY) {
		this.minY = minY;
	}

	public int getMaxX() {
		return maxX;
	}

	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}
	
	

}
