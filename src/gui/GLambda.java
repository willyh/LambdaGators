package gui;

import animation.Animation;
import animation.Shake;
import calc.Lambda;
import calc.Term;
import calc.Var;

public class GLambda extends Lambda implements GTerm {
	private int x, y, width, height;

	public GLambda(Var x, Term X) {
		super(x, X);
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
	}

	public GTerm getAbstraction() {
		return (GTerm) U;
	}

	public GVar getVar() {
		return (GVar) u;
	}

	public Term sub(Term U, Var u) {
		Lambda replacement = (Lambda) super.sub(U, u);
		if (this.getAbstraction() instanceof GVar) {
			GVar abvar = (GVar) this.getAbstraction();
			if (abvar.equals(u)) {
				Animation.queue(new Shake(abvar));
			}
		}
		return replacement;
	}

	public GLambda(GVar u, GTerm U, int x, int y, int width, int height) {
		super(u, U);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.u = u;
		this.U = U;
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
		return Util.convert(this, t.getX(), t.getY(), t.getWidth(),
				t.getHeight());
	}

}
