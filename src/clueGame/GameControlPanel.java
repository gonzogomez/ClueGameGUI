package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import clueGame.Card.CardType;


@SuppressWarnings("serial")
public class GameControlPanel extends JPanel {

	private SouthPanel sp;
	private NorthPanel np;
	private SuggestionDialog suggestionDialog;
	private boolean selectedTarget = true;
	private Board board;
	private String person;
	private Suggestion storedSuggestion;
	private int compPlayerIndex;

	//Game Control Panel contains all the game controls.
	public GameControlPanel(Board board){
		this.board = board;
		person = "Miss Scarlet           ";
		setSp(new SouthPanel());
		np = new NorthPanel();
		storedSuggestion = new Suggestion();
		suggestionDialog = new SuggestionDialog();
		compPlayerIndex = 0;
		board.setWhoseTurn(board.getHumanPlayer());
		setLayout(new BorderLayout());
		add(np, BorderLayout.NORTH);
		add(getSp(), BorderLayout.SOUTH);
	}

	//A createButton method for the Next Player and Make an Accusation buttons.
	public JButton createButton(String name){
		JButton button = new JButton(name);
		button.setPreferredSize(new Dimension(200, 60));
		return button;
	}

	public void updateDisplay(String person, String room, String weapon, Card returnedCard) {
		System.out.println("Person: " + person);
		System.out.println("Weapon: " + weapon);
		System.out.println("Room: " + room);
		
		sp.guessPanel.guessBox.setText(person + ", " + room + ", " + weapon);
		
		if(returnedCard != null){
			sp.guessReturnPanel.guessResultBox.setText(returnedCard.getName());
		} else {
			JOptionPane.showMessageDialog(board,"There was no card to be returned", "Message", JOptionPane.INFORMATION_MESSAGE);
		}
		repaint();
	}
	//Logic for the human's turn
	public void makeHumanMove(int dieRoll){
		if (!board.getHumanPlayer().isTurnFinished() && !board.getHumanPlayer().isGivenTargets()){
			HumanPlayer hp = board.getHumanPlayer();

			//Gets human players location
			int humansRow = hp.getLocationX();
			int humansColumn = hp.getLocationY();

			//Calculates targets
			board.calcTargets(board.calcIndex(humansRow, humansColumn), dieRoll);
			
			//Disallows multiple rolls of dice
			board.getHumanPlayer().setGivenTargets(true);
			
			//repaints the board
			board.repaint();
		}
	}

	//Logic for the computer's turn
	public void makeComputerMove(int dieRoll) {
		//Sets a local pointer to current computer player
		ComputerPlayer currentCompPlayer = board.getComputerPlayers().get(compPlayerIndex);

		//If computer player knows the solution, they should make an accusation
		if (currentCompPlayer.isAccusationFlag() == true) {
			currentCompPlayer.makeAccusation(storedSuggestion);
			if (currentCompPlayer.isCorrectAnswer()) {
				endGame();
			} else {
				currentCompPlayer.setAccusationFlag(false);
				JOptionPane.showMessageDialog(board,"The computer player made an incorrect guess of: " + storedSuggestion.getPerson() + ", " + storedSuggestion.getRoom() + ", " + storedSuggestion.getWeapon(), "Message", JOptionPane.INFORMATION_MESSAGE);
			}
			
		}

		//Gets computer players row and column
		int row = currentCompPlayer.getLocationX();
		int column = currentCompPlayer.getLocationY();

		//Calculates targets for current computer player's position
		board.calcTargets(board.calcIndex(row, column), dieRoll);

		//Calls pick location which chooses a random location unless there is a door present
		currentCompPlayer.pickLocation(board.getTargets());

		board.repaint();

		//Gets updated computer location
		int locationX = currentCompPlayer.getLocationX();
		int locationY = currentCompPlayer.getLocationY();

		//Checks to see if the computer's selection was a door
		if (board.getCells().get(board.calcIndex(locationX, locationY)).isRoom()) {
			//Creates and handles suggestion
			Suggestion sugg = currentCompPlayer.createSuggestion(board.getCards(), board.getCells());
			Card returnedCard = board.handleSuggestion(sugg.getPerson(), sugg.getWeapon(), sugg.getRoom());

			for (ComputerPlayer cp : board.getComputerPlayers()) {
				if (cp.getName().equals(sugg.getPerson())) {
					cp.setLocationX(currentCompPlayer.getLocationX());
					cp.setLocationY(currentCompPlayer.getLocationY());
				}
			}
			
			if (board.getHumanPlayer().getName().equals(sugg.getPerson())) {
				board.getHumanPlayer().setLocationX(currentCompPlayer.getLocationX());
				board.getHumanPlayer().setLocationY(currentCompPlayer.getLocationY());
			}
			
			//Stores the suggestion and sets accusation flag to true so computer makes a guess the next round
			if (returnedCard == null) {
				storedSuggestion = sugg;
				currentCompPlayer.setAccusationFlag(true);
				sp.guessReturnPanel.guessResultBox.setText("No new clue");
			} else {
				sp.guessPanel.guessBox.setText(sugg.getPerson() + ", " + sugg.getRoom() + ", " + sugg.getWeapon());
				sp.guessReturnPanel.guessResultBox.setText(returnedCard.getName());
			}
			repaint();
		}
	}

