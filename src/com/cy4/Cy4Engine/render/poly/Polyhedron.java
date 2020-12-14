package com.cy4.Cy4Engine.render.poly;

import java.awt.Color;
import java.awt.Graphics;

import com.cy4.Cy4Engine.math.Vector3;

public class Polyhedron {

	private Polygon3D[] polygons;
	private Color color;

	public Polyhedron(Color color, Vector3 offset, Polygon3D... polygons) {
		this.color = color;
		this.polygons = polygons;
		this.setPolygonColor();
		this.sortPolygons(offset);
	}

	public Polyhedron(Vector3 offset, Polygon3D... polygons) {
		this(Color.WHITE, offset, polygons);
	}

	public Polygon3D[] getPolygons() {
		return this.polygons;
	}

	public void render(Graphics g, Vector3 offset) {
		for (Polygon3D poly : this.polygons) {
			poly.render(g, offset);
		}
	}

//	public void translate(Vector3 pos, Vector3 offset) {
//		for (Polygon3D poly : this.polygons) {
//			poly.translate(pos, offset);
//		}
//		this.sortPolygons(offset);
//	}

	public void rotate(Vector3 rot, Vector3 lightVector, Vector3 offset) {
		for (Polygon3D p : polygons) {
			p.rotate(rot, lightVector, offset);
		}
		this.sortPolygons(offset);
	}

	public void setLighting(Vector3 lightVector) {
		for (Polygon3D p : polygons) {
			p.updateLightingRatio(lightVector);
		}
	}

	private void sortPolygons(Vector3 offset) {
		Polygon3D.sortPolygons(this.polygons, offset);
	}
	
	public void updateVisibility(Vector3 offset) {
		for (Polygon3D poly : this.polygons) {
			poly.updateVisibility(offset);
		}
	}

	private void setPolygonColor() {
		for (Polygon3D poly : this.polygons) {
			poly.setColor(this.color);
		}
	}
}
