package animation;

import gui.GVar;

/** egg shaking animation **/
public class Shake extends Animation {

	private GVar var;
	private int frame, xstart;

	public Shake(GVar var) {
		this.var = var;
		this.frame = 0;
		xstart = var.getX();
	}

	@Override
	public boolean step() {
		float shakeRange = (float) var.getWidth() / 2;
		if (frame <= 10) {
			var.setX(xstart + (int) (shakeRange * Math.sin((float) frame)));
			++frame;
			return true;
		} else if (frame <= 15) {
			++frame;
			var.setX(xstart + (int) (shakeRange/2 * Math.sin((float) frame)));
			return true;
		}
		return false;
	}

	@Override
	public boolean executeParallel() {
		return true;
	}

}
