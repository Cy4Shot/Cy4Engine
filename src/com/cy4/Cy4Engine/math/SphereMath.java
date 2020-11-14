package com.cy4.Cy4Engine.math;

import com.cy4.Cy4Engine.render.poly.Polygon3D;

public class SphereMath {
	public static final double t = (1.0 + Math.sqrt(5.0)) / 2.0;
	
	private static final Vector3[] vertices = new Vector3[] {
			new Vector3(-1, t, 0),
			new Vector3(1, t, 0),
			new Vector3(-1, -t, 0),
			new Vector3(1, -t, 0),
			new Vector3(0, -1, t),
			new Vector3(0, 1, t),
			new Vector3(0, -1, -t),
			new Vector3(0, 1, -t),
			new Vector3(t, 0, -1),
			new Vector3(t, 0, 1),
			new Vector3(-t, 0, -1),
			new Vector3(-t, 0, 1)
	};
	
	public static final Polygon3D[] faces = new Polygon3D[] {
			new Polygon3D(vertices[0], vertices[11], vertices[5]),
			new Polygon3D(vertices[0], vertices[5], vertices[1]),
			new Polygon3D(vertices[0], vertices[1], vertices[7]),
			new Polygon3D(vertices[0], vertices[7], vertices[10]),
			new Polygon3D(vertices[0], vertices[10], vertices[11]),
			new Polygon3D(vertices[1], vertices[5], vertices[9]),
			new Polygon3D(vertices[5], vertices[11], vertices[4]),
			new Polygon3D(vertices[11], vertices[10], vertices[2]),
			new Polygon3D(vertices[10], vertices[7], vertices[6]),
			new Polygon3D(vertices[7], vertices[1], vertices[8]),
			new Polygon3D(vertices[3], vertices[9], vertices[4]),
			new Polygon3D(vertices[3], vertices[4], vertices[2]),
			new Polygon3D(vertices[3], vertices[2], vertices[6]),
			new Polygon3D(vertices[3], vertices[6], vertices[8]),
			new Polygon3D(vertices[3], vertices[8], vertices[9]),
			new Polygon3D(vertices[4], vertices[9], vertices[5]),
			new Polygon3D(vertices[2], vertices[4], vertices[11]),
			new Polygon3D(vertices[6], vertices[2], vertices[10]),
			new Polygon3D(vertices[8], vertices[6], vertices[7]),
			new Polygon3D(vertices[9], vertices[8], vertices[1])
	};
	
	public static Vector3 getMiddlePoint(Vector3 p1, Vector3 p2, double t) {
		Vector3 middle = new Vector3((p1.x + p2.x) / 2.0, (p1.y + p2.y) / 2.0, (p1.z + p2.z) / 2.0);
		return Vector3.multiply(middle, t / Vector3.magnitude(middle));
	}
}
