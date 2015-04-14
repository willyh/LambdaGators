package gui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import test.Library;
import calc.*;

public class DefaultPage extends Page {

	/**
	 * 
	 */
	private static final long serialVersionUID = 527036147507436562L;
	private String[] termNames = { "K", "K*", "2++", "2+2" };
	private Term[] terms = {
			new Application(new Application(Library.K, new Var(3)), new Var(4)),
			new Application(new Application(Library.Kstar, new Var(3)), new Var(4)),
			new Application(Library.S, Library.N(2)),
			Library.Plus(Library.N(2), Library.N(2))
			};
	private Game game;

	public DefaultPage(String title, Game g) {
		super(title, g);
		this.game = g;
	}

	int maxWidth = 5;
	int maxHeight = 10;
	int width = Game.WIDTH / maxWidth;
	int height = Game.HEIGHT / maxHeight;

	private int getX(int i) {
		return (i % maxWidth) * width + 100;
	}

	private int getY(int i) {
		return (i / maxWidth) * height + 100;
	}

	private int getIndex(int x, int y) {
		return (x - 100) / width + (y - 100) / height * maxWidth;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i < termNames.length; ++i) {
			g2d.setFont(new Font("SanSerif", Font.PLAIN, 50));
			g2d.drawString(termNames[i], getX(i), getY(i));
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int index = getIndex(e.getX(), e.getY());
		if (0 <= index && index < terms.length) {
			Term t = terms[index];
			this.game.setCurrentPage(new TermPage(termNames[index], this.game, t));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
