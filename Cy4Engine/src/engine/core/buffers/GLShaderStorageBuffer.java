package engine.core.buffers;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL43;

import engine.core.math.Vec2f;
import engine.core.utils.BufferUtil;

public class GLShaderStorageBuffer {

	private int ssbo;

	public GLShaderStorageBuffer() {
		ssbo = GL15.glGenBuffers();
	}

	public void addData(Vec2f[] data) {
		GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, ssbo);
		GL15.glBufferData(GL43.GL_SHADER_STORAGE_BUFFER, BufferUtil.createFlippedBuffer(data), GL15.GL_STATIC_READ);
	}

	public void addData(int[] data) {
		GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, ssbo);
		GL15.glBufferData(GL43.GL_SHADER_STORAGE_BUFFER, BufferUtil.createFlippedBuffer(data), GL15.GL_STATIC_READ);
	}

	public void addData(float[] data) {
		GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, ssbo);
		GL15.glBufferData(GL43.GL_SHADER_STORAGE_BUFFER, BufferUtil.createFlippedBuffer(data), GL15.GL_STATIC_READ);
	}

	public void addData(ByteBuffer data) {
		GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, ssbo);
		GL15.glBufferData(GL43.GL_SHADER_STORAGE_BUFFER, data, GL15.GL_STATIC_READ);
	}

	public void bindBufferBase(int index) {
		GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, index, ssbo);
	}

}