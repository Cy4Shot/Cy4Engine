package engine.core.scene;

import engine.core.math.Transform;

public abstract class Component {
	private GameObject parent;

	public void update() {

	}

	public void input() {

	}

	public void render() {

	}

	public GameObject getParent() {
		return parent;
	}

	public void setParent(GameObject parent) {
		this.parent = parent;
	}
	
	public Transform getWorldTransform() {
		return getParent().getTransform();
	}
	
	public Transform getLocaldTransform() {
		return getParent().getLocalTransform();
	}
}
