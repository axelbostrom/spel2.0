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
	Asteroid a3;
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
		Dimension d = new Dimension(1200, 900);
		setPreferredSize(d);
		setMinimumSize(d);
		setMaximumSize(d);
		
		double earthX = 600;
		double earthY = 450;
		double G = 10;
		double earthMass = 5000;
		
		double a1mass = 50;
		double a2mass = 10;
		double a3mass = 100;

		a1 = new Asteroid(60, 60, earthX, earthY, a1mass, earthMass, G);
		a2 = new Asteroid(800, 800, earthX, earthY, a2mass, earthMass, G);
		a3 = new Asteroid(300, 300, earthX, earthY, a3mass, earthMass, G);
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
		a3.render(g);
		strategy.show();

	}

	private void update() {
		a1.update();
		a2.update();
		a3.update();
	}

}