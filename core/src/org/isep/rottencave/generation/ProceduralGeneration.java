package org.isep.rottencave.generation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import org.isep.matrice.Matrice;
import org.isep.matrice.MatriceGeneration;

import com.badlogic.gdx.Gdx;


public class ProceduralGeneration extends Thread{
	private static Long lastSeedUsed;
	public static int TILE_SIZE = 5;
	public static int CORRIDOR_SIZE = 4;
	public  final int NUM_ELEMENT = 100;
	public  ArrayList<Hall> hallList;
	public  ArrayList<Hall> mainHallList;
	public 	ArrayList<Corridor> corridorList;
	public  Long seed;
	public  double width_mean;
	public  double height_mean;
	public  ArrayList<Point> points;
	public  boolean isTriangulated = true;
	public  boolean isST = true;
	public  boolean isCorridored = false;
	public final Object hallListLock = new Object();
	public ArrayList<Triangle> triangles;
	private Matrice matrice;

	public ProceduralGeneration(Long seed){
		this.seed = seed;
	}
	@Override
	public void run() {
		createGeneration();
	}
	
	/**
	 * Main function
	 */
	public void createGeneration() {
		hallList = new ArrayList<Hall>();
		mainHallList = new ArrayList<Hall>();
		points = new ArrayList<Point>();
		triangles = new ArrayList<Triangle>();
		corridorList = new ArrayList<Corridor>();
		hallList = createHall();
		
		separateHall();
		
		setMain();
		
		launchDelaunay();
		Gdx.app.debug("Triangulation", "Triangulation faite");
		setNeighbors();
		
		Hall s = getCentered();
		isTriangulated = false;
		spanningTree(s);
		
/*		try {
			Thread.sleep(1200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		isST = false;
		createCorridor();	
		isCorridored = true;
		createHallCorridor();
				
		Gdx.app.debug("matrice", "Generation de la matrice");
		MatriceGeneration generation = new MatriceGeneration(hallList);
		matrice = generation.generate();
		Gdx.app.debug("matrice", "Print de la matrice");
		matrice.print();
		
	}
	
	/**
	 * 
	 * @return the list of random hall in a circle. 
	 * Compute the height_mean and the width_mean.
	 */
	public ArrayList<Hall> createHall() {
		double height_sum = 0;
		double width_sum = 0;
		Random randomGenerator = new Random();
		if(seed == null) {
			seed = (long) (randomGenerator.nextFloat() * 100000000);
		}

		lastSeedUsed = seed;
		randomGenerator = new Random(seed);
		
		for(int i = 0; i < NUM_ELEMENT; i++){
			int randomX = (randomGenerator.nextInt(5)+4)*TILE_SIZE;
			int randomY = (randomGenerator.nextInt(5)+4)*TILE_SIZE;
			Point point = new Point(0, 0);
			point.setRandomPositionInCircle(10*TILE_SIZE, randomGenerator);
			point.x += 75*TILE_SIZE;
			point.y += 45*TILE_SIZE;
			int largeur = (randomX);
			int longueur = (randomY);
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
		
		return hallList;
	}
	
	/**
	 *  Compute the separation of the hall. 
	 *  Stop when there is no more collision
	 */
	public void separateHall() {
		boolean collision=true;
		
		while(collision) {
			collision=false;
			for (Hall hall: hallList) {
				collision = hall.computeSeparation(hallList)? true: collision;
				hall.move();
			}
//			try {
//				Thread.sleep(1);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
	
	/**
	 * Set the main hall in the hallList using widht_mean and height_mean
	 */
	public void setMain() {
		for (Hall square: hallList){
			if(square.getLargeur() > width_mean*1.15 && square.getLongueur() > height_mean*1.15){
				square.setIsMain(true);
			}
		}
	}
	
	/**
	 * Launch the Delaunay procedure. 
	 * Triangles get the list of the triangles returned by the Delaunay
	 */
	public void launchDelaunay() {
		for (Hall hall: hallList) {
			if(hall.getIsMain()){
				Point p = hall.getPoint();
				points.add(p);
				mainHallList.add(hall);
				triangles = Triangulate.triangulate(points);
			}	
		}
	}
	
	/**
	 * Set Neighbors for each hall in the main Hall in the list if an edge of the triangle matches
	 */
	public void setNeighbors() {
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
	}
	
	/**
	 * 
	 * @return the hall the nearest of the center of the initial circle
	 */
	public Hall getCentered() {
		Hall s = mainHallList.get(0);
		float center =  Float.POSITIVE_INFINITY;
		for (Hall hall : mainHallList) {
			float norme = hall.getPoint().getNorme();
			if(norme <= center) {
				center = norme;
				s = hall;
			}
		}
		return s;
	}
	
	/**
	 * Compute the spanning tree using the central hall and the delaunay
	 * Then adding some edge to this spanning tree to add some circle
	 * @param s is the central Hall
	 */
	public void spanningTree (Hall s) {
		int edgeNumber = 0;
		LinkedList<Hall> queue = new LinkedList<Hall>();
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
		while (addedEdges > 0) {
			for (Hall hall : mainHallList) {
				if(hall.getNotLinkedNeighbor() != null && addedEdges > 0){
					Hall neighbor =  hall.getNotLinkedNeighbor();
					neighbor.isMarked = true;
					hall.sucessors.add(neighbor);
					addedEdges = addedEdges -1;
				}
			}
			
		}
		
	}
	
	/**
	 * create the list of corridor using the successors of the hall.
	 * Get exist catches the double corridor between two hall
	 */
	public void createCorridor() {
		for (Hall hall : mainHallList) {
			for (Hall sucessor : hall.sucessors) {
				if(!Corridor.getExist(corridorList, hall, sucessor)) {
					Corridor corridor = new Corridor(hall, sucessor);
					corridorList.add(corridor);
/*					try {
						Thread.sleep(150);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
				}
			}
		}
	}
	
	/**
	 * Create the real corridor wich are hall
	 */
	public void createHallCorridor() {
		for (Corridor corridor : corridorList) {
			if (corridor.getStatus() == "L") {
				//Corridor 1 
				Point point1 = new Point(0,0);
				Hall hall_corridor1 = new Hall(point1, 0, 0);
				if(corridor.entree1.x == corridor.passage.x) {
					point1.x = corridor.entree1.x;
					point1.y = Math.abs(corridor.entree1.y + corridor.passage.y)/2;
					int largeur = (CORRIDOR_SIZE)*TILE_SIZE;
					int longueur = (int) (Math.abs(corridor.entree1.y - corridor.passage.y)) + 4 * TILE_SIZE;
					hall_corridor1 = new Hall(point1, largeur, longueur);
				}
				else {
					point1.x = Math.abs(corridor.entree1.x + corridor.passage.x)/2;
					point1.y = corridor.entree1.y;
					int largeur = (int) (Math.abs(corridor.entree1.x - corridor.passage.x)) + 4 * TILE_SIZE;
					int longueur = (CORRIDOR_SIZE)*TILE_SIZE;
					hall_corridor1 = new Hall(point1, largeur, longueur);
				}	
				hall_corridor1.setCorridor(true);
				point1.setHall(hall_corridor1);
				hallList.add(hall_corridor1);
				
				
				//Corridor 2
				Point point2 = new Point(0,0);
				Hall hall_corridor2 = new Hall(point2, 0, 0);
				if(corridor.entree2.x == corridor.passage.x) {
					point2.x = corridor.entree2.x;
					point2.y = Math.abs(corridor.entree2.y + corridor.passage.y)/2;
					int largeur = (CORRIDOR_SIZE)*TILE_SIZE;
					int longueur = (int) (Math.abs(corridor.entree2.y - corridor.passage.y)) + 4 * TILE_SIZE;
					hall_corridor2 = new Hall(point2, largeur, longueur);
				}
				else {
					point2.x = Math.abs(corridor.entree2.x + corridor.passage.x)/2;
					point2.y = corridor.entree2.y;
					int largeur = (int) (Math.abs(corridor.entree2.x - corridor.passage.x)) + 4 * TILE_SIZE;
					int longueur = (CORRIDOR_SIZE)*TILE_SIZE;
					hall_corridor2 = new Hall(point2, largeur, longueur);
				}	
				hall_corridor2.setCorridor(true);
				point2.setHall(hall_corridor2);
				hallList.add(hall_corridor2);
			}
			else if (corridor.getStatus() == "ligneH") {
				Point point = new Point(0,0);
				point.x = Math.abs(corridor.entree1.x + corridor.entree2.x)/2;
				point.y = corridor.passage.y;
				int largeur = (int) (Math.abs(corridor.entree2.x - corridor.entree1.x)) + 4 * TILE_SIZE;
				int longueur = (CORRIDOR_SIZE)*TILE_SIZE;
				Hall hall_corridor = new Hall(point, largeur, longueur);
				point.setHall(hall_corridor);
				hall_corridor.setCorridor(true);
				hallList.add(hall_corridor);
			}
			else {
				Point point = new Point(0,0);
				point.x = corridor.passage.x;
				point.y = Math.abs(corridor.entree2.y + corridor.entree1.y)/2;
				int largeur = (CORRIDOR_SIZE)*TILE_SIZE;
				int longueur = (int) (Math.abs(corridor.entree2.y - corridor.entree1.y)) + 4 * TILE_SIZE ;
				Hall hall_corridor = new Hall(point, largeur, longueur);
				point.setHall(hall_corridor);
				hall_corridor.setCorridor(true);
				hallList.add(hall_corridor);
			}
		}
	}
	
	
	public Matrice getMatrice() {
		return matrice;
	}
	public static Long getLastSeedUsed() {
		return lastSeedUsed;
	}
	
}
