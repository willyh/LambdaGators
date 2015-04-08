package calc;

public class Application implements Term {
private Term U, V;

public Application(Term U, Term V) {
	this.U = U;
	this.V = V;
}

public Term beta() {
	Term reducedU = U;
	Term reducedV = V;
	if (reducedU instanceof Application) {
		reducedU = ((Application)U).beta();
	}
	if (reducedV instanceof Application) {
		reducedV = ((Application)V).beta();
	}
	if (reducedU instanceof Lambda) {
		Lambda l = (Lambda)reducedU;
		return l.getAbstraction().sub(reducedV, l.getVar());
	} else {
		return this;
	}
}

@Override
public Term sub(Term X, Var x) {
	return new Application(U.sub(X,x), V.sub(X,x));
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

}
