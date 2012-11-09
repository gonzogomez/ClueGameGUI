package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

public class RoomCell extends BoardCell {
	
	public enum DoorDirection { UP, DOWN, LEFT, RIGHT, NONE };
	
	private DoorDirection doorDirection;
	private char roomInitial;
	private boolean isDoor;
	private Map<Character, String> rooms;
	private String roomName = "";

	
	public RoomCell(String inString, int row, int col, Map<Character, String> rooms) {
		this.rooms = rooms;
		if(inString.length() == 2) {
			if(inString.contains("N")){
				char roomInitial = inString.charAt(0);
				roomName = rooms.get(roomInitial);
				
			} else {
				setDoorWay(inString);
				isDoor = true;
			}
			
		} else {
			setDoorDirection(doorDirection.NONE);
			isDoor = false;
		}
		setRow(row);
		setColumn(col);
		setRoomInitial(inString);
	}
	
	@Override
	public boolean isRoom() {
		return true;
	}
	
	public boolean isDoorway() {  
		return isDoor;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(new Color(0,0,0));
		g.fillRect(getColumn()*SIZE, getRow()*SIZE, SIZE, SIZE);
		if(this.isDoorway()){
			if(this.doorDirection == DoorDirection.RIGHT){
				g.setColor(Color.white);
				g.fillRect(this.getColumn()*SIZE + 27, this.getRow()*SIZE, 3, SIZE);
			}
			else if(this.doorDirection == DoorDirection.LEFT){
				g.setColor(Color.white);
				g.fillRect(this.getColumn()*SIZE, this.getRow()*SIZE, 3, SIZE);
			}
			else if(this.doorDirection == DoorDirection.UP){
				g.setColor(Color.white);
				g.fillRect(this.getColumn()*SIZE, this.getRow()*SIZE, SIZE, 3);
			}
			else {
				g.setColor(Color.white);
				g.fillRect(this.getColumn()*SIZE, this.getRow()*SIZE + 27, SIZE, 3);
			}
		}
	}
	
	public void drawName(Graphics g){
		if(!roomName.equals("Closet")){
			g.setColor(Color.white);
			g.drawString(roomName, getColumn()*SIZE, getRow()*SIZE);
		}
	}

	
	
	
//*Getters and Setters***************************************************
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}


	public char getInitial() {
		return roomInitial;
	}


	public void setRoomInitial(char roomInitial) {
		this.roomInitial = roomInitial;
	}
	
	public void setRoomInitial(String input) { //added 10/21
		this.roomInitial = input.charAt(0);
	}	
	
	// added 10/21
	public void setDoorWay(String inString) {
		this.isDoor = true;
		char dir = inString.charAt(1);
		if(dir == 'R')
			setDoorDirection(doorDirection.RIGHT);
		if(dir == 'L')
			setDoorDirection(doorDirection.LEFT);
		if(dir == 'U')
			setDoorDirection(doorDirection.UP);
		if(dir == 'D')
			setDoorDirection(doorDirection.DOWN);
		
	}
//**********************************************************************

}
