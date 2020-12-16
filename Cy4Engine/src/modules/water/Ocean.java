package modules.water;

import core.math.Vec3f;
import core.math.Vec4f;
import core.utils.Constants;

public class Ocean extends Water {

	public Ocean() {

		super(16, WaterShader.getInstance(), WaterWireframeShader.getInstance());

		getTransform().setScaling(Constants.ZFAR * 1.95f, 1, Constants.ZFAR * 1.95f);
		getTransform().setTranslation(new Vec3f(-Constants.ZFAR * 1.95f / 2, 100, -Constants.ZFAR * 1.95f / 2));

		setClip_offset(4);
		setClipplane(new Vec4f(0, -1, 0, getTransform().getTranslation().getY() + 20));

		initShaderBuffer();
	}
}