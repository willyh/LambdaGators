package test;

import calc.*;

public class Main {
	public static Term K() {
		return new Lambda(new Var(0), new Lambda(new Var(1), new Var(0)));
	}
	
	public static Term omega() {
		return new Lambda(new Var(0), new Application(new Var(0), new Var(0)));
	}
	
	public static Term Kstar() {
			return new Lambda(new Var(0), new Lambda(new Var(1), new Var(1)));
	}
	
	public static Term N(int n) {
		Term t = new Var(1);
		for (int i = 0; i < n; ++i) {
			t = new Application(new Var(0), t);
		}
		return new Lambda(new Var(0), new Lambda(new Var(1), t));
	}
	
	public static Term S(Term t) {
		Var x = new Var(0);
		Var y = new Var(1);
		Var z = new Var(2);
		Term S = new Lambda(z, new Lambda(x, new Lambda(y, new Application(new Application(z,x),new Application(x,y)))));
		return new Application(S, t);
	}
	
	public static Term Y() {
		Var x = new Var(0);
		Var y = new Var(1);
		Term yxxy = new Application(y, new Application(new Application(x,x),y));
		return new Lambda(x,new Lambda(y,new Application(yxxy,yxxy)));
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
	
	public static void main(String[] args) {
		Term t = Library.parse("V(La.ax)z").alpha();
		System.out.println(new Application(new Lambda(new Var(0), new Var(0)), new Var(1)).beta());
	}
}
