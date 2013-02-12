package com.ijg.darklight.build;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.filechooser.FileFilter;

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
