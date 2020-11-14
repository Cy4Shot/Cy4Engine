package com.cy4.Cy4Engine.render.entity.builder;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cy4.Cy4Engine.math.Vector3;
import com.cy4.Cy4Engine.render.entity.Entity;
import com.cy4.Cy4Engine.render.entity.IEntity;
import com.cy4.Cy4Engine.render.poly.Polygon3D;
import com.cy4.Cy4Engine.render.poly.Polyhedron;

public class BasicEntityBuilder {

	public static IEntity createCube(double size, Vector3 centre, Color c) {
		Vector3 p1 = new Vector3(centre.x + size / 2, centre.y - size / 2, centre.z - size / 2);
		Vector3 p2 = new Vector3(centre.x + size / 2, centre.y + size / 2, centre.z - size / 2);
		Vector3 p3 = new Vector3(centre.x + size / 2, centre.y + size / 2, centre.z + size / 2);
		Vector3 p4 = new Vector3(centre.x + size / 2, centre.y - size / 2, centre.z + size / 2);
		Vector3 p5 = new Vector3(centre.x - size / 2, centre.y - size / 2, centre.z - size / 2);
		Vector3 p6 = new Vector3(centre.x - size / 2, centre.y + size / 2, centre.z - size / 2);
		Vector3 p7 = new Vector3(centre.x - size / 2, centre.y + size / 2, centre.z + size / 2);
		Vector3 p8 = new Vector3(centre.x - size / 2, centre.y - size / 2, centre.z + size / 2);
		Polyhedron polyh = new Polyhedron(new Polygon3D(c, p1, p2, p3, p4), new Polygon3D(c, p5, p6, p7, p8),
				new Polygon3D(c, p1, p2, p6, p5), new Polygon3D(c, p1, p5, p8, p4), new Polygon3D(c, p2, p6, p7, p3),
				new Polygon3D(c, p4, p3, p7, p8));
		return new Entity(Arrays.asList(polyh));
	}

	public static IEntity createPlane(double size, Vector3 centre, Color c) {
		Vector3 p1 = new Vector3(centre.x - size / 2, centre.y - size / 2, centre.z);
		Vector3 p2 = new Vector3(centre.x + size / 2, centre.y - size / 2, centre.z);
		Vector3 p3 = new Vector3(centre.x - size / 2, centre.y + size / 2, centre.z);
		Vector3 p4 = new Vector3(centre.x + size / 2, centre.y + size / 2, centre.z);
		return new Entity(Arrays.asList(new Polyhedron(new Polygon3D(c, p4, p3, p1, p2))));
	}

	public static IEntity createIcosphere(double size, Vector3 centre, int recurstionDepth, Color color) {
		List<Vector3> vertices = new ArrayList<Vector3>();
		List<Polygon3D> faces = new ArrayList<Polygon3D>();

		double t = (1.0 + Math.sqrt(5.0)) / 2.0;

		vertices.add(new Vector3(-1, t, 0));
		vertices.add(new Vector3(1, t, 0));
		vertices.add(new Vector3(-1, -t, 0));
		vertices.add(new Vector3(1, -t, 0));

		vertices.add(new Vector3(0, -1, t));
		vertices.add(new Vector3(0, 1, t));
		vertices.add(new Vector3(0, -1, -t));
		vertices.add(new Vector3(0, 1, -t));

		vertices.add(new Vector3(t, 0, -1));
		vertices.add(new Vector3(t, 0, 1));
		vertices.add(new Vector3(-t, 0, -1));
		vertices.add(new Vector3(-t, 0, 1));

		faces.add(new Polygon3D(vertices.get(0), vertices.get(11), vertices.get(5)));
		faces.add(new Polygon3D(vertices.get(0), vertices.get(5), vertices.get(1)));
		faces.add(new Polygon3D(vertices.get(0), vertices.get(1), vertices.get(7)));
		faces.add(new Polygon3D(vertices.get(0), vertices.get(7), vertices.get(10)));
		faces.add(new Polygon3D(vertices.get(0), vertices.get(10), vertices.get(11)));

		faces.add(new Polygon3D(vertices.get(1), vertices.get(5), vertices.get(9)));
		faces.add(new Polygon3D(vertices.get(5), vertices.get(11), vertices.get(4)));
		faces.add(new Polygon3D(vertices.get(11), vertices.get(10), vertices.get(2)));
		faces.add(new Polygon3D(vertices.get(10), vertices.get(7), vertices.get(6)));
		faces.add(new Polygon3D(vertices.get(7), vertices.get(1), vertices.get(8)));

		faces.add(new Polygon3D(vertices.get(3), vertices.get(9), vertices.get(4)));
		faces.add(new Polygon3D(vertices.get(3), vertices.get(4), vertices.get(2)));
		faces.add(new Polygon3D(vertices.get(3), vertices.get(2), vertices.get(6)));
		faces.add(new Polygon3D(vertices.get(3), vertices.get(6), vertices.get(8)));
		faces.add(new Polygon3D(vertices.get(3), vertices.get(8), vertices.get(9)));

		faces.add(new Polygon3D(vertices.get(4), vertices.get(9), vertices.get(5)));
		faces.add(new Polygon3D(vertices.get(2), vertices.get(4), vertices.get(11)));
		faces.add(new Polygon3D(vertices.get(6), vertices.get(2), vertices.get(10)));
		faces.add(new Polygon3D(vertices.get(8), vertices.get(6), vertices.get(7)));
		faces.add(new Polygon3D(vertices.get(9), vertices.get(8), vertices.get(1)));

		for (Polygon3D poly : faces) {
			Vector3[] points = poly.getPoints();

			points[0] = Vector3.multiply(points[0], size * t / Vector3.magnitude(points[0]));
			points[1] = Vector3.multiply(points[1], size * t / Vector3.magnitude(points[1]));
			points[2] = Vector3.multiply(points[2], size * t / Vector3.magnitude(points[2]));
		}

		for (int i = 0; i < recurstionDepth + 1; i++) {
			List<Polygon3D> newFaces = new ArrayList<Polygon3D>();
			for (Polygon3D poly : faces) {
				Vector3[] points = poly.getPoints();

				Vector3 a = getMiddlePoint(points[0], points[1], size * t);
				Vector3 b = getMiddlePoint(points[1], points[2], size * t);
				Vector3 c = getMiddlePoint(points[2], points[0], size * t);

				newFaces.add(new Polygon3D(points[0], a, c));
				newFaces.add(new Polygon3D(points[1], b, a));
				newFaces.add(new Polygon3D(points[2], c, b));
				newFaces.add(new Polygon3D(a, b, c));
			}
			faces = newFaces;
		}

		return new Entity(Arrays.asList(new Polyhedron(color, faces.toArray(new Polygon3D[0]))));
	}

	private static Vector3 getMiddlePoint(Vector3 p1, Vector3 p2, double t) {
		Vector3 middle = new Vector3((p1.x + p2.x) / 2.0, (p1.y + p2.y) / 2.0, (p1.z + p2.z) / 2.0);
		return Vector3.multiply(middle, t / Vector3.magnitude(middle));
	}

}