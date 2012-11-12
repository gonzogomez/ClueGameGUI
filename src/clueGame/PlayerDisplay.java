package clueGame;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PlayerDisplay extends JPanel {
	private JLabel myCards;
	private JPanel pd;
	private JPanel rd;
	private JPanel wd;
	
	public PlayerDisplay() {
		myCards = new JLabel("My Cards");
		pd = new JPanel();
		rd = new JPanel();
		wd = new JPanel();

		setLayout(new GridLayout(8,1));
		add(myCards);
		add(pd = createDisplay("People"));
		add(rd = createDisplay("Rooms"));
		add(wd = createDisplay("Weapons"));

	}
	public JPanel createDisplay(String name) {
		JPanel panel = new JPanel();
		JTextField textBox = new JTextField(name);
		panel.add(textBox);
		panel.setBorder(new TitledBorder(new EtchedBorder(), name));
		return panel;
	}

}
