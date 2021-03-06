package clueGame;

public class HumanPlayer extends Player {
	private boolean turnFinished;
	private boolean givenTargets;
	private boolean selectedTarget;
	private char lastRoomVisited;
	private Board board;
	
	//Constructor
	public HumanPlayer(Board board) {
		turnFinished = false;
		givenTargets = false;
		setSelectedTarget(false);
		this.board = board;
	}
	
	
//	//Logic for the human's turn
//	public void makeMove(int dieRoll){
//		if (!board.getHumanPlayer().isTurnFinished()){
//			HumanPlayer hp = board.getHumanPlayer();
//			
//			//Gets human players location
//			int humansRow = hp.getLocationX();
//			int humansColumn = hp.getLocationY();
//			
//			//Calculates targets
//			board.calcTargets(board.calcIndex(humansRow, humansColumn), dieRoll);
//			
//			//repaints the board
//			board.repaint();
//		}
//	}

	
	//*Getters and Setters**********************************************************
	public boolean isTurnFinished() {
		return turnFinished;
	}

	public void setTurnFinished(boolean turnFinished) {
		this.turnFinished = turnFinished;
	}

	public boolean isGivenTargets() {
		return givenTargets;
	}


	public void setGivenTargets(boolean givenTargets) {
		this.givenTargets = givenTargets;
	}


	public boolean isSelectedTarget() {
		return selectedTarget;
	}


	public void setSelectedTarget(boolean selectedTarget) {
		this.selectedTarget = selectedTarget;
	}


	public char getLastRoomVisited() {
		return lastRoomVisited;
	}


	public void setLastRoomVisited(char lastRoomVisited) {
		this.lastRoomVisited = lastRoomVisited;
	}
	
}
