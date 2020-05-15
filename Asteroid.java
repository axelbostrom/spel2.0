package space;

import java.awt.Color;
import java.awt.Graphics2D;

public class Asteroid {
	double x;
	double y;
	double ex;
	double ey;
	double Amass;
	double G;
	double Emass;
	double mx = 2;
	double my;

	double diffX, diffY, distance, force, normX, normY, fX, fY, accX, accY;

	public Asteroid(double x, double y, double ex, double ey, double Amass, double G, double Emass) {
		this.x = x;
		this.y = y;
		this.ex = ex;
		this.ey = ey;
		this.Amass = Amass;
		this.G = G;
		this.Emass = Emass;
	}

	public double getAmass() {
		return Amass;
	}

	public void setAmass(double amass) {
		amass = Amass;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void render(Graphics2D g) {
		g.setColor(Color.GRAY);
		g.fillOval((int) getX(), (int) getY(), 10, 10);

	}

	public void update() {
		upmove();
		move();
	}

	public double getEx() {
		return ex;
	}

	public void setEx(double ex) {
		this.ex = ex;
	}

	public double getEy() {
		return ey;
	}

	public void setEy(double ey) {
		this.ey = ey;
	}

	public double getG() {
		return G;
	}

	public void setG(double g) {
		G = g;
	}

	public double getEmass() {
		return Emass;
	}

	public void setEmass(double emass) {
		Emass = emass;
	}

	public double getMx() {
		return mx;
	}

	public void setMx(double mx) {
		this.mx = mx;
	}

	public double getMy() {
		return my;
	}

	public void setMy(double my) {
		this.my = my;
	}

	// uppdaterar hastigheten
	public void upmove() {

		double diffX, diffY, distance, force, normX, normY, fX, fY, accX, accY;

		// distansen mellan partiklarna i x-led

		diffX = ex - x;

		// distansen mellan partiklarna i y-led
		diffY = ey - y;

		// int constrain(int val, int minv, int maxv) {
		// return min(max(val, minv), maxv);
		// d = constrain(d,5.0,25.0);
		distance = Math.sqrt((diffX) * (diffX) + (diffY) * (diffY));
		System.out.println("distance is " + distance);
		// distance = Math.min((Math.max(distance, 5)), 100);
		// System.out.println("distance1 is " + distance);
		force = (G * Emass * Amass) / (distance * distance);
		System.out.println("force is " + force);

		// normalizering
		normX = diffX / distance;
		normY = diffY / distance;

		// kraft uppdelning
		fX = normX * force;
		fY = normY * force;

		// avgör acceleration a=F/M
		accX = fX / getAmass();
		accY = fY / getAmass();

		System.out.println("mx is " + mx);
		System.out.println("my is " + my);
		System.out.println("getMx is " + getMx());
		System.out.println("getMy is " + getMy());

		mx = mx + accX;
		my = my + accY;

	}

	public void move() {

		x = x + mx;
		y = y + my;

	}

}