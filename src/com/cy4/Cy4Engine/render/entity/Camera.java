package com.cy4.Cy4Engine.render.entity;

import com.cy4.Cy4Engine.math.Vector3;

public class Camera {
	private Vector3 pos;
	private Vector3 rot;
	
	public double[][] projection;

	public Camera(Vector3 pos) {
		this.pos = pos;
		this.projection = new double[4][4];
		this.projection[3][1] = -10;
		this.projection[3][2] = -20;
	}

	public void translate(Vector3 v) {
		this.pos.x += v.x;
		this.pos.y += v.y;
		this.pos.z += v.z;
	}

	public Vector3 getPos() {
		return this.pos;
	}
}
