package tetris;

import javax.swing.JFrame;

public class Windowgame {
	public static final int width = 455, height = 639;
	
	private Board board;
	private JFrame window;
	
	public Windowgame() {
		window = new JFrame("Tetris");
		window.setSize(width,height);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		board = new Board();
		window.add(board);
		window.addKeyListener(board);
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Windowgame();
	}
}
