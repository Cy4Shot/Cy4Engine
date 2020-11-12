package com.cy4.Cy4Engine.render.entity.builder;

import java.awt.Color;
import java.util.Arrays;

import com.cy4.Cy4Engine.render.entity.Entity;
import com.cy4.Cy4Engine.render.entity.IEntity;
import com.cy4.Cy4Engine.render.point.Vector3;
import com.cy4.Cy4Engine.render.shapes.Polygon3D;
import com.cy4.Cy4Engine.render.shapes.Polyhedron;

public class BasicEntityBuilder {

	public static IEntity createCube(double size, Vector3 centre) {
		Vector3 p1 = new Vector3(centre.x + size / 2, centre.y - size / 2, centre.z - size / 2);
		Vector3 p2 = new Vector3(centre.x + size / 2, centre.y + size / 2, centre.z - size / 2);
		Vector3 p3 = new Vector3(centre.x + size / 2, centre.y + size / 2, centre.z + size / 2);
		Vector3 p4 = new Vector3(centre.x + size / 2, centre.y - size / 2, centre.z + size / 2);
		Vector3 p5 = new Vector3(centre.x - size / 2, centre.y - size / 2, centre.z - size / 2);
		Vector3 p6 = new Vector3(centre.x - size / 2, centre.y + size / 2, centre.z - size / 2);
		Vector3 p7 = new Vector3(centre.x - size / 2, centre.y + size / 2, centre.z + size / 2);
		Vector3 p8 = new Vector3(centre.x - size / 2, centre.y - size / 2, centre.z + size / 2);
		Polyhedron polyh = new Polyhedron(new Polygon3D(Color.RED, p1, p2, p3, p4),
				new Polygon3D(Color.RED, p5, p6, p7, p8), new Polygon3D(Color.YELLOW, p1, p2, p6, p5),
				new Polygon3D(Color.BLUE, p1, p5, p8, p4), new Polygon3D(Color.BLUE, p2, p6, p7, p3),
				new Polygon3D(Color.YELLOW, p4, p3, p7, p8));
		return new Entity(Arrays.asList(polyh));
	}

	public static IEntity createPlane(double size, Vector3 centre, Color c) {
		Vector3 p1 = new Vector3(centre.x - size / 2, centre.y - size / 2, centre.z);
		Vector3 p2 = new Vector3(centre.x + size / 2, centre.y - size / 2, centre.z);
		Vector3 p3 = new Vector3(centre.x - size / 2, centre.y + size / 2, centre.z);
		Vector3 p4 = new Vector3(centre.x + size / 2, centre.y + size / 2, centre.z);
		Polyhedron polyh = new Polyhedron(new Polygon3D(c, p4, p3, p1, p2));
		return new Entity(Arrays.asList(polyh));
	}

}
