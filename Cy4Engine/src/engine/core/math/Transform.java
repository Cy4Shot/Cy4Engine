package engine.core.math;

import engine.core.kernel.Camera;

public class Transform {

	private Vec3f translation;
	private Vec3f scale;
	private Vec3f rot;

	public Transform() {
		setTranslation(new Vec3f(0, 0, 0));
		setRot(new Vec3f(0, 0, 0));
		setScale(new Vec3f(1, 1, 1));
	}
	
	public Matrix4f getWorldMatrix() {
		Matrix4f translationMatrix = new Matrix4f().Translation(translation);
		Matrix4f rotMatrix = new Matrix4f().Rotation(rot);
		Matrix4f scaleMatrix = new Matrix4f().Scaling(scale);
		
		return translationMatrix.mul(scaleMatrix.mul(rotMatrix));
	}
	
	public Matrix4f getModelMatrix() {
		return new Matrix4f().Rotation(rot);
	}
	
	public Matrix4f getMVP() {
		return Camera.getInstance().getViewProjectionMatrix().mul(getWorldMatrix());
	}

	public Vec3f getTranslation() {
		return translation;
	}

	public void setTranslation(Vec3f translation) {
		this.translation = translation;
	}

	public Vec3f getScale() {
		return scale;
	}

	public void setScale(Vec3f scale) {
		this.scale = scale;
	}
	
	public void setScaling(float x, float y, float z) {
		this.scale = new Vec3f(x, y, z);
	}

	public Vec3f getRot() {
		return rot;
	}

	public void setRot(Vec3f rot) {
		this.rot = rot;
	}
}
