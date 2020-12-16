package modules.water;

import static org.lwjgl.opengl.GL11.GL_CCW;
import static org.lwjgl.opengl.GL11.GL_CW;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFinish;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL30.GL_CLIP_DISTANCE6;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.system.MemoryUtil.memAlloc;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL30;

import core.Cy4Engine;
import core.buffers.GLFramebuffer;
import core.buffers.GLShaderStorageBuffer;
import core.buffers.PatchVBO;
import core.kernel.Camera;
import core.kernel.Window;
import core.math.Vec2f;
import core.math.Vec4f;
import core.rendering.RenderInfo;
import core.rendering.Renderer;
import core.scene.GameObject;
import core.scene.RenderList;
import core.scene.Scenegraph;
import core.shaders.Shader;
import core.texturing.Texture2D;
import core.utils.BufferUtil;
import core.utils.Constants;
import modules.fft.FFT;
import modules.gpgpu.NormalMapRenderer;

public class Water extends GameObject {
	private Vec4f clipplane;
	private int clip_offset;
	private float motion;
	private Texture2D dudv;

	private GLFramebuffer reflection_fbo;
	private GLFramebuffer refraction_fbo;
	private Texture2D reflection_texture;

	private Texture2D refraction_texture;
	private RenderList reflectionRenderList;
	private RenderList refractionRenderList;

	private FFT fft;
	private NormalMapRenderer normalmapRenderer;
	private boolean isCameraUnderwater;

	private WaterRenderConfig renderConfig;

	private WaterConfig config;

	private GLShaderStorageBuffer ssbo;

	private float t_motion;
	private float t_distortion;
	private long systemTime = System.currentTimeMillis();

	public Water(int patches, Shader shader, Shader wireframeShader) {
		config = new WaterConfig();
		config.loadFile("water-config.properties");

		PatchVBO meshBuffer = new PatchVBO();
		meshBuffer.allocate(generatePatch(), 16);

		renderConfig = new WaterRenderConfig();

		Renderer renderer = new Renderer();
		renderer.setVbo(meshBuffer);
		renderer.setInfo(new RenderInfo(renderConfig, shader));

		Renderer wireframerenderer = new Renderer();
		wireframerenderer.setVbo(meshBuffer);
		wireframerenderer.setInfo(new RenderInfo(renderConfig, wireframeShader));

		dudv = new Texture2D("./res/textures/water/dudv/dudv1.jpg");
		dudv.bind();
		dudv.bilinearFilter();

		addComponent(Constants.RENDERER_COMPONENT, renderer);
		addComponent(Constants.WIREFRAME_RENDERER_COMPONENT, wireframerenderer);

		fft = new FFT(config.getN(), config.getL(), config.getAmplitude(), config.getWindDirection(),
				config.getAlignment(), config.getWindSpeed(), config.getCapillarWavesSupression());
		fft.setT_delta(config.getT_delta());
		fft.setChoppy(config.isChoppy());
		fft.init();

		normalmapRenderer = new NormalMapRenderer(config.getN());
		getNormalmapRenderer().setStrength(config.getNormalStrength());

		reflection_texture = new Texture2D(Window.getInstance().getWidth() / 2, Window.getInstance().getHeight() / 2,
				GL30.GL_RGBA16F);
		reflection_texture.bind();
		reflection_texture.noFilter();

		IntBuffer drawBuffers = BufferUtil.createIntBuffer(1);
		drawBuffers.put(GL_COLOR_ATTACHMENT0);
		drawBuffers.flip();

		reflection_fbo = new GLFramebuffer();
		reflection_fbo.bind();
		reflection_fbo.createColorTextureAttachment(reflection_texture.getId(), 0);
		reflection_fbo.createDepthBufferAttachment(Window.getInstance().getWidth() / 2,
				Window.getInstance().getHeight() / 2);
		reflection_fbo.setDrawBuffers(drawBuffers);
//		reflection_fbo.checkStatus();
		reflection_fbo.unbind();

		refraction_texture = new Texture2D(Window.getInstance().getWidth() / 2, Window.getInstance().getHeight() / 2,
				GL30.GL_RGBA16F);
		refraction_texture.bind();
		refraction_texture.noFilter();

		refraction_fbo = new GLFramebuffer();
		refraction_fbo.bind();
		refraction_fbo.createColorTextureAttachment(refraction_texture.getId(), 0);
		refraction_fbo.createDepthBufferAttachment(Window.getInstance().getWidth() / 2,
				Window.getInstance().getHeight() / 2);
		refraction_fbo.setDrawBuffers(drawBuffers);
//		refraction_fbo.checkStatus();
		refraction_fbo.unbind();

		refractionRenderList = new RenderList();
		reflectionRenderList = new RenderList();
	}

