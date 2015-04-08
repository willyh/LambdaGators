package calc;

public interface Term {
	public Term sub(Term X, Var x);
	public Term beta();
	public int depth();
	public int width();
}
