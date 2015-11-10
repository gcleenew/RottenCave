package org.isep.rottencave.generation;

/**
 * 
 * Punto en un plano euclideano.
 * 
 * @author Benjamin Diaz
 *
 */
public class Point {

	public float x, y, z;

	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Point(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Point() {
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Point) {
			Point p = (Point) obj;
			if (p.x == x && p.y == y) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = (int) (31 * hash + this.x + this.y);
		return hash;
	}

}
