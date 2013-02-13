package com.ijg.darklight.build;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.filechooser.FileFilter;

import com.ijg.darklight.build.panels.EPanels;

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

public class Utils {
	public static JButton genericButton(String content) {
		JButton button = new JButton(content);
		button.setSize(new Dimension(30, 15));
		return button;
	}
	
	public static void addPanelSwitchActionListener(JButton button, final EPanels panelToSwitch, final InstallerFrame parent) {
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.changePanel(panelToSwitch);
			}
		});
	}
	
	public static void addKillActionListener(JButton button, final InstallerFrame parent) {
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.kill();
			}
		});
	}
	
	public static FileFilter generateFileFilter(final String fileExtension) {
		return new FileFilter() {
			public String getDescription() {
				return fileExtension;
			}
			public boolean accept(File f) {
				try {
					if (f.getName().substring(f.getName().indexOf(".")).equals("." + fileExtension)) {
						return true;
					}
				} catch (Exception e) {}
				return false;
			}
		};
	}
}
