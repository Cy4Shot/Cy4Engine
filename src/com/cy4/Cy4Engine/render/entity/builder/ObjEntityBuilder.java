package com.cy4.Cy4Engine.render.entity.builder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.cy4.Cy4Engine.render.entity.Entity;
import com.cy4.Cy4Engine.render.entity.IEntity;
import com.cy4.Cy4Engine.render.point.Vector3;
import com.cy4.Cy4Engine.render.shapes.Polygon3D;
import com.cy4.Cy4Engine.render.shapes.Tetrahedron;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjData;
import de.javagl.obj.ObjGroup;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;

public class ObjEntityBuilder {

	public static IEntity readWavefront(InputStream file, double scale, Vector3 pos) throws Exception {
		Obj obj = ObjReader.read(file);
		obj = ObjUtils.triangulate(obj);
		
		List<Tetrahedron> tetras = new ArrayList<Tetrahedron>();
		
		for (int g = 0; g < obj.getNumGroups(); g++ ) {
			
			ObjGroup group = obj.getGroup(g);
	        Obj groupObj = ObjUtils.groupToObj(obj, group, null);
			
			float[] vertices = ObjData.getVerticesArray(groupObj);
			int[] faces = ObjData.getFaceVertexIndicesArray(groupObj);
			
			List<Vector3> points = new ArrayList<Vector3>();
			for (int i = 0; i < vertices.length; i += 3) {
				points.add(new Vector3(vertices[i] * scale, vertices[i+1] * scale, vertices[i+2] * scale));
			}
			
			List<Polygon3D> polys = new ArrayList<Polygon3D>();
			for (int i = 0; i < faces.length; i += 3) {
				polys.add(new Polygon3D(points.get(faces[i]), points.get(faces[i+1]), points.get(faces[i+2])));
			}
			
			tetras.add(new Tetrahedron(polys.toArray(new Polygon3D[0])));
		}
		
		return new Entity(tetras);
	}

}