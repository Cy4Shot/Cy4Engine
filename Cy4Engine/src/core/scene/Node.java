package core.scene;

import java.util.ArrayList;
import java.util.List;

import core.math.Transform;

public class Node {

	private Node parent;
	private List<Node> children;
	private Transform transform;
	private Transform localTransform;
	
	public Node() {
		setTransform(new Transform());
		setLocalTransform(new Transform());
		setChildren(new ArrayList<Node>());
	}
	
	public void addChild(Node node) {
		node.setParent(this);
		children.add(node);
	}
	
	public void update() {
		for (Node child : children) {
			child.update();
		}
	}
	
	public void input() {
		for (Node child : children) {
			child.input();
		}
	}
	
	public void render() {
		for (Node child : children) {
			child.render();
		}
	}
	
	public void shutdown() {
		for (Node child : children) {
			child.shutdown();
		}
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	public Transform getTransform() {
		return transform;
	}

	public void setTransform(Transform transform) {
		this.transform = transform;
	}

	public Transform getLocalTransform() {
		return localTransform;
	}

	public void setLocalTransform(Transform localTransform) {
		this.localTransform = localTransform;
	}
}
