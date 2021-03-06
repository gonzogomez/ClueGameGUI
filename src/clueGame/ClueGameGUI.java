package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class ClueGameGUI extends JFrame {
	private DetectiveNotes notes;
	private JMenuItem exit;
	private JMenuItem dNotes;
	private Board board;
	private PlayerDisplay pd;
	private GameControlPanel gcp;
	
	//Constructor
	public ClueGameGUI() {
		super();
		board = new Board();
		notes = new DetectiveNotes(board.getCards());
		pd = new PlayerDisplay(board.getHumanPlayer());
		gcp = new GameControlPanel(board);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(815, 870));
		setTitle("Clue Game");
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		setLayout(new BorderLayout());
		add(board, BorderLayout.CENTER);
		add(pd, BorderLayout.EAST);
		add(gcp, BorderLayout.SOUTH);
		
	}
	
	//Creates File menu
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File"); 
		menu.add(createFileNotesItem());
		menu.add(createFileExitItem());

		return menu;
	}
	
	//Creates Exit option 
	private JMenuItem createFileExitItem() {
		exit = new JMenuItem("Exit");

		
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);

			}
		}
		exit.addActionListener(new MenuItemListener());
		return exit;
	}
	
	//Creates DetectiveNotes option
	private JMenuItem createFileNotesItem() {
		dNotes = new JMenuItem("Show Detective Notes");
		
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				notes.setVisible(true);
			}
		}
		dNotes.addActionListener(new MenuItemListener());
		return dNotes;
	}
	
	//Main
	public static void main(String[] args) {
		ClueGameGUI clueBoard = new ClueGameGUI();
		clueBoard.setVisible(true);
		JOptionPane.showMessageDialog(clueBoard, "You are Miss Scarlet, press Next Player to begin play.", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);

	}

}
