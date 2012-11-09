package clueGame;

public class Card {
	public enum CardType {PERSON, WEAPON, ROOM}
	private String name;
	private CardType cardType;
	
	public boolean equals(Object anObject){
		if(anObject instanceof Card){
			Card c = (Card) anObject;
			if(this.getCardType().equals(c.getCardType()) && this.getName().equals(c.getName())){
				return true;
			}
		}
		return false;
	}
		
	public Card() {
		super();
	}

	public Card(String name, CardType cardType) {
		super();
		this.name = name;
		this.cardType = cardType;
	}

	//Getters and setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CardType getCardType() {
		return cardType;
	}
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	
}
