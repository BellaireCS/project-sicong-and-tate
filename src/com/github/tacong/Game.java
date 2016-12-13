package com.github.tacong;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game {

	private JFrame win;
	private JPanel gamePanel;
	private Player student;
	private Player teacher;

	@SuppressWarnings("serial")
	public Game() {
		win = new JFrame("The Game");
		student = new Player(250, 250, 20, Color.BLUE);
		teacher = new Player(250, 10, 20, Color.RED);
		gamePanel = new JPanel() {

			@Override
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(student.getColor());
				g.fillOval(student.getX(), student.getY(), student.getRadius(), student.getRadius());

				g.setColor(teacher.getColor());
				g.fillOval(teacher.getX(), teacher.getY(), student.getRadius(), student.getRadius());

			}
		};
	}

	public void initUI() {
		win.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					student.setY(student.getY() - Player.spriteSpeed);
					break;

				case KeyEvent.VK_DOWN:
					student.setY(student.getY() + Player.spriteSpeed);
					break;

				case KeyEvent.VK_LEFT:
					student.setX(student.getX() - Player.spriteSpeed);
					break;

				case KeyEvent.VK_RIGHT:
					student.setX(student.getX() + Player.spriteSpeed);
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

	public boolean isInBounds(Player p) {
		return p.getY() > 0 && p.getY() < win.getHeight() && p.getX() > 0 && p.getX() < win.getWidth();
	}

	public static void main(String[] args) {
		Game theGame = new Game();
		theGame.initUI();
	}

}
