package com.ijg.ijgsec.gui;

import java.awt.event.MouseEvent;
import java.io.IOException;

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
				gui.engine.assessModule.report();
				gui.update();
			} else if (button.getActionCommand().equals("finish")) {
				gui.engine.finishSession();
			} else if (button.getActionCommand().equals("view")) {
				try {
					Runtime.getRuntime().exec("pythonw VulnView.pyw " + gui.engine.progressFile);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

}
