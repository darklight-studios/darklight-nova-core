package com.ijg.darklight.build.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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

public class ChooseInstallPanel extends JPanel {
	private static final long serialVersionUID = -9217125500045152935L;

	private JLabel description;
	private JTextField installPath;
	private JButton browse, next, back;
	private JFileChooser fileChooser;
	
	public ChooseInstallPanel(final InstallerFrame parent) {
		super();
		
		description = new JLabel("Click \"Browse\" to select the directory where Darklight Nova Core will be installed");
		
		next = Utils.genericButton("Next");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.setInstallPath(installPath.getText());
				parent.changePanel(EPanels.CHOOSE_JAR);
			}
		});
		
		back = Utils.genericButton("Back");
		Utils.addPanelSwitchActionListener(back, EPanels.OPTION_BUILD, parent);
		
		browse = Utils.genericButton("Browse");
		browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int status = fileChooser.showOpenDialog(null);
				if (status == JFileChooser.APPROVE_OPTION)
					installPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		});
		
		installPath = new JTextField(40);
		
		fileChooser = new JFileChooser(new File("."));
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets(5, 5, 5, 5);
		add(description, c);
		
		c.gridy = 1;
		add(browse, c);
		
		c.gridy = 2;
		c.insets = new Insets(0, 5, 5, 5);
		add(installPath, c);
		
		c.gridy = 3;
		c.gridwidth = 1;
		c.insets = new Insets(0, 5, 5, 0);
		c.anchor = GridBagConstraints.WEST;
		add(back, c);
		
		c.gridx = 1;
		c.insets = new Insets(0, 0, 5, 5);
		c.anchor = GridBagConstraints.EAST;
		add(next, c);
	}
}
