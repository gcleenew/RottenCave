package org.isep.rottencave.generation;
import java.util.ArrayList;


public class Square {
	private double posX;
	private double posY;
	
	private double largeur;
	private double longueur;
	
	private double vX = 0;
	private double vY = 0;
	
	private boolean isMain;
	
	public Square(double posX, double posY, double largeur, double longueur) {
		this.posX = posX;
		this.posY = posY;
		this.largeur = largeur;
		this.longueur = longueur;
	}
	
	public double getPosX() {
		return posX;
	}
	
	public double getCenterX() {
		return posX + largeur/2;
	}
	
	public double getPosY() {
		return posY;
	}
	
	public double getCenterY() {
		return posY + longueur/2;
	}
	
	public double getLargeur() {
		return largeur;
	}
	
	public double getLongueur() {
		return longueur;
	}
	
	public boolean getIsMain() {
		return isMain;
	}
	
	public void setIsMain(boolean isMain) {
		this.isMain = isMain;
	}
	
	public boolean computeSeparation(ArrayList<Square> liste) {
		double neighborCount = 0;
		for (Square square : liste) {
			if(square != this){
				if( computeCollision(square, this)){
					double[] vector2D = computeVelocityToSeparate(this, square);
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
			double norme = Math.abs(Math.sqrt(this.vX*this.vX + this.vY*this.vY ));
			this.vX = (this.vX/(neighborCount*norme));
			this.vY = (this.vY/(neighborCount*norme));
		}
		return neighborCount > 0;
	}
	
	//return collision for square1
	public boolean computeCollision(Square square1, Square square2) {
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
	public double[] computeVelocityToSeparate(Square square1, Square square2){
		double[] vector2D = new double[2];
		
			
		vector2D[0] = square1.getCenterX() - square2.getCenterX();
		vector2D[1] = square1.getCenterY() - square2.getCenterY();
		
		return vector2D;
	}
	
	public void move(){
		this.posX += vX;
		this.posY += vY;
	}
}
