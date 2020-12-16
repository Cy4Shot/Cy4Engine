package core.texturing;

import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT;
import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RED;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glGetFloat;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.opengl.GL14.GL_MIRRORED_REPEAT;
import static org.lwjgl.opengl.GL30.GL_DEPTH_COMPONENT32F;
import static org.lwjgl.opengl.GL30.GL_R16F;
import static org.lwjgl.opengl.GL30.GL_R32F;
import static org.lwjgl.opengl.GL30.GL_R8;
import static org.lwjgl.opengl.GL30.GL_RGB32F;
import static org.lwjgl.opengl.GL30.GL_RGBA16F;
import static org.lwjgl.opengl.GL30.GL_RGBA32F;
import static org.lwjgl.opengl.GL31.GL_RGBA8_SNORM;
import static org.lwjgl.opengl.GL32.GL_TEXTURE_2D_MULTISAMPLE;
import static org.lwjgl.opengl.GL32.glTexImage2DMultisample;
import static org.lwjgl.opengl.GL42.glTexStorage2D;
import static org.lwjgl.opengl.GL42.glTexStorage3D;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import core.texturing.TextureStorage2D.ImageFormat;
import core.texturing.TextureStorage2D.SamplerFilter;
import core.texturing.TextureStorage2D.TextureWrapMode;
import core.utils.ImageLoader;

public class Texture2D {

	private int id;
	private int width;
	private int height;
	private int target;

	public Texture2D() {
	}

	public Texture2D(String file) {
		int[] data = ImageLoader.loadImage(file);
		this.target = GL11.GL_TEXTURE_2D;
		this.id = data[0];
		this.width = data[1];
		this.height = data[2];
	}

	public Texture2D(int target, int w, int h) {
		generate();
		this.setTarget(target);
		width = w;
		height = h;
	}

	public Texture2D(int width, int height, int samples, ImageFormat imageFormat) {

		this(samples > 1 ? GL_TEXTURE_2D_MULTISAMPLE : GL_TEXTURE_2D, width, height);

		bind();

		switch (imageFormat) {
		case RGBA8_SNORM:
			if (samples > 1)
				allocateImage2DMultisample(samples, GL_RGBA8_SNORM);
			else
				allocateImage2D(GL_RGBA8_SNORM, GL_RGBA, GL_FLOAT);
			break;
		case RGBA16FLOAT:
			if (samples > 1)
				allocateImage2DMultisample(samples, GL_RGBA16F);
			else
				allocateImage2D(GL_RGBA16F, GL_RGBA, GL_FLOAT);
			break;
		case RGBA32FLOAT:
			if (samples > 1)
				allocateImage2DMultisample(samples, GL_RGBA32F);
			else
				allocateImage2D(GL_RGBA32F, GL_RGBA, GL_FLOAT);
			break;
		case RGB32FLOAT:
			if (samples > 1)
				allocateImage2DMultisample(samples, GL_RGB32F);
			else
				allocateImage2D(GL_RGB32F, GL_RGBA, GL_FLOAT);
			break;
		case DEPTH32FLOAT:
			if (samples > 1)
				allocateImage2DMultisample(samples, GL_DEPTH_COMPONENT32F);
			else
				allocateImage2D(GL_DEPTH_COMPONENT32F, GL_DEPTH_COMPONENT, GL_FLOAT);
			break;
		case R16FLOAT:
			if (samples > 1)
				allocateImage2DMultisample(samples, GL_R16F);
			else
				allocateImage2D(GL_R16F, GL_RED, GL_FLOAT);
			break;
		case R32FLOAT:
			if (samples > 1)
				allocateImage2DMultisample(samples, GL_R32F);
			else
				allocateImage2D(GL_R32F, GL_RED, GL_FLOAT);
			break;
		default:
			throw new IllegalArgumentException("Format not supported yet");
		}

		unbind();
	}

