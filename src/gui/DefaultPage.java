package gui;

import java.awt.Color;
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
	private String[] termNames = { "K", "K*", "omega", "2++", "2+2", "Y turing", "xK", "Push", "Pop", "Code", "times" };
	private Term[] terms = {
			new Application(new Application(Library.K, Library.I(2)), Library.I(3)).alpha(),
			new Application(new Application(Library.Kstar, Library.I(2)), Library.I(3)).alpha(),
			new Application(Library.omega(0), Library.omega(1)).alpha(),
			new Application(Library.S, Library.N(2)).alpha(),
			Library.Plus(Library.N(2), Library.N(2)).alpha(),
			Library.Y.alpha(),
			Library.parse("(Lx.x(Luv.u))(Lxyz.y)").alpha(),
			Library.parse("V3(La.a5)").alpha(),
			Library.parse("^(La.a5E)").alpha(),
			new Application(new Lambda(new Var(6),new Application(Library.Y, new Application(new Application(Library.parse("Lunf.n(fu((Lzqy.q(zqy))n))u"), new Var(6)), Library.N(0)))), Library.K).alpha(),
			Library.parse("Y(Lfs.s(f*)x)K").alpha()
			};
	private Game game;

	public DefaultPage(String title, Game g) {
		super(title, g);
		this.game = g;
	}

	int maxWidth = 3;
	int maxHeight = 10;
	int width = Game.WIDTH / maxWidth;
	int height = Game.HEIGHT / maxHeight;

	private int getX(int i) {
		return (i % maxWidth) * width + 50;
	}

	private int getY(int i) {
		return (i / maxWidth) * height + 50;
	}

	private int getIndex(int x, int y) {
		return (x - 50) / width + (y - 50) / height * maxWidth;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i < termNames.length; ++i) {
			g2d.setColor((i % 2) == 1 ? Color.cyan : Color.pink);
			int x = getX(i);
			int y = getY(i);
			g2d.fillRect(x, y, width, height);
			g2d.setColor(Color.black);
			g2d.setFont(new Font("SanSerif", Font.PLAIN, 50));
			
			// add height b/c draw string is weird
			g2d.drawString(termNames[i], x, y + height);
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

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
