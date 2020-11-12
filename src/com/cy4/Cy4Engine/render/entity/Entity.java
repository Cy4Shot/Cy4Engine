package com.cy4.Cy4Engine.render.entity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cy4.Cy4Engine.math.Vector3;
import com.cy4.Cy4Engine.render.poly.Polygon3D;
import com.cy4.Cy4Engine.render.poly.Polyhedron;

public class Entity implements IEntity {

	private List<Polyhedron> polyhedra;
	private Polygon3D[] polygons;

	public Entity(List<Polyhedron> polyhedra) {
		List<Polygon3D> temp = new ArrayList<Polygon3D>();
		this.polyhedra = polyhedra;
		for (Polyhedron polyh : this.polyhedra) {
			temp.addAll(Arrays.asList(polyh.getPolygons()));
		}
		this.polygons = new Polygon3D[temp.size()];
		this.polygons = temp.toArray(this.polygons);
		this.sortPolygons();
	}

	@Override
	public void render(Graphics g) {
		for (Polygon3D polys : polygons) {
			polys.render(g);
		}
	}

	@Override
	public void rotate(Vector3 rot, Vector3 lightVector) {
		for (Polyhedron polyh : this.polyhedra) {
			polyh.rotate(rot, lightVector);
		}
		this.sortPolygons();
	}

	private void sortPolygons() {
		Polygon3D.sortPolygons(this.polygons);
	}

	@Override
	public void setLighting(Vector3 lightVector) {
		for (Polyhedron polyh : this.polyhedra) {
			polyh.setLighting(lightVector);
		}
	}

}
