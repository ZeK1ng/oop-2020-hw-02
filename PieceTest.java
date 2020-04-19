import junit.framework.TestCase;

import static org.junit.Assert.assertThrows;

import java.util.*;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest extends TestCase {
	// You can create data to be used in the your
	// test cases like this. For each run of a test method,
	// a new PieceTest object is created and setUp() is called
	// automatically by JUnit.
	// For example, the code below sets up some
	// pyramid and s pieces in instance variables
	// that can be used in tests.
	private Piece pyr1, pyr2, pyr3, pyr4,pyr_initial;
	private Piece s1, s1_rotated, s1_initial;
	private Piece s2,s2_rotated,s2_initial;
	private Piece L1,L1_rotate1,L1_rotate2,L1_rotate3,L1_initial;
	private Piece L2,L2_rotate1,L2_rotate2,L2_rotate3,L2_initial;
	private Piece stick,stick_rotate,stick_initial;
	private Piece square,square_initial;
	protected void setUp() throws Exception {
		super.setUp(); 
		
	
		setup_pyramid();
		setup_s1();
		setup_s2();
		setup_L1();
		setup_L2();
		setup_stick();
		setup_square();
		
	}
	
	private void setup_pyramid() {
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		pyr_initial=pyr4.computeNextRotation();	
	}

	private void setup_s1() {

		s1 = new Piece(Piece.S1_STR);
		s1_rotated = s1.computeNextRotation();
		s1_initial = s1_rotated.computeNextRotation();
		
	}
	private void setup_s2() {
		
		s2 = new Piece(Piece.S2_STR);
		s2_rotated = s2.computeNextRotation();
		s2_initial = s2_rotated.computeNextRotation();
		
	}

	private void setup_stick() {
		stick = new Piece(Piece.STICK_STR);
		stick_rotate = stick.computeNextRotation();
		stick_initial = stick_rotate.computeNextRotation();
	}

	private void setup_square() {
		square = new Piece(Piece.SQUARE_STR);
		square_initial = square.computeNextRotation();
	}
	
	private void setup_L2() {

		L2 =new Piece(Piece.L2_STR);
		L2_rotate1 = L2.computeNextRotation();
		L2_rotate2 = L2_rotate1.computeNextRotation();
		L2_rotate3 = L2_rotate2.computeNextRotation();
		L2_initial = L2_rotate3.computeNextRotation();
		
		
	}

	private void setup_L1() {
		L1 =new Piece(Piece.L1_STR);
		L1_rotate1 = L1.computeNextRotation();
		L1_rotate2 = L1_rotate1.computeNextRotation();
		L1_rotate3 = L1_rotate2.computeNextRotation();
		L1_initial = L1_rotate3.computeNextRotation();
	}

	


	//Starting to test sizes
	public void testSampleSize() {
		// Check size of pyr piece
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr1.getHeight());
		
		// Now try after rotation
		// Effectively we're testing size and rotation code here
		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr2.getHeight());
		
		// Now try with some other piece, made a different way
		Piece l = new Piece(Piece.STICK_STR);
		assertEquals(1, l.getWidth());
		assertEquals(4, l.getHeight());
		
		
	}
	
	public void testSizeSquare() {
		assertEquals(2,square.getHeight());
		assertEquals(2,square.getWidth());
		assertEquals(2,square_initial.getHeight());
		assertEquals(2,square_initial.getHeight());
	}
	
	public void testPyramid() {
		assertEquals(2, pyr3.getHeight());
		assertEquals(3,pyr3.getWidth());
		
		assertEquals(3,pyr4.getHeight());
		assertEquals(2, pyr4.getWidth());
		assertEquals(pyr1.getWidth(), pyr_initial.getWidth());
		assertEquals(pyr1.getHeight(), pyr_initial.getHeight());	
	}
	
	public void testL1Size() {
		assertEquals(3,L1.getHeight());
		assertEquals(2,L1.getWidth());
		assertEquals(L1.getHeight(),L1_initial.getHeight());
		assertEquals(L1.getWidth(),L1_initial.getWidth());
		assertEquals(2,L1_rotate1.getHeight());
		assertEquals(3,L1_rotate1.getWidth());
		
		assertEquals(3, L1_rotate2.getHeight());
		assertEquals(2, L1_rotate2.getWidth());
		
		assertEquals(2,L1_rotate3.getHeight());
		assertEquals(3,L1_rotate3.getWidth());
	}
	
	public void testdL2Size() {
		assertEquals(3,L2.getHeight());
		assertEquals(2,L2.getWidth());
		assertEquals(L2.getHeight(),L2_initial.getHeight());
		assertEquals(L2.getWidth(),L2_initial.getWidth());
		
		assertEquals(2,L2_rotate1.getHeight());
		assertEquals(3,L2_rotate1.getWidth());
		
		assertEquals(3, L2_rotate2.getHeight());
		assertEquals(2, L2_rotate2.getWidth());
		
		assertEquals(2,L2_rotate3.getHeight());
		assertEquals(3,L2_rotate3.getWidth());
		
	}
	public void testStickSize() {
		assertEquals(4, stick.getHeight());
		assertEquals(1, stick.getWidth());
		assertEquals(1, stick_rotate.getHeight());
		assertEquals(4, stick_rotate.getWidth());
		assertEquals(stick.getHeight(), stick_initial.getHeight());
		assertEquals(stick.getWidth(), stick_initial.getWidth());
	}
	public void testS1size() {
		assertEquals(2, s1.getHeight());
		assertEquals(3, s1.getWidth());
		assertEquals(3, s1_rotated.getHeight());
		assertEquals(2, s1_rotated.getWidth());
		assertEquals(s1.getHeight(), s1_initial.getHeight());
		assertEquals(s1.getWidth(), s1_initial.getWidth());
	}
	public void testS2size() {
		assertEquals(2, s2.getHeight());
		assertEquals(3, s2.getWidth());
		assertEquals(3, s2_rotated.getHeight());
		assertEquals(2, s2_rotated.getWidth());
		assertEquals(s2.getHeight(), s2_initial.getHeight());
		assertEquals(s2.getWidth(), s2_initial.getWidth());
	}
	
	//Size test end
	
	// start skirt tests
	public void testSkirtS1(){
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, s1_rotated.getSkirt()));
		assertTrue(Arrays.equals(s1.getSkirt(), s1_initial.getSkirt()));
	}
	public void testSkirtS2(){
		assertTrue(Arrays.equals(new int[] {1,0, 0}, s2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 1}, s2_rotated.getSkirt()));
		assertTrue(Arrays.equals(s2.getSkirt(), s2_initial.getSkirt()));
	}
	public void testSkirtPyr(){
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, pyr2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0,1},pyr4.getSkirt()));
		assertTrue(Arrays.equals(pyr1.getSkirt(), pyr_initial.getSkirt()));
	}
	public void testSkirtStick(){
		assertTrue(Arrays.equals(new int[] {0}, stick.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0,0,0,0}, stick_rotate.getSkirt()));
		assertTrue(Arrays.equals(stick.getSkirt(), stick_initial.getSkirt()));
	}
	public void testSkirtL1(){
		assertTrue(Arrays.equals(new int[] {0,0},L1.getSkirt()));
		assertTrue(Arrays.equals(new int[]{0,0,0},L1_rotate1.getSkirt() ));
		assertTrue(Arrays.equals(new int[]{2,0},L1_rotate2.getSkirt() ));
		assertTrue(Arrays.equals(new int[]{0,1,1},L1_rotate3.getSkirt() ));
		assertTrue(Arrays.equals(L1_initial.getSkirt(),L1.getSkirt() ));
	}
	
	public void testSkirtL2(){
		assertTrue(Arrays.equals(new int[] {0,0},L2.getSkirt()));
		assertTrue(Arrays.equals(new int[]{1,1,0},L2_rotate1.getSkirt() ));
		assertTrue(Arrays.equals(new int[]{0,2},L2_rotate2.getSkirt() ));
		assertTrue(Arrays.equals(new int[]{0,0,0},L2_rotate3.getSkirt() ));
		assertTrue(Arrays.equals(L2_initial.getSkirt(),L2.getSkirt() ));
		
	}
	public void testSkirtSquare(){
		assertTrue(Arrays.equals(new int[] {0, 0},square.getSkirt()));
		assertTrue(Arrays.equals(square.getSkirt(), square_initial.getSkirt()));
	}
	//End skirt tests
	
	//Start test Rotations
	public void testgetPieces() {
		Piece[] ps = Piece.getPieces();
		assertTrue(ps[Piece.S1].equals(s1));
		assertTrue(ps[Piece.S2].equals(s2));
		assertTrue(ps[Piece.L1].equals(L1));
		assertTrue(ps[Piece.L2].equals(L2));
		assertTrue(ps[Piece.PYRAMID].equals(pyr1));
		assertTrue(ps[Piece.SQUARE].equals(square));
		assertTrue(ps[Piece.STICK].equals(stick));
		ps=Piece.getPieces();
	}
	public void testFastRotations() {
		Piece[] ps = Piece.getPieces();
		assertTrue(ps[Piece.SQUARE].equals(ps[Piece.SQUARE].fastRotation()));
		assertTrue(ps[Piece.STICK].equals(ps[Piece.STICK].fastRotation().fastRotation()));
	}
	public void testEquals() {
		assertTrue(square.equals(square_initial));
		assertTrue(s1.equals(s1_initial));
		assertTrue(s2.equals(s2_initial));
		assertTrue(L1.equals(L1_initial));
		assertTrue(L2.equals(L2_initial));
		assertTrue(stick.equals(stick_initial));
		assertTrue(pyr1.equals(pyr_initial));
		assertTrue(pyr1.equals(pyr1));
		assertTrue(pyr2.equals(pyr2));
		assertTrue(square.equals(square));
		assertTrue(square.equals(square_initial));
		assertFalse(s1.equals("sada"));
	}
	
	
	
	public void testBadParse() {
		
		Exception excep =assertThrows(RuntimeException.class, ()->{
			Piece l = new Piece("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
		});
	}
}
