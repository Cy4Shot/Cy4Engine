package com.cy4.Cy4Engine.math;

import com.cy4.Cy4Engine.render.poly.Polygon3D;

public class SphereMath {
	public static final double t = (1.0 + Math.sqrt(5.0)) / 2.0;
	
	private static final Vector3[] verticies = new Vector3[] {
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
			new Polygon3D(verticies[0], verticies[11], verticies[5]),
			new Polygon3D(verticies[0], verticies[5], verticies[1]),
			new Polygon3D(verticies[0], verticies[1], verticies[7]),
			new Polygon3D(verticies[0], verticies[7], verticies[10]),
			new Polygon3D(verticies[0], verticies[10], verticies[11]),
			new Polygon3D(verticies[1], verticies[5], verticies[9]),
			new Polygon3D(verticies[5], verticies[11], verticies[4]),
			new Polygon3D(verticies[11], verticies[10], verticies[2]),
			new Polygon3D(verticies[10], verticies[7], verticies[6]),
			new Polygon3D(verticies[7], verticies[1], verticies[8]),
			new Polygon3D(verticies[3], verticies[9], verticies[4]),
			new Polygon3D(verticies[3], verticies[4], verticies[2]),
			new Polygon3D(verticies[3], verticies[2], verticies[6]),
			new Polygon3D(verticies[3], verticies[6], verticies[8]),
			new Polygon3D(verticies[3], verticies[8], verticies[9]),
			new Polygon3D(verticies[4], verticies[9], verticies[5]),
			new Polygon3D(verticies[2], verticies[4], verticies[11]),
			new Polygon3D(verticies[6], verticies[2], verticies[10]),
			new Polygon3D(verticies[8], verticies[6], verticies[7]),
			new Polygon3D(verticies[9], verticies[8], verticies[1])
	};
}
