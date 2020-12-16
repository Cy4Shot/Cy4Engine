package editor.listener.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import core.Cy4Engine;

public class RunProjectListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		new Cy4Engine(new String[] {});
	}

}
