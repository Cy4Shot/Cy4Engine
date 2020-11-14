package com.cy4.Cy4Engine.render.entity.builder;

import com.cy4.Cy4Engine.math.Vector3;
import com.cy4.Cy4Engine.render.entity.IEntity;

public interface IEntityBuilder {
	public IEntity createEntity(double size, Vector3 centre);
}
