package gui;

import java.awt.Container;

import javax.swing.JFrame;

public class Game {
	public static Page Default;
	public final static int EGG_HATCH_TIME = 300;
	public final static int WIDTH = 1100;
	public final static int HEIGHT = 600;
	private JFrame frame;

	private Page currentPage;

	public Game() {
		Game.Default = new DefaultPage("Default", this);
		frame = new JFrame("Pakkun");
		this.setCurrentPage(Game.Default);
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public Page getCurrentPage() {
		return this.currentPage;
	}

	public void setCurrentPage(Page page) {
		Container contain = frame.getContentPane();
		contain.removeAll();
		if (this.currentPage != null) { this.currentPage.close(); }
		this.currentPage = page;
		frame.add(page);
		frame.validate();
		frame.setVisible(true);
		page.addMouseListener(page);
		page.addMouseMotionListener(page);
		page.addKeyListener(page);
		page.setFocusable(true);
		page.requestFocus();
		repaint();
	}

	public void repaint() {
		if (this.currentPage != null) {
			this.currentPage.repaint();
		}
	}

}
