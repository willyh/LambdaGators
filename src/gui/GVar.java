package gui;

import calc.Var;

public class GVar extends Var implements GTerm {

	int x, y, width, height;
	public GVar(String s) {
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
}
