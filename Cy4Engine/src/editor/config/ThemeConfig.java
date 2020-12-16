package editor.config;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.swing.UIManager;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class ThemeConfig {

	public ThemeConfig(String file) {
		InputStream is;
		try {
			is = new FileInputStream(file);
			JSONTokener tokener = new JSONTokener(is);
			JSONObject obj = new JSONObject(tokener);

			// Load Colors
			JSONArray colors = obj.getJSONArray("colors");
			for (int i = 0; i < colors.length(); i++) {
				JSONObject color = colors.getJSONObject(i);
				UIManager.put(color.getString("name"), hex2Rgb(color.getString("color")));
			}

			// Load theme
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		;
	}

	public static Color hex2Rgb(String colorStr) {
		return new Color(Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16),
				Integer.valueOf(colorStr.substring(5, 7), 16));
	}
}
