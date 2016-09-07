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

	public static final int MODE_NOTHING = 0; 
	public static final int MODE_ADDPOINTS = 1; 
	public static final int MODE_SETSTARTPOINT = 2; 
	public static final int MODE_SETENDPOINT = -1; 
	private int mode = MODE_SETSTARTPOINT;
	private Image image;
	private int imwidth,imheight;
	private Graphics originG;
	private Graphics2D g;
	private ArrayList<? extends Point> points;	//初始化的时候要画如canvas的点
	private ArrayList<Point> way; 				// 保存用户点击的点
	protected MouseListener clickListener;
	
	/**
	 * 载入图片
	 * @param pathInJar 内置包中图片的位置
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
	 * 载入图片并进行缩放
	 * @param pathInJar 内置包中图片的位置
	 * @param width		放缩后图片的宽
	 * @param height	放缩后图片的高
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
	 * 默认载入STUJPG_PATH字段指定的图片
	 */
	public MapDisplayCanvas() {
		this(STUJPG_PATH);
	}

	/**
	 * 指定图片放缩之后的大小
	 * @param width 放缩后图片的宽
	 * @param height 放缩后图片的高
	 */
	public MapDisplayCanvas(int width,int height) {
		this(STUJPG_PATH,width,height);
	}
	
	/**
	 * 在指定图像上画出 !
	 * @param g   指定的图像
	 * @param points 画出 ! 的位置
	 */
	private void drawPoints(Graphics g , ArrayList<? extends Point> points){
		if(points==null) return;
		for(Point p : points)
		{
			g.setFont(new Font("微软雅黑", Font.BOLD,15));
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
	 * 在地图上标注 !序号 表示应该去的路径,点集是用户刚刚点击的点
	 * @param shortestOrder 表示顺序数组,其中的值代表路径点去的顺序
	 */
	public void drawResult(int shortestOrder[]){
		for(int i = 1; i<way.size() ; i++ )
			drawBigStirng("!"+shortestOrder[i], way.get(i).x, way.get(i).y);
	}
	
	/**
	 * 在地图上标注路径,点与点之间用直线连接,蓝色
	 * @param point 路径点的模型
	 * @param shortestOrder 经过的点
	 */
	public void drawResultLine(ArrayList<WayPoint> points,ArrayList<Integer> shortestOrder){
		drawResultLine(points, shortestOrder, Color.blue, 3);
	}
	
	
	/**
	 * 在地图上标注路径
	 * @param point 路径点的模型
	 * @param shortestOrder 经过的点
	 * @param color
	 * @param lineSize
	 */
	public void drawResultLine(ArrayList<WayPoint> points,ArrayList<Integer> shortestOrder,Color color,float lineSize){
		Graphics2D g = (Graphics2D) getGraphics();
		g.setColor(color);
		g.setStroke(new BasicStroke(lineSize));
		Point lastPoint = points.get(shortestOrder.get(0));
		
		
		for(int i:shortestOrder)
		{
			Point temp = points.get(i);
			g.drawLine(lastPoint.x, lastPoint.y, temp.x, temp.y);
			lastPoint = temp;
		}
	}
	
	
	/**
	 * 画线 粗细为3 刚刚好
	 * 颜色是蓝色
	 * @param p1
	 * @param p2
	 */
	public void drawLineBlue_3(Point p1,Point p2){
		drawLineColorSize(p1, p2, Color.blue, 3);
	}
	
	/**
	 * 
	 * @param p1
	 * @param p2
	 * @param color 颜色
	 * @param lineSize 线的粗细
	 */
	public void drawLineColorSize(Point p1,Point p2,Color color,float lineSize){
		Graphics2D g = (Graphics2D) getGraphics();
		g.setColor(color);
		g.setStroke(new BasicStroke(lineSize));
		g.drawLine(p1.x, p1.y, p2.x, p2.y);
	}
	
	/**
	 * 
	 * @param points
	 * @param available
	 * @param color 颜色
	 * @param lineSize 线的粗细
	 * @return
	 */
	public boolean drawAvailRoute(ArrayList<WayPoint> points,int[][] available,Color color,float lineSize){
		if(points==null||points.size()==0)
			return false;
		for(int i = 0; i<available.length; i++)
			for(int j = i; j<available.length; j++)
			{
				if(available[i][j]==1)
				{
					drawLineColorSize(points.get(i), points.get(j), color, lineSize);
				}
			}
		return true;
	}
	
	
	/**
	 * 画出路线模型
	 * @param points 所有路径点
	 * @param available 可到达数组,1表示可以直线到达,0表示不可以直线到达
	 */
	public boolean drawAvailRoute(ArrayList<WayPoint> points,int[][] available){
		if(points==null||points.size()==0)
			return false;
		for(int i = 0; i<available.length; i++)
			for(int j = i; j<available.length; j++)
			{
				if(available[i][j]==1)
				{
					drawLineBlue_3(points.get(i), points.get(j));
				}
			}
		return true;
	}
	
	/**
	 * 重置地图,清空保存的路径点
	 * 保存的点是引用,所以清空的话也会清空外部的点,注意
	 * 
	 * 重置模式为起点模式
	 */
	public void clear(){
		if(points!=null)
			points.clear();
		if(way!= null)
			way.clear();
		repaint();
		setMode(MapDisplayCanvas.MODE_SETSTARTPOINT);
	}
	
	
	/**
	 * 清空画布但是不清空保存的点
	 */
	public void clearCanvas(){
		
		getGraphics().drawImage(image, 0, 0, this);
		
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
	 * 在地图上的(x,y) 标注str,字体大小15,红色
	 * @param str 显示的字符
	 * @param x  x坐标
	 * @param y  y坐标
	 */
	protected void drawBigStirng(String str,int x,int y){
		Graphics2D g = ((Graphics2D)getGraphics());
		g.setColor(Color.red);
		g.setStroke(new BasicStroke(2f));
		g.setFont(new Font("微软雅黑", Font.BOLD,15));
		g.drawString(str, x, y);
	}
	
	
	/**
	 * 在地图上的(x,y) 标注str,字体默认大小,红色
	 * @param str 显示的字符
	 * @param x  x坐标
	 * @param y  y坐标
	 */
	protected void drawRedStirng(String str,int x,int y){
		Graphics2D g = ((Graphics2D)getGraphics());
		g.setColor(Color.red);
		g.setStroke(new BasicStroke(2f));
		g.drawString(str, x, y);
	}
	
	
	protected void drawColorString(String str,int x,int y,Color color)
	{
		Graphics2D g = ((Graphics2D)getGraphics());
		g.setColor(color);
		g.setStroke(new BasicStroke(2f));
		g.setFont(new Font("微软雅黑", Font.BOLD,15));
		g.drawString(str, x, y);
	}
	
	protected void drawColorString(String str,int x,int y,Color color,int fontSize)
	{
		Graphics2D g = ((Graphics2D)getGraphics());
		g.setColor(color);
		g.setStroke(new BasicStroke(2f));
		g.setFont(new Font("微软雅黑", Font.BOLD,fontSize));
		g.drawString(str, x, y);
	}
	
	
	/*
	 * 下面是setter getter
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
		this.points = new ArrayList<>(points);
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
