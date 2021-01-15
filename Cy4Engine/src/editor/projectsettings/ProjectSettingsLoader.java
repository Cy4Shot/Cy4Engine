package editor.projectsettings;

import java.util.ArrayList;
import java.util.List;

import editor.Editor;

public class ProjectSettingsLoader {
	
	public static List<BaseSettingsPanel> panels = new ArrayList<BaseSettingsPanel>();
	
	public static void init() {
		panels.clear();
		panels.add(new InformationSettingsPanel(Editor.getLanguage().get("settings.info")));
	}
}
