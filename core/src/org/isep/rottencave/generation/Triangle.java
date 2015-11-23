package org.isep.rottencave.generation;

public class Triangle {

	public Point p1, p2, p3, circumcenter;
	
	public Triangle(Point p1, Point p2, Point p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		calculateCircumcenter();
	}
	
	private void calculateCircumcenter() {
		Point circumcenter = new Point();
		this.circumcenter = circumcenter;
	}
	
	public boolean sharesVertex(Triangle t) {
		return p1 == t.p1 || p1 == t.p2 || p1 == t.p3 ||
		p2 == t.p1 || p2 == t.p2 || p2 == t.p3 || 
		p3 == t.p1 || p3 == t.p2 || p3 == t.p3;
	}
}
