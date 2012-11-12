package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	
	private SouthPanel die;
	private NorthPanel sp;
	
	public GameControlPanel(){
		die = new SouthPanel();
		sp = new NorthPanel();
		setLayout(new BorderLayout());
		add(sp, BorderLayout.NORTH);
		add(die, BorderLayout.SOUTH);
	}
	
	public JButton createButton(String name){
		JButton button = new JButton(name);
		button.setPreferredSize(new Dimension(200, 60));
		return button;
	}
	
	public class NorthPanel extends JPanel{
		private TurnPanel turnPanel;
		public NorthPanel(){
			turnPanel = new TurnPanel();
			setLayout(new GridLayout(1, 3));
			add(turnPanel);
			add(createButton("NextPlayer"));
			add(createButton("Make an accusation"));
		}
		public class TurnPanel extends JPanel {
			private JLabel title;
			private JTextField textBox;
			public TurnPanel() {
				title = new JLabel("Whose turn?");
				textBox = new JTextField("Person");
				setLayout(new FlowLayout());
				add(title);
				add(textBox);
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
			private JTextField rollBox;
			
			public Die() {
				setBorder(new TitledBorder(new EtchedBorder(), "Die"));
				roll = new JLabel("Roll");
				rollBox = new JTextField("0");
				add(roll);
				add(rollBox);
			}
		}
		
		public class Guess extends JPanel {
			private JLabel guess;
			private JTextField guessBox;
			public Guess() {
				setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
				guess = new JLabel("Guess");
				guessBox = new JTextField("Guess will be displayed here...");
				add(guess);
				add(guessBox);
			}
		}
		
		public class GuessReturn extends JPanel {
			private JLabel guessResultLabel;
			private JTextField guessResultBox;
			public GuessReturn() {
				setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
				guessResultLabel = new JLabel("Response");
				guessResultBox = new JTextField("Response goes here...");
				
				add(guessResultLabel);
				add(guessResultBox);
			}
		}
	}

}
