package tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements KeyListener{
	
	public static int Game_Play = 0;
	public static int Game_Pause = 1;
	public static int Game_Over = 2;
	
	private int state = Game_Play;
	
	private static int fps = 60;
	private static int delay = fps/1000;

	public static final int Board_wid = 10, Board_hei = 20, Block_size = 30;
	private Timer looper;
	private Color[][] board = new Color[Board_hei][Board_wid];
	
	private Color[] colors = {
			Color.decode("#ff0000"), Color.decode("#0000ff"), Color.decode("#00ff00"),
			Color.decode("#ffff00"), Color.decode("#ffffff"), Color.decode("#ff00ff"), Color.decode("#6600ff")
	};
	
	private Shape[] shapes = new Shape[7];
	private Shape currentShape;
	Random rd = new Random();
	
	public Board() {
		
		shapes[0] = new Shape(new int[][] {
			{ 1, 1, 1, 1}
		}, this, colors[0]);
		
		shapes[1] = new Shape(new int[][] {
			{ 1, 1, 1},
			{ 0, 1, 0},
		}, this, colors[1]);
		
		shapes[2] = new Shape(new int[][] {
			{ 1, 1, 1},
			{ 1, 0, 0},
		}, this, colors[2]);
		
		shapes[3] = new Shape(new int[][] {
			{ 1, 1, 1},
			{ 0, 0, 1},
		}, this, colors[3]);
		
		shapes[4] = new Shape(new int[][] {
			{ 1, 1, 0},
			{ 0, 1, 1},
		}, this, colors[4]);
		
		shapes[5] = new Shape(new int[][] {
			{ 0, 1, 1},
			{ 1, 1, 0},
		}, this, colors[5]);
		
		shapes[6] = new Shape(new int[][] {
			{ 1, 1},
			{ 1, 1},
		}, this, colors[6]);
		
		currentShape = shapes[0];
		looper = new Timer( delay, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				update();
				repaint();
			}
		});
		looper.start();
	}
	public void update() {
		if(state == Game_Play)
		currentShape.update();
	}
	
	public void setcurrentShape() {
		currentShape = shapes[rd.nextInt(shapes.length)];
		currentShape.reset();
		checkGameover();
	}
	
	private void checkGameover() {
		int [][]coords = currentShape.getcoords();
		for(int row = 0; row < coords.length; row++) {
			for(int col = 0; col < coords[0].length; col++) {
				if(coords[row][col] != 0) {
					if(board[row + currentShape.getY()][col + currentShape.getX()] != null) {
						state = Game_Over;
					}
				}
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		currentShape.render(g);
		
		for(int row=0;row<board.length;row++) {
			for(int col=0;col<board[row].length;col++) {
				if(board[row][col]!=null) {
					g.setColor(board[row][col]);
					g.fillRect(col*Block_size, row*Block_size, Block_size, Block_size);
				}
			}
		}
		
		g.setColor(Color.white);
		for(int row=0;row<Board_hei;row++) g.drawLine(0, Block_size * row, Block_size * Board_wid, Block_size * row);
		
		for(int col=0;col<=Board_wid;col++) g.drawLine(col*Block_size, 0, col*Block_size, Block_size*Board_hei);
		
		if(state == Game_Over) {
			g.setColor(Color.white);
			g.drawString("GAME OVER", 200, 200);
		}
	}
	
	public Color[][] getBoard() {
		return board;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			currentShape.speedUp();
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			currentShape.moveRight();
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			currentShape.moveLeft();
		}
		else if(e.getKeyCode() == KeyEvent.VK_UP) {
			currentShape.rotateShape();
		}
		
		//clean board when game over
		if(state == Game_Over) {
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				for(int row = 0; row < board.length; row++) {
					for(int col = 0; col < board[row].length; col++) {
						board[row][col] = null;
					}
				}
				setcurrentShape();
				state = Game_Play;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			currentShape.speedDown();
		}
	}
	
}
