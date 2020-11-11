package com.cy4.Cy4Engine.render.shapes;

import java.awt.Color;
import java.awt.Graphics;

import com.cy4.Cy4Engine.render.point.Vector3;

public class Tetrahedron {

	private Polygon3D[] polygons;
	private Color color;
	
	public Tetrahedron(Color color, Polygon3D... polygons) {
		this.color = color;
		this.polygons = polygons;
		this.setPolygonColor();
		this.sortPolygons();
	}
	
	public Tetrahedron(Polygon3D... polygons) {
		this.color = Color.WHITE;
		this.polygons = polygons;
		this.sortPolygons();
	}
	
	
	public void render(Graphics g) {
		for (Polygon3D poly : this.polygons) {
			poly.render(g);
		}
	}
	
	public void rotate(Vector3 rot) {
		for (Polygon3D p : polygons) {
			p.rotate(rot);
		}
		this.sortPolygons();
	}
	
	private void sortPolygons() {
		//Polygon3D.sortPolygons(this.polygons);
	}
	
	private void setPolygonColor() {
		for (Polygon3D poly : this.polygons) {
			poly.setColor(this.color);
		}
	}
}
