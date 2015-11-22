package org.isep.rottencave.generation;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class ProceduralGeneration extends Thread{
	public  final int NUM_ELEMENT = 100;
	public  ArrayList<Hall> hallList;
	public  ArrayList<Hall> mainHallList;
	public 	ArrayList<Corridor> corridorList;
	
	public  double width_mean;
	public  double height_mean;
	public  ArrayList<Point> points;
	public  boolean isTriangulated = false;
	public  boolean isST = false;
	public  boolean isCorridored = false;
	public final Object hallListLock = new Object();
	public ArrayList<Triangle> triangles;
	
	
	
	@Override
	public void run() {
		createGeneration();
	}
	
	public void createGeneration() {
		hallList = new ArrayList<Hall>();
		mainHallList = new ArrayList<Hall>();
		points = new ArrayList<Point>();
		triangles = new ArrayList<Triangle>();
		corridorList = new ArrayList<Corridor>();
		Random randomGenerator = new Random();
		double height_sum = 0;
		double width_sum = 0;
		for(int i = 0; i < NUM_ELEMENT; i++){
			int randomX = randomGenerator.nextInt(60);
			int randomY = randomGenerator.nextInt(60);
			Point point = new Point(0, 0);
			point.setRandomPositionInCircle(75);
			point.x += 350;
			point.y += 250;
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
		
		for (Hall square: hallList){
			if(square.getLargeur() > width_mean*1.15 && square.getLongueur() > height_mean*1.15){
				square.setIsMain(true);
			}
		}
		
		//delaunay 
		for (Hall hall: hallList) {
			if(hall.getIsMain()){
				Point p = hall.getPoint();
				points.add(p);
				mainHallList.add(hall);
			}	
		}
		
		
		triangles = Triangulate.triangulate(points);
		isTriangulated = true;
		for (Hall hall: mainHallList) {
			for (Triangle triangle : triangles) {
				if(triangle.p1.equals(hall.getPoint())) {
					hall.neighbors.add(triangle.p2.getHall(mainHallList));
					hall.neighbors.add(triangle.p3.getHall(mainHallList));
				}
				else if (triangle.p2.equals(hall.getPoint())) {
					hall.neighbors.add(triangle.p1.getHall(mainHallList));
					hall.neighbors.add(triangle.p3.getHall(mainHallList));
				}
				else if (triangle.p3.equals(hall.getPoint())) {
					hall.neighbors.add(triangle.p1.getHall(mainHallList));
					hall.neighbors.add(triangle.p2.getHall(mainHallList));
				}
			}
		}
		LinkedList<Hall> queue = new LinkedList<Hall>();
		
		Hall s = mainHallList.get(0);
		float center =  Float.POSITIVE_INFINITY;
		for (Hall hall : mainHallList) {
			float norme = hall.getPoint().getNorme();
			Gdx.app.debug("norme :", ""+norme);
			if(norme <= center) {
				center = norme;
				s = hall;
			}
		}
		
		int edgeNumber = 0;
		
		queue.add(s);
		s.isMarked = true;
		while(queue.size() != 0) {
			Hall hall = queue.pop();
			for (Hall neighbor : hall.neighbors) {
				if(!neighbor.isMarked){
					neighbor.isMarked = true;
					hall.sucessors.add(neighbor);
					queue.add(neighbor);
					edgeNumber += 1;
				}
				
			}
		}
		int addedEdges = (int) (edgeNumber * 40 / 100);
		Gdx.app.debug("added", ""+addedEdges);
		while (addedEdges > 0) {
			for (Hall hall : mainHallList) {
				if(hall.getNotLinkedNeighbor() != null && addedEdges > 0){
					Hall neighbor =  hall.getNotLinkedNeighbor();
					neighbor.isMarked = true;
					hall.sucessors.add(neighbor);
					addedEdges = addedEdges -1;
					Gdx.app.debug("added", ""+addedEdges);
				}
			}
		}
		isTriangulated = false;
		isST = true;
		
		
		for (Hall hall : mainHallList) {
			for (Hall sucessor : hall.sucessors) {
				if(!Corridor.getExist(corridorList, hall, sucessor)) {
					Corridor corridor = new Corridor(hall, sucessor);
					corridorList.add(corridor);
				}
			}
		}
		isST = false;
		isCorridored = true;
		
		
		
		Gdx.app.debug("Triangulation", "Triangulation faite");
	}
}
