package clueGame;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;

public class PlayerDisplay extends JPanel {
	private JLabel myCards;
	private JPanel pd;
	private JPanel rd;
	private JPanel wd;
	
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
		setLayout(new GridLayout(8,1));
		add(myCards);
		add(pd = createDisplay("Person", personCard));
		add(rd = createDisplay("Room", roomCard));
		add(wd = createDisplay("Weapon", weaponCard));

	}
	public JPanel createDisplay(String title, String name) {
		JPanel panel = new JPanel();
		JTextField textBox = new JTextField(name);
		panel.add(textBox);
		panel.setBorder(new TitledBorder(new EtchedBorder(), title));
		return panel;
	}

}
