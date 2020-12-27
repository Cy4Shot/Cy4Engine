package editor.config;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import editor.Editor;

public class ThemeConfig {

	private Font fontRegular;
	private Font fontBold;
	private Font fontItalic;
	private Font fontBoldItalic;

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

			// Font
			JSONObject font = obj.getJSONObject("font");
			try {
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

				fontRegular = Font.createFont(Font.TRUETYPE_FONT, new File(font.getString("regular")));
				fontBold = Font.createFont(Font.TRUETYPE_FONT, new File(font.getString("bold")));
				fontItalic = Font.createFont(Font.TRUETYPE_FONT, new File(font.getString("italic")));
				fontBoldItalic = Font.createFont(Font.TRUETYPE_FONT, new File(font.getString("bolditalic")));

				ge.registerFont(fontRegular);
				ge.registerFont(fontBold);
				ge.registerFont(fontItalic);
				ge.registerFont(fontBoldItalic);

			} catch (IOException | FontFormatException e) {
			}

			// Load theme
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, Editor.getLanguage().get("dialog.themeNotFound"),
					Editor.getLanguage().get("error.themeLoadingError"), JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.hashCode(), Editor.getLanguage().get("error.themeLoadingError"),
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		;
	}

	public static Color hex2Rgb(String colorStr) {
		return new Color(Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16),
				Integer.valueOf(colorStr.substring(5, 7), 16));
	}

	public Font getFontRegular() {
		return fontRegular;
	}

	public Font getFontBold() {
		return fontBold;
	}

	public Font getFontItalic() {
		return fontItalic;
	}

	public Font getFontBoldItalic() {
		return fontBoldItalic;
	}
}
