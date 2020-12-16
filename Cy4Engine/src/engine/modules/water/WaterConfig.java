package engine.modules.water;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import engine.core.math.Vec2f;
import engine.core.math.Vec3f;

public class WaterConfig {

	private int N;
	private int L;
	private float amplitude;
	private Vec2f windDirection;
	private float windSpeed;
	private float alignment;
	private float capillarWavesSupression;
	private float displacementScale;
	private float choppiness;
	private int tessellationFactor;
	private float tessellationShift;
	private float tessellationSlope;
	private int highDetailRange;
	private int uvScale;
	private float specularFactor;
	private float specularAmplifier;
	private boolean diffuse;
	private float emission;
	private float kReflection;
	private float kRefraction;
	private float distortion;
	private float fresnelFactor;
	private float waveMotion;
	private float normalStrength;
	private float t_delta;
	private boolean choppy;
	private Vec3f baseColor;
	private float reflectionBlendFactor;
	private float capillarStrength;
	private float capillarDownsampling;
	private float dudvDownsampling;
	private float underwaterBlur;

	public void loadFile(String file) {
		Properties properties = new Properties();
		try {
			InputStream stream = WaterConfig.class.getClassLoader().getResourceAsStream(file);
			properties.load(stream);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		N = Integer.valueOf(properties.getProperty("fft.resolution"));
		L = Integer.valueOf(properties.getProperty("fft.L"));
		amplitude = Float.valueOf(properties.getProperty("fft.amplitude"));
		windDirection = new Vec2f(Float.valueOf(properties.getProperty("wind.x")),
				Float.valueOf(properties.getProperty("wind.y"))).normalize();
		windSpeed = Float.valueOf(properties.getProperty("wind.speed"));
		alignment = Float.valueOf(properties.getProperty("alignment"));
		capillarWavesSupression = Float.valueOf(properties.getProperty("fft.capillarwavesSuppression"));
		displacementScale = Float.valueOf(properties.getProperty("displacementScale"));
		choppiness = Float.valueOf(properties.getProperty("choppiness"));
		distortion = Float.valueOf(properties.getProperty("distortion_delta"));
		waveMotion = Float.valueOf(properties.getProperty("wavemotion"));
		uvScale = Integer.valueOf(properties.getProperty("uvScale"));
		tessellationFactor = Integer.valueOf(properties.getProperty("tessellationFactor"));
		tessellationSlope = Float.valueOf(properties.getProperty("tessellationSlope"));
		tessellationShift = Float.valueOf(properties.getProperty("tessellationShift"));
		specularFactor = Float.valueOf(properties.getProperty("specular.factor"));
		specularAmplifier = Float.valueOf(properties.getProperty("specular.amplifier"));
		emission = Float.valueOf(properties.getProperty("emission.factor"));
		kReflection = Float.valueOf(properties.getProperty("kReflection"));
		kRefraction = Float.valueOf(properties.getProperty("kRefraction"));
		normalStrength = Float.valueOf(properties.getProperty("normalStrength"));
		highDetailRange = Integer.valueOf(properties.getProperty("highDetailRange"));
		t_delta = Float.valueOf(properties.getProperty("t_delta"));
		choppy = Boolean.valueOf(properties.getProperty("choppy"));
		fresnelFactor = Float.valueOf(properties.getProperty("fresnel.factor"));
		reflectionBlendFactor = Float.valueOf(properties.getProperty("reflection.blendfactor"));
		baseColor = new Vec3f(Float.valueOf(properties.getProperty("water.basecolor.x")),
				Float.valueOf(properties.getProperty("water.basecolor.y")),
				Float.valueOf(properties.getProperty("water.basecolor.z")));
		capillarStrength = Float.valueOf(properties.getProperty("capillar.strength"));
		capillarDownsampling = Float.valueOf(properties.getProperty("capillar.downsampling"));
		dudvDownsampling = Float.valueOf(properties.getProperty("dudv.downsampling"));
		underwaterBlur = Float.valueOf(properties.getProperty("underwater.blurfactor"));
		diffuse = Integer.valueOf(properties.getProperty("diffuse.enable")) == 0 ? false : true;
	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

	public int getL() {
		return L;
	}

	public void setL(int l) {
		L = l;
	}

	public float getAmplitude() {
		return amplitude;
	}

	public void setAmplitude(float amplitude) {
		this.amplitude = amplitude;
	}

	public Vec2f getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(Vec2f windDirection) {
		this.windDirection = windDirection;
	}

	public float getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(float windSpeed) {
		this.windSpeed = windSpeed;
	}

	public float getAlignment() {
		return alignment;
	}

	public void setAlignment(float alignment) {
		this.alignment = alignment;
	}

	public float getCapillarWavesSupression() {
		return capillarWavesSupression;
	}

	public void setCapillarWavesSupression(float capillarWavesSupression) {
		this.capillarWavesSupression = capillarWavesSupression;
	}

	public float getDisplacementScale() {
		return displacementScale;
	}

	public void setDisplacementScale(float displacementScale) {
		this.displacementScale = displacementScale;
	}

	public float getChoppiness() {
		return choppiness;
	}

	public void setChoppiness(float choppiness) {
		this.choppiness = choppiness;
	}

	public int getTessellationFactor() {
		return tessellationFactor;
	}

	public void setTessellationFactor(int tessellationFactor) {
		this.tessellationFactor = tessellationFactor;
	}

	public float getTessellationShift() {
		return tessellationShift;
	}

	public void setTessellationShift(float tessellationShift) {
		this.tessellationShift = tessellationShift;
	}

	public float getTessellationSlope() {
		return tessellationSlope;
	}

	public void setTessellationSlope(float tessellationSlope) {
		this.tessellationSlope = tessellationSlope;
	}

	public int getHighDetailRange() {
		return highDetailRange;
	}

	public void setHighDetailRange(int highDetailRange) {
		this.highDetailRange = highDetailRange;
	}

	public int getUvScale() {
		return uvScale;
	}

	public void setUvScale(int uvScale) {
		this.uvScale = uvScale;
	}

	public float getSpecularFactor() {
		return specularFactor;
	}

	public void setSpecularFactor(float specularFactor) {
		this.specularFactor = specularFactor;
	}

	public float getSpecularAmplifier() {
		return specularAmplifier;
	}

	public void setSpecularAmplifier(float specularAmplifier) {
		this.specularAmplifier = specularAmplifier;
	}

	public boolean isDiffuse() {
		return diffuse;
	}

	public void setDiffuse(boolean diffuse) {
		this.diffuse = diffuse;
	}

	public float getEmission() {
		return emission;
	}

	public void setEmission(float emission) {
		this.emission = emission;
	}

	public float getkReflection() {
		return kReflection;
	}

	public void setkReflection(float kReflection) {
		this.kReflection = kReflection;
	}

	public float getkRefraction() {
		return kRefraction;
	}

	public void setkRefraction(float kRefraction) {
		this.kRefraction = kRefraction;
	}

	public float getDistortion() {
		return distortion;
	}

	public void setDistortion(float distortion) {
		this.distortion = distortion;
	}

	public float getFresnelFactor() {
		return fresnelFactor;
	}

	public void setFresnelFactor(float fresnelFactor) {
		this.fresnelFactor = fresnelFactor;
	}

	public float getWaveMotion() {
		return waveMotion;
	}

	public void setWaveMotion(float waveMotion) {
		this.waveMotion = waveMotion;
	}

	public float getNormalStrength() {
		return normalStrength;
	}

	public void setNormalStrength(float normalStrength) {
		this.normalStrength = normalStrength;
	}

	public float getT_delta() {
		return t_delta;
	}

	public void setT_delta(float t_delta) {
		this.t_delta = t_delta;
	}

	public boolean isChoppy() {
		return choppy;
	}

	public void setChoppy(boolean choppy) {
		this.choppy = choppy;
	}

	public Vec3f getBaseColor() {
		return baseColor;
	}

	public void setBaseColor(Vec3f baseColor) {
		this.baseColor = baseColor;
	}

	public float getReflectionBlendFactor() {
		return reflectionBlendFactor;
	}

	public void setReflectionBlendFactor(float reflectionBlendFactor) {
		this.reflectionBlendFactor = reflectionBlendFactor;
	}

	public float getCapillarStrength() {
		return capillarStrength;
	}

	public void setCapillarStrength(float capillarStrength) {
		this.capillarStrength = capillarStrength;
	}

	public float getCapillarDownsampling() {
		return capillarDownsampling;
	}

	public void setCapillarDownsampling(float capillarDownsampling) {
		this.capillarDownsampling = capillarDownsampling;
	}

	public float getDudvDownsampling() {
		return dudvDownsampling;
	}

	public void setDudvDownsampling(float dudvDownsampling) {
		this.dudvDownsampling = dudvDownsampling;
	}

	public float getUnderwaterBlur() {
		return underwaterBlur;
	}

	public void setUnderwaterBlur(float underwaterBlur) {
		this.underwaterBlur = underwaterBlur;
	}
}