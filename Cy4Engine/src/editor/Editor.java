package editor;

import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.json.JSONObject;
import org.json.JSONTokener;

import editor.config.LangConfig;
import editor.config.ThemeConfig;
import editor.listener.menubar.NewProjectListener;
import editor.listener.menubar.OpenProjectListener;
import editor.listener.menubar.PreferencesActionListener;
import editor.listener.menubar.ProjectSettingsListener;
import editor.listener.menubar.QuitListener;
import editor.listener.menubar.RunProjectListener;
import editor.listener.menubar.SaveProjectListener;
import editor.panel.GamePanel;
import editor.panel.ProjectPanel;

@SuppressWarnings("serial")
public class Editor extends JFrame {

	public static Editor instance;

	public static Editor getInstance() {
		return instance;
	}
	
	private static LangConfig language;
	private static ThemeConfig theme;

	JFrame f;

	private String projectDataPath = null;
	private JSONObject projectDataJSON = new JSONObject();

	GamePanel gamepanel;
	JPanel consolepanel;
	ProjectPanel projectpanel;

	public static void main(String[] args) {

		// Initalize all components
		language = new LangConfig("./editor/lang/en_us.json");
		theme = new ThemeConfig("./editor/theme/dark_default.json");
		instance = new Editor();
	}

	public Editor() {

		// Create menubar: File
		JMenuBar mb = new JMenuBar();
		JMenu file = new JMenu(language.get("menubar.file"));
		JMenuItem newProject = new JMenuItem(language.get("menubar.newProject"));
		JMenuItem openProject = new JMenuItem(language.get("menubar.openProject"));
		JMenuItem saveProject = new JMenuItem(language.get("menubar.saveProject"));
		JMenuItem preferences = new JMenuItem(language.get("menubar.preferences"));
		JMenuItem quit = new JMenuItem(language.get("menubar.quit"));
		newProject.addActionListener(new NewProjectListener());
		openProject.addActionListener(new OpenProjectListener());
		saveProject.addActionListener(new SaveProjectListener());
		preferences.addActionListener(new PreferencesActionListener());
		quit.addActionListener(new QuitListener());
		file.add(newProject);
		file.add(openProject);
		file.add(saveProject);
		file.addSeparator();
		file.add(preferences);
		file.add(quit);

		// Create menubar: Project
		JMenu project = new JMenu(language.get("menubar.project"));
		JMenuItem runProject = new JMenuItem(language.get("menubar.runProject"));
		JMenuItem projectSettings = new JMenuItem(language.get("menubar.projectSettings"));
		runProject.addActionListener(new RunProjectListener());
		projectSettings.addActionListener(new ProjectSettingsListener());
		project.add(runProject);
		project.addSeparator();
		project.add(projectSettings);
		mb.add(file);
		mb.add(project);

		// Create Splitplanes
		gamepanel = new GamePanel(new GridLayout(1, 1));
		consolepanel = new JPanel();
		projectpanel = new ProjectPanel();
		JSplitPane editorPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, gamepanel, consolepanel);
		editorPane.setResizeWeight(0.9);
		JSplitPane mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, projectpanel, editorPane);
		mainPane.setResizeWeight(0.07);

		projectpanel.init();
		gamepanel.init();

		// Set Window Properties
		setTitle(language.get("window.title") + " - " + language.get("window.editor"));
		setContentPane(mainPane);
		setJMenuBar(mb);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1920, 1080);
		setVisible(true);
	}

	public void update() {
		if (projectDataJSON.isEmpty())
			setTitle(language.get("window.title") + " - " + language.get("window.editor"));
		else
			setTitle(language.get("window.title") + " - " + language.get("window.editor") + " | "
					+ projectDataJSON.getString("projectName"));
		gamepanel.update();
		projectpanel.update();
	}

	private JSONObject readProjectJSON() {
		InputStream is;
		try {
			is = new FileInputStream(projectDataPath);
			JSONTokener tokener = new JSONTokener(is);
			return new JSONObject(tokener);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, language.get("dialog.projectNoExist"),
					language.get("error.openProjectError"), JOptionPane.ERROR_MESSAGE);
		}

		return null;
	}

	public static LangConfig getLanguage() {
		return language;
	}

	public static void setLanguage(LangConfig language) {
		Editor.language = language;
		instance = new Editor();
	}

	public static ThemeConfig getTheme() {
		return theme;
	}

	public static void setTheme(ThemeConfig theme) {
		Editor.theme = theme;
		instance = new Editor();
	}

	public String getProjectDataPath() {
		return projectDataPath;
	}

	public void setProjectDataPath(String projectDataPath) {
		this.projectDataPath = projectDataPath;
		this.setProjectDataJSON(readProjectJSON());
		this.update();
	}

	public JSONObject getProjectDataJSON() {
		return projectDataJSON;
	}

	public void setProjectDataJSON(JSONObject projectDataJSON) {
		this.projectDataJSON = projectDataJSON;
	}

}
