package modules.water;

import org.lwjgl.opengl.GL13;

import core.Cy4Engine;
import core.shaders.Shader;
import core.texturing.Texture2D;
import core.utils.ResourceLoader;

public class UnderWaterShader extends Shader{

	private static UnderWaterShader instance = null;
	
	public static UnderWaterShader getInstance() 
	{
	    if(instance == null) 
	    {
	    	instance = new UnderWaterShader();
	    }
	      return instance;
	}
	
	protected UnderWaterShader()
	{
		super();
		
		addComputeShader(ResourceLoader.loadShader("shaders/water/underwater.comp"));
		
		compileShader();
		
		addUniform("sceneDepthMap");
		addUniform("waterRefractionColor");
	}
	
	public void updateUniforms(Texture2D sceneDepthMap) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		sceneDepthMap.bind();
		setUniformi("sceneDepthMap", 0);
		setUniform("waterRefractionColor", Cy4Engine.getInstance().getEngine().getRenderingEngine().ocean.getConfig().getBaseColor());
	}
}