package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;

public class GameControlPanel extends JPanel {
	
	private SouthPanel sp;
	private NorthPanel np;
	private boolean selectedTarget = true;
	private Board board;
	private String person;
	private int whichPlayer;
	private Suggestion storedSuggestion;
	private boolean correctAnswer;
	

	public GameControlPanel(Board board){
		person = "Miss Scarlet           ";
		sp = new SouthPanel();
		np = new NorthPanel();
		this.board = board;
		board.setWhoseTurn(board.getHumanPlayer());
		correctAnswer = false;
		whichPlayer = 0;
		setLayout(new BorderLayout());
		add(np, BorderLayout.NORTH);
		add(sp, BorderLayout.SOUTH);
	}
	
	public JButton createButton(String name){
		JButton button = new JButton(name);
		button.setPreferredSize(new Dimension(200, 60));
		return button;
	}
	
	public class NorthPanel extends JPanel{
		private TurnPanel turnPanel;
		private SuggestionDialog sd;
		public NorthPanel(){
			turnPanel = new TurnPanel();
			setLayout(new GridLayout(1, 3));
			add(turnPanel);
			
			NextButtonListener listener = new NextButtonListener();
			JButton nextButton = createButton("NextPlayer");
			JButton accusationButton = createButton("Make an accusation");
			nextButton.addActionListener(listener);
			//accusationButton.addActionListener(listener);
			add(nextButton);
			add(accusationButton);
		}
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
		
		public class NextButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				Random rand = new Random();
				int dieRoll = rand.nextInt(6) + 1;
				String dieRollString = "" + dieRoll;
				
				if(board.getWhoseTurn() == board.getHumanPlayer()) {
					board.getHumanPlayer().setTurnFinished(false);
					humanTurn(dieRoll);
					sp.diePanel.rollBox.setText(dieRollString);
					whichPlayer = 0;
					board.setWhoseTurn(board.getComputerPlayers().get(whichPlayer));
					turnPanel.textBox.setText(board.getWhoseTurn().getName());
				}
				else if(board.getWhoseTurn() == board.getComputerPlayers().get(4)){
					makeMove(dieRoll);
					board.setWhoseTurn(board.getHumanPlayer());
					turnPanel.textBox.setText(board.getWhoseTurn().getName());
				}
				else {
					makeMove(dieRoll);
					board.setWhoseTurn(board.getComputerPlayers().get(++whichPlayer));
					turnPanel.textBox.setText(board.getWhoseTurn().getName());
					sp.diePanel.rollBox.setText(dieRollString);
				}
			}
		}

		public void humanTurn(int dieRoll){
			HumanPlayer hp = board.getHumanPlayer();
			int row = hp.getLocationX();
			int column = hp.getLocationY();
			board.calcTargets(board.calcIndex(row, column), dieRoll);
			board.repaint();

		}
		
		public void makeMove(int dieRoll) {
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
		
	}
	

	public class SouthPanel extends JPanel {
		private Die diePanel;
		private Guess guessPanel;
		private GuessReturn guessReturnPanel;


		public SouthPanel() {
			diePanel = new Die();
			guessPanel = new Guess();
			guessReturnPanel = new GuessReturn();
			setLayout(new BorderLayout());
			
			add(diePanel, BorderLayout.WEST);
			add(guessPanel, BorderLayout.CENTER);
			add(guessReturnPanel, BorderLayout.EAST);
		}
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
	
	public class SuggestionDialog extends JDialog {
		private JLabel yourRoom, person, weapon;
		private JLabel roomLocation;


		private JComboBox personComboBox, weaponComboBox;
		private JButton submit, cancel;
		
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
		
		public class ButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == submit) {
					String selectedPerson = (String) personComboBox.getSelectedItem();
					String selectedWeapon = (String) weaponComboBox.getSelectedItem();
					Card returnedCard = board.handleSuggestion(selectedPerson, selectedWeapon, roomLocation.getText());
					sp.guessPanel.guessBox.setText(selectedPerson + ", " + roomLocation.getName() + ", " + selectedWeapon);
					if(returnedCard != null){
						sp.guessReturnPanel.guessResultBox.setText(returnedCard.getName());
					}
					
				} else {
					
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
	
	//////////////////////////////////////
	public boolean isSelectedTarget() {
		return selectedTarget;
	}

	public void setSelectedTarget(boolean selectedTarget) {
		this.selectedTarget = selectedTarget;
	}
}