	//************************************************************************************************************************************

	//NORTH PANEL: contains the Whose Turn?, Next Player button, and Make an Accusation Button
	public class NorthPanel extends JPanel {
		private TurnPanel turnPanel;
		private AccusationDialog accusationDialog;
		private JButton nextButton;
		private JButton accusationButton;

		//Constructor
		public NorthPanel() {
			turnPanel = new TurnPanel();
			setLayout(new GridLayout(1, 3));
			add(turnPanel);
			accusationDialog = new AccusationDialog();
			GameControlButtonListener listener = new GameControlButtonListener();
			nextButton = createButton("NextPlayer");
			accusationButton = createButton("Make an accusation");
			nextButton.addActionListener(listener);
			accusationButton.addActionListener(listener);
			add(nextButton);
			add(accusationButton);
		}

		//Displays whose turn it is.
		public class TurnPanel extends JPanel {
			private JLabel title;
			private JTextArea textBox;

			public TurnPanel() {
				title = new JLabel("Whose turn?");
				textBox = new JTextArea(person);
				setLayout(new FlowLayout());
				add(title);
				add(textBox);
			}
		}

		//NOTES:
		//1.) Suggestions don't update the south panel display for human turn
		//2.) I hate Clue.

		public class GameControlButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == nextButton) {
					Random rand = new Random();
					int dieRoll = rand.nextInt(6) + 1;
					String dieRollString = "" + dieRoll;
					
					//Disallows human from rolling multiple times in a turn
					if (board.getHumanPlayer().isGivenTargets()) {
						JOptionPane.showMessageDialog(board,"You need to finish your turn", "Message", JOptionPane.INFORMATION_MESSAGE);
					}

					//This logic checks if the human players turn has been set back to unfinished.
					if (board.getHumanPlayer().isTurnFinished() == false) {
						if (board.getWhoseTurn() == board.getComputerPlayers().get(4)) {
							board.setWhoseTurn(board.getHumanPlayer());
						} 
					}

