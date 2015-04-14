package test;

import calc.*;

public class Library {
	public static Term I = new Lambda(new Var(0), new Var(0));

	public static Term K = new Lambda(new Var(0), new Lambda(new Var(1), new Var(0)));

	public static Term Kstar = new Lambda(new Var(0), new Lambda(new Var(1), new Var(1)));

	public static Term omega = new Lambda(new Var(0), new Application(new Var(0), new Var(0)));
	
	public static Term N(int n) {
		Term t = new Var(1);
		for (int i = 0; i < n; ++i) {
			t = new Application(new Var(0), t);
		}
		return new Lambda(new Var(0), new Lambda(new Var(1), t));
	}
	
	public static Term Plus(Term X,Term Y) {
		// Luvxy.ux(vxy)
		Var x = new Var(0);
		Var y = new Var(1);
		Var u = new Var(3);
		Var v = new Var(4);
		Term ux = new Application(u,x);
		Term vxy = new Application(new Application(v,x), y);
		Term P = new Lambda(u, new Lambda(v, new Lambda(x, new Lambda(y, new Application(ux,vxy)))));
		return new Application(new Application(P, X), Y);
	}

	private static Var x = new Var(0);
	private static Var y = new Var(1);
	private static Var z = new Var(2);
	public static Term S = new Lambda(z, new Lambda(x, new Lambda(y, new Application(new Application(z,x),new Application(x,y)))));

	private static Term yxxy = new Lambda(x, new Lambda(y, new Application(y, new Application(new Application(x,x),y))));
	public static Term Y = new Application(yxxy,yxxy);
	
}
