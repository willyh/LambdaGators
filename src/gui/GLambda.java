package gui;

import calc.Lambda;
import calc.Term;
import calc.Var;

public class GLambda extends Lambda implements GTerm {

	int x, y, width, height;
	public GLambda(Var x, Term X) {
		super(x, X);
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
	}
	
	public GLambda(GVar u, GTerm U, int x, int y, int width, int height) {
		super(u, U);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

}
