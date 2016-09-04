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

@SuppressWarnings("serial")
public class MapDisplayCanvas extends Canvas {
	public static final String STUJPG_PATH = "/pic/stu.png";
//	public static final String STUJPG_PATH = "/pic/stu.jpg";
	public static final int MODE_NOTHING = 0; 
	public static final int MODE_ADDPOINTS = 1; 
	public static final int MODE_SETSTARTPOINT = 2; 
	private int scale = 10;
	private int mode = 0;
	private Image image;
	private int imwidth,imheight;
	private ArrayList<Point> points;
	private Graphics2D g;
	private ArrayList<Point> way;
	protected MouseListener clickListener;
	
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

	public MapDisplayCanvas() {
		this(STUJPG_PATH);
	}

	public MapDisplayCanvas(int width,int height) {
		this(STUJPG_PATH,width,height);
	}
	
	private void drawPoints(Graphics g , ArrayList<Point> points){
		if(points==null) return;
		for(Point p : points)
		{
			g.setFont(new Font("Arial", Font.BOLD,30));
			g.drawString("!", p.x, p.y);
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		this.g = (Graphics2D)g;
		this.g.drawImage(image, 0, 0,this);
		this.g.setColor(Color.red);
		this.g.setStroke(new BasicStroke(2f));
		drawPoints(this.g, points);
		
	}
	
	public void drawResult(int shortestOrder[]){
		for(int i = 1; i<way.size() ; i++ )
			drawBigStirng("!"+shortestOrder[i], way.get(i).x, way.get(i).y);
	}
	
	public void clear(){
		if(points!=null)
			points.clear();
		if(way!= null)
			way.clear();
		repaint();
		setMode(MapDisplayCanvas.MODE_SETSTARTPOINT);
	}
	
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
	
	protected void drawBigStirng(String str,int x,int y){
		Graphics2D g = ((Graphics2D)getGraphics());
		g.setColor(Color.red);
		g.setStroke(new BasicStroke(2f));
		g.setFont(new Font("Arial", Font.BOLD,15));
		g.drawString(str, x, y);
	}
	protected void drawRedStirng(String str,int x,int y){
		Graphics2D g = ((Graphics2D)getGraphics());
		g.setColor(Color.red);
		g.setStroke(new BasicStroke(2f));
		g.drawString(str, x, y);
	}

	/*
	 * ÏÂÃæÊÇsetter getter
	 */
	public Image getImage(){
		return image;
	}

	public int getImwidth() {
		return imwidth;
	}
	public int getImwidthScaled() {
		return imwidth/scale;
	}

	public void setImwidth(int imwidth) {
		this.imwidth = imwidth;
	}

	public int getImheight() {
		return imheight;
	}
	public int getImheightScaled() {
		return imheight/scale;
	}

	public void setImheight(int imheight) {
		this.imheight = imheight;
	}

	public ArrayList<Point> getPoints() {
		return points;
	}
	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}
	
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}


	public Graphics2D getG() {
		return g;
	}



	public void setG(Graphics2D g) {
		this.g = g;
	}



	
	public ArrayList<Point> getWay() {
		return way;
	}



	public void setWay(ArrayList<Point> way) {
		this.way = way;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}
	
	
}
