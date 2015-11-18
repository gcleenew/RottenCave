package org.isep.rottencave.generation;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

public class Point {

	public float x, y, z;
	
	private Hall hall;

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
	
	public Hall getHall(ArrayList<Hall> hallList) {
		for (Hall hall : hallList) {
			if(this.posEquals(hall.getPoint().x, hall.getPoint().y)) {
				return hall;
				
			}
		}
		return null;
	}
	
	public float getNorme(){
		return (float) Math.abs(Math.sqrt((x-350)*(x-350) + (y-250)*(y-250)));
	}
	
	public void setHall(Hall hall) {
		this.hall = hall;
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
	
	public boolean posEquals(float x, float y) {
		if( this.x == x && this.y == y) {
			return true;
		}
		return false;
	}
	
	public void setRandomPositionInCircle(int radius){
		double t = 2 * Math.PI * Math.random();
		double u = Math.random() + Math.random();
		double r = 0;
		if(u > 1){
			r = 2 - u;
		}
		else {
			r = u;
		}
		this.x = (float) (radius * r  * Math.cos(t));
		this.y = (float) (radius * r * Math.sin(t));
	}
	@Override
	public int hashCode() {
		int hash = 7;
		hash = (int) (31 * hash + this.x + this.y);
		return hash;
	}

}
