package org.isep.matrice;

import org.isep.rottencave.generation.Hall;

import com.badlogic.gdx.Gdx;

public class Matrice {
	public MatriceCell[][] matrice;
	public int minX, minY, maxX, maxY;
	public int rangeX, rangeY;
	private static int TILE_SIZE = 16;
	
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
		Gdx.app.debug("matrice", "Generation de la matrice, hall: posX :"+hall.getPosX()+", posY :"+hall.getPosY());
		Gdx.app.debug("matrice", "Generation de la matrice, hall: posX :"+x+", posY :"+y);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < length; j++) {
				if(j == 0) {
					this.matrice[x+i][y+j].setStatus(2);
				}
				else if(i == width -1) {
					this.matrice[x+i][y+j].setStatus(3);
				}
				else if(j == length -1) {
					this.matrice[x+i][y+j].setStatus(4);
				}
				else if(i == 0) {
					this.matrice[x+i][y+j].setStatus(5);
				}
				else {
					this.matrice[x+i][y+j].setStatus(1);
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
	
	public boolean isHall(Hall hall) {
		int x = (int) (hall.getPosX() - this.minX) / TILE_SIZE;
		int y = (int) (hall.getPosY() - this.minY) / TILE_SIZE;
		int width = (int) hall.getLargeur()  / TILE_SIZE;
		int length = (int) hall.getLongueur()  / TILE_SIZE;
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < length; j++) {
				if(this.matrice[x+i][y+j].getStatus() != 0) {
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
				System.out.print(this.matrice[i][j].getStatus());
			}
		}
	}
}
