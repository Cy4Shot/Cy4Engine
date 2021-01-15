package editor.projectsettings;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

import editor.Editor;

@SuppressWarnings("serial")
public class InformationSettingsPanel extends BaseSettingsPanel {

	public InformationSettingsPanel(String name) {
		super(name);
	}
	
	@Override
	public Map<String, JComponent> getSettings() {
		Map<String, JComponent> s = new HashMap<String, JComponent>(ABORT);
		s.put("settings.info.projectDisplayName", new SingleLineInputField(Editor.getInstance().getProjectDataJSON().getString("projectName"), false, 20));
		return s;
	}

}
