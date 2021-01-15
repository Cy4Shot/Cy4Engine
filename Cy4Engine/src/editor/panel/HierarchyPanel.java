package editor.panel;

import javax.swing.JLabel;
import javax.swing.JPanel;

import editor.Editor;

@SuppressWarnings("serial")
public class HierarchyPanel extends JPanel implements CustomPanel {

	JLabel title;

	@Override
	public void init() {
		title = new JLabel(Editor.getLanguage().get("panel.project.hierarchy"));
		title.setFont(Editor.getTheme().getFontBold().deriveFont(20f));
		add(title);
	}

	public void update() {
	}
}
