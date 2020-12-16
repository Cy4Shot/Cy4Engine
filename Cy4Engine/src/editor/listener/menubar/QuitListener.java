package editor.listener.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuitListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}

}
