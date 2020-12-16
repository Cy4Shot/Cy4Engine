package core.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceLoader {

	public static String loadShader(String fileName) {
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;

		try {
			shaderReader = new BufferedReader(new FileReader("./res/" + fileName));
			String line;
			while ((line = shaderReader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}

			shaderReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		return shaderSource.toString();
	}

	public static String loadShader(String fileName, String lib) {
		String shadersource = loadShader(fileName);

		InputStream is = ResourceLoader.class.getClassLoader().getResourceAsStream("shader/" + lib);
		StringBuilder shaderlibSource = new StringBuilder();
		BufferedReader shaderReader = null;

		try {
			shaderReader = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = shaderReader.readLine()) != null) {
				shaderlibSource.append(line).append("\n");
			}

			shaderReader.close();
		} catch (Exception e) {
			System.err.println("Unable to load file [" + fileName + "]!");
			e.printStackTrace();
			System.exit(1);
		}

		// replace const paramter in glsl library
//		String vlib = shaderlibSource.toString().replaceFirst("#var_shadow_map_resolution",
//				Integer.toString(BaseContext.getConfig().getShadowMapResolution()));
		String vlib = shaderlibSource.toString();

		return shadersource.replaceFirst("#lib.glsl", vlib);
	}
}