					//Human Players turn
					if (board.getWhoseTurn() == board.getHumanPlayer()) {
						//Updates displays
						turnPanel.textBox.setText(board.getWhoseTurn().getName());
						getSp().diePanel.rollBox.setText(dieRollString);

						//Calls the humans turn method
						//board.getHumanPlayer().makeHumanMove(dieRoll);
						makeHumanMove(dieRoll);

					//Controls Computer players turn
					} else {
						//Update control panel display
						turnPanel.textBox.setText(board.getWhoseTurn().getName());
						getSp().diePanel.rollBox.setText(dieRollString);

						//Calling makeMove for computer player
						makeComputerMove(dieRoll);

						//If it's the last computer players turn, set to human's turn
						if (board.getWhoseTurn() == board.getComputerPlayers().get(4)) {
							board.getHumanPlayer().setTurnFinished(false);
							compPlayerIndex = 0;
						} else {
							compPlayerIndex++;
							board.setWhoseTurn(board.getComputerPlayers().get(compPlayerIndex));
						}
					}

				} else if (e.getSource() == accusationButton) {
					if (!board.getHumanPlayer().isSelectedTarget()) {
						accusationDialog.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(board, "It is not your turn!", "Message", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		}

		//Accusation Dialog box
		public class AccusationDialog extends JDialog {
			private JLabel room, person, weapon;
			private JComboBox personComboBox, weaponComboBox, roomComboBox;
			private JButton submit, cancel;

			//Constructor
			public AccusationDialog() {
				setTitle("Make an Accusation");
				setSize(300, 200);
				setLayout(new GridLayout(4,2));
				room = new JLabel("Room");
				roomComboBox = new JComboBox();
				person = new JLabel("Person");
				weapon = new JLabel("Weapon");
				personComboBox = new JComboBox();
				weaponComboBox = new JComboBox();
				submit = new JButton("Submit");
				cancel = new JButton("Cancel");

				for(Card c : board.getCards()){
					if (c.getCardType() == CardType.ROOM) {
						roomComboBox.addItem(c.getName());
					}
					if(c.getCardType() == CardType.PERSON){
						personComboBox.addItem(c.getName());
					}
					if(c.getCardType() == CardType.WEAPON){
						weaponComboBox.addItem(c.getName());
					}
				}

				AccusationButtonListener listener = new AccusationButtonListener();

				add(person);
				add(personComboBox);
				add(room);
				add(roomComboBox);
				add(weapon);
				add(weaponComboBox);
				add(submit);
				add(cancel);

				submit.addActionListener(listener);
				cancel.addActionListener(listener);
			}

			//ButtonListener for AccusationDialog box (submit, cancel)
			public class AccusationButtonListener implements ActionListener {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == submit) {
						String selectedPerson = (String) personComboBox.getSelectedItem();
						String selectedWeapon = (String) weaponComboBox.getSelectedItem();
						String selectedRoom = (String) roomComboBox.getSelectedItem();
						boolean correctGuess = board.checkAccusation(selectedPerson, selectedRoom, selectedWeapon);
						if(correctGuess == true){
							endGame();
						} else {
							JOptionPane.showMessageDialog(board, "Zas wrong", "Message", JOptionPane.INFORMATION_MESSAGE);
						}
						accusationDialog.setVisible(false);
						board.getHumanPlayer().setSelectedTarget(true);
						board.getHumanPlayer().setGivenTargets(false);
						board.getHumanPlayer().setTurnFinished(true);
						board.setWhoseTurn(board.getComputerPlayers().get(0));

					} else if (e.getSource() == cancel) {
						accusationDialog.setVisible(false);
					}	
				}
			}
		}
	}

	//*******************************************************************************************************************************

	//SOUTH PANEL: contains Die Roll, Guess, and Guess Result boxes
	public class SouthPanel extends JPanel {
		private Die diePanel;
		private Guess guessPanel;
		private GuessReturn guessReturnPanel;

		//Constructor
		public SouthPanel() {
			diePanel = new Die();
			setGuessPanel(new Guess());
			setGuessReturnPanel(new GuessReturn());
			setLayout(new BorderLayout());

			add(diePanel, BorderLayout.WEST);
			add(getGuessPanel(), BorderLayout.CENTER);
			add(getGuessReturnPanel(), BorderLayout.EAST);
		}

		public GuessReturn getGuessReturnPanel() {
			return guessReturnPanel;
		}

		public void setGuessReturnPanel(GuessReturn guessReturnPanel) {
			this.guessReturnPanel = guessReturnPanel;
		}

		public Guess getGuessPanel() {
			return guessPanel;
		}

		public void setGuessPanel(Guess guessPanel) {
			this.guessPanel = guessPanel;
		}

		//Die Panel
		public class Die extends JPanel {
			private JLabel roll;
			private JTextArea rollBox;

			public Die() {
				setBorder(new TitledBorder(new EtchedBorder(), "Die"));
				roll = new JLabel("Roll");
				rollBox = new JTextArea("0");
				add(roll);
				add(rollBox);
			}
		}

		//Guess Panel
		public class Guess extends JPanel {
			private JLabel guess;
			private JTextArea guessBox;
			public Guess() {
				setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
				guess = new JLabel("Guess");
				setGuessBox(new JTextArea("Guess"));
				add(guess);
				add(getGuessBox());
			}
			public JTextArea getGuessBox() {
				return guessBox;
			}
			public void setGuessBox(JTextArea guessBox) {
				this.guessBox = guessBox;
			}
		}

		//Guess Result Panel
		public class GuessReturn extends JPanel {
			private JLabel guessResultLabel;
			private JTextArea guessResultBox;
			public GuessReturn() {
				setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
				guessResultLabel = new JLabel("Response");
				setGuessResultBox(new JTextArea("Response"));

				add(guessResultLabel);
				add(getGuessResultBox());
			}
			public JTextArea getGuessResultBox() {
				return guessResultBox;
			}
			public void setGuessResultBox(JTextArea guessResultBox) {
				this.guessResultBox = guessResultBox;
			}
		}
	}

	//*******************************************************************************************************************************
	//Suggestion Dialog Box 
	public class SuggestionDialog extends JDialog {
		private JLabel yourRoom, person, weapon;
		private JLabel roomLocation;
		private JComboBox personComboBox, weaponComboBox;
		private JButton submit, cancel;

		//Constructor
		public SuggestionDialog() {
			setTitle("Make a Guess");
			setSize(300, 200);
			setLayout(new GridLayout(4,2));
			yourRoom = new JLabel("Your room");
			roomLocation = new JLabel();
			person = new JLabel("Person");
			weapon = new JLabel("Weapon");
			personComboBox = new JComboBox();
			weaponComboBox = new JComboBox();
			submit = new JButton("Submit");
			cancel = new JButton("Cancel");

			for(Card c : board.getCards()){
				if(c.getCardType() == CardType.PERSON){
					personComboBox.addItem(c.getName());
				}
				if(c.getCardType() == CardType.WEAPON){
					weaponComboBox.addItem(c.getName());
				}
			}

			ButtonListener listener = new ButtonListener();

			add(yourRoom);
			add(roomLocation);
			add(person);
			add(personComboBox);
			add(weapon);
			add(weaponComboBox);
			add(submit);
			add(cancel);

			submit.addActionListener(listener);
			cancel.addActionListener(listener);
		}

		//ButtonListener for SuggestionDialog box (submit, cancel)
		public class ButtonListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == submit) {
					String selectedPerson = (String) personComboBox.getSelectedItem();
					String selectedWeapon = (String) weaponComboBox.getSelectedItem();
					Card returnedCard = board.handleSuggestion(selectedPerson, selectedWeapon, roomLocation.getText());
					
					updateDisplay(selectedPerson, roomLocation.getText(), selectedWeapon, returnedCard);
					
					//Moves correct computer player to room where human player made suggestion
					for (ComputerPlayer cp : board.getComputerPlayers()) {
						if (cp.getName().equals(selectedPerson)) {
							cp.setLocationX(board.getHumanPlayer().getLocationX());
							cp.setLocationY(board.getHumanPlayer().getLocationY());
						}
					}
					
					suggestionDialog.setVisible(false);

				} else if (e.getSource() == cancel) {
					suggestionDialog.setVisible(false);
				}
				board.repaint();
			}
		}

		public JLabel getRoomlocation() {
			return roomLocation;
		}

		public void setRoomlocation(JLabel roomlocation) {
			this.roomLocation = roomlocation;
		}
	}


	public void endGame() {
		
		
		if (board.getWhoseTurn() == board.getHumanPlayer()) {
			JOptionPane.showMessageDialog(board,"You Win!", "Message", JOptionPane.INFORMATION_MESSAGE);
		}
		else{
			JOptionPane.showMessageDialog(board,"Computer just won, answer is " + storedSuggestion.getPerson() + ", " + storedSuggestion.getRoom() + ", " + storedSuggestion.getWeapon() , "Message", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	//*Getters and Setters***************************************************************************************************
	public boolean isSelectedTarget() {
		return selectedTarget;
	}

	public void setSelectedTarget(boolean selectedTarget) {
		this.selectedTarget = selectedTarget;
	}

	public SuggestionDialog getSuggestionDialog() {
		return suggestionDialog;
	}

	public void setSuggestionDialog(SuggestionDialog suggestionDialog) {
		this.suggestionDialog = suggestionDialog;
	}

	public SouthPanel getSp() {
		return sp;
	}

	public void setSp(SouthPanel sp) {
		this.sp = sp;
	}
}
