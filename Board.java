import java.util.*;
public class Board {
	 private int[][] tiles; 
	 
	 private int[][] goal;
	 int blankX;
	 int blankY;

	
	 int hamming = 0;
	 int manhattan = 0;
	 ArrayList<Board> neighbors = new ArrayList<Board>();
	 int totalCost = 0;
	 
	 
	public Board(int[][] newTiles, int[][] goalState){
		tiles = newTiles;
		goal = goalState;
		for (int i = 0; i < tiles.length; i++){
			for (int j = 0; j < tiles[0].length; j++){
				if (tiles[i][j] == 0){
					blankX = i;
					blankY = j;
				}
			}
		}	
	}
	
	
	
	void generateNeighbors(ArrayList<Board> explored){
		int count = 0;
		
		if (canMoveDown()){
			int[][] tempTiles = new int[3][3];
			for (int i = 0; i < 3; i++){
				for (int j = 0; j < 3; j++){
					tempTiles[i][j] = tiles[i][j];
				}
			}
			Board b = new Board(tempTiles,goal);
			neighbors.add(b);
			neighbors.get(count).moveDown();
			count++;
		}
		if (canMoveUp()){
			int[][] tempTiles = new int[3][3];
			for (int i = 0; i < 3; i++){
				for (int j = 0; j < 3; j++){
					tempTiles[i][j] = tiles[i][j];
				}
			}
			neighbors.add(new Board(tempTiles,goal));
			neighbors.get(count).moveUp();
			count++;
		}
		if (canMoveLeft()){
			int[][] tempTiles = new int[3][3];
			for (int i = 0; i < 3; i++){
				for (int j = 0; j < 3; j++){
					tempTiles[i][j] = tiles[i][j];
				}
			}
			neighbors.add(new Board(tempTiles,goal));
			neighbors.get(count).moveLeft();
			count++;
		}
		if (canMoveRight()){
			int[][] tempTiles = new int[3][3];
			for (int i = 0; i < 3; i++){
				for (int j = 0; j < 3; j++){
					tempTiles[i][j] = tiles[i][j];
				}
			}
			neighbors.add(new Board(tempTiles,goal));
			neighbors.get(count).moveRight();
			count++;
		}
		
		
		//remove neighbors from explored set
		for (int i = 0; i < neighbors.size(); i++){
			
			if (checkIfEqual(neighbors.get(i),explored)){
				neighbors.remove(i);
			}
			
		}
	}
	
	boolean checkIfEqual(Board b1, ArrayList<Board> explored){
		for (int i = explored.size()-1; i >= 0; i--){
			if (isEqual(b1,explored.get(i))){
				return true;
			}
		}
		
		return false;
	}
	
	boolean isEqual(Board b1, Board b2){
		int[][] tiles1 = b1.getTiles();
		int[][] tiles2 = b2.getTiles();
		
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				if (tiles1[i][j] != tiles2[i][j]){
					return false;
				}
			}
		}
		
		return true;
	}
	

	
	ArrayList<Board> getNeighbors(){
		return neighbors;
	}
	
	int getCost(){
		getHamming();
		getManhattan();
		
		totalCost = hamming + manhattan;
		return totalCost;
		
	}
	
	int getHamming(){
		int hSum = 0;
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				if (tiles[i][j] != 0 && tiles[i][j] != goal[i][j]){
					hSum++;
				}
			}
		}
		hamming = hSum;
		return hSum;
	}
	
	int getManhattan(){
		int mSum = 0;
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				if (tiles[i][j] != 0)
					mSum += getDistance(i,j,tiles[i][j]);
			}
		}
		manhattan = mSum;
		return mSum;
	}
	
	int getDistance(int x, int y, int tile){
		int expectedX = 0;
		int expectedY = 0;
		
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				if (goal[i][j] == tile){
					expectedX = i;
					expectedY = j;
				}
			}
		}
		
		return (Math.abs(x - expectedX) + Math.abs(y - expectedY));
	}
	
	int[][] returnGoal(){
		return goal;
	}
	
	boolean isGoalState(){
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				if (tiles[i][j] != goal[i][j]){
					return false;
				}
			}
		}
		return true;
	}
	
	boolean isSolvable(){

		int numInv = 0;
		int[] temp = new int[9];
		int count = 0;
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				temp[count] = tiles[i][j];
				count++;
			}
		}
		
		
		for (int i = 0; i < temp.length; i++){
			int expect = temp[i];
			numInv += Math.abs(expect-i);
		}
	
		return (numInv % 2 == 0);
	}

	public int[][] getTiles(){
		return tiles;
	}
	
	public void moveUp(){
		tiles[blankX][blankY] = tiles[blankX-1][blankY];
		tiles[blankX-1][blankY] = 0;
		
	}
	public void moveDown(){
		tiles[blankX][blankY] = tiles[blankX+1][blankY];
		tiles[blankX+1][blankY] = 0;
	}
	public void moveRight(){
		tiles[blankX][blankY] = tiles[blankX][blankY+1];
		tiles[blankX][blankY+1] = 0;
	}
	public void moveLeft(){
		tiles[blankX][blankY] = tiles[blankX][blankY-1];
		tiles[blankX][blankY-1] = 0;
	}
	
	
	
	public void printBoard(){
		for (int i = 0; i < tiles.length; i++){
			for (int j = 0; j < tiles[0].length; j++){
				System.out.print(tiles[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public boolean canMoveLeft(){
		if (blankY != 0){
			return true;
		}
		return false;
	}
	public boolean canMoveUp(){
		if (blankX != 0){
			return true;
		}
		return false;
	}
	public boolean canMoveRight(){
		if (blankY != 2){
			return true;
		}
		return false;
	}
	public boolean canMoveDown(){
		if (blankX != 2){
			return true;
		}
		return false;
	}
}
