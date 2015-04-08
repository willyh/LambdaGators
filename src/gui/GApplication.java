package gui;

import calc.Application;
import calc.Term;

public class GApplication extends Application implements GTerm {
	int x, y, width, height;
	public GApplication(Term U, Term V) {
		super(U, V);
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
	}
	
	public GApplication(GTerm U, GTerm V, int x, int y, int width, int height) {
		super(U, V);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