	public Texture2D(int width, int height, int samples, ImageFormat imageFormat, SamplerFilter samplerFilter) {

		this(width, height, samples, imageFormat);

		bind();

		switch (samplerFilter) {
		case Nearest:
			nearestFilter();
			break;
		case Bilinear:
			bilinearFilter();
			break;
		case Trilinear:
			trilinearFilter();
			break;
		case Anistropic:
			anisotropicFilter();
			break;
		}

		unbind();
	}

	public Texture2D(int width, int height, int samples, ImageFormat imageFormat, SamplerFilter samplerFilter,
			TextureWrapMode textureWrapMode) {

		this(width, height, samples, imageFormat, samplerFilter);

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

	public Texture2D(int width, int height, ImageFormat imageFormat) {

		this(GL_TEXTURE_2D, width, height);

		bind();

		switch (imageFormat) {
		case RGBA16FLOAT:
			allocateImage2D(GL_RGBA16F, GL_RGBA, GL_FLOAT);
			break;
		case RGBA32FLOAT:
			allocateImage2D(GL_RGBA32F, GL_RGBA, GL_FLOAT);
			break;
		case DEPTH32FLOAT:
			allocateImage2D(GL_DEPTH_COMPONENT32F, GL_DEPTH_COMPONENT, GL_FLOAT);
			break;
		case R16FLOAT:
			allocateImage2D(GL_R16F, GL_RED, GL_FLOAT);
			break;
		case R32FLOAT:
			allocateImage2D(GL_R32F, GL_RED, GL_FLOAT);
			break;
		case R8:
			allocateImage2D(GL_R8, GL_RED, GL_UNSIGNED_BYTE);
			break;
		default:
			throw new IllegalArgumentException("Format not supported yet");
		}

		unbind();
	}

	public Texture2D(int width, int height, ImageFormat imageFormat, SamplerFilter samplerFilter) {

		this(width, height, imageFormat);

		bind();

		switch (samplerFilter) {
		case Nearest:
			nearestFilter();
			break;
		case Bilinear:
			bilinearFilter();
			break;
		case Trilinear:
			trilinearFilter();
			break;
		case Anistropic:
			anisotropicFilter();
			break;
		}

		unbind();
	}

	public Texture2D(int width, int height, ImageFormat imageFormat, SamplerFilter samplerFilter,
			TextureWrapMode textureWrapMode) {

		this(width, height, imageFormat, samplerFilter);

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

	public void trilinearFilter() {
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
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

	public void clampToEdge() {

		glTexParameteri(id, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(id, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
	}

	public void clampToBorder() {

		glTexParameteri(id, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
		glTexParameteri(id, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
	}

	public void repeat() {

		glTexParameteri(id, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(id, GL_TEXTURE_WRAP_T, GL_REPEAT);
	}

	public void mirrorRepeat() {

		glTexParameteri(id, GL_TEXTURE_WRAP_S, GL_MIRRORED_REPEAT);
		glTexParameteri(id, GL_TEXTURE_WRAP_T, GL_MIRRORED_REPEAT);
	}

	public void allocateImage2D(int internalFormat, int format, int type, ByteBuffer data) {

		glTexImage2D(target, 0, internalFormat, width, height, 0, format, type, data);
	}

	public void allocateImage2D(int internalFormat, int format, int type) {

		glTexImage2D(target, 0, internalFormat, width, height, 0, format, type, (ByteBuffer) null);
	}

	public void allocateImage2DMultisample(int samples, int internalFormat) {

		glTexImage2DMultisample(target, samples, internalFormat, width, height, true);
	}

	public void allocateStorage2D(int levels, int internalFormat) {

		glTexStorage2D(target, levels, internalFormat, width, height);
	}

	public void allocateStorage3D(int levels, int layers, int internalFormat) {

		glTexStorage3D(target, levels, internalFormat, width, height, layers);
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public void anisotropicFilter() {

		if (GL.getCapabilities().GL_EXT_texture_filter_anisotropic) {
			float maxfilterLevel = glGetFloat(GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT);
			glTexParameterf(target, GL_TEXTURE_MAX_ANISOTROPY_EXT, maxfilterLevel);
		} else {
			System.err.println("anisotropic not supported");
		}
	}

	public void nearestFilter() {

		glTexParameteri(target, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(target, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	}
}
