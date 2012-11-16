package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.*;

import clueGame.Card.CardType;

@SuppressWarnings("serial")
public class DetectiveNotes extends JDialog {
	private ArrayList<Card> deck;
	
	public DetectiveNotes(ArrayList<Card> deck) {
		this.deck = deck;
		
		setSize(new Dimension(550, 600));
		setLayout(new GridLayout(3,2));
		
		add(createCheckPanel("People", CardType.PERSON));
		add(createComboBoxPanel("Person Guess", CardType.PERSON));
		add(createCheckPanel("Rooms", CardType.ROOM));
		add(createComboBoxPanel("Room Guess", CardType.ROOM));
		add(createCheckPanel("Weapons", CardType.WEAPON));
		add(createComboBoxPanel("Weapon Guess", CardType.WEAPON));
	}
	
	public JPanel createCheckPanel(String name, CardType ctype){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		for(Card c: deck){
			if(c.getCardType() == ctype){
				panel.add(new JCheckBox(c.getName()));
			}
		}	
		panel.setBorder(new TitledBorder(new EtchedBorder(), name));
		return panel;
	}
	
	public JPanel createComboBoxPanel(String name, CardType ctype){
		JPanel panel = new JPanel();
		JComboBox cb = new JComboBox();
		panel.setLayout(new BorderLayout());
		cb.addItem("Don't Know");
		for(Card c : deck){
			if(c.getCardType() == ctype){
				cb.addItem(c.getName());
			}
		}
		panel.add(cb, BorderLayout.WEST);
		panel.setBorder(new TitledBorder(new EtchedBorder(), name));
		return panel;
	}
}
