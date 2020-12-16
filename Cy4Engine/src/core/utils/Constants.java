package core.utils;

import core.math.Vec4f;

public class Constants {

	public static final long NANOSECOND = 1000000000;
	public static final float ZFAR = 10000.0f;
	public static final float ZNEAR = 0.1f;
	public static Vec4f ZEROPLANE = new Vec4f(0,0,0,0);
	
	public static final String RENDERER_COMPONENT = "renderer";
	public static final String WIREFRAME_RENDERER_COMPONENT = "wireframerenderer";
	
	public static final int CameraUniformBlockBinding = 51;
}
