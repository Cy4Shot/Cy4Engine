package editor.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONTokener;

public class LangConfig {

	private Map<String, String> languageTranslator;

	public LangConfig() {
		languageTranslator = new HashMap<String, String>();
	}

	public void loadFile(String file) {
		InputStream is;
		try {
			is = new FileInputStream(file);
			JSONTokener tokener = new JSONTokener(is);
			JSONObject obj = new JSONObject(tokener);
			for (String key : obj.keySet()) {
				languageTranslator.put(key, obj.getString(key));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Map<String, String> getLanguageTranslator() {
		return languageTranslator;
	}

	public void setLanguageTranslator(Map<String, String> languageTranslator) {
		this.languageTranslator = languageTranslator;
	}

	public String get(String key) {
		return languageTranslator.getOrDefault(key, "Null");
	}
}
