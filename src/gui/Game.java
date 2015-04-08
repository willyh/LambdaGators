package gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import calc.*;
import test.Library;

public class Game extends JPanel {
	static TreeMap<String, Color> colors;
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Term add = Library.Plus(Library.N(2), Library.N(3));
		draw(add, 10, 10, 300*add.width(), 500, g2d);
		add = add.beta().beta();
		draw(add, 950, 10, 300*add.width(), 500, g2d);
		/*Term t = new Application(new Application(Library.K, new Var("u")), new Var("v"));
		draw(t, 10, 10, 300*t.width(), 500, g2d);
		t = t.beta();
		draw(Library.Y, 10, 10, 300*Library.Y.width(), 500, g2d);*/
	}

	private static BufferedImage eggOutline, eggSolid, gatorOutline, gatorSolid, old, coloredEgg, coloredGator;

	public static void main(String[] args) {
		try {
		    eggOutline = ImageIO.read(new File("src/gui/egg_outline.png"));
		    eggSolid = ImageIO.read(new File("src/gui/egg_solid.png"));
		    coloredEgg = new BufferedImage(eggSolid.getWidth(), eggSolid.getHeight(), BufferedImage.TYPE_INT_ARGB);
		    gatorOutline = ImageIO.read(new File("src/gui/alligator_outline.png"));
		    gatorSolid = ImageIO.read(new File("src/gui/alligator_solid.png"));
		    coloredGator = new BufferedImage(gatorSolid.getWidth(), gatorSolid.getHeight(), BufferedImage.TYPE_INT_ARGB);
		    old = ImageIO.read(new File("src/gui/old_gator.png"));
		} catch (IOException e) {
			System.err.println("Error: Couldnt find egg.png or alligator.png");
			System.exit(1);
		}
		
		colors = new TreeMap<String, Color>();
		colors.put("x", Color.GREEN);
		colors.put("y", Color.PINK);
		colors.put("z", Color.BLUE);
		colors.put("u", Color.YELLOW);
		colors.put("v", Color.MAGENTA);
		
		JFrame frame = new JFrame("Paku Paku Wani");
		frame.add(new Game());
		frame.setSize(1500, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void draw(Term t, int x, int y, int width, int height, Graphics2D g) {
		// adjust height for lambda that isn't in an application
		drawTerm(t, x, y, width/t.width(), height/t.depth(), g);
	}
	
	public GTerm convertTerm(Term t, int x, int y, int width, int height) {
		if (t instanceof Application) {
			GTerm U, V;
			Application a = (Application) t;
			int leftDepth = a.getLeft().depth();
			U = convertTerm(a.getLeft(), x, y, width, height/leftDepth);
			if (a.getRight() instanceof Application) {
				// different right-side applications
				int rightDepth = a.getRight().depth()+1;
				int newHeight = height/rightDepth;
				int newWidth = width/a.getRight().width();
				V = convertTerm(a.getRight(), x+a.getLeft().width()*width, y+newHeight, newWidth, newHeight);
			} else {
				int rightDepth = a.getRight().depth();
				V = convertTerm(a.getRight(), x+a.getLeft().width()*width, y, width, height/rightDepth);
			}
			return new GApplication(U, V, x, y, width, height);
		} else if (t instanceof Lambda) {
			Lambda l = (Lambda) t;
			GVar var = new GVar(l.getVar(), x, y, width, height);
			GTerm abstraction = convertTerm(l.getAbstraction(), x, y+height, width/l.getAbstraction().width(), height);
			return new GLambda(var, abstraction, x, y, width, height);
		} else if (t instanceof Var) {
			Var v = (Var) t;
			return new GVar(v, x, y, width, height);
		}
		return null;
	}
	
	public void drawTerm(Term t, int x, int y, int width, int height, Graphics2D g) {
		if (t instanceof Application) {
			Application a = (Application) t;
			int leftDepth = a.getLeft().depth();
			drawTerm(a.getLeft(), x, y, width, height/leftDepth, g);
			if (a.getRight() instanceof Application) {
				// differentiate right-side applications
				int rightDepth = a.getRight().depth()+1;
				int newHeight = height/rightDepth;
				int newWidth = width/a.getRight().width();
				g.drawImage(old, x + a.getLeft().width()*width, y, width, newHeight, null);
				drawTerm(a.getRight(), x+a.getLeft().width()*width, y+newHeight, newWidth, newHeight, g);
			} else {
				int rightDepth = a.getRight().depth();
				drawTerm(a.getRight(), x+a.getLeft().width()*width, y, width, height/rightDepth, g);
			}
		} else if (t instanceof Lambda) {
			Lambda l = (Lambda) t;
			drawColoredImage(coloredGator, gatorSolid, colors.get(l.getVar().getSymbol()));
			g.drawImage(coloredGator, x, y, width, height, null);
			g.drawImage(gatorOutline, x, y, width, height, null);
			drawTerm(l.getAbstraction(), x, y+height, width/l.getAbstraction().width(), height, g);
		} else if (t instanceof Var) {
			Var v = (Var) t;
			drawColoredImage(coloredEgg, eggSolid, colors.get(v.getSymbol()));
			g.drawImage(coloredEgg, x, y, width, height, null);
			g.drawImage(eggOutline, x, y, width, height, null);
		}
	}
	
	public static void drawColoredImage(BufferedImage canvas, BufferedImage image, Color color) {
		int gw = canvas.getWidth();
		int gh = canvas.getHeight();
		Graphics2D g = (Graphics2D) canvas.getGraphics();
		g.setColor(color);
		g.fillRect(0, 0, gw, gw);
		g.setComposite(AlphaComposite.DstIn);
		g.drawImage(image, 0, 0, gw, gh, 0, 0, gw, gh, null);
	}
}
