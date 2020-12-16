package editor;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import editor.config.LangConfig;
import editor.config.ThemeConfig;
import editor.listener.menubar.NewProjectListener;
import editor.listener.menubar.OpenProjectListener;
import editor.listener.menubar.QuitListener;
import editor.listener.menubar.RunProjectListener;
import editor.listener.menubar.SaveProjectListener;

@SuppressWarnings("serial")
public class Editor extends JFrame  {
	
	public static Editor instance;

	JFrame f;
	LangConfig language;

	public static void main(String[] args) {
		instance = new Editor();
	}

	// Constructor
	@SuppressWarnings("deprecation")
	public Editor() {
		//Initialize all classes
		new ThemeConfig("./editor/theme/dark_default.json");
		language = new LangConfig();
		language.loadFile("./editor/lang/en_us.json");
		
		f = new JFrame(language.get("window.title") + " - " + language.get("window.editor"));
		
		JMenuBar mb = new JMenuBar();
		
		JMenu file = new JMenu(language.get("menubar.file"));
		JMenuItem newProject = new JMenuItem(language.get("menubar.newProject"));
		JMenuItem openProject = new JMenuItem(language.get("menubar.openProject"));
		JMenuItem saveProject = new JMenuItem(language.get("menubar.saveProject"));
		JMenuItem quit = new JMenuItem(language.get("menubar.quit"));
		newProject.addActionListener(new NewProjectListener());
		openProject.addActionListener(new OpenProjectListener());
		saveProject.addActionListener(new SaveProjectListener());
		quit.addActionListener(new QuitListener());
		file.add(newProject);
		file.add(openProject);
		file.add(saveProject);
		file.addSeparator();
		file.add(quit);

		JMenu editor = new JMenu(language.get("menubar.editor"));
		JMenuItem runProject = new JMenuItem(language.get("menubar.runProject"));
		runProject.addActionListener(new RunProjectListener());
		editor.add(runProject);
		mb.add(file);
		mb.add(editor);

		f.setJMenuBar(mb);
		f.setSize(500, 500);
		f.show();
	}
}
