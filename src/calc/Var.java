package calc;
public class Var implements Term {
	private String symbol;

	public Var(String symbol) {
		this.symbol = symbol;
	}
	
	public String getSymbol() {
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
	
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Var)) {
			return false;
		} else {
			Var v = (Var) o;
			return this.symbol.equals(v.symbol);
		}
	}
	
	public String toString() {
		return this.symbol;
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
}
