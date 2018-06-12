package gui;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public abstract class Page extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private final Game g;

	public Page(String title, final Game g) {
		this.title = title;
		this.g = g;
	}
	
	public Game getGame() {
		return this.g;
	}

	public String getTitle() {
		return this.title;
	}
	
	public abstract void close();

}
