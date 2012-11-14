package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Set;

public class WalkwayCell extends BoardCell {
	
	public WalkwayCell(int row, int col){
		setRow(row);
		setColumn(col);
	}
	
	@Override
	public boolean isWalkway() {
		return true;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(getColumn()*SIZE, getRow()*SIZE, SIZE, SIZE);
		g.setColor(Color.black);
		g.drawRect(getColumn()*SIZE, getRow()*SIZE, SIZE, SIZE);
	}
}
