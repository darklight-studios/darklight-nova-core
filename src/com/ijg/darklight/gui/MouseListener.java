package com.ijg.darklight.gui;

import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 * @author Isaac Grant
 * @author Lucas Nicodemus
 * @version .1
 *
 */

public class MouseListener implements java.awt.event.MouseListener {

	JButton button;
	GUI gui;
	
	/**
	 * 
	 * @param gui The gui to which this mouse listener belongs
	 * @param button The button that this mouse listener will listen to 
	 */
	public MouseListener(GUI gui, JButton button) {
		this.button = button;
		this.gui = gui;
		button.addMouseListener(this);
	}
	
	public void mouseClicked(MouseEvent e) {
		if (button.contains(e.getPoint())) {
			if (button.getActionCommand().equals("refresh")) {
				// Refresh button
				gui.engine.moduleHandler.checkAllVulnerabilities();
				gui.update();
			} else if (button.getActionCommand().equals("finish")) {
				// Finish button
				gui.engine.finishSession();
			}
		}
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

}
