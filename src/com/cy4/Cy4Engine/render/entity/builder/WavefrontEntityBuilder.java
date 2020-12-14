package com.cy4.Cy4Engine.render.entity.builder;

import java.awt.Color;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.cy4.Cy4Engine.math.Vector3;
import com.cy4.Cy4Engine.render.entity.Entity;
import com.cy4.Cy4Engine.render.entity.IEntity;
import com.cy4.Cy4Engine.render.poly.Polygon3D;
import com.cy4.Cy4Engine.render.poly.Polyhedron;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjData;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;

public class WavefrontEntityBuilder {

	public static IEntity readWavefront(InputStream file, double scale, Vector3 centre) throws Exception {
		return WavefrontEntityBuilder.readWavefront(file, scale, centre, Color.PINK);
	}

	public static IEntity readWavefront(InputStream file, double scale, Vector3 centre, Color color) throws Exception {
		Obj obj = ObjUtils.triangulate(ObjReader.read(file));

		List<Polyhedron> polyhedra = new ArrayList<Polyhedron>();

		for (int g = 0; g < obj.getNumGroups(); g++) {

			Obj groupObj = ObjUtils.groupToObj(obj, obj.getGroup(g), null);

			float[] vertices = ObjData.getVerticesArray(groupObj);
			int[] faces = ObjData.getFaceVertexIndicesArray(groupObj);

			List<Vector3> points = new ArrayList<Vector3>();
			for (int i = 0; i < vertices.length; i += 3) {
				points.add(new Vector3(vertices[i] * scale, vertices[i + 1] * scale, vertices[i + 2] * scale));
			}

			List<Polygon3D> polys = new ArrayList<Polygon3D>();
			for (int i = 0; i < faces.length; i += 3) {
				polys.add(
						new Polygon3D(color, points.get(faces[i]), points.get(faces[i + 1]), points.get(faces[i + 2])));
			}

			polyhedra.add(new Polyhedron(color, centre, polys.toArray(new Polygon3D[0])));
		}

		Entity e = new Entity(polyhedra);
		e.translate(centre);
		return e;
	}

}