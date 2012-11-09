package clueGameTests;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.RoomCell;

public class InitialTests {
	private static Board board;
	public static final int NUM_ROOMS = 11;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 23;
	private Map<Character,String> rooms;

	@Before
	public void initBoard() {
		board = new Board();
		rooms = new HashMap<Character, String>();
	}

	@Test
	//Tests number of rooms
	public void testRooms() {
		Map<Character, String> rooms = board.getRooms();
		//System.out.println("rooms.size() = " + rooms.size());
		assertEquals(NUM_ROOMS, rooms.size());
	}

	@Test
	//TODO Check actual room names
	//Check if keys/labels of rooms are correct
	public void testKeyValues() {
		Map<Character, String> rooms = board.getRooms();
		assertEquals("Library", rooms.get('L'));
		assertEquals("Kitchen", rooms.get('K'));
		assertEquals("Conservatory", rooms.get('C'));
		assertEquals("Dining room", rooms.get('D'));
		assertEquals("Billiard room", rooms.get('R'));
		assertEquals("Walkway", rooms.get('W'));
		assertEquals("Lounge", rooms.get('O'));
		assertEquals("Study", rooms.get('S'));
		assertEquals("Closet", rooms.get('X'));
		assertEquals("Ballroom", rooms.get('B'));
		assertEquals("Hall", rooms.get('H'));
	}

	@Test
	//Tests the number of rows and columns in the board
	public void testBoardSize() {
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}

	@Test
	//Test one of each door direction (UP/DOWN/RIGHT/LEFT)
	public void testDoorDirections() {
		RoomCell room;

		//Testing the door in the conservatory (RIGHT)
		room = board.getRoomCellAt(4, 3);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());

		//Testing the door in the Billiard Room (R) (DOWN)
		room = board.getRoomCellAt(4, 8);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());

		//Testing upper door in Hall (LEFT)
		room = board.getRoomCellAt(9, 17);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());

		//Testing upper door in Dining Room (UP)
		room = board.getRoomCellAt(14, 11);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection());

		//Testing that part of rooms aren't doors
		room = board.getRoomCellAt(0, 0);
		assertFalse(room.isDoorway());	

		//Testing that walkways are not doors
		room = board.getRoomCellAt(0, 7);  //BoardCell cell = board.getRoomCellAt(0, 6);
		assertFalse(room.isDoorway());	//assertFalse(cell.isDoorway(cell));
	}

	@Test
	//Testing that we have the correct number of doors
	public void testNumberOfDoorways() {
		int numDoors = 0;
		int totalCells = board.getNumColumns() * board.getNumRows();
		assertEquals(506, totalCells);
		for(int i=0; i < totalCells; i++) {
			BoardCell b = board.getRoomCellAt(i);
			if (b.isDoorway())
				numDoors++;
		}
		assertEquals(16, numDoors);
	}

	@Test
	//Testing the calcIndex function 
	public void testcalcIndex() {
		// Test each corner of the board
		assertEquals(0, board.calcIndex(0, 0));
		assertEquals(NUM_COLUMNS-1, board.calcIndex(0, NUM_COLUMNS-1));
		assertEquals(483, board.calcIndex(NUM_ROWS-1, 0));
		assertEquals(505, board.calcIndex(NUM_ROWS-1, NUM_COLUMNS-1));
		// Test a couple others
		assertEquals(24, board.calcIndex(1, 1));
		assertEquals(66, board.calcIndex(2, 20));
	}

	@Test
	//Testing room initials
	public void testRoomInitials() {
		assertEquals('C', board.getRoomCellAt(2, 2).getInitial());
		assertEquals('R', board.getRoomCellAt(2, 9).getInitial());
		assertEquals('B', board.getRoomCellAt(10, 2).getInitial());
		assertEquals('O', board.getRoomCellAt(18, 20).getInitial());
		assertEquals('K', board.getRoomCellAt(18, 2).getInitial());
		assertEquals('S', board.getRoomCellAt(2, 22).getInitial());
		assertEquals('X', board.getRoomCellAt(9, 12).getInitial());
		assertEquals('H', board.getRoomCellAt(10, 20).getInitial());
		assertEquals('D', board.getRoomCellAt(18, 11).getInitial());
		assertEquals('L', board.getRoomCellAt(2, 15).getInitial());
	}
}
