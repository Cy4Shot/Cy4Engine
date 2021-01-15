package editor.listener.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import editor.Editor;
import editor.projectsettings.ProjectSettingsWindow;

public class ProjectSettingsListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if (Editor.getInstance().isProjectOpen())
			ProjectSettingsWindow.createAndShowGUI();
		else
			JOptionPane.showMessageDialog(null, Editor.getLanguage().get("dialog.noProjectOpen"),
					Editor.getLanguage().get("error.projectError"), JOptionPane.ERROR_MESSAGE);
	}

}
