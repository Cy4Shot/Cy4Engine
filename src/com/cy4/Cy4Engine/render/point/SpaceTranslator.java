package com.cy4.Cy4Engine.render.point;

import java.awt.Point;

import com.cy4.Cy4Engine.render.Display;

public class SpaceTranslator {
	
	private static double scale = 1d;
	
	public static Point convertPoint(Vector3 p) {
		double x3d = p.y * scale;
		double y3d = p.z * scale;
		double z3d =  p.x * scale;
		double[] newVal = scale(x3d, y3d, z3d);
		
		int x2d = (int) (Display.WIDTH / 2 + newVal[0]);
		int y2d = (int) (Display.HEIGHT / 2 - newVal[1]);
		
		Point p2d = new Point(x2d, y2d);
		return p2d;
	}
	
	private static double[] scale(double x3d, double y3d, double z3d) {
		double dist = Math.sqrt(x3d*x3d + y3d*y3d);
		double theta = Math.atan2(y3d, x3d);
		double depth = 15 - z3d;
		double localScale = Math.abs(1400/(depth+1400));
		dist *= Display.ORTHOGRAPHIC ? 1 : localScale;
		double[] newVal = new double[2];
		newVal[0] = dist * Math.cos(theta);
		newVal[1] = dist * Math.sin(theta);
		return newVal;
	}
	
	public static void rotateAxisX(Vector3 p, double degrees) {
		double radius  = Math.sqrt(p.y * p.y + p.z * p.z);
		double theta = Math.atan2(p.z, p.y);
		theta += 2 * Math.PI / 360 * -degrees;
		p.y = radius * Math.cos(theta);
		p.z = radius * Math.sin(theta);
	}
	
	public static void rotateAxisY(Vector3 p, double degrees) {
		double radius  = Math.sqrt(p.x * p.x + p.z * p.z);
		double theta = Math.atan2(p.x, p.z);
		theta += 2 * Math.PI / 360 * -degrees;
		p.x = radius * Math.sin(theta);
		p.z = radius * Math.cos(theta);
	}
	
	public static void rotateAxisZ(Vector3 p, double degrees) {
		double radius  = Math.sqrt(p.y * p.y + p.x * p.x);
		double theta = Math.atan2(p.y, p.x);
		theta += 2 * Math.PI / 360 * -degrees;
		p.y = radius * Math.sin (theta);
		p.x = radius * Math.cos(theta);
	}
	
}
