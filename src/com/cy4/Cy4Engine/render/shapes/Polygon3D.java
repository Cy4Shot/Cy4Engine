package com.cy4.Cy4Engine.render.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.cy4.Cy4Engine.render.point.SpaceTranslator;
import com.cy4.Cy4Engine.render.point.Vector3;

public class Polygon3D {
	private Vector3[] points;
	private Color color;

	public Polygon3D(Color color, Vector3... points) {
		this.color = color;
		this.points = new Vector3[points.length];
		for (int i = 0; i < points.length; i++) {
			Vector3 copy = points[i];
			this.points[i] = new Vector3(copy.x, copy.y, copy.z);
		}
	}

	public Polygon3D(Vector3... points) {
		this.color = Color.WHITE;
		this.points = new Vector3[points.length];
		for (int i = 0; i < points.length; i++) {
			Vector3 copy = points[i];
			this.points[i] = new Vector3(copy.x, copy.y, copy.z);
		}
	}

	public void render(Graphics g) {
		Polygon poly = new Polygon();
		for (int i = 0; i < this.points.length; i++) {
			Point p = SpaceTranslator.convertPoint(this.points[i]);
			poly.addPoint(p.x, p.y);
		}
		g.setColor(this.color);
		g.fillPolygon(poly);
	}

	public void rotate(Vector3 rot) {
		for (Vector3 p : points) {
			SpaceTranslator.rotateAxisX(p, rot.x);
			SpaceTranslator.rotateAxisY(p, rot.y);
			SpaceTranslator.rotateAxisZ(p, rot.z);
		}
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getAverageX() {
		double sum = 0;
		for (Vector3 v : this.points)
			sum += v.x;
		return sum / this.points.length;
	}

	public static Polygon3D[] sortPolygons(Polygon3D[] polygons) {
		List<Polygon3D> polyList = Arrays.asList(polygons);

		Collections.sort(polyList, new Comparator<Polygon3D>() {
			@Override
			public int compare(Polygon3D o1, Polygon3D o2) {
				return o2.getAverageX() - o1.getAverageX() < 0 ? 1 : -1;
			}
		});

		for (int i = 0; i < polygons.length; i++)
			polygons[i] = polyList.get(i);

		return polygons;
	}
}
