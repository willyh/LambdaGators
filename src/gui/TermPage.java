package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import calc.Application;
import calc.Term;

public class TermPage extends Page implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1067369080404363262L;
	public final static int EGG_HATCH_TIME = 300;
	private volatile int xoffset, yoffset;
	private volatile int xstart, ystart;
	private Term term;
	private volatile GTerm gterm;
	private final Object updateLock;
	private int height, width;

	public TermPage(String title, Game g, Term term) {
		super(title, g);
		this.width = Game.WIDTH;
		this.height = Game.HEIGHT - 50;
		this.term = term;
		this.gterm = Util.convert(term, 0, 0, width, height);
		updateLock = new Object();

		Thread th = new Thread(this);
		th.start();

	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Util.drawTerm(gterm, xoffset, yoffset, g2d);
	}

	public void update() {
		Term ret = gterm.beta();
		if (ret != null) {
			term = ret;
		}

		ret = term.alpha();
		if (ret != null) {
			term = ret;
		}
		gterm = Util.convert(term, gterm.getX(), gterm.getY(), gterm.getWidth()
				* term.width(), height);
	}

	@Override
	public void run() {
		while (true) {
			synchronized (updateLock) {
				try {
					updateLock.wait();
					update();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		synchronized (updateLock) {
			updateLock.notify();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		xstart = e.getX() - xoffset;
		ystart = e.getY() - yoffset;
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
		xoffset = e.getX() - xstart;
		yoffset = e.getY() - ystart;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			this.getGame().setCurrentPage(Game.Default);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
