import junit.framework.TestCase;


public class BoardTest extends TestCase {
	Board b;
	Piece pyr1, pyr2, pyr3, pyr4;
	private Piece s1, s1_rotated;
	private Piece s2,s2_rotated;
	private Piece L1,L1_rotate1,L1_rotate2,L1_rotate3;
	private Piece L2,L2_rotate1,L2_rotate2,L2_rotate3;
	private Piece stick,stick_rotate;
	private Piece square;

	// This shows how to build things in setUp() to re-use
	// across tests.
	
	// In this case, setUp() makes shapes,
	// and also a 3X6 board, with pyr placed at the bottom,
	// ready to be used by tests.
	
	protected void setUp() throws Exception {
		b = new Board(3, 6);
		
		setup_pyramid();
		setup_s1();
		setup_s2();
		setup_L1();
		setup_L2();
		setup_stick();
		setup_square();
	
		b.place(pyr1, 0, 0);
	}
	private void setup_pyramid() {
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();

	}

	private void setup_s1() {

		s1 = new Piece(Piece.S1_STR);
		s1_rotated = s1.computeNextRotation();
	
		
	}
	private void setup_s2() {
		
		s2 = new Piece(Piece.S2_STR);
		s2_rotated = s2.computeNextRotation();
	
		
	}

	private void setup_stick() {
		stick = new Piece(Piece.STICK_STR);
		stick_rotate = stick.computeNextRotation();
		
	}

	private void setup_square() {
		square = new Piece(Piece.SQUARE_STR);
		
	}
	
	private void setup_L2() {

		L2 =new Piece(Piece.L2_STR);
		L2_rotate1 = L2.computeNextRotation();
		L2_rotate2 = L2_rotate1.computeNextRotation();
		L2_rotate3 = L2_rotate2.computeNextRotation();
	
		
		
	}

	private void setup_L1() {
		L1 =new Piece(Piece.L1_STR);
		L1_rotate1 = L1.computeNextRotation();
		L1_rotate2 = L1_rotate1.computeNextRotation();
		L1_rotate3 = L1_rotate2.computeNextRotation();
	
	}

	
	// Check the basic width/height/max after the one placement
	public void testSample1() {
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
	}
	
	// Place sRotated into the board, then check some measures
	public void testSample2() {
		b.commit();
		int result = b.place(s1_rotated, 1, 1);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
	}
	
	// Makre  more tests, by putting together longer series of 
	// place, clearRows, undo, place ... checking a few col/row/max
	// numbers that the board looks right after the operations.
	
	public void testDropHeightBad() {
		b = new Board(3,2);
		assertEquals(-1, b.dropHeight(stick, 1));
		b = new Board(3,6);
		assertEquals(0, b.dropHeight(pyr1, 1));
		assertEquals(-1, b.dropHeight(pyr1, -1));
		assertEquals(-1, b.dropHeight(pyr1, 5));
		assertEquals(-1, b.dropHeight(stick_rotate, 1));

	}
	public void testSizes() {
		assertEquals(-1,b.getColumnHeight(-1));
		assertEquals(-1,b.getColumnHeight(10));
		assertEquals(-1,b.getRowWidth(-1));
		assertEquals(-1, b.getRowWidth(10));
		b = new Board(3,6);
		b.place(pyr1, 0, 0);
		b.commit();
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(3, b.getRowWidth(0));
	}
	
	public void testgetGrid() {
		 b = new Board(3,6);
		 assertTrue(b.getGrid(20, 20));
		 assertTrue(b.getGrid(-1, 20));
		 assertTrue(b.getGrid(2, 20));
		 assertTrue(b.getGrid(2, -1));
		 b.place(pyr1, 0, 0);
		 assertFalse(b.getGrid(0, 1));
		 assertTrue(b.getGrid(0, 0));
	}
	
	public void testToString() {
		 b = new Board(3,6);
		 b.place(pyr1, 0, 0);
		 b.commit();
		 String s1=b.toString();
		 b.place(pyr1, -1, 2);
		 String s2 = b.toString();
		 assertTrue(s1.equals(s2));
	}
	public void testPlace() {
		b = new Board(3,6);

		assertEquals(Board.PLACE_OUT_BOUNDS,b.place(pyr1, -1, -1));
		assertEquals(Board.PLACE_OUT_BOUNDS,b.place(pyr1, 0, -1));
		assertEquals(Board.PLACE_OUT_BOUNDS,b.place(pyr1, 10, 10));
		assertEquals(Board.PLACE_OUT_BOUNDS,b.place(pyr1, 2, 10));
		assertEquals(Board.PLACE_OUT_BOUNDS,b.place(pyr1, 2, 5));
		assertEquals(Board.PLACE_OUT_BOUNDS,b.place(pyr1, 0, 5));
	}
	public void testPlace1() {
		b = new Board(3,6);
//		String s1 = b.toString();
//		System.out.println(s1);
		
		assertEquals(false,b.getGrid(0, 0));
		b.place(pyr1, 0, 0);
		try {
			b.place(L1, 2, 1);
		}catch(Exception e){
			b.commit();
		}
		b.commit();
		b.undo();
		assertEquals(false, b.getGrid(0,0));
	}
	public void testPlace2() {
		b = new Board(3,6);
//		String s1 = b.toString();
//		System.out.println(s1);
		assertEquals(false,b.getGrid(0, 0));
		b.place(pyr1, 0, 0);
		b.undo();
		b.commit();
		assertEquals(Board.PLACE_BAD, b.place(pyr1, 0, 0));
		b.undo();
		assertEquals(true, b.getGrid(0,0));
//		String s1 = b.toString();
//		System.out.println(s1);
	}
	
	public void testPlace3() {
		b = new Board(4,6);
//		String s1 = b.toString();
//		System.out.println(s1);
		b.place(stick_rotate, 0, 0);
		b.commit();
		b.place(stick_rotate, 0, 1);
		b.commit();
		b.place(stick_rotate,0, 2);
		b.commit();
		String s1 = b.toString();
		System.out.println(s1);
		assertEquals(3, b.clearRows());
		s1 = b.toString();
		System.out.println(s1);
		
	}
}

