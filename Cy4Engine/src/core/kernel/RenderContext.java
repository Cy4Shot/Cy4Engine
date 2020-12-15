package core.kernel;

public class RenderContext {
	private static RenderContext instance = null;

	public static RenderContext getInstance() {

		if (instance == null) {

			instance = new RenderContext();
		}
		return instance;
	}

	public boolean isWireframe() {
		return wireframe;
	}

	public void setWireframe(boolean wireframe) {
		this.wireframe = wireframe;
	}

	private boolean wireframe;
}
