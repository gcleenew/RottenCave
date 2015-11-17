package org.isep.rottencave.generation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;

public class Triangulate {

	private static final float EPSILON = (float) Math.ulp(1.0);

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

	private static boolean circumCircle(Point p, Triangle t, Point circle) {

		float m1, m2, mx1, mx2, my1, my2;
		float dx, dy, rsqr, drsqr;

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

	public static ArrayList<Triangle> triangulate(ArrayList<Point> pointList) {

		Collections.sort(pointList, new XComparator());

		Iterator<Point> pIter = pointList.iterator();

		ArrayList<Triangle> triangles = new ArrayList<Triangle>();

		HashSet<Triangle> complete = new HashSet<Triangle>();

		Point p1 = new Point(-3700, 0);
		Point p2 = new Point(300, 5000);
		Point p3 = new Point(4300, 0);
		Triangle superTriangle = new Triangle(p1, p2, p3);
		triangles.add(superTriangle);


		ArrayList<Edge> edges = new ArrayList<Edge>();
		pIter = pointList.iterator();
		while (pIter.hasNext()) {

			Point p = (Point) pIter.next();

			edges.clear();

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

					if (e1.p1 == e2.p1 && e1.p2 == e2.p2) {
						e1.p1 = null;
						e1.p2 = null;
						e2.p1 = null;
						e2.p2 = null;
					}
				}
			}

			for (int j = 0; j < edges.size(); j++) {
				Edge e = (Edge) edges.get(j);
				if (e.p1 == null || e.p2 == null) {
					continue;
				}
				triangles.add(new Triangle(e.p1, e.p2, p));
			}

		}

		for (int i = triangles.size() - 1; i >= 0; i--) {
			Triangle t = (Triangle) triangles.get(i);
			if (t.sharesVertex(superTriangle)) {
				triangles.remove(i);
			}
		}

		return triangles;
	}

}
