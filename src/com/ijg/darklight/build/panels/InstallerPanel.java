package com.ijg.darklight.build.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

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

public class InstallerPanel extends JPanel {
	private static final long serialVersionUID = -6984914898253757316L;

	JButton finish;
	JLabel installText;
	JProgressBar progress;
	
	public InstallerPanel(final InstallerFrame parent) {
		super();

		finish = Utils.genericButton("Finish");
		Utils.addKillActionListener(finish, parent);
		finish.setEnabled(false);
		
		installText = new JLabel("Installing Darklight Nova Core");
		progress = new JProgressBar();
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 5, 5, 5);
		add(installText, c);
		
		c.gridy = 1;
		add(progress, c);
		
		c.gridy = 2;
		c.anchor = GridBagConstraints.EAST;
		add(finish, c);
		parent.install(this);
	}
	
	public void setProgress(int percent) {
		progress.setValue(percent);
		if (percent == 100)
			finish.setEnabled(true);
	}
}
