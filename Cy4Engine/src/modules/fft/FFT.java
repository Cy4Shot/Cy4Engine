package modules.fft;

import static org.lwjgl.opengl.GL11.glFinish;
import static org.lwjgl.opengl.GL15.GL_READ_WRITE;
import static org.lwjgl.opengl.GL30.GL_RGBA32F;
import static org.lwjgl.opengl.GL42.glBindImageTexture;
import static org.lwjgl.opengl.GL43.glDispatchCompute;

import org.lwjgl.opengl.GL30;

import core.math.Vec2f;
import core.texturing.Texture2D;
import core.texturing.TextureStorage2D;

public class FFT {

	private Texture2D Dy;
	private Texture2D Dx;
	private Texture2D Dz;
	private boolean choppy;
	protected Texture2D pingpongTexture;
	private int log_2_N;
	private int pingpong;
	private int N;
	private float t;
	private long systemTime = System.currentTimeMillis();
	private float t_delta;
	private FFTButterflyShader butterflyShader;
	protected FFTInversionShader inversionShader;
	private TwiddleFactors twiddleFactors;

	protected H0k h0k;
	protected Hkt hkt;

	public Texture2D getDy() {
		return Dy;
	}

	public void setDy(Texture2D dy) {
		Dy = dy;
	}

	public Texture2D getDx() {
		return Dx;
	}

	public void setDx(Texture2D dx) {
		Dx = dx;
	}

	public Texture2D getDz() {
		return Dz;
	}

	public void setDz(Texture2D dz) {
		Dz = dz;
	}

	public boolean isChoppy() {
		return choppy;
	}

	public void setChoppy(boolean choppy) {
		this.choppy = choppy;
	}

	public Texture2D getPingpongTexture() {
		return pingpongTexture;
	}

	public void setPingpongTexture(Texture2D pingpongTexture) {
		this.pingpongTexture = pingpongTexture;
	}

	public int getLog_2_N() {
		return log_2_N;
	}

	public void setLog_2_N(int log_2_N) {
		this.log_2_N = log_2_N;
	}

	public int getPingpong() {
		return pingpong;
	}

