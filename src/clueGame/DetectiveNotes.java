package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class DetectiveNotes extends JDialog {
	private People people;
	private Rooms rooms;
	private PersonGuess personGuess;
	private RoomGuess roomGuess;
	private Weapons weapons;
	private WeaponGuess weaponGuess;
	
	public DetectiveNotes() {
		people = new People();
		rooms = new Rooms();
		personGuess = new PersonGuess();
		roomGuess = new RoomGuess();
		weapons = new Weapons();
		weaponGuess = new WeaponGuess();
		
		setSize(new Dimension(550, 600));
		
		setLayout(new GridLayout(3,2));
		
		add(people);
		add(personGuess);
		add(rooms);
		add(roomGuess);
		add(weapons);
		add(weaponGuess);
	}
	
	public class People extends JPanel {
		private JCheckBox scarlet, mustard, green, white, peacock, plum;
		
		public People(){
			scarlet = new JCheckBox("Miss Scarlet");
			mustard = new JCheckBox("Colonel Mustard");
			green = new JCheckBox("Mr. Green");
			white = new JCheckBox("Mrs. White");
			peacock = new JCheckBox("Mrs. Peacock");
			plum = new JCheckBox("Professor Plum");
			
			setLayout(new GridLayout(0,2));
			add(scarlet);
			add(mustard);
			add(green);
			add(white);
			add(peacock);
			add(plum);
			
			setBorder(new TitledBorder(new EtchedBorder(), "People"));
		}
		
		
		
		
	}
	
	public class Rooms extends JPanel {
		private JCheckBox kitchen, diningRoom, lounge, ballroom, conservatory, hall, study, library, billiardRoom;
		
		public Rooms() {
			kitchen = new JCheckBox("Kitchen");
			diningRoom = new JCheckBox("Dining Room");
			lounge = new JCheckBox("Lounge");
			ballroom = new JCheckBox("Ballroom");
			conservatory = new JCheckBox("Conservatory");
			hall = new JCheckBox("Hall");
			study = new JCheckBox("Study");
			library = new JCheckBox("Library");
			billiardRoom = new JCheckBox("Billiard Room");
			
			setLayout(new GridLayout(0,2));
			add(kitchen);
			add(diningRoom);
			add(lounge);
			add(ballroom);
			add(conservatory);
			add(hall);
			add(study);
			add(library);
			add(billiardRoom);
			
			setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		}
	}
	
	public class Weapons extends JPanel {
		private JCheckBox candleStick, knife, leadPipe, revolver, rope, wrench;
		
		public Weapons(){
			candleStick = new JCheckBox("Candlestick");
			knife = new JCheckBox("Knife");
			leadPipe = new JCheckBox("Lead Pipe");
			revolver = new JCheckBox("Revolver");
			rope = new JCheckBox("Rope");
			wrench = new JCheckBox("Wrench");
			
			setLayout(new GridLayout(0,2));
			add(candleStick);
			add(knife);
			add(leadPipe);
			add(revolver);
			add(rope);
			add(wrench);
			
			setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		}
		
	}
	
	public class PersonGuess extends JPanel {
		private JComboBox personGuessBox;
		
		public PersonGuess(){
			personGuessBox = new JComboBox();
			createPersonGuessBox();
			setLayout(new BorderLayout());
			add(personGuessBox, BorderLayout.WEST);
			setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		}
		private void createPersonGuessBox() {
			personGuessBox.addItem("Don't Know");
			personGuessBox.addItem("Miss Scarlet");
			personGuessBox.addItem("Colonel Mustard");
			personGuessBox.addItem("Mr. Green");
			personGuessBox.addItem("Mrs. White");
			personGuessBox.addItem("Mrs. Peacock");
			personGuessBox.addItem("Professor Plum");
			
		}
		
	}
	
	
	
	
	//Room Guess - Combo Box
	public class RoomGuess extends JPanel {
		private JComboBox roomGuessBox;
		
		public RoomGuess() {
			roomGuessBox = new JComboBox();
			createRoomGuessBox();
			setLayout(new BorderLayout());
			add(roomGuessBox, BorderLayout.WEST);
			setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		}
		
		private void createRoomGuessBox() {
			roomGuessBox.addItem("Don't Know");
			roomGuessBox.addItem("Kitchen");
			roomGuessBox.addItem("Dining Room");
			roomGuessBox.addItem("Lounge");
			roomGuessBox.addItem("Ballroom");
			roomGuessBox.addItem("Conservatory      ");
			roomGuessBox.addItem("Hall");
			roomGuessBox.addItem("Study");
			roomGuessBox.addItem("Library");
			roomGuessBox.addItem("Billiard Room");
		}
	}
	
	
	
	
	//Weapon Guess - Combo Box
	public class WeaponGuess extends JPanel {
		private JComboBox weaponGuessBox;
		
		public WeaponGuess() {
			weaponGuessBox = new JComboBox();
			createWeaponGuessCombo();
			setLayout(new BorderLayout());
			add(weaponGuessBox, BorderLayout.WEST);
			setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));
		}
		
		private void createWeaponGuessCombo() {
			weaponGuessBox.addItem("Don't Know");
			weaponGuessBox.addItem("Candlestick         ");
			weaponGuessBox.addItem("Knife");
			weaponGuessBox.addItem("Lead Pipe");
			weaponGuessBox.addItem("Revolver");
			weaponGuessBox.addItem("Rope");
			weaponGuessBox.addItem("Wrench");
		}
		
	}
}
