package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JPanel;

import clueGame.RoomCell.DoorDirection;

public class Board extends JPanel {
	
	//Variables
	private ArrayList<BoardCell> cells;
	private Map<Character,String> rooms;
	private int numRows;
	private int numColumns;
	private int boardSize;
	private Map<Integer, LinkedList<Integer>> adjMtx;
	private Set<BoardCell> targets;
	private boolean[] visitedPoints;
	private static final int START_NAME = 3;
	private RoomCell roomTemp;
	private BoardCell boardcell;
	private ArrayList<Card> cards;
	private ArrayList<ComputerPlayer> computerPlayers;
	private HumanPlayer humanPlayer;
	private Solution solution;
	private Player whoseTurn;
	private int numPeople;
	private int numRooms;
	private int numWeapons;

	//Constructor - Calls loadConfigFiles() method.
	public Board() {
		adjMtx = new HashMap<Integer, LinkedList<Integer>>();
		targets = new HashSet<BoardCell>();
		rooms = new HashMap<Character, String>();
		cells = new ArrayList<BoardCell>();
		humanPlayer = new HumanPlayer();
		computerPlayers = new ArrayList<ComputerPlayer>();
		cards = new ArrayList<Card>();
		loadConfigFiles();
		boardSize = numRows*numColumns;
		calcAdjacencies();
	}

	//Takes path of the legend and CSV file and calls their helper functions.
	public void loadConfigFiles() {
		String legend = "Clue Game - Legend";
		String csvFile = "ClueGameBoard.csv";
		String players = "player_config.txt";
		String cards = "cards_config.txt";
		try{
			loadLegend(legend);
			loadCSV(csvFile);
			loadPlayers(players);
			loadCards(cards);
		}
		catch(FileNotFoundException e) {
			System.out.println("Can't find the file, check the path.");
		}
		catch(BadConfigFormatException e) {
			System.out.println(e);
		}
	}


	//Iterates through the legend and loads the Initial and Room Name for each room into a HashMap called rooms.
	private void loadLegend(String fname) throws BadConfigFormatException, FileNotFoundException {
		String inString = null;
		Character initial = null;  		// Single letter initials of the space
		String name = null;				// Full name of the room

		FileReader reader = null;
		
		reader = new FileReader(fname);

		Scanner in = new Scanner(reader);
		while(in.hasNextLine()) {
			inString = in.nextLine();
			initial = inString.charAt(0);  //do a toupper
			//if second char is not a comma, throw exception
			if(!(inString.charAt(1) == ',')){
				throw new BadConfigFormatException("comma bad");
				}

			name = inString.substring(START_NAME);
			//System.out.println("Char: " + initial + " name: " + name);
			rooms.put(initial, name);
		}
		//close resources
		in.close();		
	}
	
	//new loadCSV:
	private void loadCSV(String fname) throws BadConfigFormatException, FileNotFoundException{
		FileReader reader = null;
		reader = new FileReader(fname);
		Scanner in = new Scanner(reader);
		int rows = 0;
		int columns = 0;
		while(in.hasNextLine()) {
			String inString = in.next();
			String[] tokens = inString.split(",");
			columns = tokens.length;
			
			for(int i = 0; i < tokens.length; i++) {
				BoardCell cell = null;
				if(tokens[i].equalsIgnoreCase("W")) {
					cell = new WalkwayCell(rows, i);
				} else {
					cell = new RoomCell(tokens[i], rows, i, rooms);
				}
				cells.add(cell);
			}
			rows++;
		}
	numColumns = columns;
	numRows = rows;
	in.close();	
	}
	//Load Players
	public void loadPlayers(String fileName) throws BadConfigFormatException, FileNotFoundException{
		FileReader reader = null;
		reader = new FileReader(fileName);
		Scanner in = new Scanner(reader);
		//Load human player
		String firstString = in.nextLine();
		String[] firstTokens = firstString.split(",");
		humanPlayer.setName(firstTokens[0]);
		String number = firstTokens[1];
		String number2 = firstTokens[2];
		int location = Integer.parseInt(number);
		int location2 = Integer.parseInt(number2);
		humanPlayer.setLocationX(location);
		humanPlayer.setLocationY(location2);
		String strColor = firstTokens[3];
		Color color = convertColor(strColor);
		humanPlayer.setColor(color);
		//Load computer players
		while(in.hasNextLine()) {
			String inString = in.nextLine();
			String[] tokens = inString.split(",");
			ComputerPlayer compPlayer = new ComputerPlayer();
			compPlayer.setName(tokens[0]);
			number = tokens[1];
			number2 = tokens[2];
			location = Integer.parseInt(number);
			location2 = Integer.parseInt(number2);
			compPlayer.setLocationX(location);
			compPlayer.setLocationY(location2);
			strColor = tokens[3];
			color = convertColor(strColor);
			compPlayer.setColor(color);
			computerPlayers.add(compPlayer);
		}
	in.close();	
	}
	//Load Cards
	public void loadCards(String fileName) throws BadConfigFormatException, FileNotFoundException{
		FileReader reader = null;
		reader = new FileReader(fileName);
		Scanner in = new Scanner(reader);
		while(in.hasNextLine()) {
			String inString = in.nextLine();
			String[] tokens = inString.split(",");
			Card card = new Card();
			card.setName(tokens[0]);
			if(tokens[1].equals("PERSON")){
				card.setCardType(Card.CardType.PERSON);
				++numPeople;
			}
			else if(tokens[1].equals("ROOM")){
				card.setCardType(Card.CardType.ROOM);
				++numRooms;
			}
			else{
				card.setCardType(Card.CardType.WEAPON);
				++numWeapons;
			}
			cards.add(card);
		}
	in.close();	
	}
	//Calculates the board index, given a row and column number.
	public int calcIndex(int row, int column) {
		return ((row * numColumns) + (column));
	}
	
