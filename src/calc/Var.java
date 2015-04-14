package calc;

import java.util.HashSet;
import java.util.Set;

public class Var implements Term {
	private int symbol;

	public Var(int symbol) {
		this.symbol = symbol;
	}
	
	public int getSymbol() {
		return this.symbol;
	}

	@Override
	public Term sub(Term X, Var x2) {
		if (this.equals(x2)) {
			return X;
		} else {
			return this;
		}
	}

	@Override
	public Term rebind(Var x, Var y) {
		if (this.equals(x)) {
			return y;
		} else {
			return this;
		}
	}
	
	public boolean boudedIn(Term X) {
		if (X instanceof Lambda) {
			Lambda l = (Lambda) X;
			return equals(l.getVar());
		} else if (X instanceof Application) {
			Application UV = (Application) X;
			return boudedIn(UV.getLeft()) || boudedIn(UV.getRight());
		} else {
			return false;
		}
	}
	
	public int hashCode() {
		return this.symbol;
	}
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Var)) {
			return false;
		} else {
			Var v = (Var) o;
			return this.symbol == v.symbol;
		}
	}
	
	public String toString() {
		return this.symbol + "";
	}

	@Override
	public Term beta() {
		return null;
	}

	@Override
	public int depth() {
		return 1;
	}

	@Override
	public int width() {
		return 1;
	}

	@Override
	public Set<Var> getVariables() {
		Set<Var> var = new HashSet<Var>();
		var.add(this);
		return var;
	}
}
