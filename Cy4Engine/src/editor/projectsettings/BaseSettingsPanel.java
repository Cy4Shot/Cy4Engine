package editor.projectsettings;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import editor.Editor;
import editor.util.SpringUtilities;

@SuppressWarnings("serial")
public class BaseSettingsPanel extends JPanel {

	public String name;

	public BaseSettingsPanel(String name) {
		super(new SpringLayout());

		this.name = name;

		Map<String, JComponent> settings = getSettings();

		for (Entry<String, JComponent> e : settings.entrySet()) {
			JLabel l = new JLabel(Editor.getLanguage().get(e.getKey()));
			l.setFont(Editor.getTheme().getFontRegular().deriveFont(12f));
			JLabel l2 = new JLabel("hello");
			l2.setFont(Editor.getTheme().getFontRegular().deriveFont(10f));
			l2.setForeground(Color.RED);
			this.add(l);
			e.getValue().setMaximumSize(
					new Dimension(e.getValue().getMaximumSize().width, e.getValue().getPreferredSize().height));
			l.setLabelFor(e.getValue());
			this.add(e.getValue());
			this.add(new JLabel());
			this.add(l2);
		}

		SpringUtilities.makeCompactGrid(this, 2, 2, 7, 7, 7, 7);
	}

	public Map<String, JComponent> getSettings() {
		return new HashMap<String, JComponent>();
	}
}
