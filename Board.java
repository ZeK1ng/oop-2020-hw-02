import static org.junit.Assert.assertTrue;

import java.util.Arrays;

// Board.java

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
*/
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int height;
	private boolean[][] grid;
	private boolean DEBUG = true;
	boolean committed;
	
	private int[] widths;
	private int[] heights;
	private boolean [][] backupGrid;
	private int[] backUpWidths;
	private int[] backUpHeights;
	
	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;
	
	
	
	
	// Here a few trivial methods are provided:
	
	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	*/
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new boolean[width][height];
		committed = true;
		this.widths=new int[height];
		this.heights= new int[width];
		backupGrid = new boolean[width][height];
		this.backUpHeights= new int[width];
		this.backUpWidths = new int[height];
	}
	
	
	/**
	 Returns the width of the board in blocks.
	*/
	public int getWidth() {
		return width;
	}
	
	
	/**
	 Returns the height of the board in blocks.
	*/
	public int getHeight() {
		return height;
	}
	
	
	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	*/
	public int getMaxHeight() {	 
		int max = 0;
		for(int i =0; i<this.heights.length; i++) {
			max = Math.max(max, this.heights[i]);
		}
		return max;
	}
	
	
	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	*/
	private void setup_sanity_check(int[] checker_widths,int[] checker_heights) {
		for(int x= 0; x<width;x++) {
			for(int y = 0 ;y <height;y++ ) {
				if(grid[x][y]) {
				//	if(y>=checker_heights[x]) {
						checker_heights[x]=y+1;
				//	}
					checker_widths[y]++;
				}
			}
		}
	}
	public void sanityCheck() {
		if (DEBUG) {
			int [] checker_widths = new int[height];
			int [] checker_heights = new int [width];
			setup_sanity_check(checker_widths, checker_heights);
			assertTrue(Arrays.equals(checker_heights, heights));
			assertTrue(Arrays.equals(checker_widths, widths));
		}
		
	}
	
	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.
	 
	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	*/
	public int dropHeight(Piece piece, int x) {
		if(x<0 || x>= width ||piece.getHeight() > this.getHeight() ||
			piece.getWidth() > this.getWidth()) return -1;
		
		int res =0;
		for(int i=0; i<piece.getSkirt().length; i++) {
			int k = getColumnHeight(x+i)-piece.getSkirt()[i];
			if(k >0) {
				res = Math.max(res, k);
			}
		}
		return res;
	}
	
	
	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	*/
	public int getColumnHeight(int x) {
		if(x<0 || x>=width)return -1;
		return this.heights[x];
		
	}

	
	/**
	 Returns the number of filled blocks in
	 the given row.
	*/
	public int getRowWidth(int y) {
		if(y<0||y>=height)return -1;
		return this.widths[y];
	}
	
	
	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	*/
	public boolean getGrid(int x, int y) {
		return (x> this.width || x<0 || y>this.height || y<0 || this.grid[x][y]);
	}
	
	

	
	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.
	 
	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	*/
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
		if (!committed) throw new RuntimeException("place commit problem");
		committed=false;
		backupGrid();
		if(x <0 || y<0) return PLACE_OUT_BOUNDS; 
		if(x>=width || y>=height) return PLACE_OUT_BOUNDS;
		int result = PLACE_OK;
		
		for(TPoint pt:piece.getBody()) {
			int new_x = x+pt.x;
			int new_y = y+ pt.y;
			if(new_x>=width||new_y>=height) return PLACE_OUT_BOUNDS;
			if(grid[new_x][new_y])return PLACE_BAD;
		}
		
		for(TPoint pt : piece.getBody()) {
			int new_x = x+pt.x;
			int new_y = y+ pt.y;
			grid[new_x][new_y] = true;
			
		//	if(this.heights[new_x]<new_y+1) {
				this.heights[new_x] = new_y+1;
			//}
			this.widths[new_y]++;
			if(widths[new_y]==width) {
				result = PLACE_ROW_FILLED;
			}
			
		}
		
		sanityCheck();
		
		return result;
	}
	
	private void backupGrid() {
		for(int i=0; i<grid.length;i++ ) {
			System.arraycopy(grid[i], 0, backupGrid[i], 0, grid[i].length);
		}
		System.arraycopy(widths, 0, backUpWidths, 0, widths.length);
		System.arraycopy(heights, 0, backUpHeights, 0, heights.length);
	}
	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	*/
	public int clearRows() {
		if(committed) {
			committed=false;
			backupGrid();
		}
		int rowsCleared = 0;
		for(int row= 0; row<height;row++) {
			if(widths[row]==width) {
				clearRow(row--);
				rowsCleared++;
			}
		}
		sanityCheck();
		return rowsCleared;
	}
	private void clearRow(int row) {
		for(int col = 0; col<width; col++) {
			heights[col] =0 ; ///update this later
			for(int i = 0; i<grid[col].length-1; i++) {
			//	if(i>=row) {
					grid[col][i]=grid[col][i+1];
					if(col == 0) {
						widths[i] = widths[i+1];
					}
					if(grid[col][i]) {
						heights[col]=i+1;
					}
				
			}
		}
		this.widths[grid[0].length-1] = 0;
		for(int i = 0; i< grid.length; i++) {
			grid[i][grid[0].length-1] = false;
		}
	}
	
	private void go_to_prev_state() {
		boolean[][] tempBoard = grid;
		grid = backupGrid;
		backupGrid=tempBoard;
		int [] tempWidthts = widths;
		widths=backUpWidths;
		backUpWidths=tempWidthts;
		int[] tempHeight = heights;
		heights=backUpHeights;
		backUpHeights=tempHeight;
	}


	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	*/
	public void undo() {
		if(committed)return;
		go_to_prev_state();
		commit();
	}
	
	
	/**
	 Puts the board in the committed state.
	*/
	public void commit() {
		committed = true;
	}


	
	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility) 
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
}


