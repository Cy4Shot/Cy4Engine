package com.cy4.Cy4Engine.render.entity;

import java.awt.Graphics;

import com.cy4.Cy4Engine.render.point.Vector3;

public interface IEntity {
	
	void render(Graphics g);
	
	void rotate(Vector3 rot, Vector3 lightVector);
	
	void setLighting(Vector3 lightVector);
	
}
