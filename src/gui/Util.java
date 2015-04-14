package gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.TreeMap;

import calc.Application;
import calc.Lambda;
import calc.Term;
import calc.Var;

public class Util {

	public static BufferedImage eggOutline, eggSolid, gatorOutline, gatorSolid,
			old, coloredEgg, coloredGator;
	public static TreeMap<Integer, Color> colors;

	public static GTerm convert(Term t, int x, int y, int width, int height) {
		// adjust height for lambda that isn't in an application
		return convertTerm(t, x, y, width / t.width(), height / t.depth());
	}

	private static GTerm convertTerm(Term t, int x, int y, int width, int height) {
		if (t instanceof Application) {
			GTerm U, V;
			Application a = (Application) t;
			int leftDepth = a.getLeft().depth();
			U = convertTerm(a.getLeft(), x, y, width, height / leftDepth);
			if (a.getRight() instanceof Application) {
				// different right-side applications
				int rightDepth = a.getRight().depth() + 1;
				int newHeight = height / rightDepth;
				int newWidth = width / a.getRight().width();
				V = convertTerm(a.getRight(), x + a.getLeft().width() * width,
						y + newHeight, newWidth, newHeight);
			} else {
				int rightDepth = a.getRight().depth();
				V = convertTerm(a.getRight(), x + a.getLeft().width() * width,
						y, width, height / rightDepth);
			}
			return new GApplication(U, V, x, y, width, height);
		} else if (t instanceof Lambda) {
			Lambda l = (Lambda) t;
			GVar var = new GVar(l.getVar(), x, y, width, height);
			GTerm abstraction = convertTerm(l.getAbstraction(), x, y + height,
					width / l.getAbstraction().width(), height);
			return new GLambda(var, abstraction, x, y, width, height);
		} else if (t instanceof Var) {
			Var v = (Var) t;
			return new GVar(v, x, y, width, height);
		}
		return null;
	}

	public static void drawTerm(GTerm t, int xoffset, int yoffset, Graphics2D g) {
		if (t.getWidth() == 0 || t.getHeight() == 0) {
			// no need to draw terms with no width/height
			return;
		}
		if (t instanceof Application) {
			GApplication a = (GApplication) t;
			drawTerm(a.getLeft(), xoffset, yoffset, g);
			if (a.getRight() instanceof Application) {
				// draw old gator for right-side applications
				g.drawImage(old, a.getRight().getX() + xoffset, a.getRight()
						.getY() - a.getRight().getHeight() + yoffset, a
						.getRight().getWidth() * a.getRight().width(), a
						.getRight().getHeight(), null);
			}
			drawTerm(a.getRight(), xoffset, yoffset, g);
		} else if (t instanceof Lambda) {
			GLambda l = (GLambda) t;
			GVar v = l.getVar();
			int x = v.getX();
			int y = v.getY();
			int width = v.getWidth();
			int height = v.getHeight();
			drawColoredImage(coloredGator, gatorSolid,
					colors.get(v.getSymbol()));
			g.drawImage(coloredGator, x + xoffset, y + yoffset, width, height,
					null);
			g.drawImage(gatorOutline, x + xoffset, y + yoffset, width, height,
					null);
			drawTerm(l.getAbstraction(), xoffset, yoffset, g);
		} else if (t instanceof Var) {
			GVar v = (GVar) t;
			int x = v.getX();
			int y = v.getY();
			int width = v.getWidth();
			int height = v.getHeight();
			drawColoredImage(coloredEgg, eggSolid, colors.get(v.getSymbol()));
			g.drawImage(coloredEgg, x + xoffset, y + yoffset, width, height,
					null);
			g.drawImage(eggOutline, x + xoffset, y + yoffset, width, height,
					null);
		}
	}

	public static void drawTerm(GTerm t, Graphics2D g) {
		drawTerm(t, 0, 0, g);
	}

	private static void drawColoredImage(BufferedImage canvas,
			BufferedImage image, Color color) {
		int gw = canvas.getWidth();
		int gh = canvas.getHeight();
		Graphics2D g = (Graphics2D) canvas.getGraphics();
		g.setColor(color);
		g.fillRect(0, 0, gw, gw);
		g.setComposite(AlphaComposite.DstIn);
		g.drawImage(image, 0, 0, gw, gh, 0, 0, gw, gh, null);
	}
}
