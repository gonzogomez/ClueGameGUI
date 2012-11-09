package clueGame;

public class BadConfigFormatException extends Exception {
	private static final long serialVersionUID = 1L;

	BadConfigFormatException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return "BadConfigFormatException [Delimiter error: comma or space in wrong spot: ]" + getMessage();
	}
	
}
