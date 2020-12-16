package modules.fft;

import core.shaders.Shader;
import core.utils.ResourceLoader;

public class FFTInversionShader extends Shader {
private static FFTInversionShader instance = null;
	
	public static FFTInversionShader getInstance() 
	{
	    if(instance == null) 
	    {
	    	instance = new FFTInversionShader();
	    }
	    return instance;
	}
		
	protected FFTInversionShader()
	{
		super();
			
		addComputeShader(ResourceLoader.loadShader("shaders/fft/inversion.comp"));
		compileShader();
			
		addUniform("pingpong");
		addUniform("N");
	}
		
	public void updateUniforms(int N, int pingpong)
	{
		setUniformi("N", N);
		setUniformi("pingpong", pingpong);
	}
}