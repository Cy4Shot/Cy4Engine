package com.cy4.Cy4Engine.render.entity;

import java.awt.Graphics;
import java.util.List;

import com.cy4.Cy4Engine.render.point.Vector3;
import com.cy4.Cy4Engine.render.shapes.Tetrahedron;

public class Entity implements IEntity {
	
	private List<Tetrahedron> tetrahedrons;
	
	public Entity(List<Tetrahedron> tetrahedrons) {
		this.tetrahedrons = tetrahedrons;
	}

	@Override
	public void render(Graphics g) {
		for (Tetrahedron tetra : tetrahedrons) {
			tetra.render(g);
		}
	}

	@Override
	public void rotate(Vector3 rot) {
		for (Tetrahedron tetra : tetrahedrons) {
			tetra.rotate(rot);
		}
	}

}
