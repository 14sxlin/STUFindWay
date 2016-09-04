package com.stu.gui;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.stu.graph.Point;
import com.stu.graph.WayPoint;

@SuppressWarnings("serial")
public class MapDisplayCanvas extends Canvas {
	public static final String STUJPG_PATH = "/pic/stu.png";
//	public static final String STUJPG_PATH = "/pic/stu.jpg";
	public static final int MODE_NOTHING = 0; 
	public static final int MODE_ADDPOINTS = 1; 
	public static final int MODE_SETSTARTPOINT = 2; 
	public static final int MODE_SETENDPOINT = -1; 
	private int mode = MODE_SETSTARTPOINT;
	private Image image;
	private int imwidth,imheight;
	private Graphics2D g;
	private ArrayList<? extends Point> points;	//��ʼ����ʱ��Ҫ����canvas�ĵ�
	private ArrayList<Point> way; 		// �����û�����ĵ�
	protected MouseListener clickListener;
	
	/**
	 * ����ͼƬ
	 * @param pathInJar ���ð���ͼƬ��λ��
	 */
	public MapDisplayCanvas(String pathInJar) {
		try {
			way = new ArrayList<>();
			image = ImageIO.read(getClass().getResourceAsStream(pathInJar));
			imwidth = image.getWidth(this);
			imheight = image.getHeight(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ����ͼƬ����������
	 * @param pathInJar ���ð���ͼƬ��λ��
	 * @param width		������ͼƬ�Ŀ�
	 * @param height	������ͼƬ�ĸ�
	 */
	public MapDisplayCanvas(String pathInJar,int width,int height) {
		try {
			way = new ArrayList<>();
			image = ImageIO.read(getClass().getResourceAsStream(pathInJar))
					.getScaledInstance(width, height, Image.SCALE_DEFAULT);
			imwidth = width;
			imheight = height;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Ĭ������STUJPG_PATH�ֶ�ָ����ͼƬ
	 */
	public MapDisplayCanvas() {
		this(STUJPG_PATH);
	}

	/**
	 * ָ��ͼƬ����֮��Ĵ�С
	 * @param width ������ͼƬ�Ŀ�
	 * @param height ������ͼƬ�ĸ�
	 */
	public MapDisplayCanvas(int width,int height) {
		this(STUJPG_PATH,width,height);
	}
	
	/**
	 * ��ָ��ͼ���ϻ��� !
	 * @param g   ָ����ͼ��
	 * @param points ���� ! ��λ��
	 */
	private void drawPoints(Graphics g , ArrayList<? extends Point> points){
		if(points==null) return;
		for(Point p : points)
		{
			g.setFont(new Font("Arial", Font.BOLD,15));
			try{
				g.drawString("@"+((WayPoint)p).getName(), p.x, p.y);
			}catch(Exception e)
			{
				g.drawString("!", p.x, p.y);
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		this.g = (Graphics2D)g;
		this.g.drawImage(image, 0, 0,this);
		this.g.setColor(Color.red);
		this.g.setStroke(new BasicStroke(2f));
		drawPoints(this.g, (ArrayList<WayPoint>) points);
		
	}
	
	/**
	 * �ڵ�ͼ�ϱ�ע !��� ��ʾӦ��ȥ��·��,�㼯���û��ոյ���ĵ�
	 * @param shortestOrder ��ʾ˳������,���е�ֵ����·����ȥ��˳��
	 */
	public void drawResult(int shortestOrder[]){
		for(int i = 1; i<way.size() ; i++ )
			drawBigStirng("!"+shortestOrder[i], way.get(i).x, way.get(i).y);
	}
	
	/**
	 * �ڵ�ͼ�ϱ�ע·��,�����֮����ֱ������,��ɫ
	 * @param point ·�����ģ��
	 * @param shortestOrder �����ĵ�
	 */
	public void drawResultLine(ArrayList<WayPoint> points,ArrayList<Integer> shortestOrder){
		Graphics2D g = (Graphics2D) getGraphics();
		g.setColor(Color.blue);
		g.setStroke(new BasicStroke(3));
		Point lastPoint = points.get(shortestOrder.get(0));
		
		
		for(int i:shortestOrder)
		{
			Point temp = points.get(i);
			g.drawLine(lastPoint.x, lastPoint.y, temp.x, temp.y);
			lastPoint = temp;
		}
	}
	
	/**
	 * ����
	 * @param p1
	 * @param p2
	 */
	public void drawBigLine(Point p1,Point p2){
		Graphics2D g = (Graphics2D) getGraphics();
		g.setColor(Color.blue);
		g.setStroke(new BasicStroke(3));
		g.drawLine(p1.x, p1.y, p2.x, p2.y);
	}
	
	/**
	 * ���õ�ͼ,��ձ����·����
	 */
	public void clear(){
		if(points!=null)
			points.clear();
		if(way!= null)
			way.clear();
		repaint();
		setMode(MapDisplayCanvas.MODE_SETSTARTPOINT);
	}
	
	@SuppressWarnings("unused")
	@Deprecated
	private void clickAndDraw(int x,int y){
		if(mode==MODE_SETSTARTPOINT){
			drawBigStirng("S", x, y);
			setMode(MODE_ADDPOINTS);
		}
		else if (mode==MODE_ADDPOINTS){
			drawBigStirng("!", x, y);
		}
		way.add(new Point(x, y));
	}
	
	
	/**
	 * �ڵ�ͼ�ϵ�(x,y) ��עstr,�����С15,��ɫ
	 * @param str ��ʾ���ַ�
	 * @param x  x����
	 * @param y  y����
	 */
	protected void drawBigStirng(String str,int x,int y){
		Graphics2D g = ((Graphics2D)getGraphics());
		g.setColor(Color.red);
		g.setStroke(new BasicStroke(2f));
		g.setFont(new Font("Arial", Font.BOLD,15));
		g.drawString(str, x, y);
	}
	
	
	/**
	 * �ڵ�ͼ�ϵ�(x,y) ��עstr,����Ĭ�ϴ�С,��ɫ
	 * @param str ��ʾ���ַ�
	 * @param x  x����
	 * @param y  y����
	 */
	protected void drawRedStirng(String str,int x,int y){
		Graphics2D g = ((Graphics2D)getGraphics());
		g.setColor(Color.red);
		g.setStroke(new BasicStroke(2f));
		g.drawString(str, x, y);
	}

	
	
	/*
	 * ������setter getter
	 */
	public Image getImage(){
		return image;
	}

	public int getImwidth() {
		return imwidth;
	}


	public int getImheight() {
		return imheight;
	}


	public ArrayList<? extends Point> getPoints() {
		return points;
	}
	public void setPoints(ArrayList<? extends Point > points) {
		this.points = points;
	}
	
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}


	public ArrayList<Point> getWay() {
		return way;
	}


	public void setWay(ArrayList<Point> way) {
		this.way = way;
	}

	
}
