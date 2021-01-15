package editor.projectsettings;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextArea;

import editor.Editor;
import editor.util.JTextFieldLimit;

@SuppressWarnings("serial")
public class SingleLineInputField extends JTextArea {

	public boolean canBeEmpty;

	public SingleLineInputField(String defaultIn, boolean canBeEmpty, int maxLength) {
		super(defaultIn, 1, 10);

		this.canBeEmpty = canBeEmpty;
		this.setFont(Editor.getTheme().getFontRegular().deriveFont(12f));

		this.setDocument(new JTextFieldLimit(maxLength));
		this.getDocument().putProperty("filterNewlines", Boolean.TRUE);

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				SingleLineInputField t = (SingleLineInputField) e.getSource();
				System.out.println(t.getText());
			}
		});
	}
}
