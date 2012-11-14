package clueGame;

public class HumanPlayer extends Player {
	private boolean turnFinished;
	
	public HumanPlayer() {
		turnFinished = false;
	}

	public boolean isTurnFinished() {
		return turnFinished;
	}

	public void setTurnFinished(boolean turnFinished) {
		this.turnFinished = turnFinished;
	}
	
}
