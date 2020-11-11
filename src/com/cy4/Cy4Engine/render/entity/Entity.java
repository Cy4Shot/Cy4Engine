package com.cy4.Cy4Engine.render.entity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cy4.Cy4Engine.render.point.Vector3;
import com.cy4.Cy4Engine.render.shapes.Polygon3D;
import com.cy4.Cy4Engine.render.shapes.Tetrahedron;

public class Entity implements IEntity {
	
	private List<Tetrahedron> tetrahedrons;
	private Polygon3D[] polygons;
	
	public Entity(List<Tetrahedron> tetrahedrons) {
		List<Polygon3D> temp = new ArrayList<Polygon3D>();
		this.tetrahedrons = tetrahedrons;
		for (Tetrahedron tetra : this.tetrahedrons) {
			temp.addAll(Arrays.asList(tetra.getPolygons()));
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
		for (Tetrahedron tetra : tetrahedrons) {
			tetra.rotate(rot, lightVector);
		}
		this.sortPolygons();
	}
	
	private void sortPolygons() {
		Polygon3D.sortPolygons(this.polygons);
	}

	@Override
	public void setLighting(Vector3 lightVector) {
		for (Tetrahedron tetra : tetrahedrons) {
			tetra.setLighting(lightVector);
		}
	}

}
