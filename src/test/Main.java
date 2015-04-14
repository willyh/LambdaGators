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
		Term identity = new Lambda(new Var(0), new Var(0));
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
