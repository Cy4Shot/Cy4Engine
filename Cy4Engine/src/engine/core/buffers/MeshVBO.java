package engine.core.buffers;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import engine.core.model.Mesh;
import engine.core.utils.BufferUtil;

public class MeshVBO implements BaseVBO {

	private int vbId;
	private int ibId;
	private int vaId;
	private int size;

	public MeshVBO() {
		vbId = GL15.glGenBuffers();
		ibId = GL15.glGenBuffers();
		vaId = GL30.glGenVertexArrays();
	}

	public void allocate(Mesh mesh) {
		size = mesh.getIndices().length;

		// Buffer Vertex Array
		GL30.glBindVertexArray(vaId);

		// Buffer verticies
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, BufferUtil.createFlippedBufferAOS(mesh.getVertices()),
				GL15.GL_STATIC_DRAW);

		// Buffer indicies
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibId);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, BufferUtil.createFlippedBuffer(mesh.getIndices()),
				GL15.GL_STATIC_DRAW);

		// Setup Pointers
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, Float.BYTES * 8, 0);
		GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, Float.BYTES * 8, Float.BYTES * 3);
		GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, Float.BYTES * 8, Float.BYTES * 6);

		// Unbind verticies
		GL30.glBindVertexArray(0);
	}

	@Override
	public void draw() {
		// Buffer Vertex Array
		GL30.glBindVertexArray(vaId);
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, size, GL11.GL_UNSIGNED_INT, 0);
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		
		GL30.glBindVertexArray(0);
	}

	@Override
	public void delete() {
		
		GL30.glBindVertexArray(vaId);
		GL15.glDeleteBuffers(vbId);
		GL15.glDeleteBuffers(ibId);
		GL30.glDeleteVertexArrays(vaId);
		GL30.glBindVertexArray(0);
	}

}