	public void update() {
		isCameraUnderwater = Camera.getInstance().getPosition().getY() < getTransform().getTranslation().getY();
		t_motion += (System.currentTimeMillis() - systemTime) * config.getWaveMotion();
		t_distortion += (System.currentTimeMillis() - systemTime) * config.getDistortion();
		systemTime = System.currentTimeMillis();
	}

//	public void renderWireframe() {
//
//		fft.render();
//
//		ssbo.bindBufferBase(1);
//
//		super.renderWireframe();
//
//		glFinish();
//	}

	public void render() {
		if (!isCameraUnderwater) {
			glEnable(GL_CLIP_DISTANCE6);
			Camera.getInstance().setUnderwater(false);
		} else {
			Camera.getInstance().setUnderwater(true);
		}

		Scenegraph scenegraph = Cy4Engine.getInstance().getEngine().getRenderingEngine().getSceneGraph();

//		Cy4Engine.getInstance().getEngine().getRenderingEngine().getConfig().setClipplane(clipplane);

		// -----------------------------------//
		// mirror scene to clipplane //
		// -----------------------------------//

		scenegraph.getTransform().setScaling(1, -1, 1);
//		GLTerrain.getConfig().setVerticalScaling(GLTerrain.getConfig().getVerticalScaling() * -1f);
//		GLTerrain.getConfig().setReflectionOffset(clip_offset * 2);
		scenegraph.update();

		// -----------------------------------//
		// render reflection to texture //
		// -----------------------------------//

		int tempScreenResolutionX = Window.getInstance().getWidth();
		int tempScreenResolutionY = Window.getInstance().getHeight();
		Window.getInstance().setWidth(tempScreenResolutionX / 2);
		Window.getInstance().setHeight(tempScreenResolutionY / 2);
		glViewport(0, 0, tempScreenResolutionX / 2, tempScreenResolutionY / 2);

//		Cy4Engine.getInstance().getEngine().getRenderingEngine().getConfig().setRenderReflection(true);

		reflection_fbo.bind();
		renderConfig.clearScreenDeepOcean();
		glFrontFace(GL_CCW);

		if (!isCameraUnderwater) {

			scenegraph.record(reflectionRenderList);

			reflectionRenderList.remove(this.id);

			reflectionRenderList.getValues().forEach(object -> {
				object.render();
			});
		}

		// glFinish() important, to prevent conflicts with following compute shaders
		glFinish();
		glFrontFace(GL_CW);
		reflection_fbo.unbind();

//		Cy4Engine.getInstance().getEngine().getRenderingEngine().getConfig().setRenderReflection(false);

		// -----------------------------------//
		// antimirror scene to clipplane //
		// -----------------------------------//

		scenegraph.getTransform().setScaling(1, 1, 1);

//		if (scenegraph.hasTerrain()) {
//			GLTerrain.getConfig().setVerticalScaling(GLTerrain.getConfig().getVerticalScaling() / -1f);
//			GLTerrain.getConfig().setReflectionOffset(0);
//		}

		scenegraph.update();

		// -----------------------------------//
		// render refraction to texture //
		// -----------------------------------//

//		Cy4Engine.getInstance().getEngine().getRenderingEngine().getConfig().setRenderRefraction(true);

		refraction_fbo.bind();
		renderConfig.clearScreenDeepOcean();

		scenegraph.record(refractionRenderList);

		refractionRenderList.remove(this.id);

		refractionRenderList.getValues().forEach(object -> {
			object.render();
		});

		// glFinish() important, to prevent conflicts with following compute shaders
		glFinish();
		refraction_fbo.unbind();

		// -----------------------------------//
		// reset rendering settings //
		// -----------------------------------//

//		Cy4Engine.getInstance().getEngine().getRenderingEngine().getConfig().setRenderRefraction(false);

		glDisable(GL_CLIP_DISTANCE6);
//		Cy4Engine.getInstance().getEngine().getRenderingEngine().getConfig().setClipplane(Constants.ZEROPLANE);

		glViewport(0, 0, tempScreenResolutionX, tempScreenResolutionY);
		Window.getInstance().setWidth(tempScreenResolutionX);
		Window.getInstance().setHeight(tempScreenResolutionY);

		// GLContext.getResources().getPrimaryFbo().bind();

		// -----------------------------------//
		// render FFT'S //
		// -----------------------------------//
		fft.render();
		normalmapRenderer.render(fft.getDy());

		ssbo.bindBufferBase(1);

		super.render();

		// glFinish() important, to prevent conflicts with following compute shaders
		glFinish();
	}

