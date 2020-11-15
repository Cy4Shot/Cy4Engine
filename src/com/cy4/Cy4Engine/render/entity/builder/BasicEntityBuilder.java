package com.cy4.Cy4Engine.render.entity.builder;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cy4.Cy4Engine.math.IcosphereData;
import com.cy4.Cy4Engine.math.Vector3;
import com.cy4.Cy4Engine.render.entity.Entity;
import com.cy4.Cy4Engine.render.entity.IEntity;
import com.cy4.Cy4Engine.render.poly.Polygon3D;
import com.cy4.Cy4Engine.render.poly.Polyhedron;

public class BasicEntityBuilder {

	public static IEntity createCube(double size, Vector3 centre, Color c) {
		Vector3 p1 = new Vector3(+size / 2, -size / 2, -size / 2);
		Vector3 p2 = new Vector3(+size / 2, +size / 2, -size / 2);
		Vector3 p3 = new Vector3(+size / 2, +size / 2, +size / 2);
		Vector3 p4 = new Vector3(+size / 2, -size / 2, +size / 2);
		Vector3 p5 = new Vector3(-size / 2, -size / 2, -size / 2);
		Vector3 p6 = new Vector3(-size / 2, +size / 2, -size / 2);
		Vector3 p7 = new Vector3(-size / 2, +size / 2, +size / 2);
		Vector3 p8 = new Vector3(-size / 2, -size / 2, +size / 2);
		Polyhedron polyh = new Polyhedron(c, new Polygon3D(p1, p2, p3, p4), new Polygon3D(p5, p6, p7, p8),
				new Polygon3D(p1, p2, p6, p5), new Polygon3D(p1, p5, p8, p4), new Polygon3D(p2, p6, p7, p3),
				new Polygon3D(p4, p3, p7, p8));

		Entity e = new Entity(Arrays.asList(polyh));
		e.translate(centre);
		return e;
	}

	public static IEntity createPlane(double size, Vector3 centre, Color c) {
		Vector3 p1 = new Vector3(-size / 2, -size / 2, 0);
		Vector3 p2 = new Vector3(+size / 2, -size / 2, 0);
		Vector3 p3 = new Vector3(-size / 2, +size / 2, 0);
		Vector3 p4 = new Vector3(+size / 2, +size / 2, 0);
		Entity e = new Entity(Arrays.asList(new Polyhedron(new Polygon3D(c, p4, p3, p1, p2))));
		e.translate(centre);
		return e;
	}

	public static IEntity createIcosphere(double size, Vector3 centre, int recurstionDepth, Color color) {
		List<Polygon3D> faces = Arrays.asList(IcosphereData.faces);
		size *= IcosphereData.t;

		for (Polygon3D poly : faces) {
			Vector3[] points = poly.getPoints();

			points[0] = Vector3.multiply(points[0], size / Vector3.magnitude(points[0]));
			points[1] = Vector3.multiply(points[1], size / Vector3.magnitude(points[1]));
			points[2] = Vector3.multiply(points[2], size / Vector3.magnitude(points[2]));
		}

		for (int i = 0; i < recurstionDepth; i++) {
			List<Polygon3D> newFaces = new ArrayList<Polygon3D>();
			for (Polygon3D poly : faces) {
				Vector3[] points = poly.getPoints();

				Vector3 a = IcosphereData.getMiddlePoint(points[0], points[1], size);
				Vector3 b = IcosphereData.getMiddlePoint(points[1], points[2], size);
				Vector3 c = IcosphereData.getMiddlePoint(points[2], points[0], size);

				newFaces.add(new Polygon3D(points[0], a, c));
				newFaces.add(new Polygon3D(points[1], b, a));
				newFaces.add(new Polygon3D(points[2], c, b));
				newFaces.add(new Polygon3D(a, b, c));
			}
			faces = newFaces;
		}

		Entity e = new Entity(Arrays.asList(new Polyhedron(color, faces.toArray(new Polygon3D[0]))));
		e.translate(centre);
		return e;
	}

}