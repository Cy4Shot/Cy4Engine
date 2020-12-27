package editor.listener.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.json.JSONObject;

import editor.Editor;

public class NewProjectListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

		// Get Project Name
		String name = JOptionPane.showInputDialog(null, Editor.getLanguage().get("dialog.enterProjectName"),
				Editor.getLanguage().get("menubar.newProject"), JOptionPane.PLAIN_MESSAGE);

		// Check if cancelled.
		if (name == null)
			return;

		// Check if valid name
		if (name.isEmpty()) {
			JOptionPane.showMessageDialog(null, Editor.getLanguage().get("dialog.invalidProjectName"),
					Editor.getLanguage().get("error.newProjectError"), JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Get Project Path
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("."));
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setDialogTitle(Editor.getLanguage().get("dialog.selectProjectPath"));

		int ret = fc.showOpenDialog(null);
		if (ret == JFileChooser.APPROVE_OPTION) {

			// Create file at path
			File projectFolder = fc.getSelectedFile();
			String newPath = projectFolder.getPath() + "\\" + name.replace(" ", "_") + "\\";
			try {

				Files.createDirectories(Paths.get(newPath));
				Files.createFile(Paths.get(newPath + name.replace(" ", "_") + ".cy4"));
			} catch (FileAlreadyExistsException e1) {
				JOptionPane.showMessageDialog(null, Editor.getLanguage().get("dialog.projectAlreadyExists"),
						Editor.getLanguage().get("error.newProjectError"), JOptionPane.ERROR_MESSAGE);
				return;
			} catch (IOException e2) {
				return;
			}

			// Construct JSON data.
			JSONObject obj = new JSONObject();
			obj.put("projectName", name);
			obj.put("projectFormattedName", name.replace(" ", "_"));
			obj.put("projectPath", newPath);
			obj.put("projectDataPath", newPath + name.replace(" ", "_") + ".cy4");

			// Write JSON data to file.
			try {
				FileWriter myWriter = new FileWriter(newPath + name.replace(" ", "_") + ".cy4");
				myWriter.write(obj.toString());
				myWriter.close();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, e.hashCode(), Editor.getLanguage().get("error.newProjectError"),
						JOptionPane.ERROR_MESSAGE);
			}

			// Update Project
			Editor.getInstance().setProjectDataPath(newPath + name.replace(" ", "_") + ".cy4");

		}
	}

}
