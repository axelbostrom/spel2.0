package space;

import java.awt.Color;
import java.awt.Graphics2D;

public class Particle {
	private double x, y;
	private double mx, my;
	private double mass;
	private double diamater;
	private Color color;
	private int id;

	public Particle(double x, double y, double mx, double my, double mass, double diamater, Color color, int id) {
		super();
		this.x = x;
		this.y = y;
		this.mx = mx;
		this.my = my;
		this.mass = mass;
		this.diamater = diamater;
		this.color = color;
		this.id = id;
	}
	
	public int getId() {
		return id;
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

	public double getMx() {
		return mx;
	}

	public void setMx(double mx) {
		this.mx = mx;
		// System.out.println("mx is " + mx);
	}

	public double getMy() {
		return my;
	}

	public void setMy(double my) {
		this.my = my;
		// System.out.println("my is " + my);
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public double getDiamater() {
		return diamater;
	}

	public void setDiamater(double diamater) {
		this.diamater = diamater;
	}

	public Color getColor() {
		return color;
	}

	public void render(Graphics2D g) {
		g.setColor(color);
		g.fillOval((int) x, (int) y, (int) diamater, (int) diamater);

	}
}
