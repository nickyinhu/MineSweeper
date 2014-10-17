package basicMineSweeper;
import java.util.Random;

/**
 * 
 * This class implements the basic behavior of a game board, width and height
 * are defined, two 2-D arrays are used, one is for recording mines position, 
 * the other one is for recording the state of board cells. 
 *
 */
public abstract class Board {
	protected int width, height;
	
	protected int numMines;
	protected int numUnknown;
	protected int numMarked;
	
	protected boolean[][] mines;
	protected int[][] board;
	
	public static final int UNKNOWN = -1;
	public static final int MARKED = -2;
	public static final int MINE = -3;
	
	/**
	 * Initialize state of cells and mines on game board
	 */
	 
	public Board(int width, int height, int numMines){
		this.width = width;
		this.height = height;
		this.numMines = numMines;
		this.numMarked = 0;
		this.numMarked = 0;
		this.numUnknown = width * height;
		mines = new boolean[width][height];
		board = new int[width][height];
		for (int i =0;i<width;i++){
			for (int j=0;j<height;j++){
				mines[i][j] = false;
				board[i][j] = UNKNOWN;
			}
		}
		
		int cells = width * height;
		int temp = 0;
		Random rand = new Random();
		
		/**
		 *  Initialize mines in board
		 */
		 
		while (temp < numMines){
			int cell = rand.nextInt();
			cell = (cell < 0 ? -cell : cell) % cells;
			if (!mines[cell/height][cell%width]){
				mines[cell/height][cell%width] = true;
				temp ++;
			}
		}				
	}
	
	/**
	 * needs to be overridden
	 */
	public abstract void draw();
	
	public int reveal (int x, int y){
		if (board[x][y] == MARKED){
			numMarked--;			
		} else if (board[x][y] == UNKNOWN){
			numUnknown--;
		}
		if(mines[x][y]){
			board[x][y] = MINE;
		} else {
			board[x][y] = neighborMines(x,y);
		}
		return board[x][y];
	}
	
	public void revealMore(int x, int y){
		int minx, miny, maxx, maxy;
		
		minx = (x <= 0 ? 0 : x-1);
		miny = (y <= 0 ? 0 : y-1);
		maxx = (x>= width-1 ? width-1: x+1);
		maxy = (y>= height-1 ? height-1:y+1);
		for (int i = minx;i<=maxx;i++){
			for (int j = miny;j<=maxy;j++){
				if(!mines[i][j] && board[i][j]==UNKNOWN){
					board[i][j] = reveal(i,j);
					if(board[i][j] == 0){
						revealMore(i,j);
					}
				}
			}
		}
	}
	
	public boolean mark (int x, int y){
		if (numMines > numMarked && board[x][y] == UNKNOWN){
			board[x][y] = MARKED;
			numMarked ++;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean unmark (int x, int y){
		if(board[x][y] == MARKED){
			board[x][y] = UNKNOWN;
			numMarked --;
			return true;
		} else {
			return false;
		}
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getMines(){
		return numMines;
	}
	
	public int getMarked(){
		return numMarked;
	}
	
	public int getUnknown(){
		return numUnknown;
	}
	
	private int neighborMines(int x, int y){
		int minx, miny,maxx,maxy;
		minx = (x<=0 ? 0 : x-1);
		miny = (y<=0 ? 0 : y-1);
		maxx = (x>=width-1 ? width-1 : x+1);
		maxy = (y>=height-1 ? height-1 : y+1);
		int result = 0;
		for(int i = minx;i<=maxx;i++){
			for (int j = miny;j<=maxy;j++){
				if(mines[i][j]){
					result++;
				}
			}
		}
		return result;
	}
}
