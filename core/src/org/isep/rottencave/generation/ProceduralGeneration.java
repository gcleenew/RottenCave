package org.isep.rottencave.generation;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import com.badlogic.gdx.Gdx;

public class ProceduralGeneration extends Thread{
	public  final int NUM_ELEMENT = 100;
	public  ArrayList<Hall> hallList;
	public  double width_mean;
	public  double height_mean;
	public  ArrayList<Point> points;
	public  boolean isTriangulated = false;
	public  boolean isST = false;
	public final Object hallListLock = new Object();
	public ArrayList<Triangle> triangles;
	
	
	
	@Override
	public void run() {
		createGeneration();
	}
	
	public void createGeneration() {
		hallList = new ArrayList<Hall>();
		points = new ArrayList<Point>();
		triangles = new ArrayList<Triangle>();
		Random randomGenerator = new Random();
		double height_sum = 0;
		double width_sum = 0;
		for(int i = 0; i < NUM_ELEMENT; i++){
			int randomX = randomGenerator.nextInt(60);
			int randomY = randomGenerator.nextInt(60);
			Point point = new Point(0, 0);
			point.setRandomPositionInCircle(75);
			point.x += 300;
			point.y += 300;
			int largeur = randomX + 7 ;
			int longueur = randomY + 7;
			Hall square = new Hall(point, largeur, longueur);
			point.setHall(square); 
			synchronized (hallListLock) {
				hallList.add(square);
			}
					
			
			height_sum += largeur;
			width_sum += longueur;
		}
		height_mean = height_sum/NUM_ELEMENT;
		width_mean = width_sum/NUM_ELEMENT;

		boolean collision=true;
		while(collision) {
			collision=false;
			for (Hall hall: hallList) {
				collision = hall.computeSeparation(hallList)? true: collision;
				hall.move();
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//delaunay 
		for (Hall hall: hallList) {
			if(hall.getIsMain()){
				Point p = hall.getPoint();
				points.add(p);
			}	
		}
		
		
		triangles = Triangulate.triangulate(points);
		isTriangulated = true;
		
		for (Hall hall: hallList) {
			Gdx.app.debug("Hall", ""+hall.getPoint().x+" "+hall.getPoint().y);
			for (Triangle triangle : triangles) {
				if(triangle.p1.equals(hall.getPoint())) {
					hall.neighbors.add(triangle.p2.getHall(hallList));
					hall.neighbors.add(triangle.p3.getHall(hallList));
				}
				else if (triangle.p2.equals(hall.getPoint())) {
					hall.neighbors.add(triangle.p1.getHall(hallList));
					hall.neighbors.add(triangle.p3.getHall(hallList));
				}
				else if (triangle.p3.equals(hall.getPoint())) {
					Gdx.app.debug("Triangle", ""+triangle.p1.x+" "+triangle.p1.y);
					Gdx.app.debug("Triangle", ""+triangle.p2.x+" "+triangle.p2.y);
					Gdx.app.debug("Triangle", ""+triangle.p3.x+" "+triangle.p3.y);
					for (Hall hall2 : hallList) {
						if(triangle.p1.posEquals(hall2.getPoint().x, hall2.getPoint().y)) {
							Gdx.app.debug("Triangle et hall?", ""+triangle.p1.x+" "+triangle.p1.y);
						}
					}
					Gdx.app.debug("hall final?", ""+triangle.p1.getHall(hallList).getPoint().x+ ""+triangle.p1.getHall(hallList).getPoint().y);
					hall.neighbors.add(triangle.p1.getHall(hallList));
					
					hall.neighbors.add(triangle.p2.getHall(hallList));
				}
			}
		}
		
		Stack<Hall> stack = new Stack<Hall>();
		
		Hall s = hallList.get(0);
		float center =  Float.POSITIVE_INFINITY;
		for (Hall hall : hallList) {
			float norme = hall.getPoint().getNorme();
			if(norme <= center) {
				center = norme;
				s = hall;
			}
		}
		
		stack.push(s);
		s.isMarked = true;
		while(!stack.empty()) {
			Hall hall = stack.peek();
			if(hall.markedNeighbors()) {
				stack.pop();
			}
			else {
				Hall sucessor = hall.getNotMarkedNeighbor();
				sucessor.isMarked = true;
				hall.sucessors.add(sucessor);
				stack.push(sucessor);
			}
			
		}
		isTriangulated = false;
		isST = true;
		
		Gdx.app.debug("Triangulation", "Triangulation faite");
	}
}
