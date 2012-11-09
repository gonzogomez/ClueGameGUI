package clueGame;

public class Solution {
	private String person;
	private String weapon;
	private String room;
	
	
	public Solution(String person, String room ,String weapon) {
		super();
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}

//Getters and setters
	public String getPerson() {
		return person;
	}


	public void setPerson(String person) {
		this.person = person;
	}


	public String getWeapon() {
		return weapon;
	}


	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}


	public String getRoom() {
		return room;
	}


	public void setRoom(String room) {
		this.room = room;
	}
	
	
	
}
