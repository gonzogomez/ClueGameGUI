package clueGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	private char lastRoomVisited;
	private ArrayList<Card> seenCards;
	private boolean accusationFlag;
	private boolean correctAnswer;
	private Board board;
	
	//Constructor
	public ComputerPlayer(Board board) {
		super();
		this.board = board;
		accusationFlag = false;
		setCorrectAnswer(false);
		seenCards = new ArrayList<Card>();
	}
		
	public void pickLocation(Set<BoardCell> targets){
		boolean pickedNewLocation = false;
		for(BoardCell b : targets){
			if(b.isRoom() == true){
				RoomCell room = (RoomCell) b;
				if(room.getInitial() != lastRoomVisited){
					this.setLocationX(b.getRow());
					this.setLocationY(b.getColumn());
					this.setLocation(b.getLocation());
					lastRoomVisited = room.getInitial();
					pickedNewLocation = true;
				}
			}
		}
		if(pickedNewLocation == false){
			Random rand = new Random();
			int ranNumber = rand.nextInt(targets.size());
			int i = 0;
			for(BoardCell b: targets){
				if(i == ranNumber){
					this.setLocationX(b.getRow());
					this.setLocationY(b.getColumn());
					this.setLocation(b.getLocation());
				}
				i++;
			}
		}
	}
	
	public Suggestion createSuggestion(ArrayList<Card> deck, ArrayList<BoardCell> cells){
		Random rand = new Random();
		RoomCell rcell = (RoomCell) cells.get(getLocation());
		Suggestion sugg = new Suggestion();
		String weapon = null;
		String person = null;
		String room = convertInitial(rcell.getInitial());
		while(weapon == null || person == null){
			int ranNumber = rand.nextInt(deck.size());
			if(!seenCards.contains(deck.get(ranNumber))){
				if(deck.get(ranNumber).getCardType() == Card.CardType.PERSON){
					person = deck.get(ranNumber).getName();
				}
				if(deck.get(ranNumber).getCardType() == Card.CardType.WEAPON){
					weapon = deck.get(ranNumber).getName();
				}
			}
		}
		sugg.setPerson(person);
		sugg.setWeapon(weapon);
		sugg.setRoom(room);
		return sugg;
	}
	

	public void makeAccusation(Suggestion suggestion) {
		setCorrectAnswer(board.checkAccusation(suggestion.getPerson(), suggestion.getRoom(), suggestion.getWeapon()));
	}
	
	
	public void updateSeen(Card seen){
		seenCards.add(seen);
	}
	
	//Converts room initial to full name
	public String convertInitial(char initial){
		String name = null;
		if(initial == 'C'){
			name = "Conservatory";
		}
		if(initial == 'K'){
			name = "Kitchen";
		}
		if(initial == 'B'){
			name = "Ballroom";
		}
		if(initial == 'R'){
			name = "Billiard room";
		}
		if(initial == 'L'){
			name = "Library";
		}
		if(initial == 'S'){
			name = "Study";
		}
		if(initial == 'D'){
			name = "Dining room";
		}
		if(initial == 'O'){
			name = "Lounge";
		}
		if(initial == 'H'){
			name = "Hall";
		}
		return name;
	}
	//Getters and setters

	public char getLastRoomVisited() {
		return lastRoomVisited;
	}

	public void setLastRoomVisited(char lastRoomVisited) {
		this.lastRoomVisited = lastRoomVisited;
	}

	public ArrayList<Card> getSeenCards() {
		return seenCards;
	}

	public void setSeenCards(ArrayList<Card> seenCards) {
		this.seenCards = seenCards;
	}
	
	public boolean isAccusationFlag() {
		return accusationFlag;
	}

	public void setAccusationFlag(boolean makeAccusation) {
		this.accusationFlag = makeAccusation;
	}

	public boolean isCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(boolean correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	
}
