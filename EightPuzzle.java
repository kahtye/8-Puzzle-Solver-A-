import java.util.Scanner;
import java.util.*;

public class EightPuzzle {
	public static void main(String[] args){
		Scanner reader = new Scanner(System.in);
		
		System.out.println("8-Puzzle solver");
		System.out.println("1) Random puzzles");
		System.out.println("2) Input a puzzle");
		
		
		int[][] goal = {{0,1,2},{3,4,5},{6,7,8}};
		
		ArrayList<Board> explored = new ArrayList<Board>();
		int input = reader.nextInt();
		
		if (input == 1){			
			ArrayList<Integer> p = generateRandomPuzzle();
			int[][] b = new int[3][3];
			b = listToMatrix(p);
			
			
			Board board = new Board(b,goal);
			
			while (!board.isSolvable()){
				p = generateRandomPuzzle();
				b = listToMatrix(p);
				board = new Board(b,goal);
			}
			
			solve(board,explored);
			
		}
		else if (input == 2){
			reader = new Scanner(System.in);
			System.out.print("Input puzzle separated by spaces ");
	 		
			String[] arr=reader.nextLine().split(" ");
			int[] b =new int[arr.length];

			for(int i=0;i<arr.length;i++)
			 b[i]=Integer.parseInt(arr[i]);
			
			
			if (b.length != 9){
				System.out.println("Board length need to have exactly 9 numbers");
				System.exit(0);
			}
			
			int[] temp = copy(b);
			Arrays.sort(temp);
			
			for (int i = 0; i < temp.length; i++){
				if (temp[i] != i){
					System.out.println("You need numbers 0-8");
					System.exit(0);
				}
			}
			
			int[][] tiles = new int[3][3];
			tiles = convertTo2d(b);
			
			
			Board board = new Board(tiles,goal);
			solve(board,explored);
			
		}
		else{
			System.out.println("Invalid input");
		}
	}
	
	static int[][] convertTo2d(int[] board){
		int[][] temp = new int[3][3];
		int count = 0;
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				temp[i][j] = board[count];
				count++;	
			}
		}
		return temp;
	}
	
	static int[] copy(int[] b){
		int[] copy = new int[b.length];
		
		for (int i = 0; i < copy.length; i++){
			copy[i] = b[i];
		}
		
		return copy;
	}
	
	static ArrayList<Integer> generateRandomPuzzle(){
		ArrayList<Integer> puzzle = new ArrayList<Integer>();
		puzzle.add(1);
		puzzle.add(2);
		puzzle.add(3);
		puzzle.add(4);
		puzzle.add(5);
		puzzle.add(6);
		puzzle.add(7);
		puzzle.add(8);
		puzzle.add(0);
		Collections.shuffle(puzzle);
		return puzzle;
	}
	
	static int[][] listToMatrix(ArrayList<Integer> p){
		int[][] b = new int[3][3];
		int count =0;
		for (int i = 0; i < b.length; i++){
			for (int j = 0; j < b[0].length; j++){
				b[i][j] = p.get(count);
				count++;
			}
		}
		return b;
	}
	
	static void solve(Board board, ArrayList<Board> explored){
		
		if (board.isGoalState()){
			System.out.println("Board is already in goal state, congrats");
			System.exit(0);
		}
		
		if (!board.isSolvable()){
			System.out.println("Board is not solvable");
			System.exit(0);
		}
		
		else{	
			Board current = board;
			printBoard(current);
			System.out.println();
			explored.add(current);
			
			while (!current.isGoalState()){
				
				current.generateNeighbors(explored);
				ArrayList<Board> neighbors = current.getNeighbors();
				
				
				ArrayList<Integer> sums = new ArrayList<Integer>();
				for (int i = 0; i < neighbors.size(); i++){
					sums.add(neighbors.get(i).getCost());
					explored.add(neighbors.get(i));
				}
				
				
				int minIndex = 0;
				int min = sums.get(0);
				for (int i = 0; i < sums.size(); i++){
					if (sums.get(i) < min){
						minIndex = i;
					}
					
				}
				
				Board b = neighbors.get(minIndex);
				int[][] t = b.getTiles();
				
				b = new Board(t,neighbors.get(minIndex).returnGoal());
				current = b;
				
				
				printBoard(current);
				System.out.println();
			}
		}
		
	}
	
	
	
	static void printBoard(Board b){
		int[][] tiles = b.getTiles();
		for (int i = 0; i < tiles.length; i++){
			for (int j = 0; j < tiles[0].length; j++){
				System.out.print(tiles[i][j] + " ");
			}
			System.out.println();
		}
	
	}
	
	static void printTiles(int[][] tiles){
		for (int i = 0; i < tiles.length; i++){
			for (int j = 0; j < tiles[0].length; j++){
				System.out.print(tiles[i][j] + " ");
			}
			System.out.println();
		}
		
	}
	
	
	

}
