package core.texturing;

import static org.lwjgl.opengl.GL30.GL_DEPTH_COMPONENT32F;
import static org.lwjgl.opengl.GL30.GL_R16F;
import static org.lwjgl.opengl.GL30.GL_R32F;
import static org.lwjgl.opengl.GL30.GL_RGBA16F;
import static org.lwjgl.opengl.GL30.GL_RGBA32F;

import org.lwjgl.opengl.GL11;

public class TextureStorage2D extends Texture2D {

	public TextureStorage2D(int width, int height, int levels, int imageFormat) {

		super(GL11.GL_TEXTURE_2D, width, height);

		bind();

		switch (imageFormat) {
		case GL_RGBA16F:
			allocateStorage2D(levels, GL_RGBA16F);
			break;
		case GL_RGBA32F:
			allocateStorage2D(levels, GL_RGBA32F);
			break;
		case GL_DEPTH_COMPONENT32F:
			allocateStorage2D(levels, GL_DEPTH_COMPONENT32F);
			break;
		case GL_R16F:
			allocateStorage2D(levels, GL_R16F);
			break;
		case GL_R32F:
			allocateStorage2D(levels, GL_R32F);
			break;
		default:
			throw new IllegalArgumentException("Format not supported yet");
		}

		unbind();
	}

	public TextureStorage2D(int width, int height, int levels, int imageFormat, TextureWrapMode textureWrapMode) {

		this(width, height, levels, imageFormat);

		bind();

		switch (textureWrapMode) {
		case ClampToBorder:
			clampToBorder();
			break;
		case ClampToEdge:
			clampToEdge();
			break;
		case MirrorRepeat:
			mirrorRepeat();
			break;
		case Repeat:
			repeat();
			break;
		}

		unbind();
	}

	public enum TextureWrapMode {
		ClampToEdge, ClampToBorder, Repeat, MirrorRepeat
	}

	public enum ImageFormat {
		RGBA8_SNORM, RGBA32FLOAT, RGB32FLOAT, RGBA16FLOAT, DEPTH32FLOAT, R16FLOAT, R32FLOAT, R8
	}

	public enum SamplerFilter {
		Nearest, Bilinear, Trilinear, Anistropic
	}

}