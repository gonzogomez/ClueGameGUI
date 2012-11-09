package cluePathTests;

import java.util.LinkedList;
import java.util.TreeSet;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import cluePath.IntBoard;



public class IntBoardTests {
	IntBoard board;
	
	@Before
	public void initBoard() {
		board = new IntBoard();
		board.calcAdjacencies();
	}
	
	//*calcIndex Tests********************************************************
	@Test
	public void testCalcIndex_5(){
		int row = 1;
		int column = 1;
		int expected = 5;
		
		int actual = board.calcIndex(row, column);
		Assert.assertEquals(expected, actual);		
	}
	
	@Test
	public void testCalcIndex_11(){
		IntBoard board = new IntBoard();
		int row = 2;
		int column = 3;
		int expected = 11;
		
		int actual = board.calcIndex(row, column);
		Assert.assertEquals(expected, actual);		
	}
	
	//*Adjacency Tests********************************************************
	@Test
	public void testAdjacency0() {
		LinkedList testList = board.getAdjList(0);
		Assert.assertTrue(testList.contains(4));
		Assert.assertTrue(testList.contains(1));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacency15() {
		LinkedList testList = board.getAdjList(15);
		Assert.assertTrue(testList.contains(11));
		Assert.assertTrue(testList.contains(14));
		Assert.assertEquals(2, testList.size());
	}

	@Test
	public void testAdjacency7() {
		LinkedList testList = board.getAdjList(7);
		Assert.assertTrue(testList.contains(11));
		Assert.assertTrue(testList.contains(3));
		Assert.assertTrue(testList.contains(6));
		Assert.assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjacency8() {
		LinkedList testList = board.getAdjList(8);
		Assert.assertTrue(testList.contains(12));
		Assert.assertTrue(testList.contains(9));
		Assert.assertTrue(testList.contains(4));
		Assert.assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjacency5() {
		LinkedList testList = board.getAdjList(5);
		Assert.assertTrue(testList.contains(4));
		Assert.assertTrue(testList.contains(9));
		Assert.assertTrue(testList.contains(6));
		Assert.assertTrue(testList.contains(1));
		Assert.assertEquals(4, testList.size());
	}
	
	public void testAdjacency10() {
		LinkedList testList = board.getAdjList(10);
		Assert.assertTrue(testList.contains(14));
		Assert.assertTrue(testList.contains(11));
		Assert.assertTrue(testList.contains(6));
		Assert.assertTrue(testList.contains(9));
		Assert.assertEquals(4, testList.size());
	}
	
	//6 Path Creation Tests*********************************************************
	
	@Test
	public void testTargets0_1() {
		board.calcTargets(0, 1);
		board.printTargets(0, 1);
		TreeSet targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(1));
		Assert.assertTrue(targets.contains(4));
	}
	
	@Test
	public void testTargets0_2() {
		board.calcTargets(0, 2);
		board.printTargets(0, 2);
		TreeSet targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(2));
		Assert.assertTrue(targets.contains(5));
		Assert.assertTrue(targets.contains(8));
	}
	
	@Test
	public void testTargets0_3() {
		board.calcTargets(0, 3);
		board.printTargets(0, 3);
		TreeSet targets= board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(1));
		Assert.assertTrue(targets.contains(3));
		Assert.assertTrue(targets.contains(4));
		Assert.assertTrue(targets.contains(6));
		Assert.assertTrue(targets.contains(9));
		Assert.assertTrue(targets.contains(12));
	}
	
	@Test
	public void testTargets0_4() {
		board.calcTargets(0, 4);
		board.printTargets(0, 4);
		TreeSet targets= board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(2));
		Assert.assertTrue(targets.contains(5));
		Assert.assertTrue(targets.contains(7));
		Assert.assertTrue(targets.contains(8));
		Assert.assertTrue(targets.contains(10));
		Assert.assertTrue(targets.contains(13));
	}
	
	@Test
	public void testTargets0_5() {
		board.calcTargets(0, 5);
		board.printTargets(0, 5);
		TreeSet targets= board.getTargets();
		Assert.assertEquals(8, targets.size());
		Assert.assertTrue(targets.contains(1));
		Assert.assertTrue(targets.contains(3));
		Assert.assertTrue(targets.contains(4));
		Assert.assertTrue(targets.contains(6));
		Assert.assertTrue(targets.contains(9));
		Assert.assertTrue(targets.contains(11));
		Assert.assertTrue(targets.contains(12));
		Assert.assertTrue(targets.contains(14));
	}
	
	@Test
	public void testTargets0_6() {
		board.calcTargets(0, 6);
		board.printTargets(0, 6);
		TreeSet targets= board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(2));
		Assert.assertTrue(targets.contains(5));
		Assert.assertTrue(targets.contains(7));
		Assert.assertTrue(targets.contains(8));
		Assert.assertTrue(targets.contains(10));
		Assert.assertTrue(targets.contains(13));
		Assert.assertTrue(targets.contains(15));
		
	}
	
}
