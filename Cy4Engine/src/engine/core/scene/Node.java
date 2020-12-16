package engine.core.scene;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import engine.core.math.Transform;

public class Node {

	protected String id;
	private Node parent;
	private List<Node> children;
	private Transform transform;
	private Transform localTransform;

	public Node() {
		id = UUID.randomUUID().toString();
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

	public void record(RenderList renderList) {
		for (Node child : children) {
			child.record(renderList);
		}
	}

	public void renderWireframe() {
		for (Node child : children) {
			child.renderWireframe();
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
