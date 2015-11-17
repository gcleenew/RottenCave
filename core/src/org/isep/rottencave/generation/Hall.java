package org.isep.rottencave.generation;
import java.util.ArrayList;


public class Hall {
	private Point centre;
	
	private float largeur;
	private float longueur;
	
	private float vX = 0;
	private float vY = 0;
	
	private boolean isMain;
	
	public boolean isMarked;
	
	public ArrayList<Hall> neighbors;
	public ArrayList<Hall> sucessors;
	
	public Hall(Point point, float largeur, float longueur) {
		this.centre = point;
		this.largeur = largeur;
		this.longueur = longueur;
		this.neighbors = new ArrayList<Hall>();
		this.sucessors = new ArrayList<Hall>();
	}
	
	public Point getPoint() {
		return centre;
	}
	
	public float getPosX() {
		return centre.x - largeur/2;
	}
	
	public float getCenterX() {
		return centre.x;
	}
	
	public float getPosY() {
		return centre.y - longueur/2;
	}
	
	public float getCenterY() {
		return centre.y;
	}
	
	public float getLargeur() {
		return largeur;
	}
	
	public float getLongueur() {
		return longueur;
	}
	
	public boolean getIsMain() {
		return isMain;
	}
	
	public void setIsMain(boolean isMain) {
		this.isMain = isMain;
	}
	
	public boolean markedNeighbors(){
		for (Hall hall : neighbors) {
			if (!hall.isMarked) {
				return false;
			}
		}
		return true;
	}
	
	public Hall getNotMarkedNeighbor(){
		for (Hall hall : neighbors) {
			if (!hall.isMarked) {
				return hall;
			}
		}
		return null;
	}
	
	public boolean computeSeparation(ArrayList<Hall> liste) {
		float neighborCount = 0;
		for (Hall square : liste) {
			if(square != this){
				if( computeCollision(square, this)){
					float[] vector2D = computeVelocityToSeparate(this, square);
					this.vX += vector2D[0];
					this.vY += vector2D[1];
					neighborCount++;
				}
			}
		}
		
		if(neighborCount == 0){
			this.vX = 0;
			this.vY = 0;
		}
		else {
			float norme = (float) Math.abs(Math.sqrt(this.vX*this.vX + this.vY*this.vY ));
			this.vX = (this.vX/(neighborCount*norme));
			this.vY = (this.vY/(neighborCount*norme));
		}
		return neighborCount > 0;
	}
	
	//return collision for square1
	public boolean computeCollision(Hall square1, Hall square2) {
		if (square1.getPosX() < square2.getPosX() + square2.getLargeur() &&
		        square1.getPosX() + square1.getLargeur() > square2.getPosX() &&
		        square1.getPosY() < square2.getPosY() + square2.getLongueur() &&
		        square1.getLongueur() + square1.getPosY() > square2.getPosY()){
			return true;
		}
		else {
			return false;
		}
		
	}
	
	//return velocity of the square1 to separate from square2*
	/**
	 * 
	 * @param square1
	 * @param square2
	 * @return
	 */
	public float[] computeVelocityToSeparate(Hall square1, Hall square2){
		float[] vector2D = new float[2];
		
			
		vector2D[0] = square1.getCenterX() - square2.getCenterX();
		vector2D[1] = square1.getCenterY() - square2.getCenterY();
		
		return vector2D;
	}
	
	public void move(){
		this.centre.x += vX;
		this.centre.y += vY;
	}
}
