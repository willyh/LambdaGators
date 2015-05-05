package gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Animation {
	private static final int FRAME_RATE = 500;
	private static Queue<Animation> animationQ = new LinkedList<Animation>();
	private static ArrayList<Animation> activeAnimations = new ArrayList<Animation>();

	public static void queue(Animation a) {
		animationQ.add(a);
	}
	
	public static boolean nextFrame() {
		ArrayList<Animation> deletedAnimations = new ArrayList<Animation>();
		if (activeAnimations.isEmpty()) {
			if (animationQ.isEmpty()) {
				return false;
			}
			Animation anim = animationQ.remove();
			activeAnimations.add(anim);
			while (anim.executeParallel()) {
				anim = animationQ.remove();
				activeAnimations.add(anim);
			}
		}
		
		// step all animations
		for (Animation anim : activeAnimations) {
			if (!anim.step()) {
				deletedAnimations.add(anim);
			}
		}
		
		// remove finished animaitons
		activeAnimations.removeAll(deletedAnimations);
		if (activeAnimations.isEmpty() && animationQ.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public abstract boolean step();
	public abstract boolean executeParallel();
}
