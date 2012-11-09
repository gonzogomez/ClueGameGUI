package clueGame;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ClueGameGUI extends JFrame {
	private DetectiveNotes notes;
	private JMenuItem exit;
	private JMenuItem dNotes;
	private Board board;
	
	public ClueGameGUI(){
		super();
		board = new Board();
		notes = new DetectiveNotes();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(825, 880));
		setTitle("Clue Game");
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		add(board);
	}
	
	private JMenu createFileMenu()
	{
		JMenu menu = new JMenu("File"); 
		menu.add(createFileNotesItem());
		menu.add(createFileExitItem());

		return menu;
	}
	private JMenuItem createFileExitItem()
	{
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
	
	private JMenuItem createFileNotesItem()
	{
		dNotes = new JMenuItem("Show Detective Notes");
		
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e)
			{
				notes.setVisible(true);
			}
		}
		dNotes.addActionListener(new MenuItemListener());
		return dNotes;
	}
	public static void main(String[] args) {
		ClueGameGUI clueBoard = new ClueGameGUI();
		clueBoard.setVisible(true);

	}

}
