package com.cy4.Cy4Engine.render.entity;

import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.cy4.Cy4Engine.input.ClickType;
import com.cy4.Cy4Engine.input.MouseInput;
import com.cy4.Cy4Engine.render.entity.builder.ObjEntityBuilder;
import com.cy4.Cy4Engine.render.point.Vector3;

public class EntityManager {
	
	List<IEntity> entities;
	
	public EntityManager() {
		this.entities = new ArrayList<IEntity>();
	}
	

	
	public void init() throws FileNotFoundException, Exception {
		//this.entities.add(BasicEntityBuilder.createCube(100, new Vector3(0, 0, 0)));
		this.entities.add(ObjEntityBuilder.readWavefront(new FileInputStream("./data/sample.obj"), 60, new Vector3(0, 0, 0)));
	}
	
	int ix, iy;
	public void update(MouseInput m) {
		int x = m.getMouseX();
		int y = m.getMouseY();
		if (m.getMouseButton() == ClickType.LEFT_CLICK) {
			int xDif = x - ix;
			int yDif = y - iy;
			
			this.rotate(new Vector3(0, 0, -xDif));
		}
		if (m.getMouseButton() == ClickType.RIGHT_CLICK) {
			int xDif = x - ix;
			int yDif = y - iy;
			
			this.rotate(new Vector3(0, -yDif, 0));
		}
		
		ix = x;
		iy = y;
	}
	
	public void render(Graphics g) {
		for(IEntity e : entities) {
			e.render(g);
		}
	}
	
	public void rotate(Vector3 rot) {
		for(IEntity e : entities) {
			e.rotate(rot);
		}
	}

}
