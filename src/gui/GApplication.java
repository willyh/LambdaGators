package gui;

import animation.Animation;
import animation.LeftEatRight;
import animation.Shake;
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

	public Term sub(Term U, Var u) {
		Application replacement = (Application) super.sub(U, u);
		// check for the animation ahead of time
		if (this.U instanceof GVar) {
			GVar uvar = (GVar) this.U;
			if (uvar.equals(u)) {
				Animation.queue(new Shake(uvar));
			}
		}
		if (this.V instanceof GVar) {
			GVar vvar = (GVar) this.V;
			if (vvar.equals(u)) {
				Animation.queue(new Shake(vvar));
			}
		}
		return replacement;
	}
	
	public Term beta() {
		if (this.getLeft() instanceof GLambda) {
			Animation.queue(new LeftEatRight(((GLambda)this.getLeft()).getVar(), this.getRight()));
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
