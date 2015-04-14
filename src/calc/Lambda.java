package calc;

import java.util.Set;

public class Lambda implements Term {
	protected Var u;
	protected Term U;

	public Lambda(Var x, Term X) {
		this.u = x;
		this.U = X;
	}

	public Term sub(Term U, Var u) {
		return new Lambda(this.u, this.U.sub(U, u));
	}
	
	public Term rebind(Var x, Var y) {
		return new Lambda((Var)u.rebind(x,y), U.rebind(x, y));
	}

	public Term getAbstraction() {
		return U;
	}

	public Var getVar() {
		return this.u;
	}
	
	public String toString() {
		Term abstraction = U;
		StringBuilder sb = new StringBuilder();
		sb.append("(L");
		sb.append(u);
		while (abstraction instanceof Lambda) {
			Lambda l = (Lambda) abstraction;
			sb.append(l.u);
			abstraction = l.U;
		}
		sb.append(".");
		sb.append(abstraction.toString());
		sb.append(")");
		return sb.toString();
	}

	@Override
	public Term beta() {
		Term reduced = this.U.beta();
		if (reduced != null) {
			return new Lambda(this.u, reduced);
		}
		return null;
	}

	@Override
	public int depth() {
		return U.depth() + 1;
	}

	@Override
	public int width() {
		return 1;
	}

	@Override
	public Set<Var> getVariables() {
		Set<Var> vars = U.getVariables();
		vars.add(u);
		return vars;
	}

	public void setAbstraction(Term sub) {
		this.U = sub;
	}
}
