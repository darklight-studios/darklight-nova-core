package com.ijg.darklight.build;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

public class Installer {
	
	private GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	private Rectangle screenSize = gd.getDefaultConfiguration().getBounds();
	private Point center = new Point(screenSize.width / 2, screenSize.height / 2);
	
	private final String title = "Darklight Installer";
	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	
	JFrame frame;
	JPanel welcome;
	JPanel panel2, panel3;
	
	public Installer() {
		frame = new JFrame();
		
		initWelcomeFrame();
		
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setContentPane(welcome);
		frame.pack();
	}
	
	public static void main(String[] args) {
		new Installer();
	}
	
	private void kill() {
		System.exit(0);
	}
	
	/**
	 * 
	 */
	private void initWelcomeFrame() {
		welcome = new JPanel();
		
		JButton next = new JButton("Continue");
		next.setSize(new Dimension(30, 15));
		JButton close = new JButton("Quit");
		close.setSize(new Dimension(30, 15));
		JLabel welcomeLabel = new JLabel("Welcome to the Darklight installer! Press continue to install or quit to cancel");
		
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadpanel2();
			}
		});
		
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kill();
			}
		});
		
		welcome.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 4;
		c.insets = new Insets(5, 5, 5, 5);
		welcome.add(welcomeLabel, c);
		
		c.gridy = 1;
		welcome.add(new JLabel(" "), c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0, 5, 5, 0);
		welcome.add(close, c);
		
		c.gridx = 2;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(0, 0, 5, 5);
		welcome.add(next, c);
		
		frame.setLocation(center);
		frame.setContentPane(welcome);
		frame.pack();
	}
	
	private void loadpanel2() {
		welcome.removeAll();
		frame.remove(welcome);
		
		panel2 = (JPanel) frame.getContentPane();
		
		final JButton next = new JButton("Next");
		next.setEnabled(false);
		next.setSize(new Dimension(30, 15));
		JButton close = new JButton("Quit");
		close.setSize(new Dimension(30, 15));
		final JCheckBox agree = new JCheckBox("I have read and agree to the above terms of use");
		
		JTextArea conditions = new JTextArea(
				"By installing Darklight you agree to the following terms:\n" +
				"[1] Darklight is not to be distributed by any person or third party\n" +
				"[2] Darklight may not be redistributed under another name\n" +
				"[3] An attempt to reverse engineer any Darklight Security Tools is prohibited and punishable by law\n" +
				"Darklight Core contains tamper protection that may damage your computer " +
				"if it detects an attempt\n to reverse engineer or modify Darklight Core in any way");
		conditions.setEditable(false);
		conditions.setBackground(Color.white);
		
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadFrame3();
			}
		});
		
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kill();
			}
		});
		
		agree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (agree.isSelected()) {
					next.setEnabled(true);
				} else {
					next.setEnabled(false);
				}
			}
		});
		
		panel2.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 5, 5, 5);
		panel2.add(conditions, c);
		
		c.gridy = 1;
		panel2.add(agree, c);
		
		c.gridy = 2;
		c.insets = new Insets(0, 5, 5, 0);
		c.anchor = GridBagConstraints.WEST;
		panel2.add(close, c);
		
		c.gridx = 1;
		c.insets = new Insets(0, 0, 5, 5);
		c.anchor = GridBagConstraints.EAST;
		panel2.add(next, c);
		
		frame.setContentPane(panel2);
		frame.pack();
	}
	
	private void loadFrame3() {
		panel2.removeAll();
		frame.remove(panel2);
		
		panel3 = (JPanel) frame.getContentPane();
		
		JButton next = new JButton("Next");
		next.setSize(new Dimension(30, 15));
		JButton close = new JButton("Quit");
		close.setSize(new Dimension(30, 15));
		JFileChooser buildFileChooser = new JFileChooser(new File("."));
		buildFileChooser.setSelectedFile(new File(new File("."), "install.dbuild"));
		buildFileChooser.setFileFilter(new FileFilter() {
			public String getDescription() {
				return "dbuild";
			}
			public boolean accept(File f) {
				try {
					if (f.getName().substring(f.getName().indexOf(".")).equals(".dbuild")) {
						return true;
					}
				} catch (Exception e) {}
				return false;
			}
		});
		buildFileChooser.setMultiSelectionEnabled(false);
		
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadFrame4();
			}
		});
		
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kill();
			}
		});
		
		panel3.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		panel3.add(buildFileChooser);
		
		c.gridy = 1;
		c.insets = new Insets(0, 5, 5, 0);
		c.anchor = GridBagConstraints.WEST;
		panel3.add(close, c);
		
		c.insets = new Insets(0, 0, 5, 5);
		c.anchor = GridBagConstraints.EAST;
		panel3.add(next, c);
		
		frame.setContentPane(panel3);
		frame.pack();
	}
	
	private void loadFrame4() {
		
	}
}
