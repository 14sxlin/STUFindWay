package com.stu.database;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.stu.graph.BarrierGenerator;

public class TestObjectTXTManager {

	private ObjectTXTManager manager ;
	private BarrierGenerator bg ;
	
	@Before
	public void setUp() throws Exception {
		 manager = new ObjectTXTManager("test.txt");
		 bg = new BarrierGenerator(10, 10);
		 bg.dropBarrier(1, 1);
	}	

	@Test
	@Ignore
	public void test() {
		manager.writeObject(bg);
		BarrierGenerator exp =  (BarrierGenerator) manager.readObject();
		assertArrayEquals(exp.getMap(), bg.getMap());
		System.out.println(exp.toString());
	}
	
//	@Test
//	public void test1(){
//		manager = new ObjectTXTManager("savedMap.data");
//		bg = (BarrierGenerator) manager.readObject();
//		for(int i = 49;i<=53; i++)
//		{
//			bg.getMap()[i][27] = '0';
//		}
//		for(int i = 26;i<=31; i++)
//		{
//			bg.getMap()[57][i] = '0';
//			bg.getMap()[64][i] = '0';
//		}
//		System.out.println(bg.toString());
//		manager.writeObject(bg);
//	}

}
