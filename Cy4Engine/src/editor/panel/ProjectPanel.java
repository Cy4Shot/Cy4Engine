package editor.panel;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.JSONObject;

import editor.Editor;

@SuppressWarnings("serial")
public class ProjectPanel extends JPanel implements CustomPanel {

	JLabel title;

	@Override
	public void init() {
		title = new JLabel(Editor.getLanguage().get("panel.project.defaultTitle"));
		title.setFont(Editor.getTheme().getFontBold().deriveFont(20f));
		add(title);
	}

	public void update() {
		JSONObject project = Editor.getInstance().getProjectDataJSON();

		if (project.isEmpty()) {
			title.setText(Editor.getLanguage().get("panel.project.defaultTitle"));
		} else {
			title.setText(project.getString("projectName") + " " + Editor.getLanguage().get("panel.project.settings"));
		}
	}
}
