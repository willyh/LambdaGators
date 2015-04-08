package test;

import calc.*;

public class Main {
	public static Term K() {
		return new Lambda(new Var("x"), new Lambda(new Var("y"), new Var("x")));
	}
	
	public static Term omega() {
		return new Lambda(new Var("x"), new Application(new Var("x"), new Var("x")));
	}
	
	public static Term Kstar() {
			return new Lambda(new Var("x"), new Lambda(new Var("y"), new Var("y")));
	}
	
	public static Term N(int n) {
		Term t = new Var("y");
		for (int i = 0; i < n; ++i) {
			t = new Application(new Var("x"), t);
		}
		return new Lambda(new Var("x"), new Lambda(new Var("y"), t));
	}
	
	public static Term S(Term t) {
		Var x = new Var("x");
		Var y = new Var("y");
		Var z = new Var("z");
		Term S = new Lambda(z, new Lambda(x, new Lambda(y, new Application(new Application(z,x),new Application(x,y)))));
		return new Application(S, t);
	}
	
	public static Term Y() {
		Var x = new Var("x");
		Var y = new Var("y");
		Term yxxy = new Application(y, new Application(new Application(x,x),y));
		return new Lambda(x,new Lambda(y,new Application(yxxy,yxxy)));
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
	
	public static void main(String[] args) {
		Term identity = new Lambda(new Var("x"), new Var("x"));
		System.out.println("I=" + identity);
		
		Application test = new Application(new Application(K(), K()), Kstar());
		System.out.println("Test: " + test + " -> " + test.beta());
		
		Application test2 = new Application(new Application(Kstar(), K()), Kstar());
		System.out.println("Test: " + test2 + " -> " + test2.beta());
		
		Application Omega = new Application(omega(), omega());
		System.out.println(Omega + " -> " + Omega.beta());
		
		System.out.println("0 = " + N(0));
		
		System.out.println("3 = " + N(3));
		System.out.println("0 + 0 = " + Plus(N(0), N(0)).beta().beta());
		/*
		Term n = N(0);
		for (int i = 0; i < 3; ++i) {
			n = S(n).beta().beta();
			System.out.println("S(" + i + ") -> " + n);
		}
		*/
		// Lzxy.z(
		// Lxy.xy Lxy.xxy -> Lxy.xxxy
		
		//System.out.println(new Application(Y(), Y()).beta());
				// Lxy.(y(xxy))(y(xxy))
	}
}
