package gui;

import calc.Term;
import calc.Var;

public class GVar extends Var implements GTerm {
	private int x, y, width, height;
	public GVar(int s) {
		super(s);
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
	}
	
	public GVar(Var v, int x, int y, int width, int height) {
		super(v.getSymbol());
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void shakeAnimation() {
		int xstart = this.getX();
		float shakeRange = (float)this.getWidth()/2;
		// egg shaking animation
		for (int i = 0; i < 10; ++i) {
			this.setX(xstart + (int)(shakeRange*Math.sin((float) i)));
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				System.err.println("Interrupted");
				System.exit(0);
			}
		}
	}
	
	public Term sub(Term X, Var x2) {
		return super.sub(((GTerm)X).copySize(this), x2);
	}

	@Override
	public int getX() {
		return x;
	}
	@Override
	public int getY() {
		return y;
	}
	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public void setWidth(int width) {
		this.width = width;
	}
	@Override
	public int getHeight() {
		return height;
	}
	
	@Override
	public void setHeight(int h) {
		this.height = h;
	}
	public void setX(int i) {
		this.x = i;
	}

	public void setY(int i) {
		this.y = i;
	}

	@Override
	public GTerm copySize(GTerm t) {
		return Util.convert(this, t.getX(), t.getY(), t.getWidth(), t.getHeight());
	}
}
