package basicMineSweeper;
import java.io.*;
/**
 * 
 * A simple implementation of the MineSweeper game, used TextBoard to initiate game
 * board, with methods of cells revealer and attribute getter
 *
 */
public class MineSweeper {
	private Board board;
	private StreamTokenizer tok;
	private boolean done, quit, win;
	private int lastCell;
	
	  /**
	   * Create and initialize a new game of the given dimensions.
	   */
	public MineSweeper(int width, int height, int mines){
		board = new TextBoard(width, height, mines);
		
		tok = new StreamTokenizer(new InputStreamReader(System.in));
		
		done = win = quit = false;
	}
	
	  /**
	   * Main game loop.
	   */
	public void play() throws IOException {
		long StartTime = System.currentTimeMillis();
		
		while(!done){
			board.draw();
			
			System.out.print("Command: ");
			System.out.flush();
			tok.nextToken();
			
			if(tok.ttype == StreamTokenizer.TT_WORD){
				doCommand();
			} else if (tok.ttype == StreamTokenizer.TT_EOL){
				continue;
			} else if (tok.ttype == StreamTokenizer.TT_EOF){
				done = quit = true;
			} else {
				System.out.println("Unknown command, try 'help");
			}
			
			if (board.getMines() == board.getMarked()){
				done = win = true;
			} else if (lastCell == Board.MINE){
				done = true;
			}
			System.in.skip(System.in.available());
		}
		long elapsedTime = System.currentTimeMillis() - StartTime;
		
		for (int i =0;i<board.getWidth();i++){
			for(int j = 0 ;j<board.getHeight();j++){
				board.reveal(i, j);
			}
		}
		
		board.draw();
		
		if(win){
			 System.out.println("Congratulations -- you found all the mines!");
		}
		if(!quit){
			System.out.println("Bad luck -- you stepped on a mine!");
		}
		System.out.println("Total game time: "+(elapsedTime/1000)+" seconds");
	}
	
	private void doCommand() throws IOException {
	    int x, y;

	    if (tok.sval.equals("reveal")) {
		      // Reveal a cell (and perhaps some around it)
		      tok.nextToken();
		      x = (int)tok.nval;
		      tok.nextToken();
		      y = (int)tok.nval;
		      lastCell = board.reveal(x, y);
		      if(lastCell == -3){
		    	  done = true;
		      }
		      if (lastCell == 0) {
		    	  board.revealMore(x, y);
		      	}
	    }
	    else if (tok.sval.equals("mark")) {
		      // Mark a cell
		      tok.nextToken();
		      x = (int)tok.nval;
		      tok.nextToken();
		      y = (int)tok.nval;
		      board.mark(x, y);
	    }
	    else if (tok.sval.equals("unmark")) {
		      // Unmark a cell
		      tok.nextToken();
		      x = (int)tok.nval;
		      tok.nextToken();
		      y = (int)tok.nval;
		      board.unmark(x, y);
	    }
	    else if (tok.sval.equals("help")) {
		      // Print some help
		      System.out.println("reveal <x> <y>");
		      System.out.println("mark <x> <y>");
		      System.out.println("unmark <x> <y>");
		      System.out.println("help");
		      System.out.println("quit");
	    }
	    else if (tok.sval.equals("quit")) {
	    	// Quit and die
	    	quit = done = true;
	    }
	    else {
	    	System.out.println("type 'help' for more information");
	    }
	 }
	  public static void main(String[] args) throws IOException {

		    MineSweeper game;
		    
		    if (args.length < 3) {
		      System.out.println("Command: java MineSweeper <width> <height> <mines>");
		      System.exit(0);
		    }
		    else {
		      int width = Integer.parseInt(args[0]);
		      int height = Integer.parseInt(args[1]);
		      int mines = Integer.parseInt(args[2]);
		      game = new MineSweeper(width, height, mines);
		      game.play();
		    }
		  }
}
