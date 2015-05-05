package animation;

public class Pause extends Animation {

	private int frame, length;
	public Pause(int length) {
		frame = 0;
		this.length = length;
	}
	
	@Override
	public boolean step() {
		if (frame < length) {
			++frame;
			return true;
		}
		return false;
	}

	@Override
	public boolean executeParallel() {
		return false;
	}

}
