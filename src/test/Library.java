package test;

import java.util.Map;
import java.util.TreeMap;

import calc.*;

public class Library {
	public static Var x = new Var(0);
	public static Var y = new Var(1);
	public static Term I = new Lambda(new Var(0), new Var(0));

	public static Term I(int i) {
		return new Lambda(new Var(i), new Var(i));
	}

	public static Term K = new Lambda(new Var(0), new Lambda(new Var(1),
			new Var(0)));

	public static Term Kstar = new Lambda(new Var(0), new Lambda(new Var(1),
			new Var(1)));

	public static Term omega = new Lambda(new Var(0), new Application(
			new Var(0), new Var(0)));

	public static Term omega(int i) {
		return new Lambda(new Var(i), new Application(new Var(i), new Var(i)));
	}

	public static Term N(int n) {
		Term t = new Var(1);
		for (int i = 0; i < n; ++i) {
			t = new Application(new Var(0), t);
		}
		return new Lambda(new Var(0), new Lambda(new Var(1), t));
	}

	public static Term Plus(Term X, Term Y) {
		// Luvxy.ux(vxy)
		Var x = new Var(0);
		Var y = new Var(1);
		Var u = new Var(3);
		Var v = new Var(4);
		Term ux = new Application(u, x);
		Term vxy = new Application(new Application(v, x), y);
		Term P = new Lambda(u, new Lambda(v, new Lambda(x, new Lambda(y,
				new Application(ux, vxy)))));
		return new Application(new Application(P, X), Y);
	}
	
	public static Term Push = Library.parse("Luva.auv");
	public static Term Pop = Library.parse("Lu.uK");
	public static Term EmptyStack = Library.parse("Lb.b*");

	private static Var z = new Var(2);
	public static Term S = new Lambda(z, new Lambda(x, new Lambda(y,
			new Application(x, new Application(new Application(z, x), y)))));

	private static Term yxxy = new Lambda(x, new Lambda(y, new Application(y,
			new Application(new Application(x, x), y))));
	public static Term Y = new Application(yxxy, yxxy);

	public static Term parse(String s) {
		Map<Character, Term> varMap = new TreeMap<Character, Term>();
		varMap.put('I', I);
		varMap.put('Y', Y);
		varMap.put('K', K);
		varMap.put('*', Kstar);
		varMap.put('V', Push);
		varMap.put('^', Pop);
		varMap.put('E', EmptyStack);
		for (int i = (int)'0'; i < (int)'0'+10; ++i) {
			varMap.put((char)i, Library.N(i-(int)'0'));
		}
		return parse(s, varMap);
	}

	private static Term parse(String s, Map<Character, Term> varMap) {
		if (s.length() == 0) {
			return null;
		}
		switch (s.charAt(0)) {
		case 'L':
			int i = 1;
			for (; i < s.length() && s.charAt(i) != '.'; ++i) {
				char c = s.charAt(i);
				if (!varMap.containsKey(c)) {
					varMap.put(c, new Var(varMap.size()));
				}
			}

			// create term backwards
			Term ret = parse(s.substring(i + 1), varMap);
			--i;
			for (; i > 0; --i) {
				char c = s.charAt(i);
				// require anything between L and . to be a variable
				try {
				ret = new Lambda((Var)varMap.get(c), ret);
				} catch (Exception e) {
					System.err.println("Parsing Error: \"" + s + "\"");
					e.printStackTrace();
				}
			}
			return ret;
		default:
			Term t = null;
			for (int j = 0; j < s.length(); ++j) {
				char c = s.charAt(j);
				if (c != '(' && c != ')' && c != 'L' && c != '.') {
					if (!varMap.containsKey(c)) {
						varMap.put(c, new Var(varMap.size()));
					}
				}
				if (c == '(') {
					int match = parenMatchIndex(s, j);
					if (match == -1) {
						System.err.println("Parsing Error: \"" + s + "\"");
						new Exception().printStackTrace();
					}
					String content = s.substring(j+1, match);
					if (t == null) {
						t = parse(content, varMap);
					} else {
						t = new Application(t, parse(content, varMap));
					}
					j = match;
				} else {
					if (t == null) {
						t = varMap.get(c);
					} else {
						t = new Application(t, varMap.get(c));
					}
				}
			}
			return t;
		}
	}

	/**
	 * Find the index of the right paren that matches a left paren at start or
	 * vice versa if start points to a right paren
	 * 
	 * @param s
	 * @param start
	 * @return
	 */
	private static int parenMatchIndex(String s, int start) {
		int count = 1;
		int index = start;
		if (s.charAt(start) == '(') {
			++index;
			for (; index < s.length() && count > 0; ++index) {
				if (s.charAt(index) == ')') {
					--count;
				} else if (s.charAt(index) == '(') {
					++count;
				}
			}
		} else if (s.charAt(start) == ')') {
			--index;
			for (; index >= 0 && count > 0; ++index) {
				if (s.charAt(index) == ')') {
					++count;
				} else if (s.charAt(index) == '(') {
					--count;
				}
			}

		}
		if (count == 0) {
			return index - 1;
		} else {
			return -1;
		}
	}

}