	//Calculates the RoomCell, given a row and column (Works with our test)
	public RoomCell getRoomCellAt(int row, int column) {
		int index = calcIndex(row, column);
		roomTemp = (RoomCell) cells.get(index);
		return  roomTemp;
	}
	
	//Duplicate of previous test except can pass in an index instead of row and column.
	public BoardCell getRoomCellAt(int index) {
		boardcell = cells.get(index);
		return  boardcell;
	}
//***************************************************************************
	
//* IntBoard Code ***********************************************************
	
	//Calculates the adjacency lists for each grid space.
	public void calcAdjacencies() {
		for(int i=0; i < boardSize; i++) {
			LinkedList<Integer> list = new LinkedList<Integer>();
			BoardCell bc = cells.get(i);
			if (bc.isWalkway() || bc.isDoorway()) {
				//Checking move upwards
				if(i - numColumns  >= 0) {
					BoardCell b = cells.get(i - numColumns);
					if(b.isWalkway()) {
						list.add(i-numColumns);
					}

					if(b.isDoorway() == true) {
						RoomCell r = (RoomCell) b;
						if (r.getDoorDirection() == RoomCell.DoorDirection.DOWN) 
							list.add(i-numColumns);
					}
				}
				//Checking move to left
				//(i % COLS) returns the column number
				if(i % numColumns != 0) {
					BoardCell b = cells.get(i - 1);
					if(b.isWalkway()) {
						list.add(i-1);
					}
					if(b.isDoorway() == true) {
						RoomCell r = (RoomCell) b;
						if (r.getDoorDirection() == RoomCell.DoorDirection.RIGHT) 
							list.add(i-1);
					}
				}
				//Checking move to right
				if(i % numColumns != (numColumns - 1)) {
					BoardCell b = cells.get(i + 1);
					if(b.isWalkway()) {
						list.add(i+1);
					}
					if(b.isDoorway() == true) {
						RoomCell r = (RoomCell) b;
						if (r.getDoorDirection() == RoomCell.DoorDirection.LEFT) 
							list.add(i+1);
					}
				}
				//Checking move downwards
				if(i + numColumns < boardSize) {
					BoardCell b = cells.get(i + numColumns);
					if(b.isWalkway()) {
						list.add(i+numColumns);
					}
					if(b.isDoorway() == true) {
						RoomCell r = (RoomCell) b;
						if (r.getDoorDirection() == RoomCell.DoorDirection.UP) 
							list.add(i+numColumns);
					}
				}
			}
			adjMtx.put(i, list);
		}
	}
	
	//Determines possible move locations based on a starting location and the die roll
	public void calcTargets(int startLocation, int numberOfSteps) {
		targets = new HashSet<BoardCell>();
		LinkedList<Integer> path = new LinkedList<Integer>();
		LinkedList<Integer> list = new LinkedList<Integer>();
		
		//Sets every point to unseen
		visitedPoints = new boolean[boardSize];				
		for(int i = 0; i < boardSize; i++){
			visitedPoints[i] = false;
		}
		
		visitedPoints[startLocation] = true;
		list = getAdjList(startLocation);
		calcTargetsHelper(path, list, numberOfSteps);
	}
	
	//The helper function for calcTargets (contains a recursive call).
	public void calcTargetsHelper(LinkedList<Integer> path, LinkedList<Integer> adjList, int numberOfSteps) {
		for(Integer i : adjList) {
			if(visitedPoints[i] == false) {
				visitedPoints[i] = true;
				path.addLast(i);
				if(path.size() == numberOfSteps || cells.get(i).isDoorway()) {					
					BoardCell b = cells.get(i);
					b.setLocation(i);
					targets.add(b);
				} else {
					LinkedList<Integer> recursiveList = new LinkedList<Integer>(); 
					recursiveList = getAdjList(i);
					calcTargetsHelper(path, recursiveList, numberOfSteps);
				}
				path.removeLast();
				visitedPoints[i] = false;
			}
		}
	}
	
	
	//Gets the list of targets in the form of a HashSet
	public Set<BoardCell> getTargets() {
		return targets;
	}

