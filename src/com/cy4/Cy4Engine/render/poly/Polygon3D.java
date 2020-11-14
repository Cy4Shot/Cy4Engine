package com.cy4.Cy4Engine.render.poly;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.cy4.Cy4Engine.math.WorldToScreenSpace;
import com.cy4.Cy4Engine.math.Vector3;
import com.cy4.Cy4Engine.render.Display;

public class Polygon3D {
	private Vector3[] points;
	private Color baseColor, lightingColor;

	public Polygon3D(Color color, Vector3... points) {
		this.baseColor = this.lightingColor = color;
		this.points = new Vector3[points.length];
		for (int i = 0; i < points.length; i++) {
			Vector3 copy = points[i];
			this.points[i] = new Vector3(copy.x, copy.y, copy.z);
		}
	}

	public Polygon3D(Vector3... points) {
		this.baseColor = this.lightingColor = Color.WHITE;
		this.points = new Vector3[points.length];
		for (int i = 0; i < points.length; i++) {
			Vector3 copy = points[i];
			this.points[i] = new Vector3(copy.x, copy.y, copy.z);
		}
	}

	public void render(Graphics g) {
		Polygon poly = new Polygon();
		for (int i = 0; i < this.points.length; i++) {
			Point p = WorldToScreenSpace.convertPoint(this.points[i]);
			poly.addPoint(p.x, p.y);
		}

		g.setColor(this.lightingColor);
		g.fillPolygon(poly);
	}

	public void rotate(Vector3 rot, Vector3 lightV) {
		for (Vector3 p : points) {
			WorldToScreenSpace.rotateAxisX(p, rot.x);
			WorldToScreenSpace.rotateAxisY(p, rot.y);
			WorldToScreenSpace.rotateAxisZ(p, rot.z);
		}

		this.updateLightingRatio(lightV);
	}
	
	public void translate(Vector3 amount) {
		for (Vector3 p : points) {
			p.x += amount.x;
			p.y += amount.y;
			p.z += amount.z;
		}
	}

	public void setColor(Color color) {
		this.baseColor = color;
	}
	
	public Vector3[] getPoints() {
		return this.points;
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
				double diff = o2.getAverageX() - o1.getAverageX();
				if (diff == 0)
					return 0;
				return diff < 0 ? 1 : -1;
			}
		});

		for (int i = 0; i < polygons.length; i++)
			polygons[i] = polyList.get(i);

		return polygons;
	}

	public void updateLightingRatio(Vector3 lightVector) {
		if (this.points.length < 3)
			return;

		Vector3 v1 = new Vector3(this.points[0], this.points[1]);
		Vector3 v2 = new Vector3(this.points[1], this.points[2]);
		Vector3 normal = Vector3.normalize(Vector3.cross(v2, v1));
		double dot = Vector3.dot(normal, lightVector);
		double sign = dot < 0 ? -1 : 1;
		dot = sign * dot * dot;
		dot = (dot + 1) / 2 * 0.8;

		double lightRatio = Math.min(1, Math.max(0, Display.AMBIENT_LIGHT + dot));
		this.updateLightingColor(lightRatio);
	}

	private void updateLightingColor(double lightRatio) {
		int red = (int) (this.baseColor.getRed() * lightRatio);
		int green = (int) (this.baseColor.getGreen() * lightRatio);
		int blue = (int) (this.baseColor.getBlue() * lightRatio);
		
		this.lightingColor = new Color(red, green, blue);
	}
}
