package com.stu.graph;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.stu.database.ObjectTXTManager;

/**
 * 
 * @author LinSixin
 * �û������·����Ĺ���
 * �㷨�ĵ��÷�װ��������
 */
public class WayPointManager {

	/**�յ�**/
	private Point endPoint;
	
	/**������Ҫ�����ĵ�**/
	private ArrayList<Point> watchPoints;
	
	/**���**/
	private Point startPoint;
	
	/**�ṩ��Դ���·�����㷨**/
	private FindWayP2P p2p;
	
	/**�ṩ�������ۻ�������㷨**/
	private FindWayPoints p2ps;
	
	/**�ṩ·��ģ�͵Ĺ���**/
	private StuWayPointManager stuWpManager;
	
	/**��������ݶ�ȡ**/
	private ObjectTXTManager objTxtManager;
	
	
	/**
	 * ֻ�������յ�,û��Ҫ��ι۵�
	 * @param startPoint ���
	 * @param endPoint	�յ�
	 */
	public WayPointManager(Point startPoint,Point endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
	
	
	/**
	 * ָ���˾��������յ�,Ҫ�󾭹�һЩ�ι۵�
	 * @param startPoint
	 * @param endPoint
	 * @param watchPoints
	 */
	public WayPointManager(Point startPoint,Point endPoint,ArrayList<Point> watchPoints) {
		this(startPoint,endPoint);
		this.watchPoints = watchPoints;
	}
	
	
	/**
	 * ��ȡ·��ģ���ļ�,ʹ����Ӧ��·��ģ��<br/>
	 * @param modelFileName ·��ģ��  ʹ�� "waypointmodel.data"
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public void readRouteModel(String modelFileName) throws ClassNotFoundException, FileNotFoundException, IOException{
		objTxtManager = new ObjectTXTManager(modelFileName);
		stuWpManager = (StuWayPointManager) objTxtManager.readObject();
	}
	
	/**
	 * ����·��ģ�ͻ�ȡ������֮����·��ģ���е����·��,�������·��ģ���еĵ�����
	 * @param p1
	 * @param p2
	 * @return ����·�������Ա�,������������С��100,�򷵻�null
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
	 * ���������յ�����·��
	 * @return ����·�������Ա�,������������С��100,�򷵻�null
	 */
	public ArrayList<Integer> calculateRouteS2E(){
		return calculateRouteP2P(startPoint, endPoint);
	}
	
	
	/**
	 * 
	 * @author Linsixin
	 * ������Ѱ·�Ľ��
	 */
	public class PathResult{
		/**���е�·����**/
		public ArrayList<? extends Point> model;
		
		/**�û�Ҫȥ�ĵط�**/
		public ArrayList<Place> places;
		
		/**��˳��Ҫȥ�ĵ���ģ���е�index ����: 1 5 6 7 ���������ϵ���ô��**/
		public int []order;
		
		/**��˳��Ҫȥ�ĵ�֮���·��**/
		public ArrayList<Integer> []paths;
		
		public PathResult(int order[],ArrayList<Integer> paths[],ArrayList<Place> places,ArrayList<? extends Point> model) {
			this.order = order;
			this.paths = paths;
			this.places = places;
			this.model = model;
		}
	}
	
	/**
	 * �������·��,Ҫ�������,�յ�Ͳι۵�,���ص����
	 * @param watchPoint Ҫ�����Ĳι۵�
	 * @return ���صĽ����װ��
	 */
	public PathResult calcculateRouteWith(ArrayList<? extends Point> watchPoint)
	{
		//��ȡ�û�����ĵ����ص��ڽӵ�
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
		
		//��ȡ��ص�������֮��ľ���
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
				dis[i][j] = p2p.getRslt()[placeList.get(j).getModelIndex()];		//�ҵ���Ӧ�յ��ֵ
				path[i][j] = p2p.getPathList(placeList.get(j).getModelIndex());		//��������֮����̵�·��
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
		int[] order = p2ps.getShortestOrder();		//���Ҫ��תһ��
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
