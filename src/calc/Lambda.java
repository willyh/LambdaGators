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
	
	@Override
	public Set<Var> getBoundVariables() {
		Set<Var> vars = U.getBoundVariables();
		vars.add(u);
		return vars;
	}

	public void setAbstraction(Term sub) {
		this.U = sub;
	}

	@Override
	public Term alpha() {
		return new Lambda(this.getVar(), this.getAbstraction().alpha());
	}

	@Override
	public Term alpha(int max) {
		// if U rebinds u later on then change u to a different var
		// and fix all variables bound to this u
		if (getAbstraction().getBoundVariables().contains(u)) {
			Var rebound = new Var(max+1);
			return new Lambda(rebound, this.getAbstraction().rebindFree(u,rebound).alpha(max+1));
		}
		return new Lambda(this.getVar(), this.getAbstraction().alpha(max));
	}

	@Override
	public Term rebindFree(Var u, Var var) {
		if (u.equals(this.u)) {
			return this;
		} else {
			return new Lambda(this.u, this.U.rebindFree(u, var));
		}
	}
}
