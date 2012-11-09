package clueGame;

import java.awt.Graphics;

import clueGame.RoomCell.DoorDirection;

public abstract class BoardCell {
	private int row;
	private int column;
	private int location;
	protected final static int SIZE = 30;
	
	public boolean isWalkway() {
		return false;
	}
	
	public boolean isRoom() {
		return false;
	}

	public boolean isDoorway( ) { //10/21
		return false;
	}
	
	public abstract void draw(Graphics g);
	
	
	// Getters and setters
	public int getRow() {
		return row;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	//TODO Add abstract method called draw
}
