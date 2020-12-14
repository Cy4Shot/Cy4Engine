package com.cy4.Cy4Engine.render.poly;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.cy4.Cy4Engine.math.ProjectionMath;
import com.cy4.Cy4Engine.math.Vector3;
import com.cy4.Cy4Engine.render.Display;

public class Polygon3D {
	private Vector3[] points;
	private Color baseColor, lightingColor;
	private boolean visible;

	public Polygon3D(Color color, Vector3... points) {
		this.baseColor = this.lightingColor = color;
		this.points = new Vector3[points.length];
		this.visible = false;
		
		for (int i = 0; i < points.length; i++) {
			Vector3 copy = points[i];
			this.points[i] = new Vector3(copy.x, copy.y, copy.z);
		}
		
		this.updateVisibility(Vector3.zero);
	}

	public Polygon3D(Vector3... points) {
		this(Color.WHITE, points);
	}

	public void render(Graphics g, Vector3 offset) {
		if(!this.visible) return;
		
		Polygon poly = new Polygon();
		for (int i = 0; i < this.points.length; i++) {
			Point p = ProjectionMath.convertPoint(Vector3.add(this.points[i], offset));
			poly.addPoint(p.x, p.y);
		}

		g.setColor(this.lightingColor);
		g.fillPolygon(poly);
	}

	public void rotate(Vector3 rot, Vector3 lightV, Vector3 offset) {
		for (Vector3 p : points) {
			ProjectionMath.rotateAxisX(p, rot.x);
			ProjectionMath.rotateAxisY(p, rot.y);
			ProjectionMath.rotateAxisZ(p, rot.z);
		}
		
		this.updateVisibility(offset);
		this.updateLightingRatio(lightV);
	}

	public void translate(Vector3 amount, Vector3 offset) {
		for (Vector3 p : points) {
			p.x += amount.x;
			p.y += amount.y;
			p.z += amount.z;
		}
		
		this.updateVisibility(offset);
	}

	public void setColor(Color color) {
		this.baseColor = color;
	}

	public Vector3[] getPoints() {
		return this.points;
	}

	public double getAverageX(Vector3 offset) {
		double sum = 0;
		for (Vector3 v : this.points)
			sum += v.x + offset.x;
		return sum / this.points.length;
	}

	public static Polygon3D[] sortPolygons(Polygon3D[] polygons, Vector3 offset) {
		List<Polygon3D> polyList = Arrays.asList(polygons);

		Collections.sort(polyList, new Comparator<Polygon3D>() {
			@Override
			public int compare(Polygon3D o1, Polygon3D o2) {
				Vector3 o1av = o1.getAveragePoint(offset);
				Vector3 o2av = o2.getAveragePoint(offset);
				double o1dist = Vector3.dist(o1av, Vector3.zero);
				double o2dist = Vector3.dist(o2av, Vector3.zero);
				double diff = o1dist - o2dist;
				if (diff == 0)
					return 0;
				return diff < 0 ? 1 : -1;
			}
		});

		for (int i = 0; i < polygons.length; i++)
			polygons[i] = polyList.get(i);

		return polygons;
	}
	
	public boolean isVisible() {
		return this.visible;
	}
	
	public void updateVisibility(Vector3 offset) {
		this.visible = this.getAverageX(offset) < 0;
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

	private Vector3 getAveragePoint(Vector3 offset) {
		double x = 0;
		double y = 0;
		double z = 0;
		for(Vector3 p : this.points) {
			x += p.x + offset.x;
			y += p.y + offset.y;
			z += p.z + offset.z;
		}

		x /= this.points.length;
		y /= this.points.length;
		z /= this.points.length;
		
		return new Vector3(x, y, z);
	}
}
