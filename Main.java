package space;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;

public class Main extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private static ArrayList<Particle> particles = new ArrayList<>();
	private boolean running;
	private double G = 10; // Gravitationskonstant
	Random rand = new Random();
	Particle particle; // initierar en particle

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
		Dimension d = new Dimension(1920, 1080);
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
				Thread.sleep(50);
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
		double smass = 5000;
		int amountPlanets = 10; // antalet planeter man vill ha
		double x, y, vx, vy, pmass, rad;

		for (int i = 0; i < amountPlanets; i++) {
			pmass = rand.nextInt(100) + 30;
			x = rand.nextInt(6200);
			y = rand.nextInt(3600);
			vx = rand.nextInt(5) - 3;
			vy = rand.nextInt(5) - 3;
			rad = pmass / 3;
			particle = new Particle(x, y, vx, vy, pmass, rad, Color.red, 1);
			particles.add(particle);

		}
		Particle s1 = new Particle(3200, 1800, 0, 0, smass, 150, Color.yellow, 0);
		particles.add(s1);
	}

	private void render() {

		BufferStrategy strategy = getBufferStrategy();
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.scale(0.2, 0.2);

		for (Particle p : particles) {
			p.render(g);

		}
		strategy.show();

	}

	private void update() {
		for (Particle p : particles) {
			for (Particle p1 : particles) {
				if (!((p.getId() == 2) || (p1.getId() == 2))) {
					upmove(p, p1);
					move(p);
				}

			}
		}
	}

	public void upmove(Particle p, Particle p1) {

		double diffX, diffY, distance, force, normX, normY, fX, fY, accX, accY;

		if (p.getId() == 0) {
			p.setMx(0);
			p.setMy(0);

		} else {

			// distansen mellan partiklarna i x-led

			diffX = p.getX() - p1.getX();

			// distansen mellan partiklarna i y-led
			diffY = p.getY() - p1.getY();

			if (diffX != 0 || diffY != 0) {
				distance = Math.sqrt((diffX) * (diffX) + (diffY) * (diffY));

				if (distance < (p.getDiamater() + p1.getDiamater()) / 2) {

					// vilken partikel träffar vilken
					// om hastighet 0 = knas
					if (p1.getMass() >= p.getMass()) {
						collision(p1, p);
						return;
					} else {
						collision(p, p1);
						return;
					}
				} else {
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
					p.setMx((p.getMx() - accX));
					p.setMy((p.getMy() - accY));
				}
			}
		}
	}

	public void move(Particle p) {

		p.setX(p.getX() + p.getMx());
		p.setY(p.getY() + p.getMy());
	}

	public void collision(Particle p, Particle p1) {
		double vx, vy;

		vx = (p.getMass() * p.getMx() + p1.getMass() * p1.getMx()) / (p.getMass() + p1.getMass());
		vy = (p.getMass() * p.getMy() + p1.getMass() * p1.getMy()) / (p.getMass() + p1.getMass());

		if (p.getId() == 1) {
			p.setMass(p.getMass() + p1.getMass());
			p.setDiamater(p.getDiamater() + p1.getDiamater() / 2);
		}
		p.setMass(p.getMass() + p1.getMass());
		p.setDiamater(p.getDiamater() + p1.getDiamater() / 5);

		p.setMx(vx);
		p.setMy(vy);

		p1.setDiamater(0);
		p1.setMass(0);
		p1.setMx(0);
		p1.setMy(0);
		p1.setId(2);

	}
}
