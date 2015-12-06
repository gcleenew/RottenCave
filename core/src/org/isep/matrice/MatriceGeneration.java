package org.isep.matrice;

import java.util.ArrayList;

import org.isep.rottencave.generation.Hall;

public class MatriceGeneration {
	public ArrayList<Hall> hallList;
	
	public MatriceGeneration(ArrayList<Hall> hallList) {
		this.hallList = hallList;
	}
	
	public Matrice generate(){
		Matrice matrice = create();
		for (Hall hall : hallList) {
			if (hall.getIsMain()) {
				matrice.fillHall(hall);
				hall.isActive = true;
			}
		}
		
		for (Hall hall : hallList) {
			if(hall.isCorridor()) {
				matrice.fillHall(hall);
				hall.isActive = true;
			}
		}
		
		for (Hall hall : hallList) {
			if(!hall.isCorridor() && !hall.getIsMain()) {
				if(matrice.isHall(hall)){
					matrice.fillHall(hall);
					hall.isActive = true;
				}
			}
		}
		return matrice;
	}
	
	public Matrice create() {
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE; 
		int maxY = Integer.MIN_VALUE;
		
		for (Hall hall : hallList) {
			if (hall.getPosX() <= minX) {
				minX = (int) hall.getPosX();
			};
			if (hall.getPosY() <= minY) {
				minY = (int) hall.getPosY();
			};
			if (hall.getPosX() + hall.getLargeur() >= maxX) {
				maxX = (int) (hall.getPosX() + hall.getLargeur());
			};
			if (hall.getPosY() + hall.getLongueur() >= maxY) {
				maxY = (int) (hall.getPosY() + hall.getLongueur());
			};
		}
		
		Matrice matrice = new Matrice(minX, minY, maxX, maxY);
	
		return matrice;
	}
	
}
