package com.ijg.darklight.sdk.core.gui;

import java.awt.event.MouseEvent;
import javax.swing.JButton;

/*
 * Copyright (C) 2013  Isaac Grant
 * 
 * This file is part of the Darklight Nova Core.
 *  
 * Darklight Nova Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Darklight Nova Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the Darklight Nova Core.  If not, see <http://www.gnu.org/licenses/>.
 */

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