	public void initShaderBuffer() {

		ssbo = new GLShaderStorageBuffer();
		ByteBuffer byteBuffer = memAlloc(Float.BYTES * 33 + Integer.BYTES * 6);
		byteBuffer.put(BufferUtil.createByteBuffer(getTransform().getWorldMatrix()));
		byteBuffer.putInt(config.getUvScale());
		byteBuffer.putInt(config.getTessellationFactor());
		byteBuffer.putFloat(config.getTessellationSlope());
		byteBuffer.putFloat(config.getTessellationShift());
		byteBuffer.putFloat(config.getDisplacementScale());
		byteBuffer.putInt(config.getHighDetailRange());
		byteBuffer.putFloat(config.getChoppiness());
		byteBuffer.putFloat(config.getkReflection());
		byteBuffer.putFloat(config.getkRefraction());
		byteBuffer.putInt(Window.getInstance().getWidth());
		byteBuffer.putInt(Window.getInstance().getHeight());
		byteBuffer.putInt(config.isDiffuse() ? 1 : 0);
		byteBuffer.putFloat(config.getEmission());
		byteBuffer.putFloat(config.getSpecularFactor());
		byteBuffer.putFloat(config.getSpecularAmplifier());
		byteBuffer.putFloat(config.getReflectionBlendFactor());
		byteBuffer.putFloat(config.getBaseColor().getX());
		byteBuffer.putFloat(config.getBaseColor().getY());
		byteBuffer.putFloat(config.getBaseColor().getZ());
		byteBuffer.putFloat(config.getFresnelFactor());
		byteBuffer.putFloat(config.getCapillarStrength());
		byteBuffer.putFloat(config.getCapillarDownsampling());
		byteBuffer.putFloat(config.getDudvDownsampling());
		byteBuffer.flip();
		ssbo.addData(byteBuffer);
	}

	public Vec2f[] generatePatch() {

		// 16 vertices for each patch
		Vec2f[] vertices = new Vec2f[16];

		int index = 0;

		vertices[index++] = new Vec2f(0, 0);
		vertices[index++] = new Vec2f(0.333f, 0);
		vertices[index++] = new Vec2f(0.666f, 0);
		vertices[index++] = new Vec2f(1, 0);

		vertices[index++] = new Vec2f(0, 0.333f);
		vertices[index++] = new Vec2f(0.333f, 0.333f);
		vertices[index++] = new Vec2f(0.666f, 0.333f);
		vertices[index++] = new Vec2f(1, 0.333f);

		vertices[index++] = new Vec2f(0, 0.666f);
		vertices[index++] = new Vec2f(0.333f, 0.666f);
		vertices[index++] = new Vec2f(0.666f, 0.666f);
		vertices[index++] = new Vec2f(1, 0.666f);

		vertices[index++] = new Vec2f(0, 1);
		vertices[index++] = new Vec2f(0.333f, 1);
		vertices[index++] = new Vec2f(0.666f, 1);
		vertices[index++] = new Vec2f(1, 1);

		return vertices;
	}

