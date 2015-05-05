package animation;

import gui.GTerm;
import gui.GVar;

public class LeftEatRight extends Animation {

	private boolean forward, backward;
	private GTerm right;
	private GVar v;
	private int xstart, hstart;

	public LeftEatRight(GVar var, GTerm right) {
		this.v = var;
		this.right = right;
		forward = true;
		backward = false;
		xstart = v.getX();
		hstart = v.getHeight();
	}

	@Override
	public boolean step() {
		int time = 500 / 50; // milliseconds
		int rate = Math.max(1, (right.getX() - xstart) / time);
		if (forward) {
			v.setX(v.getX() + rate);
			if (v.getX() >= right.getX()) {
				forward = false;
				backward = true;
			}
			return true;
		} else if (backward) {
			right.setWidth(0);
			v.setX(v.getX() - rate);
			if (v.getX() <= xstart) {
				backward = false;
			}
			return true;
		} else {
			rate = hstart / time;
			v.setHeight(v.getHeight() - rate);
			v.setY(v.getY() + rate);
			if (v.getHeight() <= 0) {
				v.setHeight(0);
				return false;
			}
			return true;
		}
	}

	@Override
	public boolean executeParallel() {
		return false;
	}

}
