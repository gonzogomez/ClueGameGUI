package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
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
	private int whichPlayer;
	private Suggestion storedSuggestion;
	private boolean correctAnswer;
	private int compPlayerIndex;
	
	//Game Control Panel contains all the game controls.
	public GameControlPanel(Board board){
		this.board = board;
		person = "Miss Scarlet           ";
		sp = new SouthPanel();
		np = new NorthPanel();
		suggestionDialog = new SuggestionDialog();
		compPlayerIndex = 0;
		board.setWhoseTurn(board.getHumanPlayer());
		correctAnswer = false;
		whichPlayer = 0;
		setLayout(new BorderLayout());
		add(np, BorderLayout.NORTH);
		add(sp, BorderLayout.SOUTH);
	}
	
	//A createButton method for the Next Player and Make an Accusation buttons.
	public JButton createButton(String name){
		JButton button = new JButton(name);
		button.setPreferredSize(new Dimension(200, 60));
		return button;
	}
	
//************************************************************************************************************************************
	
	//NORTH PANEL: contains the Whose Turn?, Next Player button, and Make an Accusation Button
	public class NorthPanel extends JPanel{
		private TurnPanel turnPanel;
		private AccusationDialog accusationDialog;
		private JButton nextButton;
		private JButton accusationButton;
		
		//Constructor
		public NorthPanel(){
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
		
		
		//Button Listener for "Next Player" and "Make an Accusation"
		public class GameControlButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == nextButton) {
					Random rand = new Random();
					int dieRoll = rand.nextInt(6) + 1;
					String dieRollString = "" + dieRoll;
					
					//If it's the human player's turn
					if(board.getWhoseTurn() == board.getHumanPlayer()) {
						turnPanel.textBox.setText(board.getWhoseTurn().getName());
						sp.diePanel.rollBox.setText(dieRollString);
						board.getHumanPlayer().setTurnFinished(false);
						humanTurn(dieRoll);
						whichPlayer = 0;
						board.setWhoseTurn(board.getComputerPlayers().get(whichPlayer));
			
					}
					else {
						turnPanel.textBox.setText(board.getWhoseTurn().getName());
						sp.diePanel.rollBox.setText(dieRollString);
						computerTurn(dieRoll);
						if(whichPlayer == 4){
							board.setWhoseTurn(board.getHumanPlayer());
						}
						else{
							board.setWhoseTurn(board.getComputerPlayers().get(++whichPlayer));
						}
					}
				} else if (e.getSource() == accusationButton) {
					accusationDialog.setVisible(true);
				}
			}
		}

		//Logic for the human's turn
		public void humanTurn(int dieRoll){
			if (!board.getHumanPlayer().isTurnFinished()){
				HumanPlayer hp = board.getHumanPlayer();
				int row = hp.getLocationX();
				int column = hp.getLocationY();
				board.calcTargets(board.calcIndex(row, column), dieRoll);
				board.repaint();
				BoardCell bc = board.getCells().get(board.calcIndex(hp.getLocationX(), hp.getLocationY()));
				if(bc.isRoom()){
					RoomCell rc = (RoomCell) bc;
					String name = board.getRooms().get(rc.getInitial());
					suggestionDialog.getRoomlocation().setText(name);
					suggestionDialog.setVisible(true);
				}
			}
		}
		
		//Logic for the computer's turn
		public void computerTurn(int dieRoll) {
			ComputerPlayer currentCompPlayer = board.getComputerPlayers().get(whichPlayer);
			
			if (currentCompPlayer.isAccusationFlag() == true) {
				correctAnswer = board.checkAccusation(storedSuggestion.getPerson(), storedSuggestion.getRoom(), storedSuggestion.getWeapon());
				if (correctAnswer == true) {
					endGame();
				}
			}
			int row = currentCompPlayer.getLocationX();
			int column = currentCompPlayer.getLocationY();
			board.calcTargets(board.calcIndex(row, column), dieRoll);
			currentCompPlayer.pickLocation(board.getTargets());
			board.repaint();
			int locationX = currentCompPlayer.getLocationX();
			int locationY = currentCompPlayer.getLocationY();
			
			if (board.getCells().get(board.calcIndex(locationX, locationY)).isRoom()){
				Suggestion sugg = currentCompPlayer.createSuggestion(board.getCards(), board.getCells());
				Card returnedCard = board.handleSuggestion(sugg.getPerson(), sugg.getWeapon(), sugg.getRoom());
				if (returnedCard == null) {
					storedSuggestion = sugg;
					currentCompPlayer.setAccusationFlag(true);
					sp.guessReturnPanel.guessResultBox.setText("Guess could not be disproved");
				}
				
				if(returnedCard != null){
					sp.guessPanel.guessBox.setText(sugg.getPerson() + ", " + sugg.getRoom() + ", " + sugg.getWeapon());
					sp.guessReturnPanel.guessResultBox.setText(returnedCard.getName());
				}
			}
		}
		
		//Accusation Dialog box
		public class AccusationDialog extends JDialog {
			private JLabel room, person, weapon;
			private JComboBox<String> personComboBox, weaponComboBox, roomComboBox;
			private JButton submit, cancel;
			
			//Constructor
			public AccusationDialog() {
				setTitle("Make an Accusation");
				setSize(300, 200);
				setLayout(new GridLayout(4,2));
				room = new JLabel("Room");
				roomComboBox = new JComboBox<String>();
				person = new JLabel("Person");
				weapon = new JLabel("Weapon");
				personComboBox = new JComboBox<String>();
				weaponComboBox = new JComboBox<String>();
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
						boolean correctGuess = board.checkAccusation(selectedPerson, selectedWeapon, selectedRoom);
						if(correctGuess == true){
							endGame();
						}
						
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
			guessPanel = new Guess();
			guessReturnPanel = new GuessReturn();
			setLayout(new BorderLayout());
			
			add(diePanel, BorderLayout.WEST);
			add(guessPanel, BorderLayout.CENTER);
			add(guessReturnPanel, BorderLayout.EAST);
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
				guessBox = new JTextArea("Guess");
				add(guess);
				add(guessBox);
			}
		}
		
		//Guess Result Panel
		public class GuessReturn extends JPanel {
			private JLabel guessResultLabel;
			private JTextArea guessResultBox;
			public GuessReturn() {
				setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
				guessResultLabel = new JLabel("Response");
				guessResultBox = new JTextArea("Response");
				
				add(guessResultLabel);
				add(guessResultBox);
			}
		}
	}
	
	public void endGame() {
		//TODO stuff.
	}
	
	//Suggestion Dialog Box 
	public class SuggestionDialog extends JDialog {
		private JLabel yourRoom, person, weapon;
		private JLabel roomLocation;
		private JComboBox<String> personComboBox, weaponComboBox;
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
			personComboBox = new JComboBox<String>();
			weaponComboBox = new JComboBox<String>();
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
					sp.guessPanel.guessBox.setText(selectedPerson + ", " + roomLocation.getText() + ", " + selectedWeapon);
					if(returnedCard != null){
						sp.guessReturnPanel.guessResultBox.setText(returnedCard.getName());
					}
					suggestionDialog.setVisible(false);
					
				} else if (e.getSource() == cancel) {
					suggestionDialog.setVisible(false);
				}	
			}
		}
		
		public JLabel getRoomlocation() {
			return roomLocation;
		}

		public void setRoomlocation(JLabel roomlocation) {
			this.roomLocation = roomlocation;
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
}
