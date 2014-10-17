package basicMineSweeper;
/**
 * 
 * TextBoard extended from Board
 * draw method is overridden, to draw the game board on console
 *
 */
public class TextBoard extends Board {
	private int colLength, rowLength;
	private String[] colNums, rowNums;
	private String spacer;
/**
 * 
 * @param width
 * @param height
 * @param numMines
 * construct a new TextBoard width x height, with numMines mines.
 * 
 */
	public TextBoard(int width, int height, int numMines) {
		super(width, height, numMines);
		// TODO Auto-generated constructor stub
		colLength = Integer.toString(width-1).length();
		rowLength = Integer.toString(height-1).length();
		colNums = new String[width];
		rowNums = new String[height];
		
		for(int i = 0; i<width;i++){
			StringBuffer col = new StringBuffer(Integer.toString(i));
			while (col.length() < colLength){
				col.insert(0, ' ');
			}
			colNums[i] = col.toString();
		}
		
		StringBuffer spaces = new StringBuffer();
		for (int i = 0; i< rowLength + 2; i++){
			spaces.append(' ');
		}
		spacer = spaces.toString();
		
		for (int i = 0; i<height;i++){
			StringBuffer row = new StringBuffer(Integer.toString(i));
			while (row.length() <= rowLength){
				row.insert(0, ' ');
			}
			row.append(' ');
			rowNums[i] = row.toString();
			
		}
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		System.out.println();
		
		for(int i = 0; i<colLength;i++){
			System.out.print(spacer);
			for (int j = 0; j<rowNums.length;j++){
				System.out.print(rowNums[j].charAt(i+2));
			}
			System.out.println();
		}
		
		System.out.println();
		
		for (int i = 0; i < width; i++){
			System.out.print(rowNums[i]);
			for(int j = 0; j<height;j++){
				if(board[i][j] == UNKNOWN){
					System.out.print("#");
				} else if(board[i][j] == MARKED){
					System.out.print("X");
				} else if (board[i][j] == 0){
					System.out.print(".");
				} else if (board[i][j] == MINE){
					System.out.print("o");
				} else {
					System.out.print(board[i][j]);
				}				
			}
			System.out.println(rowNums[i]);
		}
		
		System.out.println();
		
		for(int i = 0;i<colLength;i++){
			System.out.print(spacer);
			for (int j = 0; j<rowNums.length;j++){
				System.out.print(rowNums[j].charAt(i+2));
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println("Mines remaining: " + (getMines() - getMarked()));
		System.out.println();
	}
	
}
