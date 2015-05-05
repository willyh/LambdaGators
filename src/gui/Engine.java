package gui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import animation.Animation;

public class Engine implements Runnable {
	public static void main(String[] args) {
		try {
			Util.eggOutline = ImageIO.read(new File("src/gui/egg_outline.png"));
			Util.eggSolid = ImageIO.read(new File("src/gui/egg_solid.png"));
			Util.coloredEgg = new BufferedImage(Util.eggSolid.getWidth(),
					Util.eggSolid.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Util.gatorOutline = ImageIO.read(new File(
					"src/gui/alligator_outline.png"));
			Util.gatorSolid = ImageIO.read(new File(
					"src/gui/alligator_solid.png"));
			Util.coloredGator = new BufferedImage(Util.gatorSolid.getWidth(),
					Util.gatorSolid.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Util.old = ImageIO.read(new File("src/gui/old_gator.png"));
		} catch (IOException e) {
			System.err.println("Error: Couldnt find egg.png or alligator.png");
			System.exit(1);
		}

		Util.colors = new TreeMap<Integer, Color>();
		Util.colors.put(0, Color.GREEN);
		Util.colors.put(1, Color.PINK);
		Util.colors.put(2, Color.BLUE);
		Util.colors.put(3, Color.YELLOW);
		Util.colors.put(4, Color.MAGENTA);
		Util.colors.put(5, Color.ORANGE);
		Util.colors.put(6, Color.LIGHT_GRAY);
		Util.colors.put(7, Color.CYAN);
		Util.colors.put(8, Color.BLACK);
		Util.colors.put(9, Color.DARK_GRAY);
		Util.colors.put(10, Color.RED);

		Thread t = new Thread(new Engine());
		t.start();
	}

	@Override
	public void run() {
		Game g = new Game();
		try {
			while (true) {
				synchronized (Animation.animationLock) {
					Animation.animationLock.wait();
					while (Animation.nextFrame()) {
						g.repaint();
						Thread.sleep(50);
					}
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
