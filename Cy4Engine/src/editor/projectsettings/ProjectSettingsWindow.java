package editor.projectsettings;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;

import editor.Editor;

public class ProjectSettingsWindow {
	final static int extraWindowWidth = 100;

	public void addComponentToPane(Container pane) {
		JTabbedPane main = new JTabbedPane();

		ProjectSettingsLoader.init();
		for (BaseSettingsPanel p : ProjectSettingsLoader.panels) {
			main.addTab(p.name, new JScrollPane(p, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
		}

		pane.add(main, BorderLayout.CENTER);
	}

	public static void createAndShowGUI() {
		JFrame frame = new JFrame(Editor.getLanguage().get("window.projectSettings"));
		frame.setPreferredSize(new Dimension(500, 500));
		frame.setLocationRelativeTo(null);
		
		ProjectSettingsWindow settings = new ProjectSettingsWindow();
		settings.addComponentToPane(frame.getContentPane());
		
		frame.pack();
		frame.setVisible(true);
	}
}
