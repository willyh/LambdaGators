package calc;

import java.util.HashSet;
import java.util.Set;

public class Application implements Term {
	protected Term U, V;

	public Application(Term U, Term V) {
		this.U = U;
		this.V = V;
	}

	public Term beta() {
		Term reduced = null;
		if (U instanceof Lambda) {
			Lambda l = (Lambda) U;
			return l.getAbstraction().sub(V, l.getVar());
		}
		reduced = U.beta();
		if (reduced != null) {
			return new Application(reduced, V);
		}
		reduced = V.beta();
		if (reduced != null) {
			return new Application(U, reduced);
		}
		return reduced;
	}

	public Term alpha() {
		if (U instanceof Application) {
			return new Application(((Application) U).alpha(), V);
			
		} else if (U instanceof Lambda) {
			Lambda l = (Lambda) U;
			// rebind variables in V that are bound in U
			Set<Var> leftVars = l.getAbstraction().getVariables();
			Set<Var> rightVars = V.getVariables();
			Set<Var> intersect = new HashSet<Var>();
			for (Var v : leftVars) {
				if (rightVars.contains(v)) {
					intersect.add(v);
				}
			}
			// don't wanna remove the var we are subbing for tho
			rightVars.remove(l.getVar());
			// find a new element that isn't in L
			int max = 0;
			for (Var vl : leftVars) {
				max = Math.max(max, vl.getSymbol());
			}
			
			Term newV = V;
			for (Var v : intersect) {
				Var rebind = new Var(++max);
				newV = newV.rebind(v, rebind);
			}
			return new Application(U, newV);
		}
		return this;
	}

	@Override
	public Term sub(Term X, Var x) {
		return new Application(U.sub(X, x), V.sub(X, x));
	}

	@Override
	public Term rebind(Var x, Var y) {
		return new Application(U.rebind(x, y), V.rebind(x, y));
	}

	public String toString() {
		return "(" + U.toString() + V.toString() + ")";
	}

	public Term getLeft() {
		return U;
	}

	public Term getRight() {
		return V;
	}

	@Override
	public int depth() {
		return 1;
	}

	@Override
	public int width() {
		return 1 + U.width();
	}

	@Override
	public Set<Var> getVariables() {
		Set<Var> left = U.getVariables();
		Set<Var> right = V.getVariables();
		left.addAll(right);
		return left;
	}

}
