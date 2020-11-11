package com.cy4.Cy4Engine.render.entity.builder;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.cy4.Cy4Engine.render.entity.Entity;
import com.cy4.Cy4Engine.render.entity.IEntity;
import com.cy4.Cy4Engine.render.point.Vector3;
import com.cy4.Cy4Engine.render.shapes.Polygon3D;
import com.cy4.Cy4Engine.render.shapes.Tetrahedron;

public class ComplexEntityBuilder {

	public static IEntity createRubiksCube(double size, Vector3 centre, double cubeSpacing) {
		List<Tetrahedron> tetrahedrons = new ArrayList<Tetrahedron>();

		for (int i = -1; i < 2; i++) {
			double cubeCentreX = i * (size + cubeSpacing) + centre.x;
			for (int j = -1; j < 2; j++) {
				double cubeCentreY = j * (size + cubeSpacing) + centre.y;
				for (int k = -1; k < 2; k++) {
					if (i == 0 && j == 0 && k == 0)
						continue;
					double cubeCentreZ = k * (size + cubeSpacing) + centre.z;

					Vector3 p1 = new Vector3(cubeCentreX - size / 2, cubeCentreY - size / 2, cubeCentreZ - size / 2);
					Vector3 p2 = new Vector3(cubeCentreX - size / 2, cubeCentreY - size / 2, cubeCentreZ + size / 2);
					Vector3 p3 = new Vector3(cubeCentreX - size / 2, cubeCentreY + size / 2, cubeCentreZ - size / 2);
					Vector3 p4 = new Vector3(cubeCentreX - size / 2, cubeCentreY + size / 2, cubeCentreZ + size / 2);
					Vector3 p5 = new Vector3(cubeCentreX + size / 2, cubeCentreY - size / 2, cubeCentreZ - size / 2);
					Vector3 p6 = new Vector3(cubeCentreX + size / 2, cubeCentreY - size / 2, cubeCentreZ + size / 2);
					Vector3 p7 = new Vector3(cubeCentreX + size / 2, cubeCentreY + size / 2, cubeCentreZ - size / 2);
					Vector3 p8 = new Vector3(cubeCentreX + size / 2, cubeCentreY + size / 2, cubeCentreZ + size / 2);
					
					Polygon3D poly1 = new Polygon3D(Color.RED, p5, p6, p8, p7);
					Polygon3D poly2 = new Polygon3D(Color.WHITE, p2, p4, p8, p6);
					Polygon3D poly3 = new Polygon3D(Color.BLUE, p3, p7, p8, p4);
					Polygon3D poly4 = new Polygon3D(Color.GREEN, p1, p2, p6, p5);
					Polygon3D poly5 = new Polygon3D(Color.ORANGE, p1, p3, p4, p2);
					Polygon3D poly6 = new Polygon3D(Color.YELLOW, p1, p5, p7, p3);
					
					tetrahedrons.add(new Tetrahedron(poly1, poly2, poly3, poly4, poly5, poly6));
				}
			}
		}

		return new Entity(tetrahedrons);
	}
}
