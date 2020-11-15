package com.cy4.Cy4Engine.render.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.cy4.Cy4Engine.input.ClickType;
import com.cy4.Cy4Engine.input.UserInput;
import com.cy4.Cy4Engine.math.Vector3;
import com.cy4.Cy4Engine.render.entity.builder.ObjEntityBuilder;

public class EntityManager {

	List<IEntity> entities;
	private Vector3 lightVector = Vector3.normalize(new Vector3(-1, 1, -1));
	private UserInput input;
	private Camera camera;

	private static double movementSpeed = 5d;

	public EntityManager() {
		this.entities = new ArrayList<IEntity>();
		this.camera = new Camera(Vector3.zero);
	}

	public void init(UserInput input) throws FileNotFoundException, Exception {
		this.entities.add(ObjEntityBuilder.readWavefront(new FileInputStream("./data/sample.obj"), 60, Vector3.zero, Color.BLUE));
//		this.entities.add(BasicEntityBuilder.createIcosphere(100, new Vector3(0, 0, 0), 2, Color.WHITE));
//		this.entities.add(ComplexEntityBuilder.createRubiksCube(100, Vector3.zero, 10));
		this.setLighting();
		this.input = input;
	}

	int ix, iy;

	public void update() {
		int x = this.input.mouse.getMouseX();
		int y = this.input.mouse.getMouseY();
		if (this.input.mouse.getMouseButton() == ClickType.LEFT_CLICK) {
			int xDif = x - ix;
			int yDif = y - iy;

			this.rotate(new Vector3(0, -yDif, -xDif));
		}
		if (this.input.mouse.getMouseButton() == ClickType.RIGHT_CLICK) {
			int xDif = x - ix;
			int yDif = y - iy;

			this.rotate(new Vector3(0, -yDif, 0));
		}

		if (this.input.keyboard.getKey(KeyEvent.VK_LEFT) || this.input.keyboard.getKey(KeyEvent.VK_A)) {
			this.camera.translate(new Vector3(0, -movementSpeed, 0));
			for (IEntity entity : this.entities) {
				entity.translate(new Vector3(0, movementSpeed, 0));
			}
		}

		if (this.input.keyboard.getKey(KeyEvent.VK_RIGHT) || this.input.keyboard.getKey(KeyEvent.VK_D)) {
			this.camera.translate(new Vector3(0, movementSpeed, 0));
			for (IEntity entity : this.entities) {
				entity.translate(new Vector3(0, -movementSpeed, 0));
			}
		}

		if (this.input.keyboard.getKey(KeyEvent.VK_UP) || this.input.keyboard.getKey(KeyEvent.VK_W)) {
			this.camera.translate(new Vector3(-movementSpeed, 0, 0));
			for (IEntity entity : this.entities) {
				entity.translate(new Vector3(movementSpeed, 0, 0));
			}
		}

		if (this.input.keyboard.getKey(KeyEvent.VK_DOWN) || this.input.keyboard.getKey(KeyEvent.VK_S)) {
			this.camera.translate(new Vector3(movementSpeed, 0, 0));
			for (IEntity entity : this.entities) {
				entity.translate(new Vector3(-movementSpeed, 0, 0));
			}
		}

		if (this.input.keyboard.getKey(KeyEvent.VK_SPACE)) {
			this.camera.translate(new Vector3(0, 0, movementSpeed));
			for (IEntity entity : this.entities) {
				entity.translate(new Vector3(0, 0, -movementSpeed));
			}
		}

		if (this.input.keyboard.getKey(KeyEvent.VK_SHIFT)) {
			this.camera.translate(new Vector3(0, 0, -movementSpeed));
			for (IEntity entity : this.entities) {
				entity.translate(new Vector3(0, 0, movementSpeed));
			}
		}

		ix = x;
		iy = y;
	}

	public void render(Graphics g) {
		for (IEntity e : entities) {
			e.render(g);
		}
	}

	public void rotate(Vector3 rot) {
		for (IEntity e : entities) {
			e.rotate(rot, this.lightVector);
		}
	}

	private void setLighting() {
		for (IEntity entity : this.entities) {
			entity.setLighting(this.lightVector);
		}
	}

}
