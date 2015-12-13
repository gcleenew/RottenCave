package org.isep.matrice;

import org.isep.rottencave.generation.Hall;

public class MatriceCell {
	public int status;
	public Hall belonging;
	public int x, y; 
	
	public MatriceCell(int x, int y) {
		this.x = x;
		this.y = y;
		this.status = 0;
	}
	
	
	public int getStatus(){
		return this.status;
	}
}
