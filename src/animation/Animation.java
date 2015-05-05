package animation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Animation {
	private static final int FRAME_RATE = 500;
	private static Queue<Animation> animationQ = new LinkedList<Animation>();
	private static ArrayList<Animation> activeAnimations = new ArrayList<Animation>();
	public static final Object animationLock = new Object();

	public static void queue(Animation a) {
		synchronized (animationQ) {
			animationQ.add(a);
			animationQ.notify();
		}
	}

	public static boolean nextFrame() {
		if (activeAnimations.isEmpty()) {
			synchronized (animationQ) {
				while (animationQ.isEmpty()) {
					try {
						animationQ.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Animation anim = animationQ.remove();
				activeAnimations.add(anim);
				// first remove one then if it's parallel remove all parallel
				if (anim.executeParallel()) {
					while (!animationQ.isEmpty()
							&& animationQ.peek().executeParallel()) {
						activeAnimations.add(animationQ.remove());
					}
				}
			}
		}

		ArrayList<Animation> deletedAnimations = new ArrayList<Animation>();
		// step all animations
		for (Animation anim : activeAnimations) {
			if (!anim.step()) {
				deletedAnimations.add(anim);
			}
		}

		// remove finished animaitons
		activeAnimations.removeAll(deletedAnimations);
		synchronized (activeAnimations) {
			if (activeAnimations.isEmpty() && animationQ.isEmpty()) {
				activeAnimations.notifyAll();
				return false;
			} else {
				return true;
			}
		}
	}

	public static void animationWait() {
		synchronized (activeAnimations) {
			while (!activeAnimations.isEmpty() || !animationQ.isEmpty()) {
				try {
					activeAnimations.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public abstract boolean step();

	public abstract boolean executeParallel();
}
