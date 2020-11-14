package com.cy4.Cy4Engine.math;

public class Vector3 {

	public double x, y, z;

	public static Vector3 zero = new Vector3();

	public Vector3() {
		this.x = this.y = this.z = 0;
	}

	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3(Vector3 a, Vector3 b) {
		this.x = b.x - a.x;
		this.y = b.y - a.y;
		this.z = b.z - a.z;
	}

	public static double magnitude(Vector3 v) {
		return Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
	}

	public static Vector3 multiply(Vector3 v, double d) {
		return new Vector3(v.x * d, v.y * d, v.z * d);
	}

	public static double dot(Vector3 a, Vector3 b) {
		return a.x * b.x + a.y * b.x + a.z * b.z;
	}

	public static Vector3 cross(Vector3 a, Vector3 b) {
		return new Vector3(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
	}

	public static Vector3 average(Vector3 a, Vector3 b) {
		return new Vector3((a.x + b.x) / 2.0, (a.y + b.y) / 2.0, (a.z + b.z) / 2.0);
	}

	public static Vector3 normalize(Vector3 v) {
		double magnitude = Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
		return new Vector3(v.x / magnitude, v.y / magnitude, v.z / magnitude);
	}
}
