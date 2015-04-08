package test;

import calc.*;

public class Library {
	public static Term I = new Lambda(new Var("x"), new Var("x"));

	public static Term K = new Lambda(new Var("x"), new Lambda(new Var("y"), new Var("x")));

	public static Term Kstar = new Lambda(new Var("x"), new Lambda(new Var("y"), new Var("y")));

	public static Term N(int n) {
		Term t = new Var("y");
		for (int i = 0; i < n; ++i) {
			t = new Application(new Var("x"), t);
		}
		return new Lambda(new Var("x"), new Lambda(new Var("y"), t));
	}
	
	public static Term Plus(Term X,Term Y) {
		// Luvxy.ux(vxy)
		Var x = new Var("x");
		Var y = new Var("y");
		Var u = new Var("u");
		Var v = new Var("v");
		Term ux = new Application(u,x);
		Term vxy = new Application(new Application(v,x), y);
		Term P = new Lambda(u, new Lambda(v, new Lambda(x, new Lambda(y, new Application(ux,vxy)))));
		return new Application(new Application(P, X), Y);
	}

	private static Var x = new Var("x");
	private static Var y = new Var("y");
	private static Var z = new Var("z");
	public static Term S = new Lambda(z, new Lambda(x, new Lambda(y, new Application(new Application(z,x),new Application(x,y)))));

	private static Term yxxy = new Lambda(x, new Lambda(y, new Application(y, new Application(new Application(x,x),y))));
	public static Term Y = new Application(yxxy,yxxy);
	
}
