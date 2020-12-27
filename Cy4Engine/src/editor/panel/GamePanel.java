package editor.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.json.JSONObject;

import editor.Editor;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements CustomPanel {
	
	JLabel centreText;
	
	public GamePanel(GridLayout layout) {
		super(layout);
	}

	@Override
	public void init() {
		centreText = new JLabel(Editor.getLanguage().get("panel.game.noProjectOpen"), SwingConstants.CENTER);
		centreText.setFont(Editor.getTheme().getFontItalic().deriveFont(20f));
		add(centreText, BorderLayout.PAGE_END);
	}

	public void update() {
		JSONObject project = Editor.getInstance().getProjectDataJSON();

		if (project.isEmpty()) {
			centreText.setText(Editor.getLanguage().get("panel.game.noProjectOpen"));
		} else {
			centreText.setText(Editor.getLanguage().get("panel.game.selectEdit"));
		}
	}
}