	public Vec4f getClipplane() {
		return clipplane;
	}

	public void setClipplane(Vec4f clipplane) {
		this.clipplane = clipplane;
	}

	public int getClip_offset() {
		return clip_offset;
	}

	public void setClip_offset(int clip_offset) {
		this.clip_offset = clip_offset;
	}

	public float getMotion() {
		return motion;
	}

	public void setMotion(float motion) {
		this.motion = motion;
	}

	public Texture2D getDudv() {
		return dudv;
	}

	public void setDudv(Texture2D dudv) {
		this.dudv = dudv;
	}

	public GLFramebuffer getReflection_fbo() {
		return reflection_fbo;
	}

	public void setReflection_fbo(GLFramebuffer reflection_fbo) {
		this.reflection_fbo = reflection_fbo;
	}

	public GLFramebuffer getRefraction_fbo() {
		return refraction_fbo;
	}

	public void setRefraction_fbo(GLFramebuffer refraction_fbo) {
		this.refraction_fbo = refraction_fbo;
	}

	public Texture2D getReflection_texture() {
		return reflection_texture;
	}

	public void setReflection_texture(Texture2D reflection_texture) {
		this.reflection_texture = reflection_texture;
	}

	public Texture2D getRefraction_texture() {
		return refraction_texture;
	}

	public void setRefraction_texture(Texture2D refraction_texture) {
		this.refraction_texture = refraction_texture;
	}

	public RenderList getReflectionRenderList() {
		return reflectionRenderList;
	}

	public void setReflectionRenderList(RenderList reflectionRenderList) {
		this.reflectionRenderList = reflectionRenderList;
	}

	public RenderList getRefractionRenderList() {
		return refractionRenderList;
	}

	public void setRefractionRenderList(RenderList refractionRenderList) {
		this.refractionRenderList = refractionRenderList;
	}

	public FFT getFft() {
		return fft;
	}

	public void setFft(FFT fft) {
		this.fft = fft;
	}

	public NormalMapRenderer getNormalmapRenderer() {
		return normalmapRenderer;
	}

	public void setNormalmapRenderer(NormalMapRenderer normalmapRenderer) {
		this.normalmapRenderer = normalmapRenderer;
	}

	public boolean isCameraUnderwater() {
		return isCameraUnderwater;
	}

	public void setCameraUnderwater(boolean isCameraUnderwater) {
		this.isCameraUnderwater = isCameraUnderwater;
	}

	public WaterRenderConfig getRenderConfig() {
		return renderConfig;
	}

	public void setRenderConfig(WaterRenderConfig renderConfig) {
		this.renderConfig = renderConfig;
	}

	public WaterConfig getConfig() {
		return config;
	}

	public void setConfig(WaterConfig config) {
		this.config = config;
	}

	public GLShaderStorageBuffer getSsbo() {
		return ssbo;
	}

	public void setSsbo(GLShaderStorageBuffer ssbo) {
		this.ssbo = ssbo;
	}

	public float getT_motion() {
		return t_motion;
	}

	public void setT_motion(float t_motion) {
		this.t_motion = t_motion;
	}

	public float getT_distortion() {
		return t_distortion;
	}

	public void setT_distortion(float t_distortion) {
		this.t_distortion = t_distortion;
	}

	public long getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(long systemTime) {
		this.systemTime = systemTime;
	}
}
