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
	double mx = 0;
	double my = 0;

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

		diffX = x - ex;

		// distansen mellan partiklarna i y-led
		diffY = y - ey;

		distance = Math.sqrt((diffX) * (diffX) + (diffY) * (diffY));
		force = (G * Emass * Amass) / (distance * distance);

		// normalizering
		normX = diffX / distance;
		normY = diffY / distance;

		// kraft uppdelning
		fX = normX * force;
		fY = normY * force;

		// avgör acceleration
		accX = fX / getAmass();
		accY = fY / getAmass();

		// På något sätt råkade jag räkna accelrationen baklänges så jag får subtrahera
		// den med den nuvarande hastigheten
		mx = mx - accX;
		my = my - accY;

	}

	public void move() {

		x = x + mx;
		y = y + my;

	}

}
