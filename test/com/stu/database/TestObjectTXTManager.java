package com.stu.database;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.stu.graph.BarrierGenerator;

@SuppressWarnings("deprecation")
public class TestObjectTXTManager {

	private ObjectTXTManager manager ;
	private BarrierGenerator bg ;
	
	@Before
	public void setUp() throws Exception {
		 manager = new ObjectTXTManager("test.txt");
	}	

	@Test
	@Ignore
	@Deprecated
	public void test() throws ClassNotFoundException, FileNotFoundException, IOException {
		manager.writeObject(bg);
		bg = new BarrierGenerator(10, 10);
		bg.dropBarrier(1, 1);
		BarrierGenerator exp =  (BarrierGenerator) manager.readObject();
		assertArrayEquals(exp.getMap(), bg.getMap());
		System.out.println(exp.toString());
	}
}