	//Returns the adjacency list for a given index
	public LinkedList<Integer> getAdjList(int index) {
		LinkedList<Integer> b = new LinkedList<Integer>();
		b = adjMtx.get(index);
		return b;
	}
	
//*Clue players methods***********************************************************
	// Be sure to trim the color, we don't want spaces around the name
	public Color convertColor(String strColor) {
		Color color; 
		try {     
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
			color = (Color)field.get(null); } 
		catch (Exception e) {  
			color = null; // Not defined } 
		}
		return color;
	}
	public void selectAnswer(){

	}
	
	public void deal(ArrayList<String> cardlist){
		if(cardlist.size() < cards.size()){
			Random rand = new Random();
			int number = rand.nextInt(cards.size());
			if(!cardlist.contains(cards.get(number).getName())){
				if(humanPlayer.getMyCards().size() < 3){
					if(cards.get(number).getCardType() == Card.CardType.PERSON && !humanPlayer.getMyCards().contains(Card.CardType.PERSON)){
						humanPlayer.getMyCards().add(cards.get(number));
						cardlist.add(cards.get(number).getName());
					}
					if(cards.get(number).getCardType() == Card.CardType.ROOM && !humanPlayer.getMyCards().contains(Card.CardType.ROOM)){
						humanPlayer.getMyCards().add(cards.get(number));
						cardlist.add(cards.get(number).getName());
					}
					if(cards.get(number).getCardType() == Card.CardType.WEAPON && !humanPlayer.getMyCards().contains(Card.CardType.WEAPON)){
						humanPlayer.getMyCards().add(cards.get(number));
						cardlist.add(cards.get(number).getName());
					}
				}
				for(ComputerPlayer p: computerPlayers){
					if(!cardlist.contains(cards.get(number).getName()) && p.getMyCards().size() < 4){
						p.getMyCards().add(cards.get(number));
						cardlist.add(cards.get(number).getName());
					}
					number = rand.nextInt(cards.size());
				}
			}
			deal(cardlist);
		}
	}

	public boolean checkAccusation(String person, String room, String weapon){
		if(person.equals(solution.getPerson()) && room.equals(solution.getRoom()) && weapon.equals(solution.getWeapon())){
			return true;
		}
		else{
			return false;
		}
	}

	public Card handleSuggestion(String person, String weapon, String room){
		ArrayList<Player> players = new ArrayList<Player>();
		Card card = new Card();
		card = null;
		Random rand = new Random();
		for(Player p: computerPlayers){
			players.add(p);
		}
		players.add(humanPlayer);
		int ranNumber = rand.nextInt(players.size());
		for(int i = 0; i < players.size()-1; ++i){
			if(card != null){
				break;
			}
			if(ranNumber + i < 6 && whoseTurn != players.get(ranNumber + i)){
				card = players.get(ranNumber + i).disproveSuggestion(person, room, weapon);
			}
			else{
				if(whoseTurn != players.get(Math.abs(ranNumber -i))){
					card = players.get(Math.abs(ranNumber - i)).disproveSuggestion(person, room, weapon);
				}	
			}
		}
		return card;
	}
//Draws the board
	public void paintComponent(Graphics g) {
		ArrayList<Player> players = new ArrayList<Player>();
		for (Player cp : computerPlayers) {
			players.add(cp);
		}
		players.add(humanPlayer);
		super.paintComponent(g);
		for(BoardCell c: cells){
			c.draw(g);
		}
		for(BoardCell c: cells){
			if(c.isRoom()){
				RoomCell rc = (RoomCell) c;
				rc.drawName(g);
			}
		}
		for(Player p : players) {
			p.draw(g);
		}
	}
	
//*Getters and Setters***********************************************************
	
	
	public ArrayList<BoardCell> getCells() {
		return cells;
	}

	public Player getWhoseTurn() {
		return whoseTurn;
	}

	public void setWhoseTurn(Player whoseTurn) {
		this.whoseTurn = whoseTurn;
	}

	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
	}

	public int getNumPeople() {
		return numPeople;
	}

	public void setNumPeople(int numPeople) {
		this.numPeople = numPeople;
	}

	public int getNumRooms() {
		return numRooms;
	}

	public void setNumRooms(int numRooms) {
		this.numRooms = numRooms;
	}

	public int getNumWeapons() {
		return numWeapons;
	}

	public void setNumWeapons(int numWeapons) {
		this.numWeapons = numWeapons;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

	public ArrayList<ComputerPlayer> getComputerPlayers() {
		return computerPlayers;
	}

	public void setComputerPlayers(ArrayList<ComputerPlayer> computerPlayers) {
		this.computerPlayers = computerPlayers;
	}

	public HumanPlayer getHumanPlayer() {
		return humanPlayer;
	}

	public void setHumanPlayer(HumanPlayer humanPlayer) {
		this.humanPlayer = humanPlayer;
	}

	public void setCells(ArrayList<BoardCell> cells) {
		this.cells = cells;
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}

	public void setRooms(Map<Character, String> rooms) {
		this.rooms = rooms;
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public void setNumColumns(int numColumns) {
		this.numColumns = numColumns;
	}
//******************************************************************

}
