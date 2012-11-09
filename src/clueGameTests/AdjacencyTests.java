package clueGameTests;

import java.util.LinkedList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class AdjacencyTests {
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		board = new Board();
	}
	
	@Test
	//Test one location with only Walkway as adjacencies.
	public void testAdjacencyWalkwaysOnly() {
		LinkedList<Integer> testList;
		testList = board.getAdjList(board.calcIndex(14, 16));
		Assert.assertTrue(testList.contains(board.calcIndex(15, 16)));
		Assert.assertTrue(testList.contains(board.calcIndex(13, 16)));
		Assert.assertTrue(testList.contains(board.calcIndex(14, 17)));
		Assert.assertTrue(testList.contains(board.calcIndex(14, 15)));
		Assert.assertEquals(4, testList.size());
	}
	
	@Test	
	//Test two locations at the edge of the board
	public void testAdjacencyEdge() {
		LinkedList<Integer> testList;
		
		testList = board.getAdjList(board.calcIndex(13, 22));
		Assert.assertTrue(testList.contains(board.calcIndex(13, 21)));
		Assert.assertTrue(testList.contains(board.calcIndex(14, 22)));
		Assert.assertEquals(2, testList.size());
		
		testList = board.getAdjList(board.calcIndex(0, 5));
		Assert.assertTrue(testList.contains(board.calcIndex(0, 4)));
		Assert.assertTrue(testList.contains(board.calcIndex(0, 6)));
		Assert.assertTrue(testList.contains(board.calcIndex(1, 5)));
		Assert.assertEquals(3, testList.size());
	}
	
	@Test
	//Test two walkway locations next to rooms but not doors
	public void testAdjacencyNextToRoomsNotDoors() {
		LinkedList<Integer> testList;								
		
		testList = board.getAdjList(board.calcIndex(20, 17));
		Assert.assertTrue(testList.contains(board.calcIndex(19, 17)));
		Assert.assertTrue(testList.contains(board.calcIndex(21, 17)));
		Assert.assertTrue(testList.contains(board.calcIndex(20, 16)));
		Assert.assertEquals(3, testList.size());
		
		testList = board.getAdjList(board.calcIndex(15, 1));
		Assert.assertTrue(testList.contains(board.calcIndex(15, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(14, 1)));
		Assert.assertTrue(testList.contains(board.calcIndex(15, 2)));
		Assert.assertEquals(3, testList.size());
	}
	
	@Test
	//Test two walkway locations next to doors
	public void testAdjacencyNextToDoors() {
		LinkedList<Integer> testList;	
			
		testList = board.getAdjList(board.calcIndex(9, 16));
		Assert.assertTrue(testList.contains(board.calcIndex(10, 16)));
		Assert.assertTrue(testList.contains(board.calcIndex(8, 16)));
		Assert.assertTrue(testList.contains(board.calcIndex(9, 17)));
		Assert.assertEquals(3, testList.size());
		
		testList = board.getAdjList(board.calcIndex(9, 7));
		Assert.assertTrue(testList.contains(board.calcIndex(9, 8)));
		Assert.assertTrue(testList.contains(board.calcIndex(8, 7)));
		Assert.assertTrue(testList.contains(board.calcIndex(10, 7)));
		Assert.assertTrue(testList.contains(board.calcIndex(9, 6)));
		Assert.assertEquals(4, testList.size());
	}
	
	@Test
	//New test to check for door directions as an adjacent location
	public void testAdjacencyNextToDoors2(){
		LinkedList<Integer> testList;
		
		testList = board.getAdjList(board.calcIndex(5, 3));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 2)));
		Assert.assertTrue(testList.contains(board.calcIndex(6, 3)));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 4)));
		Assert.assertEquals(3, testList.size());
	}
}
