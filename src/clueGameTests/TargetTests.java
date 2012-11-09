package clueGameTests;

import java.util.Set;
import java.util.TreeSet;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class TargetTests {
	
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		board = new Board();
		board.calcAdjacencies();
//		Set<BoardCell> targets = board.getTargets();
//		targets.clear();
	}
	
	@Test
	//Test targets of two different walkway locations with step of one.
	public void testWalkwayTargetsOneStep() {
		board.calcTargets(board.calcIndex(6, 1), 1);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(6, 0))));
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(5, 1))));
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(6, 2))));		
		Assert.assertEquals(3, targets.size());
		targets.clear();
		
		board.calcTargets(board.calcIndex(20, 6), 1);
		targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(19, 6))));
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(20, 7))));	
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(21, 6))));
		Assert.assertEquals(3, targets.size());
		targets.clear();
	}
	
	@Test
	//Test targets of two different walkway locations with step of two.
	public void testWalkwayTargetsTwoSteps() {
		board.calcTargets(board.calcIndex(6, 1), 2);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(5, 0))));
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(5, 2))));
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(6, 3))));	
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(7, 0))));
		Assert.assertEquals(4, targets.size());
		targets.clear();
		
		board.calcTargets(board.calcIndex(20, 6), 2);
		targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(18, 6))));
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(19, 7))));	
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(21, 7))));
		Assert.assertEquals(3, targets.size());
		targets.clear();
	}
	
	@Test
	//Test targets of two different walkway locations with step of three.
	public void testWalkwayTargetsThreeSteps() {
		board.calcTargets(board.calcIndex(6, 1), 3);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(8, 0))));
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(5, 1))));
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(5, 3))));
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(6, 4))));
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(6, 0))));	
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(6, 2))));	
		Assert.assertEquals(6, targets.size());
		targets.clear();
		
		board.calcTargets(board.calcIndex(20, 6), 3);
		targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(20, 7))));
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(19, 6))));	
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(17, 6))));
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(18, 7))));	
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(21, 6))));	
		Assert.assertEquals(5, targets.size());
		targets.clear();
	}
	
	@Test
	//Test targets for two walkway locations where you can enter rooms.
	public void testTargetsEnterRooms() {
		board.calcTargets(board.calcIndex(17, 6), 1);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(17, 5))));
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(16, 6))));
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(17, 7))));
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(18, 6))));	
		Assert.assertEquals(4, targets.size());
		targets.clear();
		
		board.calcTargets(board.calcIndex(4, 19), 3);
		targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(4, 20))));
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(1, 19))));
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(2, 18))));
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(6, 20))));
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(6, 18))));
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(4, 18))));
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(5, 17))));
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(5, 19))));
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(3, 19))));
		Assert.assertEquals(9, targets.size());
		targets.clear();
	}
	
	@Test
	//Test targets for 2 door locations when leaving room.
	public void testTargetsLeavingRooms() {
		board.calcTargets(board.calcIndex(7, 20), 1);
		
		Set<BoardCell> targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(6, 20))));	
		Assert.assertEquals(1, targets.size());
		targets.clear();
		
		board.calcTargets(board.calcIndex(2, 13), 3);
		targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(4, 12))));
		Assert.assertTrue(targets.contains(board.getRoomCellAt(board.calcIndex(0, 12))));
		Assert.assertEquals(2, targets.size());
		targets.clear();
	}
}
