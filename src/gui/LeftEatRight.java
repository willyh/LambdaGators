package gui;

public class LeftEatRight extends Animation {

	private boolean forward, backward;
	private GTerm right;
	private GVar v;

	public LeftEatRight(GVar var, GTerm right) {
		this.v = var;
		this.right = right;
		forward = true;
		backward = false;
	}

	@Override
	public boolean step() {
		int xstart = v.getX();
		int time = 500 / 50; // milliseconds
		int rate = (right.getX() - v.getX()) / time;
		if (forward) {

			v.setX(v.getX() + rate);
			if (v.getX() >= right.getX()) {
				forward = false;
				backward = true;
			}
		} else if (backward) {
			right.setWidth(0);
			v.setX(v.getX() - rate);
			if (v.getX() <= xstart) {
				backward = false;
			}
		} else {
			rate = v.getHeight() / time;
			while (v.getHeight() > 0) {
				v.setHeight(v.getHeight() - rate);
				v.setY(v.getY() + rate);
			}
		}
		v.setHeight(0);
		return false;
	}

	@Override
	public boolean executeParallel() {
		// TODO Auto-generated method stub
		return false;
	}

}
