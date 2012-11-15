package clueGame;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;

@SuppressWarnings("serial")
public class PlayerDisplay extends JPanel {
	private JLabel myCards;
	private JPanel pd;
	private JPanel rd;
	private JPanel wd;
	
	//Constructor
	public PlayerDisplay(HumanPlayer hp) {
		myCards = new JLabel("My Cards");
		pd = new JPanel();
		rd = new JPanel();
		wd = new JPanel();
		String personCard = null;
		String roomCard = null;
		String weaponCard = null;
		
		for(Card c : hp.getMyCards()) {
			if (c.getCardType() == CardType.PERSON) {
				personCard = c.getName();
			}
			else if (c.getCardType() == CardType.ROOM) {
				roomCard = c.getName();
			}
			else {
				weaponCard = c.getName();
			}
		}
		
		pd = createDisplay("Person", personCard);
		rd = createDisplay("Room", roomCard);
		wd = createDisplay("Weapon", weaponCard);
		
		setLayout(new GridLayout(8,1));
		add(myCards);
		add(pd);
		add(rd);
		add(wd);
	}
	
	//Creates person, room, and weapon textFields that display the human player's cards.
	public JPanel createDisplay(String title, String name) {
		JPanel panel = new JPanel();
		JTextField textBox = new JTextField(name);
		panel.add(textBox);
		panel.setBorder(new TitledBorder(new EtchedBorder(), title));
		return panel;
	}

}