	public void setPingpong(int pingpong) {
		this.pingpong = pingpong;
	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

	public float getT() {
		return t;
	}

	public void setT(float t) {
		this.t = t;
	}

	public long getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(long systemTime) {
		this.systemTime = systemTime;
	}

	public float getT_delta() {
		return t_delta;
	}

	public void setT_delta(float t_delta) {
		this.t_delta = t_delta;
	}

	public FFTButterflyShader getButterflyShader() {
		return butterflyShader;
	}

	public void setButterflyShader(FFTButterflyShader butterflyShader) {
		this.butterflyShader = butterflyShader;
	}

	public FFTInversionShader getInversionShader() {
		return inversionShader;
	}

	public void setInversionShader(FFTInversionShader inversionShader) {
		this.inversionShader = inversionShader;
	}

	public TwiddleFactors getTwiddleFactors() {
		return twiddleFactors;
	}

	public void setTwiddleFactors(TwiddleFactors twiddleFactors) {
		this.twiddleFactors = twiddleFactors;
	}

	public H0k getH0k() {
		return h0k;
	}

	public void setH0k(H0k h0k) {
		this.h0k = h0k;
	}

	public Hkt getHkt() {
		return hkt;
	}

	public void setHkt(Hkt hkt) {
		this.hkt = hkt;
	}

	public FFT(int N, int L, float amplitude, Vec2f direction, float alignment, float intensity,
			float capillarSupressFactor) {
		this.N = N;
		log_2_N = (int) (Math.log(N) / Math.log(2));
		twiddleFactors = new TwiddleFactors(N);
		h0k = new H0k(N, L, amplitude, direction, alignment, intensity, capillarSupressFactor);
		hkt = new Hkt(N, L);

		butterflyShader = FFTButterflyShader.getInstance();
		inversionShader = FFTInversionShader.getInstance();

		pingpongTexture = new TextureStorage2D(N, N, 1, GL30.GL_RGBA32F);
		Dy = new TextureStorage2D(N, N, 1, GL30.GL_RGBA32F);
		Dx = new TextureStorage2D(N, N, 1, GL30.GL_RGBA32F);
		Dz = new TextureStorage2D(N, N, 1, GL30.GL_RGBA32F);
	}

	public void init() {
		h0k.render();
		twiddleFactors.render();
		hkt.setImageH0k(h0k.getImageH0k());
		hkt.setImageH0minusK(h0k.getImageH0minusK());
	}

	public void render() {
		hkt.render(t);

		pingpong = 0;
		butterflyShader.bind();

		glBindImageTexture(0, twiddleFactors.getTexture().getId(), 0, false, 0, GL_READ_WRITE, GL_RGBA32F);
		glBindImageTexture(1, hkt.getImageDyCoeficcients().getId(), 0, false, 0, GL_READ_WRITE, GL_RGBA32F);
		glBindImageTexture(2, getPingpongTexture().getId(), 0, false, 0, GL_READ_WRITE, GL_RGBA32F);

		// 1D FFT horizontal
		for (int i = 0; i < log_2_N; i++) {
			butterflyShader.updateUniforms(pingpong, 0, i);
			glDispatchCompute(N / 16, N / 16, 1);
			glFinish();
			pingpong++;
			pingpong %= 2;
		}

		// 1D FFT vertical
		for (int j = 0; j < log_2_N; j++) {
			butterflyShader.updateUniforms(pingpong, 1, j);
			glDispatchCompute(N / 16, N / 16, 1);
			glFinish();
			pingpong++;
			pingpong %= 2;
		}

		inversionShader.bind();
		inversionShader.updateUniforms(N, pingpong);
		glBindImageTexture(0, Dy.getId(), 0, false, 0, GL_READ_WRITE, GL_RGBA32F);
		glDispatchCompute(N / 16, N / 16, 1);
		glFinish();

		if (choppy) {

			// Dx-FFT

			pingpong = 0;

			butterflyShader.bind();

			glBindImageTexture(0, twiddleFactors.getTexture().getId(), 0, false, 0, GL_READ_WRITE, GL_RGBA32F);
			glBindImageTexture(1, hkt.getImageDxCoefficients().getId(), 0, false, 0, GL_READ_WRITE, GL_RGBA32F);
			glBindImageTexture(2, getPingpongTexture().getId(), 0, false, 0, GL_READ_WRITE, GL_RGBA32F);

			// 1D FFT horizontal
			for (int i = 0; i < log_2_N; i++) {
				butterflyShader.updateUniforms(pingpong, 0, i);
				glDispatchCompute(N / 16, N / 16, 1);
				glFinish();
				pingpong++;
				pingpong %= 2;
			}

			// 1D FFT vertical
			for (int j = 0; j < log_2_N; j++) {
				butterflyShader.updateUniforms(pingpong, 1, j);
				glDispatchCompute(N / 16, N / 16, 1);
				glFinish();
				pingpong++;
				pingpong %= 2;
			}

			inversionShader.bind();
			inversionShader.updateUniforms(N, pingpong);
			glBindImageTexture(0, Dx.getId(), 0, false, 0, GL_READ_WRITE, GL_RGBA32F);
			glDispatchCompute(N / 16, N / 16, 1);
			glFinish();

			// Dz-FFT

			pingpong = 0;

			butterflyShader.bind();

			glBindImageTexture(0, twiddleFactors.getTexture().getId(), 0, false, 0, GL_READ_WRITE, GL_RGBA32F);
			glBindImageTexture(1, hkt.getImageDzCoefficients().getId(), 0, false, 0, GL_READ_WRITE, GL_RGBA32F);
			glBindImageTexture(2, getPingpongTexture().getId(), 0, false, 0, GL_READ_WRITE, GL_RGBA32F);

			// 1D FFT horizontal
			for (int i = 0; i < log_2_N; i++) {
				butterflyShader.updateUniforms(pingpong, 0, i);
				glDispatchCompute(N / 16, N / 16, 1);
				glFinish();
				pingpong++;
				pingpong %= 2;
			}

			// 1D FFT vertical
			for (int j = 0; j < log_2_N; j++) {
				butterflyShader.updateUniforms(pingpong, 1, j);
				glDispatchCompute(N / 16, N / 16, 1);
				glFinish();
				pingpong++;
				pingpong %= 2;
			}

			inversionShader.bind();
			inversionShader.updateUniforms(N, pingpong);
			glBindImageTexture(0, Dz.getId(), 0, false, 0, GL_READ_WRITE, GL_RGBA32F);
			glDispatchCompute(N / 16, N / 16, 1);
			glFinish();
		}

		t += (System.currentTimeMillis() - systemTime) * t_delta;
		systemTime = System.currentTimeMillis();
	}
}