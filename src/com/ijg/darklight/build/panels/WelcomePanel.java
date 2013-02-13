package com.ijg.darklight.build.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ijg.darklight.build.InstallerFrame;
import com.ijg.darklight.build.Utils;

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

public class WelcomePanel extends JPanel {
	private static final long serialVersionUID = -1411206033690589925L;
	
	private JButton next, close;
	private JLabel welcomeLabel;
	
	public WelcomePanel(final InstallerFrame parent) {
		super();
		
		next = Utils.genericButton("Continue");
		Utils.addPanelSwitchActionListener(next, EPanels.TERMS, parent);
		
		close = Utils.genericButton("Quit");
		Utils.addKillActionListener(close, parent);
		
		welcomeLabel = new JLabel("Welcome to the Darklight Nova Core installer! Press continue to install or quit to cancel.");
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 4;
		c.insets = new Insets (5, 5, 5, 5);
		add(welcomeLabel, c);
		
		c.gridy = 1;
		add(new JLabel(" "), c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0, 5, 5, 0);
		add(close, c);
		
		c.gridx = 1;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(0, 0, 5, 5);
		add(next, c);
	}
}
