package modules.water;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import core.scene.GameObject;
import core.shaders.Shader;
import core.utils.Constants;
import core.utils.ResourceLoader;

public class WaterWireframeShader extends Shader {

	private static WaterWireframeShader instance = null;

	public static WaterWireframeShader getInstance() {
		if (instance == null) {
			instance = new WaterWireframeShader();
		}
		return instance;
	}

	protected WaterWireframeShader() {
		super();

		addVertexShader(ResourceLoader.loadShader("shaders/water/water.vert"));
		addTessellationControlShader(ResourceLoader.loadShader("shaders/water/water.tesc"));
		addTessellationEvaluationShader(ResourceLoader.loadShader("shaders/water/water.tese"));
		addGeometryShader(ResourceLoader.loadShader("shaders/water/water.wireframe.geom"));
		addFragmentShader(ResourceLoader.loadShader("shaders/water/water.wireframe.frag"));
		compileShader();

		addUniform("Dy");
		addUniform("Dx");
		addUniform("Dz");
		addUniform("motion");
		addUniform("wind");

		addUniformBlock("Camera");
	}

	public void updateUniforms(GameObject object) {
		bindUniformBlock("Camera", Constants.CameraUniformBlockBinding);

		Water water = (Water) object;

		setUniformf("motion", water.getT_motion());
		setUniform("wind", water.getConfig().getWindDirection());

		glActiveTexture(GL_TEXTURE0);
		water.getFft().getDy().bind();
		setUniformi("Dy", 0);
		glActiveTexture(GL_TEXTURE1);
		water.getFft().getDx().bind();
		setUniformi("Dx", 1);
		glActiveTexture(GL_TEXTURE2);
		water.getFft().getDz().bind();
		setUniformi("Dz", 2);
	}
}