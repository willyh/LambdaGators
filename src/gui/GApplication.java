package gui;

import calc.Application;
import calc.Term;
import calc.Var;

public class GApplication extends Application implements GTerm {
	private int x, y, width, height;
	public GApplication(Term U, Term V) {
		super(U, V);
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
	}
	public GTerm getLeft() {
		return (GTerm)U;
	}
	
	public GTerm getRight() {
		return (GTerm)V;
	}
	public GApplication(GTerm U, GTerm V, int x, int y, int width, int height) {
		super(U, V);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.U = U;
		this.V = V;
	}
	
	public void leftEatRight() {
		GVar v = ((GLambda)this.getLeft()).getVar();
		int xstart = v.getX();
		int time = 500/50; //milliseconds
		int rate = (this.getRight().getX() - v.getX())/time;
		while(v.getX() < this.getRight().getX()) {
			v.setX(v.getX() + rate);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				System.err.println("Interrupted");
				System.exit(0);
			}
		}
		this.getRight().setWidth(0);
		while(v.getX() > xstart) {
			v.setX(v.getX() - rate);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				System.err.println("Interrupted");
				System.exit(0);
			}
		}
		rate = v.getHeight()/time;
		while (v.getHeight() > 0) {
			v.setHeight(v.getHeight()-rate);
			v.setY(v.getY() + rate);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				System.err.println("Interrupted");
				System.exit(0);
			}
		}
		v.setHeight(0);
	}

	public Term sub(Term U, Var u) {
		Application replacement = (Application) super.sub(U, u);
		// check for the animation ahead of time
		if (this.U instanceof GVar) {
			GVar uvar = (GVar) this.U;
			if (uvar.equals(u)) {
				uvar.shakeAnimation();
				this.U = replacement.getLeft();
				try {
					Thread.sleep(Game.EGG_HATCH_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		if (this.V instanceof GVar) {
			GVar vvar = (GVar) this.V;
			if (vvar.equals(u)) {
				vvar.shakeAnimation();
				this.V = replacement.getRight();
				try {
					Thread.sleep(Game.EGG_HATCH_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return replacement;
	}
	
	public Term beta() {
		if (this.getLeft() instanceof GLambda) {
			Animation.queue(new LeftEatRight(((GLambda)this.getLeft()).getVar(), this.getRight()));
			leftEatRight();
		}
		return super.beta();
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
	@Override
	public GTerm copySize(GTerm t) {
		return Util.convert(this, t.getX(), t.getY(), t.getWidth(), t.getHeight());
	}
}
