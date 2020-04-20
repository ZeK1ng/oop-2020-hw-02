
import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class JBrainTetris extends JTetris{
	private Brain brain;
	private Brain.Move best_move;
	private JLabel adversary_lbl_status;
	private JSlider adversary_slider;
	private JCheckBox brain_check;
	
	JBrainTetris(int pixels) {
		super(pixels);
		best_move = new Brain.Move();
		brain = new DefaultBrain();
		
	}
	@Override
	public JComponent createControlPanel() {
		JPanel new_panel = (JPanel)super.createControlPanel();
	
		brain_check = new JCheckBox("Brain");
		adversary_slider = new JSlider(0,100,0);
		new_panel.add(brain_check);
		JLabel adv_lbl = new JLabel("Adversary:");
		new_panel.add(adv_lbl);
		new_panel.add(adversary_slider);		
		adversary_lbl_status = new JLabel("ok");
		new_panel.add(adversary_lbl_status);
		return new_panel;
	}
	
	@Override
	public void tick(int verb) {
		if(verb == DOWN &&  brain_check.isSelected()) {
			board.undo();
			best_move = brain.bestMove(board, currentPiece, board.getHeight(), best_move);
			if(best_move != null) {
				if(!best_move.piece.equals(currentPiece))super.tick(ROTATE);
				if(best_move.x<currentX) super.tick(LEFT);
				if(best_move.x>currentX)super.tick(RIGHT);
			}
		}
		super.tick(verb);
	}
	
	@Override 
	public Piece pickNextPiece() {
		
		Random rd = new Random();
		if(rd.nextInt(100)>=adversary_slider.getValue()) {
			adversary_lbl_status.setText("ok");
		}else {
			adversary_lbl_status.setText("*ok");
			Piece worstPiece = getWorstPiece();
			return worstPiece;
		}
		return super.pickNextPiece();
		
	}
	
	
	
	private Piece getWorstPiece() {
		// TODO Auto-generated method stub
		Piece[] all_p = Piece.getPieces();
		Piece res = null;
		double curr_score=0.0;
		Brain.Move currM = new Brain.Move();
		for(int i=0; i<all_p.length; i++) {
			currM = brain.bestMove(board, all_p[i], HEIGHT, currM);
			if(currM == null) return pieces[i];
			if(currM.score>curr_score) {
				curr_score = currM.score;
				res = currM.piece;
			}
		}
		return res;
	}
	public static void main(String[] args) {
		// Set GUI Look And Feel Boilerplate.
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		JBrainTetris tetris = new JBrainTetris(16);
		JFrame frame = JTetris.createFrame(tetris);
		frame.setVisible(true);
	}
	
}