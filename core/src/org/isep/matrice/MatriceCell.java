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
	
	public void setStatus(int status){
		if((int) this.status >= 2 && (int) status >= 2) {
			this.status = 1;
		}
		if(this.status != 1) {
			this.status = status;
		}
	}
	
	public int getStatus(){
		return this.status;
	}
}
