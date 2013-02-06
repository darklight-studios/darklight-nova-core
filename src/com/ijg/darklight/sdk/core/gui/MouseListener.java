package com.ijg.darklight.sdk.core.gui;

import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class MouseListener implements java.awt.event.MouseListener {

	JButton button;
	GUI gui;
	
	public MouseListener(GUI gui, JButton button) {
		this.button = button;
		this.gui = gui;
		button.addMouseListener(this);
	}
	
	public void mouseClicked(MouseEvent e) {
		if (button.contains(e.getPoint())) {
			if (button.getActionCommand().equals("refresh")) {
				gui.engine.update();
				gui.update();
			} else if (button.getActionCommand().equals("finish")) {
				gui.engine.finishSession();
			}
		}
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

}
