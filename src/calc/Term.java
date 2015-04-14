package calc;

import java.util.Set;

public interface Term {
	public Term sub(Term X, Var x);
	/**
	 * Beta reduce a term
	 * @return the beta reduced term or null if no beta reduction can be made
	 */
	public Term beta();
	public int depth();
	public int width();
	public Set<Var> getVariables();
	public Term rebind(Var x, Var y);
}
