package space;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Main extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Asteroid a1;
	Asteroid a2;
	Earth earth;
	private boolean running;

	public static void main(String[] args) {
		JFrame myFrame = new JFrame("Amazing space game");
		Main load = new Main();
		myFrame.add(load);
		myFrame.pack();
		myFrame.setResizable(false);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setVisible(true);
		load.start();
		myFrame.setLocationRelativeTo(null);
	}

	public Main() {
		Dimension d = new Dimension(800, 600);
		setPreferredSize(d);
		setMinimumSize(d);
		setMaximumSize(d);
		
		double earthX = 500;
		double earthY = 100;
		double G = 1;
		double earthMass = 100;
		
		double a1mass = 5;
		double a2mass = 5;

		a1 = new Asteroid(60, 60, earthX, earthY, a1mass, earthMass, G);
		a2 = new Asteroid(300, 30, earthX, earthY, a2mass, earthMass, G);
		earth = new Earth(earthX, earthY, earthMass, G);

	}

	@Override
	public void run() {
		while (running) {
			update();
			render();

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
				running = false;
			}
		}

	}
	
	public void start() {
		if(!running) {
			Thread t = new Thread(this);
			createBufferStrategy(3);
			running = true;
			t.start();
		}
	}

	private void render() {

		BufferStrategy strategy = getBufferStrategy();
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		earth.render(g);
		a1.render(g);
		a2.render(g);
		strategy.show();

	}

	private void update() {
		a1.update();
		a2.update();
	}

}
