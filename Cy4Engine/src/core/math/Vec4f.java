package core.math;

public class Vec4f {

	private float X;
	private float Y;
	private float Z;
	private float W;

	public Vec4f() {
		this.setX(0);
		this.setY(0);
		this.setZ(0);
		this.setW(0);
	}

	public Vec4f(float x, float y, float z, float w) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
		this.setW(w);
	}

	public Vec4f(Vec4f v) {
		this.X = v.getX();
		this.Y = v.getY();
		this.Z = v.getZ();
		this.W = v.getW();
	}
	public float getX() {
		return X;
	}

	public void setX(float x) {
		X = x;
	}

	public float getY() {
		return Y;
	}

	public void setY(float y) {
		Y = y;
	}

	public float getZ() {
		return Z;
	}

	public void setZ(float z) {
		Z = z;
	}

	public float getW() {
		return W;
	}

	public void setW(float w) {
		W = w;
	}

}
