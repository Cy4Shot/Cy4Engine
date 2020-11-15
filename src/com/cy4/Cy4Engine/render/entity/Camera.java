package com.cy4.Cy4Engine.render.entity;

import com.cy4.Cy4Engine.math.Vector3;

public class Camera {
	private Vector3 pos;
	private Vector3 rot;
	
	public Camera(Vector3 pos) {
		this.pos = pos; 
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
