package com.stu.graph;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.stu.database.ObjectTXTManager;

/**
 * 
 * @author LinSixin
 * 用户输入的路径点的管理
 * 算法的调用封装在这里面
 */
public class WayPointManager {

	/**终点**/
	private Point endPoint;
	
	/**保存了要经过的点**/
	private ArrayList<Point> watchPoints;
	
	/**起点**/
	private Point startPoint;
	
	/**提供单源最短路径的算法**/
	private FindWayP2P p2p;
	
	/**提供旅行商售货问题的算法**/
	private FindWayPoints p2ps;
	
	/**提供路径模型的管理**/
	private StuWayPointManager stuWpManager;
	
	/**保存的数据读取**/
	private ObjectTXTManager objTxtManager;
	
	
	/**
	 * 只有起点和终点,没有要求参观点
	 * @param startPoint 起点
	 * @param endPoint	终点
	 */
	public WayPointManager(Point startPoint,Point endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
	
	
	/**
	 * 指定了经过起点和终点,要求经过一些参观点
	 * @param startPoint
	 * @param endPoint
	 * @param watchPoints
	 */
	public WayPointManager(Point startPoint,Point endPoint,ArrayList<Point> watchPoints) {
		this(startPoint,endPoint);
		this.watchPoints = watchPoints;
	}
	
	
	/**
	 * 读取路径模型文件,使用相应的路径模型<br/>
	 * @param modelFileName 路径模型  使用 "waypointmodel.data"
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public void readRouteModel(String modelFileName) throws ClassNotFoundException, FileNotFoundException, IOException{
		objTxtManager = new ObjectTXTManager(modelFileName);
		stuWpManager = (StuWayPointManager) objTxtManager.readObject();
	}
	
	/**
	 * 根据路径模型获取两个点之间在路径模型中的最短路径,保存的是路径模型中的点的序号
	 * @param p1
	 * @param p2
	 * @return 返回路径的线性表,如果两个点距离小于100,则返回null
	 */
	public ArrayList<Integer> calculateRouteP2P(Point p1,Point p2){
		if(Point.lengthOf(p1, p2)<100)
		{
			return null;
		}
		int start_near = stuWpManager.findShortestWayPoint(startPoint);
		int end_near = stuWpManager.findShortestWayPoint(endPoint);
		p2p = new FindWayP2P(stuWpManager.getWayPointList(), start_near);
		p2p.findWay();
		return p2p.getPathList(end_near);
	}
	
	/**
	 * 计算起点和终点的最短路径
	 * @return 返回路径的线性表,如果两个点距离小于100,则返回null
	 */
	public ArrayList<Integer> calculateRouteS2E(){
		return calculateRouteP2P(startPoint, endPoint);
	}
	
	
	/**
	 * 
	 * @author Linsixin
	 * 保存多点寻路的结果
	 */
	public class PathResult{
		/**所有的路径点**/
		public ArrayList<? extends Point> model;
		
		/**用户要去的地方**/
		public ArrayList<Place> places;
		
		/**按顺序要去的点在模型中的index 比如: 1 5 6 7 就是字面上的这么走**/
		public int []order;
		
		/**按顺序要去的点之间的路径**/
		public ArrayList<Integer> []paths;
		
		public PathResult(int order[],ArrayList<Integer> paths[],ArrayList<Place> places,ArrayList<? extends Point> model) {
			this.order = order;
			this.paths = paths;
			this.places = places;
			this.model = model;
		}
	}
	
	/**
	 * 计算最短路径,要经过起点,终点和参观点,最后回到起点
	 * @param watchPoint 要经过的参观点
	 * @return 返回的结果封装类
	 */
	public PathResult calcculateRouteWith(ArrayList<? extends Point> watchPoint)
	{
		//获取用户点击的点和相关的邻接点
		ArrayList<Place> placeList = new ArrayList<>();
		ArrayList<WayPoint> model = stuWpManager.getWayPointList();
		
//		System.out.println("model : "+model);
		int start_near = stuWpManager.findShortestWayPoint(startPoint);
		placeList.add(new Place(startPoint, model.get(start_near)));
//		System.out.println("start:"+start_near);
		for(Point p:watchPoint)
		{
			int tempIndex = stuWpManager.findShortestWayPoint(p);
			placeList.add(new Place(p,model.get(tempIndex),tempIndex));
		}
		
		int end_near = stuWpManager.findShortestWayPoint(endPoint);
//		System.out.println("end:"+end_near);
		placeList.add(new Place(endPoint, model.get(end_near),end_near));
		
//		System.out.println(placeList);
		
		//获取相关点中两两之间的距离
		int n = placeList.size();
		int[][] dis = new int[n][n];
		
		@SuppressWarnings("unchecked")
		ArrayList<Integer> [][]path = new ArrayList[n][n];
		
		for(int i = 0; i<n; i++)
		{	
			p2p = new FindWayP2P(stuWpManager.getDis(), 
					placeList.get(i).getModelIndex());
			p2p.findWay();
			for(int j = 0; j<n; j++)
			{
				dis[i][j] = p2p.getRslt()[placeList.get(j).getModelIndex()];		//找到对应终点的值
				path[i][j] = p2p.getPathList(placeList.get(j).getModelIndex());		//保存两点之间最短的路径
//				System.out.print("rslt: ");
//				for(int k:p2p.getRslt())
//					System.out.print(""+k+" ");
//				System.out.println();
//				System.out.println("path from "+placeList.get(i).getModelIndex()+" to "+placeList.get(j).getModelIndex()+": "+path[i][j]+"   ");
			}
//			System.out.println();
		}
		
//		Point.printMatrix(dis);
		
		p2ps = new FindWayPoints(dis);
		p2ps.findWay(1);
		int[] order = p2ps.getShortestOrder();		//这个要反转一下
		int[] revOrder = new int[order.length];
		
		for(int i = 0; i<order.length; i++)
		{
			revOrder[order[i]] = i;
		}
		
		System.out.println("------order: ");
		for(int i = 0; i<order.length; i++)
		{
			System.out.print(""+order[i]+" ");
		}
		System.out.println();
		System.out.println("------revorder: ");
		for(int i = 0; i<order.length; i++)
		{
			System.out.print(""+revOrder[i]+" ");
		}
		
		@SuppressWarnings("unchecked")
		ArrayList<Integer> []pathResult = new ArrayList[revOrder.length];
		int last = revOrder[0];
		
		for(int i = 1; i<revOrder.length; i++)
		{
			pathResult[i-1] = path[last][revOrder[i]];
			last = revOrder[i];
		}
		pathResult[revOrder.length-1] = path[last][0];
		
		return new PathResult(revOrder, pathResult, placeList,stuWpManager.getWayPointList());
	}
	
	

}
