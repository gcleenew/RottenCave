package org.isep.rottencave.generation;

import java.util.ArrayList;

public class Corridor {
	public Hall h1, h2;
	public ArrayList<Hall> littleHall;
	private Point midpoint;
	private enum Status {ligneH, ligneV, L};
	public Status status;
	
	public Point entree1, entree2, passage;
	
	public Corridor (Hall h1, Hall h2) {
		this.h1 = h1;
		this.h2 = h2;
		setMidpoint();
		setStatus();
		setPassage();
	}
	
	public void setMidpoint() {
		float mX = Math.abs( (this.h1.getCenterX() + this.h2.getCenterX())/2 );
		float mY = Math.abs( (this.h1.getCenterY() + this.h2.getCenterY())/2 );
		
		this.midpoint = new Point(mX, mY);
	}
	
	public Point getMidpoint() {
		return this.midpoint;
	}
	
	public void setStatus() {
		if ( ( this.midpoint.x >= this.h1.getPosX() && this.midpoint.x <= this.h1.getPosX()+ this.h1.getLargeur()) 
				&& ( this.midpoint.x >= this.h2.getPosX() && this.midpoint.x <= this.h2.getPosX() + this.h2.getLargeur() ) ){
			this.status = Status.ligneV;
		}
		
		else if ( ( this.midpoint.y >= this.h1.getPosY() && this.midpoint.y <= this.h1.getPosY() + this.h1.getLongueur()) 
				&& ( this.midpoint.y >= this.h2.getPosY() && this.midpoint.y <= this.h2.getPosY() + this.h2.getLongueur() ) ){
			this.status = Status.ligneH;
		}
		else {
			this.status = Status.L;
		}
	}
	public void setPassage() {
		if (this.status == Status.ligneH) {
			if(this.h1.getPosX() <= this.h2.getPosX()){
				this.entree1 = new Point(this.h1.getPosX() + this.h1.getLargeur(), this.midpoint.y);
				this.entree2 = new Point(this.h2.getPosX(), this.midpoint.y);
			}
			else {
				this.entree1 = new Point(this.h1.getPosX(), this.midpoint.y);
				this.entree2 = new Point(this.h2.getPosX() + this.h2.getLargeur(), this.midpoint.y);
			}
			this.passage = this.midpoint;
		}
		else if (this.status == Status.ligneV) {
			if(this.h1.getPosY() <= this.h2.getPosY()){
				this.entree1 = new Point(this.midpoint.x, this.h1.getPosY() + this.h1.getLongueur());
				this.entree2 = new Point(this.midpoint.x, this.h2.getPosY());
			}
			else {
				this.entree1 = new Point(this.midpoint.x, this.h1.getPosY());
				this.entree2 = new Point(this.midpoint.x, this.h2.getPosY() + this.h2.getLongueur());
			}
			this.passage = this.midpoint;
		}
		else {
			if (this.h1.getPosX() <= this.h2.getPosX() && this.h1.getPosY() <= this.h2.getPosY()) {
				this.entree1 = new Point(this.h1.getCenterX(), this.h1.getPosY() + this.h1.getLongueur());
				this.entree2 = new Point(this.h2.getPosX(), this.h2.getCenterY());
				this.passage = new Point(this.h1.getCenterX(), this.h2.getCenterY());
			}
			else if (this.h1.getPosX() >= this.h2.getPosX() && this.h1.getPosY() <= this.h2.getPosY()) {
				this.entree1 = new Point(this.h1.getCenterX(), this.h1.getPosY() + this.h1.getLongueur());
				this.entree2 = new Point(this.h2.getPosX() + this.h2.getLargeur(), this.h2.getCenterY());
				this.passage = new Point(this.h1.getCenterX(), this.h2.getCenterY());
			}
			else if (this.h1.getPosX() <= this.h2.getPosX() && this.h1.getPosY() >= this.h2.getPosY()) {
				this.entree1 = new Point(this.h1.getCenterX(), this.h1.getPosY());
				this.entree2 = new Point(this.h2.getPosX(), this.h2.getCenterY());
				this.passage = new Point(this.h1.getCenterX(), this.h2.getCenterY());
			}
			else {
				this.entree1 = new Point(this.h1.getCenterX(), this.h1.getPosY());
				this.entree2 = new Point(this.h2.getPosX() + this.h2.getLargeur(), this.h2.getCenterY());
				this.passage = new Point(this.h1.getCenterX(), this.h2.getCenterY());
			}
		}
	}
	
	public static boolean getExist(ArrayList<Corridor> corridorList, Hall h1, Hall h2) {
		for (Corridor corridor : corridorList) {
			if( (h1 == corridor.h1 && h2 == corridor.h2) || (h1 == corridor.h2 && h2 == corridor.h1) ) {
				return true;
			}
		}
		return false;
	}
}
