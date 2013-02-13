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

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
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

public class ChooseShortcutPanel extends JPanel {
	private static final long serialVersionUID = 3889298921544574966L;
	
	private JLabel description;
	private JTextField selectedShortcutText, selectedDestinationText;
	private JButton selectShortcut, selectDestination, next, back;
	private JFileChooser shortcutChooser, destChooser;
	
	public ChooseShortcutPanel(final InstallerFrame parent) {
		super();
		
		description = new JLabel("Select the shortcut file and the folder to which it will be copied.");
		
		selectedShortcutText = new JTextField(30);
		selectedDestinationText = new JTextField(30);
		
		next = Utils.genericButton("Next");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(selectedShortcutText.getText());
				System.out.println(selectedDestinationText.getText());
				JsonObject copyInfo = new JsonObject();
				copyInfo.add("source", new JsonPrimitive(selectedShortcutText.getText()));
				copyInfo.add("destination", new JsonPrimitive(selectedDestinationText.getText()));
				
				parent.setCopyInfo(copyInfo);
				parent.changePanel(EPanels.INSTALL);
			}
		});
		
		back = Utils.genericButton("Back");
		Utils.addPanelSwitchActionListener(back, EPanels.CHOOSE_CONFIG, parent);
		
		selectShortcut = Utils.genericButton("Select Shortcut");
		selectShortcut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int status = shortcutChooser.showOpenDialog(null);
				if (status == JFileChooser.APPROVE_OPTION)
					selectedShortcutText.setText(shortcutChooser.getSelectedFile().getAbsolutePath());
			}
		});
		
		selectDestination = Utils.genericButton("Select Destination");
		selectDestination.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int status = destChooser.showOpenDialog(null);
				if (status == JFileChooser.APPROVE_OPTION)
					selectedDestinationText.setText(destChooser.getSelectedFile().getAbsolutePath());
			}
		});
		
		shortcutChooser = new JFileChooser(new File("."));
		if (System.getProperty("os.name").toLowerCase().contains("win")) {
			shortcutChooser.setSelectedFile(new File(".", "Darklight.lnk"));
		} else if (System.getProperty("os.name").toLowerCase().contains("linux")) {
			shortcutChooser.setSelectedFile(new File(".", "Darklight.desktop"));
		}
		shortcutChooser.setMultiSelectionEnabled(false);
		
		destChooser = new JFileChooser();
		destChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		destChooser.setMultiSelectionEnabled(false);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets(5, 5, 5, 5);
		add(description, c);
		
		c.gridy = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.WEST;
		add(selectShortcut, c);
		
		c.gridx = 1;
		c.anchor = GridBagConstraints.EAST;
		add(selectedShortcutText, c);
		
		c.gridy = 2;
		c.gridx = 0;
		c.anchor = GridBagConstraints.WEST;
		add(selectDestination, c);
		
		c.gridx = 1;
		c.anchor = GridBagConstraints.EAST;
		add(selectedDestinationText, c);
		
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(0, 5, 5, 0);
		c.anchor = GridBagConstraints.WEST;
		add(back, c);
		
		c.gridx = 1;
		c.insets = new Insets(0, 0, 5, 5);
		c.anchor = GridBagConstraints.EAST;
		add(next, c);
	}
}
