package com.cy4.Cy4Engine.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseInput implements MouseListener, MouseMotionListener, MouseWheelListener {
	
	private int mouseX = -1;
	private int mouseY = -1;
	private int mouseB = -1;
	
	public int getMouseX() {
		return this.mouseX;
	}
	
	public int getMouseY() {
		return this.mouseY;
	}
	
	public ClickType getMouseButton() {
		return getButtonType(this.mouseB);
	}
	
	private ClickType getButtonType(int id) {
		switch(id) {
		case 1:
			return ClickType.LEFT_CLICK;
		case 2:
			return ClickType.SCROLL_CLICK;
		case 3:
			return ClickType.RIGHT_CLICK;
		case 4:
			return ClickType.MOUSE_3;
		case 5:
			return ClickType.MOUSE_4;
		default:
			return ClickType.UNKNOWN;
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		this.mouseX = e.getX();
		this.mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.mouseX = e.getX();
		this.mouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.mouseB = -1;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.mouseB = e.getButton();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.mouseB = -1;
	}
}
