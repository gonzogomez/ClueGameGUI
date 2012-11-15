package clueGame;

import java.awt.Color;
import java.awt.Graphics;


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

	public boolean isDoorway( ) {
		return false;
	}
	
	//Draws the possible targets for the human player to select from.
	public void drawHighlight(Graphics g){
		g.setColor(Color.white);
		g.fillRect(getColumn()*SIZE, getRow()*SIZE, SIZE, SIZE);
		g.setColor(Color.black);
		g.drawRect(getColumn()*SIZE, getRow()*SIZE, SIZE, SIZE);
	}
	
	//Abstract Draw Method
	public abstract void draw(Graphics g);
	
	//*Getters and Setters*****************************************************
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
}
