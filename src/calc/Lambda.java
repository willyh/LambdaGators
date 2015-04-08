package calc;
public class Lambda implements Term {
	private Var x;
	private Term X;

	public Lambda(Var x, Term X) {
		this.x = x;
		this.X = X;
	}

	public Term sub(Term U, Var u) {
		return new Lambda(this.x, this.X.sub(U, u));
	}

	public Term getAbstraction() {
		return X;
	}

	public Var getVar() {
		return this.x;
	}
	
	public String toString() {
		Term abstraction = X;
		StringBuilder sb = new StringBuilder();
		sb.append("(L");
		sb.append(x);
		while (abstraction instanceof Lambda) {
			Lambda l = (Lambda) abstraction;
			sb.append(l.x);
			abstraction = l.X;
		}
		sb.append(".");
		sb.append(abstraction.toString());
		sb.append(")");
		return sb.toString();
	}

	@Override
	public Term beta() {
		return new Lambda(this.x, this.X.beta());
	}

	@Override
	public int depth() {
		return X.depth() + 1;
	}

	@Override
	public int width() {
		return 1;
	}
}
