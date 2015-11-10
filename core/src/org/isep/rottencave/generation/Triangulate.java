package org.isep.rottencave.generation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Basado en el trabajo de Paul Bourke:
 * http://paulbourke.net/papers/triangulate/ (primera conversion a Java por
 * Florian Jenett). Basado en la adaptacion de Tom Carden y las modificaciones
 * de Daniel Shiffman.
 * 
 * Clase estatica que realiza la triangulacion de Delaunay de una serie de
 * puntos P en un plano S de 2D, implementando el algoritmo incremental de
 * Bowyer–Watson.
 * 
 * @author Benjamin Diaz
 * 
 */
public class Triangulate {

	private static final float EPSILON = (float) Math.ulp(1.0);

	/* Comparador de orden ascendente basado en el eje x */
	public static class XComparator implements Comparator<Point> {

		public int compare(Point p1, Point p2) {
			if (p1.x < p2.x) {
				return -1;
			} else if (p1.x > p2.x) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	/**
	 * Revisa si un punto p se encuentra dentro de la circunferencia
	 * circunscrita de un triangulo t. Si el punto se encuentra justo en el
	 * borde de la circunferencia se considerara dentro del circulo. Asi se
	 * evitan esos casos particulares. El proceso se realiza mediante la
	 * obtencion de la determinante de una matriz compuesta por los vertices del
	 * triangulo y el punto a revisar. Mayor info en
	 * http://mathworld.wolfram.com/Circumcircle.html
	 * 
	 * @param p
	 *            El punto a revisar
	 * @param t
	 *            El triangulo a revisar
	 * @param circle
	 *            El punto que servira para establecer el centro de la
	 *            circunferencia circunscrita. Se modifica durante el proceso,
	 *            por lo que no importa lo que contenga al ser pasado a la
	 *            funcion
	 * @return Verdadero si el punto p se encuentra dentro de la circunferencia
	 *         circunscrita del triangulo t
	 */
	/* TODO Construir la circunferencia circuncrita en la clase Triangulo */
	private static boolean circumCircle(Point p, Triangle t, Point circle) {

		float m1, m2, mx1, mx2, my1, my2;
		float dx, dy, rsqr, drsqr;

		/* Revisa si los puntos son coincidentes */
		if (Math.abs(t.p1.y - t.p2.y) < EPSILON
				&& Math.abs(t.p2.y - t.p3.y) < EPSILON) {
			System.err.println("CircumCircle: Points are coincident.");
			return false;
		}

		if (Math.abs(t.p2.y - t.p1.y) < EPSILON) {
			m2 = -(t.p3.x - t.p2.x) / (t.p3.y - t.p2.y);
			mx2 = (t.p2.x + t.p3.x) / 2.0f;
			my2 = (t.p2.y + t.p3.y) / 2.0f;
			circle.x = (t.p2.x + t.p1.x) / 2.0f;
			circle.y = m2 * (circle.x - mx2) + my2;
		} else if (Math.abs(t.p3.y - t.p2.y) < EPSILON) {
			m1 = -(t.p2.x - t.p1.x) / (t.p2.y - t.p1.y);
			mx1 = (t.p1.x + t.p2.x) / 2.0f;
			my1 = (t.p1.y + t.p2.y) / 2.0f;
			circle.x = (t.p3.x + t.p2.x) / 2.0f;
			circle.y = m1 * (circle.x - mx1) + my1;
		} else {
			m1 = -(t.p2.x - t.p1.x) / (t.p2.y - t.p1.y);
			m2 = -(t.p3.x - t.p2.x) / (t.p3.y - t.p2.y);
			mx1 = (t.p1.x + t.p2.x) / 2.0f;
			mx2 = (t.p2.x + t.p3.x) / 2.0f;
			my1 = (t.p1.y + t.p2.y) / 2.0f;
			my2 = (t.p2.y + t.p3.y) / 2.0f;
			circle.x = (m1 * mx1 - m2 * mx2 + my2 - my1) / (m1 - m2);
			circle.y = m1 * (circle.x - mx1) + my1;
		}

		dx = t.p2.x - circle.x;
		dy = t.p2.y - circle.y;
		rsqr = dx * dx + dy * dy;
		circle.z = (float) Math.sqrt(rsqr);

		dx = p.x - circle.x;
		dy = p.y - circle.y;
		drsqr = dx * dx + dy * dy;

		t.circumcenter = circle;
		return drsqr <= rsqr;
	}

	/**
	 * Subrutina que realiza la triangulacion Delaunay.
	 * 
	 * @param pointList
	 *            Lista que contiene los puntos en el plano
	 * @return Lista que contiene los triangulos que conforman la triangulacion
	 *         Delaunay
	 */

	public static ArrayList<Triangle> triangulate(ArrayList<Point> pointList) {

		/*
		 * Ordena los puntos de forma ascendente, de acuerdo al valor de x de
		 * cada uno
		 */
		Collections.sort(pointList, new XComparator());

		Iterator<Point> pIter = pointList.iterator();

		/* Triangulos */
		ArrayList<Triangle> triangles = new ArrayList<Triangle>();
		/* Triangulos listos (cumplen condicion Delaunay) */
		HashSet<Triangle> complete = new HashSet<Triangle>();

		/*
		 * Construye el supertriangulo. Todos los puntos del plano se encuentran
		 * dentro de este. Es el primer triangulo de la lista. Al final del
		 * proceso se eliminara. El tamaño debe ser lo más grande posible. Este
		 * esta pensado para una ventana de 600x600. Si se utiliza una ventana
		 * más grande, aumentar el tamaño.
		 * TODO: Hacerlo dinámico.
		 */		
		Point p1 = new Point(-3700, 0);
		Point p2 = new Point(300, 5000);
		Point p3 = new Point(4300, 0);
		Triangle superTriangle = new Triangle(p1, p2, p3);
		triangles.add(superTriangle);

		/*
		 * Agrega cada punto a la triangulacion uno por uno.
		 */
		ArrayList<Edge> edges = new ArrayList<Edge>();
		pIter = pointList.iterator();
		while (pIter.hasNext()) {

			Point p = (Point) pIter.next();

			edges.clear();

			/*
			 * Prepara el buffer de aristas. Si el punto p esta dentro de la
			 * circunferencia circunscrita del triangulo, entonces las aristas
			 * de ese triangulo se agregan al buffer y el triangulo se elimina
			 * de la lista de triangulos.
			 */
			Point circle = new Point();

			for (int j = triangles.size() - 1; j >= 0; j--) {
				Triangle t = (Triangle) triangles.get(j);
				if (complete.contains(t)) {
					continue;
				}

				boolean inside = circumCircle(p, t, circle);

				if (circle.x + circle.z < p.x) {
					complete.add(t);
				}
				if (inside) {
					edges.add(new Edge(t.p1, t.p2));
					edges.add(new Edge(t.p2, t.p3));
					edges.add(new Edge(t.p3, t.p1));
					triangles.remove(j);
				}

			}

			/*
			 * Asigna null a las aristas repetidas.
			 */
			for (int j = 0; j < edges.size() - 1; j++) {
				Edge e1 = (Edge) edges.get(j);
				for (int k = j + 1; k < edges.size(); k++) {
					Edge e2 = (Edge) edges.get(k);
					if (e1.p1 == e2.p2 && e1.p2 == e2.p1) {
						e1.p1 = null;
						e1.p2 = null;
						e2.p1 = null;
						e2.p2 = null;
					}
					/* Esto no debiera necesitarse, pero lo dejo por si acaso */
					if (e1.p1 == e2.p1 && e1.p2 == e2.p2) {
						e1.p1 = null;
						e1.p2 = null;
						e2.p1 = null;
						e2.p2 = null;
					}
				}
			}

			/*
			 * Forma nuevos triangulos evitando las aristas repetidas. Todas las
			 * aristas estan ordenadas en sentido del reloj.
			 */
			for (int j = 0; j < edges.size(); j++) {
				Edge e = (Edge) edges.get(j);
				if (e.p1 == null || e.p2 == null) {
					continue;
				}
				triangles.add(new Triangle(e.p1, e.p2, p));
			}

		}

		/*
		 * Remueve los triangulos que comparten vertices con el supertriangulo
		 * del inicio.
		 */
		for (int i = triangles.size() - 1; i >= 0; i--) {
			Triangle t = (Triangle) triangles.get(i);
			if (t.sharesVertex(superTriangle)) {
				triangles.remove(i);
			}
		}

		return triangles;
	}

}
