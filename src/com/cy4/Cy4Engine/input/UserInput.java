package com.cy4.Cy4Engine.input;

import com.cy4.Cy4Engine.render.Display;

public class UserInput {

	public Mouse mouse;
	public Keyboard keyboard;

	public UserInput(Display d) {
		this(d, new Mouse(), new Keyboard());
	}

	public UserInput(Display d, Mouse m, Keyboard k) {
		this.mouse = m;
		this.keyboard = k;
		
		d.addMouseListener(m);
		d.addMouseMotionListener(m);
		d.addMouseWheelListener(m);
		d.addKeyListener(k);
	}
}
