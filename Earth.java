package space;

import java.awt.Color;
import java.awt.Graphics2D;

public class Earth {
	
	double x;
	double y;
	double mass;
	double G;

	public Earth(double x, double y, double mass, double G) {
		this.x = x;
		this.y = y;
		this.mass = mass;
		this.G = G;
		
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

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public double getG() {
		return G;
	}

	public void setG(double g) {
		G = g;
	}

	public void render(Graphics2D g) {
		g.setColor(Color.BLUE);
		g.fillOval((int) getX(), (int) getY(), 20, 20);
		
	}

}
