package engine.modules.water;

import static org.lwjgl.opengl.GL11.glFinish;
import static org.lwjgl.opengl.GL15.GL_READ_ONLY;
import static org.lwjgl.opengl.GL15.GL_WRITE_ONLY;
import static org.lwjgl.opengl.GL30.GL_RGBA16F;
import static org.lwjgl.opengl.GL42.glBindImageTexture;
import static org.lwjgl.opengl.GL43.glDispatchCompute;

import org.lwjgl.opengl.GL30;

import engine.core.kernel.Window;
import engine.core.texturing.Texture2D;

public class UnderWaterRenderer {
	
	private Texture2D underwaterSceneTexture;
	private UnderWaterShader underWaterShader;
	
	private Texture2D dudvMap;
	private Texture2D causticsMap;
	
	public UnderWaterRenderer() {
		underWaterShader = UnderWaterShader.getInstance();
		
		underwaterSceneTexture = new Texture2D(Window.getInstance().getWidth(),
				Window.getInstance().getHeight(),
				GL30.GL_RGBA16F);
		underwaterSceneTexture.bind();
		underwaterSceneTexture.bilinearFilter();
		
		dudvMap = new Texture2D("./res/textures/water/dudv/dudv1.jpg");
		dudvMap.bind();
		dudvMap.bilinearFilter();
		
		causticsMap = new Texture2D("./res/textures/water/caustics/caustics.jpg");
		causticsMap.bind();
		causticsMap.bilinearFilter();
		
//		GLContext.getResources().setUnderwaterCausticsMap(causticsMap);
//		GLContext.getResources().setUnderwaterDudvMap(dudvMap);
	}
	
	public void render(Texture2D sceneTexture, Texture2D sceneDepthMap) {
		
		underWaterShader.bind();
		glBindImageTexture(0, sceneTexture.getId(), 0, false, 0, GL_READ_ONLY, GL_RGBA16F);
		glBindImageTexture(1, underwaterSceneTexture.getId(), 0, false, 0, GL_WRITE_ONLY, GL_RGBA16F);
		underWaterShader.updateUniforms(sceneDepthMap);
		glDispatchCompute(Window.getInstance().getWidth()/8, Window.getInstance().getHeight()/8, 1);	
		glFinish();
	}

}