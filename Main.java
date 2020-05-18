package space;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
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
	private double G = 10;

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
		double emass = 50;
		double smass = 100.989;

		//Particle s1 = new Particle(600, 450, 0, 0, smass, 30, Color.yellow, 0);
		Particle e1 = new Particle(0, 450, 1, 0, emass, 10, Color.blue, 1);
		Particle e2 = new Particle(1200, 450, -1, 0, emass, 10, Color.blue, 1);

		particles.add(e2);
		particles.add(e1);
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
				upmove(p, p1);
				move(p);
			}
		}
	}

	public void upmove(Particle p, Particle p1) {

		double diffX, diffY, distance, force, normX, normY, fX, fY, accX, accY;

		if (p.getId() == 0) {
			p.setMx(0);
			p.setMy(0);

		} else {
			// System.out.println(G);

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

				if (distance < (p.getDiamater() + p1.getDiamater()) / 2) {
					collision(p, p1);
					return;
				} else {

					// distance = Math.min((Math.max(distance, 5)), 100);
					// System.out.println("distance1 is " + distance);
					force = ((G * p.getMass() * p1.getMass())) / (distance * distance);
					// normalizering
					normX = diffX / distance;
					normY = diffY / distance;

					// kraft uppdelning
					fX = normX * force;
					fY = normY * force;

					// avgör acceleration a=F/M
					accX = fX / p.getMass();
					accY = fY / p.getMass();
				}

			} else {
				accX = 0;
				accY = 0;
			}

			// System.out.println("acc X is " + accX);

			p.setMx((p.getMx() - accX));
			p.setMy((p.getMy() - accY));
		}
	}

	public void move(Particle p) {

		p.setX(p.getX() + p.getMx());
		p.setY(p.getY() + p.getMy());
	}

	public void collision(Particle p, Particle p1) {

		double velocityf1x = (((p.getMass()-p1.getMass())/(p.getMass()+p1.getMass()))*p.getMx()) + ((2*p1.getMass())/(p.getMass()+p1.getMass())) * p1.getMx();
		double velocityf1y = (((p.getMass()-p1.getMass())/(p.getMass()+p1.getMass()))*p.getMy()) + ((2*p1.getMass())/(p.getMass()+p1.getMass())) * p1.getMy();
		
		double velocityf2x = (((p1.getMass()-p.getMass())/(p1.getMass()+p.getMass()))*p.getMx()) + ((2*p.getMass())/(p1.getMass()+p.getMass())) * p1.getMx();
		double velocityf2y = (((p1.getMass()-p.getMass())/(p1.getMass()+p.getMass()))*p.getMy()) + ((2*p.getMass())/(p1.getMass()+p.getMass())) * p1.getMy();
		
		p.setMx(velocityf1x);
		p.setMy(velocityf1y);
		p1.setMx(velocityf2x);
		p1.setMy(velocityf2y);
	}

}