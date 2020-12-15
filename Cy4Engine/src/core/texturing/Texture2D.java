package core.texturing;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;

import org.lwjgl.opengl.GL11;

import core.utils.ImageLoader;

public class Texture2D {

	private int id;
	private int width;
	private int height;

	public Texture2D() {
	}

	public Texture2D(String file) {
		int[] data = ImageLoader.loadImage(file);
		this.id = data[0];
		this.width = data[1];
		this.height = data[2];
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}

	public void generate() {
		id = glGenTextures();
	}

	public void delete() {
		glDeleteTextures(id);
	}

	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public void noFilter() {
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
	}
	
	public void bilinearFilter() {
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	}

	public int getId() {
		return id;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
