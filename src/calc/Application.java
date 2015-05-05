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
		int max = 0;
		for (Var vl : this.getVariables()) {
			max = Math.max(max, vl.getSymbol());
		}
		return this.alpha(max);
	}

	public Term alpha(int numBound) {
		Term newV = V;
		int max = numBound;
		if (U instanceof Lambda) {
			Lambda l = (Lambda) U;
			// rebind variables in V that are bound in U
			Set<Var> leftVars = l.getAbstraction().getBoundVariables();
			Set<Var> rightVars = V.getBoundVariables();
			Set<Var> intersect = new HashSet<Var>();
			for (Var v : leftVars) {
				if (rightVars.contains(v)) {
					intersect.add(v);
				}
			}
			// don't wanna remove the var we are subbing for tho
			rightVars.remove(l.getVar());
			// find a new element that isn't in L

			for (Var v : intersect) {
				Var rebind = new Var(++max);
				newV = newV.rebind(v, rebind);
			}
		}

		return new Application(U.alpha(max), newV.alpha(max));
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

	@Override
	public Set<Var> getBoundVariables() {
		Set<Var> left = U.getBoundVariables();
		Set<Var> right = V.getBoundVariables();
		left.addAll(right);
		return left;
	}

	@Override
	public Term rebindFree(Var u, Var var) {
		return new Application(U.rebindFree(u, var), V.rebindFree(u, var));
	}

}
