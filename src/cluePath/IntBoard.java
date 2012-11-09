package cluePath;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class IntBoard {
	static int ROWS = 4;
	static int COLS = 4;
	static int SIZE_OF_BOARD = ROWS*COLS;
	static int GRID_PIECES;
	private Map<Integer, LinkedList<Integer>> adjMtx = new HashMap<Integer, LinkedList<Integer>>();
	private boolean[] visitedPoints = new boolean[SIZE_OF_BOARD]; 
	TreeSet<Integer> targets = new TreeSet<Integer>();
	
	//IntBoard board = new IntBoard();
	
	public IntBoard() {
		super();
		//board = new IntBoard();
	}
	
	//Calculates the adjacency lists for each grid space.
	public void calcAdjacencies() {
		for(int i=0; i < (SIZE_OF_BOARD); i++) {
			LinkedList<Integer> list = new LinkedList<Integer>();
			//Checking move upwards
			if(i - COLS  >= 0) {
				list.add(i-COLS);
			}
			//Checking move to left
			//(i % COLS) returns the column number
			if(i % COLS != 0) {
				list.add(i-1);
			}
			//Checking move to right
			if(i % COLS != (COLS - 1)) {
				list.add(i+1);
			}
			//Checking move downwards
			if(i + COLS < SIZE_OF_BOARD) {
				list.add(i+COLS);
			}
			adjMtx.put(i, list);
		}
	}
	
	//Prints the adjacency lists for all indices
	public void printAdjacencyList() {
		for(int i=0; i < SIZE_OF_BOARD; i++) {
			System.out.println(i + " = " + adjMtx.get(i));
		}
	}
	
	//Determines possible move locations based on a starting location and the die roll
	public void calcTargets(int startLocation, int numberOfSteps) {
		LinkedList<Integer> path = new LinkedList<Integer>();
		LinkedList<Integer> list = new LinkedList<Integer>();
		//Sets every point to unseen
		for(int i = 0; i < SIZE_OF_BOARD; i++){
			visitedPoints[i] = false;
		}
		visitedPoints[startLocation] = true;
		list = getAdjList(startLocation);
		calcTargetsHelper(path, list, numberOfSteps);
	}
	
	public void calcTargetsHelper(LinkedList<Integer> path, LinkedList<Integer> adjList, int numberOfSteps) {
		for(int vertex : adjList) {
			if(visitedPoints[vertex] == false) {
				visitedPoints[vertex] = true;
				path.addLast(vertex);
				if(path.size() == numberOfSteps) {
					targets.add(vertex);
				} else {
					LinkedList<Integer> recursiveList = new LinkedList<Integer>();
					recursiveList = getAdjList(vertex);
					calcTargetsHelper(path, recursiveList, numberOfSteps);
				}
				path.removeLast();
				visitedPoints[vertex] = false;
			}
		}
	}
	
	//Prints the targets
	public void printTargets(int startLocation, int numberOfSteps) {
		System.out.println("(" + startLocation + "," + numberOfSteps + ")");
		for(int i : targets) {
			System.out.println(i);
		}
		System.out.println();
	}
	
	//Gets the list of targets in the form of a TreeSet
	public TreeSet<Integer> getTargets() {
		return targets;
	}
	
	//Returns the adjacency list for a given index
	public LinkedList<Integer> getAdjList(int index) {
		return adjMtx.get(index);
	}
	
	//Calculates the grid index based on the row and column.
	public static int calcIndex(int row, int column) {
		int gridIndex = row*ROWS + column;
		return gridIndex;
	}
	
	public static void main(String[] Args) {
		//IntBoard board = new IntBoard();
	}
}
