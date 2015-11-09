package org.isep.rottencave.generation;
import java.util.ArrayList;
import java.util.Random;

public class ProceduralGeneration extends Thread{
	public  final int NUM_ELEMENT = 100;
	public  ArrayList<Square> squareList;
	public  double width_mean;
	public  double height_mean;
	public  DelaunayTriangulator dt;
	public  ArrayList<PVector> points;
	public  boolean isTriangulated = false;
	public final Object squareListLock = new Object();
	
	public static SphericalCoordinate getRandomPointInCircle(int radius){
		double t = 2 * Math.PI * Math.random();
		double u = Math.random() + Math.random();
		double r = 0;
		if(u > 1){
			r = 2 - u;
		}
		else {
			r = u;
		}
		SphericalCoordinate point = new SphericalCoordinate(radius * r  * Math.cos(t), radius * r * Math.sin(t));
		return point;
	}
	
	@Override
	public void run() {
		createGeneration();
	}
	
	public void createGeneration() {
		squareList = new ArrayList<Square>();
		points = new ArrayList<PVector>();	
		Random randomGenerator = new Random();
		double height_sum = 0;
		double width_sum = 0;
		for(int i = 0; i < NUM_ELEMENT; i++){
			int randomX = randomGenerator.nextInt(60);
			int randomY = randomGenerator.nextInt(60);
			SphericalCoordinate point = getRandomPointInCircle(75);
			int posX = (int) point.getX() + 300;
			int posY = (int) point.getY() + 300;
			int largeur = randomX + 7 ;
			int longueur = randomY + 7;
			Square square = new Square(posX, posY, largeur, longueur);
			synchronized (squareListLock) {
				squareList.add(square);
			}
					
			
			height_sum += largeur;
			width_sum += longueur;
		}
		height_mean = height_sum/NUM_ELEMENT;
		width_mean = width_sum/NUM_ELEMENT;

		boolean collision=true;
		while(collision) {
			collision=false;
			for (Square square: squareList) {
				collision = square.computeSeparation(squareList)? true: collision;
				square.move();
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//delaunay 
		for (Square square: squareList) {
			if(square.getIsMain()){
				PVector p = new PVector((float) square.getCenterX(), (float) square.getCenterY());
				points.add(p);
			}	
		}
		
		
		dt = new DelaunayTriangulator();
		dt.points = points.toArray(new PVector[points.size()]);
		dt.triangles = dt.Calculate();
		isTriangulated = true;
		System.out.println("triangulation faite");
		
	}
}
