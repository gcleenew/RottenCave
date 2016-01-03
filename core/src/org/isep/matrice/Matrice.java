package org.isep.matrice;

import org.isep.rottencave.generation.Hall;

import com.badlogic.gdx.Gdx;

public class Matrice {
	public MatriceCell[][] matrice;
	public int minX, minY, maxX, maxY;
	public int rangeX, rangeY;
	private static int TILE_SIZE = 5;
	
	public Matrice(int minX, int minY, int maxX, int maxY) {
		this.minX = minX; this.minY = minY; this.maxX = maxX; this.maxY = maxY;
		this.rangeX = (maxX - minX)/TILE_SIZE;
		this.rangeY = (maxY - minY)/TILE_SIZE;
		this.matrice = new MatriceCell[rangeX][rangeY];
		for (int i = 0; i < rangeX; i++) {
			for (int j = 0; j < rangeY; j++) {
				this.matrice[i][j] = new MatriceCell(i, j);
			}
		}
		
	}
	
	public void fillHall(Hall hall) {
		int x = (int) (hall.getPosX() - this.minX) / TILE_SIZE;
		int y = (int) (hall.getPosY() - this.minY) / TILE_SIZE;
		int width = (int) hall.getLargeur()  / TILE_SIZE;
		int length = (int) hall.getLongueur()  / TILE_SIZE;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < length; j++) {
				if(j == 0) {
					setStatus(2, this.matrice[x+i][y+j], 1, i, j, width, length) ;
				}
				else if(i == width -1) {
					setStatus(3, this.matrice[x+i][y+j], 2, i, j, width, length);
				}
				else if(j == length -1) {
					setStatus(4, this.matrice[x+i][y+j], 1, i, j, width, length);
				}
				else if(i == 0) {
					setStatus(5, this.matrice[x+i][y+j], 2, i, j, width, length);
				}
				else {
					setStatus(1, this.matrice[x+i][y+j], 0, i, j, width, length);
				};			
				
				if (this.matrice[x+i][y+j].belonging != null){
					if(!this.matrice[x+i][y+j].belonging.getIsMain()) {
						this.matrice[x+i][y+j].belonging = hall;
					}
				}
				else {
					this.matrice[x+i][y+j].belonging = hall;
				}
			}
		}
	}
	
	public void setStatus(int status, MatriceCell cell, int position, int x, int y, int width, int length ){
		if((int) cell.status >= 2 && (int) status >= 2 && cell.belonging.getIsMain()) {
			if(width <= 4 && position == 1 && x != 0 && x != width-1) {
				cell.status = 1;
			}
			if(length <= 4 && position == 2 && y != 0 && y != length-1) {
				cell.status = 1;
			}
		}
		if(cell.status != 1) {
			cell.status = status;
		}
	}
	
	public boolean isHall(Hall hall) {
		int x = (int) (hall.getPosX() - this.minX) / TILE_SIZE;
		int y = (int) (hall.getPosY() - this.minY) / TILE_SIZE;
		int width = (int) hall.getLargeur()  / TILE_SIZE;
		int length = (int) hall.getLongueur()  / TILE_SIZE;
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < length; j++) {
				if(this.matrice[x+i][y+j].getStatus() == 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void print() {
		for (int j = 0; j < rangeY; j++) {
			System.out.println();
			for (int i = 0; i < rangeX; i++) {
				System.out.print(this.matrice[i][rangeY-j-1].getStatus());
			}
		}
	}
}
