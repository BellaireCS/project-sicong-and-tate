package com.github.tacong;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Game {
	public static final int CLASS_COLUMNS = 6;
	public static final int CLASS_ROWS = 6;

	private JFrame win;
	private JPanel gamePanel;
	private int[][] coordinates;
	private Player student;
	private Player peer;
	private Player teacher;

	@SuppressWarnings("serial")
	public Game() {
		/* Initialize game components */
		coordinates = generateCoordinates();
		student = new Player(0, 0, Color.BLUE);
		peer = new Player(0, 0, Color.GREEN);
		assignRandomSeating();

		teacher = new Player(250, 10, Color.RED);

		/* Initialize GUI components */
		win = new JFrame("The Game");
		gamePanel = new JPanel() {

			@Override
			public void paint(Graphics g) {
				super.paint(g);
				Graphics2D g2d = (Graphics2D) g;

				// Paint the player
				g.setColor(student.getColor());
				g.fillOval(student.getX(), student.getY(), Player.SPRITE_RADIUS, Player.SPRITE_RADIUS);

				// Paint the teacher
				g2d.setColor(teacher.getColor());
				g2d.fillOval(teacher.getX(), teacher.getY(), Player.SPRITE_RADIUS, Player.SPRITE_RADIUS);

				// Paint the target peer
				g2d.setColor(peer.getColor());
				g2d.fillOval(peer.getX(), peer.getY(), Player.SPRITE_RADIUS, Player.SPRITE_RADIUS);

				// Paint other irrelevant students who aren't trying to cheat
				g2d.setColor(Color.BLACK);
				for (int[] xy : coordinates) {
					if (!Arrays.equals(xy, student.getCoordinates()) && !Arrays.equals(xy, peer.getCoordinates())) {
						g.fillOval(xy[0], xy[1], Player.SPRITE_RADIUS, Player.SPRITE_RADIUS);
					}
				}

				// Display the teacher's line of sight

				/*
				 * YET TO BE IMPLEMENTED
				 * 
				 * g2d.setColor(Color.YELLOW);
				 * g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.
				 * SRC_OVER, (float) 0.3)); g.fillRect(teacher.getX(),
				 * teacher.getY(), 10, 450);
				 */
			}
		};

	}

	/*
	 * Set up GUI components
	 */
	public void initUI() {
		win.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					student.setY(student.getY() - Player.SPRITE_SPEED);
					break;

				case KeyEvent.VK_DOWN:
					student.setY(student.getY() + Player.SPRITE_SPEED);
					break;

				case KeyEvent.VK_LEFT:
					student.setX(student.getX() - Player.SPRITE_SPEED);
					break;

				case KeyEvent.VK_RIGHT:
					student.setX(student.getX() + Player.SPRITE_SPEED);
					break;

				default:
					break;
				}

				gamePanel.repaint();

			}

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
		win.getContentPane().add(gamePanel, BorderLayout.CENTER);
		win.setSize(500, 500);
		win.setResizable(false);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setLocationRelativeTo(null);
		win.setVisible(true);
	}

	/*
	 * Shows how bad of a player you are
	 */
	public void showGameOver(String msg) {
		int option = JOptionPane.showOptionDialog(win, msg, "Game Over!", JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE, null, new Object[] { "Try Again", "Quit" }, null);

		if (option == 0) {
			restart();
		}

		else {
			System.exit(0);
		}
	}

	/*
	 * Take a guess
	 */
	public void restart() {
		assignRandomSeating();
		gamePanel.repaint();
	}

	/*
	 * Returns a random position from the listing of coordinates
	 */
	public int[] generateRandomPositions() {
		return coordinates[(int) (Math.random() * CLASS_ROWS * CLASS_COLUMNS)];
	}

	/*
	 * Randomizes student and peer seating
	 */
	public void assignRandomSeating() {
		int[] newStudentCoordinates = generateRandomPositions();
		int[] newPeerCoordinates = generateRandomPositions();

		student.setX(newStudentCoordinates[0]);
		student.setY(newStudentCoordinates[1]);

		peer.setX(newPeerCoordinates[0]);
		peer.setY(newPeerCoordinates[1]);
	}

	/*
	 * Generates list of student seats
	 */
	public int[][] generateCoordinates() {
		int[][] xy = new int[CLASS_ROWS * CLASS_ROWS][2];
		int currIndex = 0;

		for (int x = 80; x < 440; x += 60) {
			for (int y = 80; y < 440; y += 60) {
				xy[currIndex][0] = x;
				xy[currIndex][1] = y;
				currIndex++;
			}
		}

		return xy;
	}

	/*
	 * Tests whether the player is in the classroom
	 */
	public boolean isInBounds() {
		return student.getY() > 0 && student.getY() < win.getHeight() && student.getX() > 0
				&& student.getX() < win.getWidth();
	}

	/*
	 * Checks for win
	 */
	public boolean hasWon() {
		return student.getX() < peer.getX() + Player.SPRITE_RADIUS
				&& student.getX() + Player.SPRITE_RADIUS > peer.getX()
				&& student.getY() < peer.getY() + Player.SPRITE_RADIUS
				&& student.getY() + Player.SPRITE_RADIUS > peer.getY();
	}

	public static void main(String[] args) {

		Game theGame = new Game();

		Thread gameThread = new Thread(() -> {
			System.out.println("Starting Game...");
			theGame.initUI();
		});

		Thread checkInBoundsThread = new Thread(() -> {

			System.out.println("Checking boundedness...");
			while (true) {
				System.out.print("");
				if (!theGame.isInBounds()) {
					theGame.showGameOver("You have jumped out of a window to your death!");
				}
			}
		});

		Thread checkWinThread = new Thread(() -> {
			while (true) {
				System.out.print("");
				if (theGame.hasWon()) {
					theGame.showGameOver("You have won!");
				}
			}
		});

		checkInBoundsThread.start();
		gameThread.start();
		checkWinThread.start();

	}

}
