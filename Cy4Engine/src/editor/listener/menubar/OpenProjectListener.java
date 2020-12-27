package editor.listener.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import editor.Editor;

public class OpenProjectListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

		// Get Project Path
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("."));
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileFilter(new FileNameExtensionFilter(Editor.getLanguage().get("dialog.engineSaveFile"), "cy4"));
		fc.setDialogTitle(Editor.getLanguage().get("dialog.selectProjectPath"));
		int ret = fc.showOpenDialog(null);
		if (ret == JFileChooser.APPROVE_OPTION) {

			// Load Path
			File projectFile = fc.getSelectedFile();
			Editor.getInstance().setProjectDataPath(projectFile.getPath());
		}
	}

}
