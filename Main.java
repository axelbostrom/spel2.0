package space;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Main extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ArrayList<Particle> particles = new ArrayList<>();
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

		makeParticles();

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
		if (!running) {
			Thread t = new Thread(this);
			createBufferStrategy(3);
			running = true;
			t.start();
		}
	}

	public void makeParticles() {
		Particle earth = new Particle(600, 450, 0, 0, 1000000, 20, 10, Color.blue, 0);
		Particle a1 = new Particle(60, 60, 2, 1, 50, 5, 0.01, Color.gray, 1);
		Particle a2 = new Particle(900, 900, -2, -1, 25, 4, 0.01, Color.green, 2);
		Particle a3 = new Particle(60, 800, 3, 1, 10, 4, 0.01, Color.blue, 3);
		particles.add(earth);
		particles.add(a1);
		particles.add(a2);
		particles.add(a3);
	}

	private void render() {

		BufferStrategy strategy = getBufferStrategy();
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		for (Particle p : particles) {
			p.render(g);
		}
		strategy.show();

	}

	private void update() {
		for (Particle p : particles) {
			for (Particle p1 : particles) {
				//System.out.println("px before loop" + p.getMass());
				upmove(p, p1);
				move(p);
			}
		}
	}

	public void upmove(Particle p, Particle p1) {

		if (p.getId() == 0) { //kollar så att det inte är jorden som kollar mot andra asteroider
			p.setMx(0);
			p.setMy(0);

		} else {

			double diffX, diffY, distance, force, normX, normY, fX, fY, accX, accY;

			// distansen mellan partiklarna i x-led

			// System.out.println("getx is " + p.getX());
			diffX = p.getX() - p1.getX();

			// distansen mellan partiklarna i y-led
			diffY = p.getY() - p1.getY();

			// int constrain(int val, int minv, int maxv) {
			// return min(max(val, minv), maxv);
			// d = constrain(d,5.0,25.0);

			if (!(diffX == 0)) {
				distance = Math.sqrt((diffX) * (diffX) + (diffY) * (diffY));
				// distance = Math.min((Math.max(distance, 5)), 100);
				// System.out.println("distance1 is " + distance);
				force = (p.getG() * p.getMass() * p1.getMass()) / (distance * distance);

				// normalizering
				normX = diffX / distance;
				normY = diffY / distance;

				// kraft uppdelning
				fX = normX * force;
				fY = normY * force;

				// avgör acceleration a=F/M
				accX = fX / p.getMass();
				accY = fY / p.getMass();

			} else {
				accX = 0;
				accY = 0;
			}

			// System.out.println("acc X is " + accX);

			p.setMx(p.getMx() - accX);
			// System.out.println("mx is " + p.getMx());
			p.setMy(p.getMy() - accY);
			// System.out.println("my is " + p.getMy());
		}

	}

	public void move(Particle p) {
		p.setX(p.getX() + p.getMx());
		p.setY(p.getY() + p.getMy());
	}
	
	public boolean checkCollision(Particle p, Particle p1) {
		// behöver fixas
		return true;
	}

}