package com.ijg.darklight.build;

import java.awt.BorderLayout;
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
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.ijg.darklight.sdk.core.Settings;
import com.ijg.darklight.sdk.loader.DarklightLoader;

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

public class DarklightInstaller2 {
	
	private GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	private Rectangle screenSize = graphicsDevice.getDefaultConfiguration().getBounds();
	private Point center = new Point(screenSize.width / 2, screenSize.height / 2);
	
	private final String title = "Darklight Installer";
	
	private JFrame frame;
	private JPanel welcome;
	private JPanel terms, preChooseBuild, chooseBuild, chooseJar, 
					preChooseConfig, chooseConfig, chooseShortcut, installPanel;
	
	private JsonObject installConfig;
	
	private String installPath;
	private String jarToCopy;
	private String configFileToCopy;
	private JsonObject copyInfo;
	
	/**
	 * GUI constructor
	 */
	public DarklightInstaller2() {
		frame = new JFrame();
		
		initWelcomeFrame();
		
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setContentPane(welcome);
		frame.pack();
		
		try {
			File logFile = new File("log.txt");
			if (logFile.exists())
				logFile.delete();
			logFile.createNewFile();
			PrintStream printStream = new PrintStream(new FileOutputStream(logFile));
			System.setOut(printStream);
			System.setErr(printStream);
		} catch (Exception e) {
			System.err.println("Error redirecting STDOUT/STDERR");
		}
	}
	
	/**
	 * CLI constructor
	 * Unimplemented
	 * @param args CLI arguments
	 */
	public DarklightInstaller2(String[] args) {
		// TODO implement
	}
	
	/**
	 * @param args CLI arguments
	 */
	public static void main(String[] args) {
		new DarklightInstaller2();
	}
	
	/**
	 * Create and display the welcome panel
	 */
	private void initWelcomeFrame() {
		welcome = new JPanel();
		
		JButton next = new JButton("Continue");
		next.setSize(new Dimension(30, 15));
		JButton close = new JButton("Quit");
		close.setSize(new Dimension(30, 15));
		JLabel welcomeLabel = new JLabel("Welcome to the Darklight installer! Press continue to install or quit to cancel.");
		
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadTerms(welcome);
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
		
		frame.setContentPane(welcome);
		frame.pack();
		frame.setLocation((int) ((screenSize.getWidth() / 2) - ((double) (frame.getWidth()) / 2)), 
				(int) ((screenSize.getHeight() / 2) - ((double) (frame.getHeight()) / 2)));
	}
	
	/**
	 * Create and display the terms of service panel
	 */
	private void loadTerms(JPanel parent) {
		parent.removeAll();
		frame.remove(parent);
		
		terms = (JPanel) frame.getContentPane();
		
		final JButton next = new JButton("Next");
		next.setEnabled(true);
		next.setSize(new Dimension(30, 15));
		JButton close = new JButton("Quit");
		close.setSize(new Dimension(30, 15));
		close.setSize(new Dimension(30, 15));
		final JCheckBox agree = new JCheckBox("I agree to the aforementioned Terms of Service");
		
		JTextArea conditions = new JTextArea(
				"By installing Darklight you agree to the following terms:\n" +
				"[1] This specific Darklight Installation should not be redistributed in executable form.\n" +
				"[2] Darklight's Core Source Code may be redistributed under the terms set by the GNU GPL V3.\n" +
				"[3] Reverse engineering proprietary modules produced for Darklight is in violation of the\nUnited States Digital Millennium Copyright Act of 1998, as well as the copyright held by Isaac Grant and Lucas Nicodemus.\n" +
				"For more information, refer to the COPYING file packaged with your downloaded installation.\n\n" +
				"WARNING: Darklight's Core contains tamper protection that may damage your computer if an attempt is made\n" +
				"to retroactively tamper with the Darklight modules & core. Proceed at your own risk.\n\n" +
				"Darklight is distributed in the hope that it will be useful, " +
				"but WITHOUT ANY WARRANTY; without even the implied warranty of\n" +
				"MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the " + 
				"GNU General Public License for more details.");
		conditions.setEditable(false);
		
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadPreChooseBuild(terms);
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
		
		terms.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 5, 5, 5);
		terms.add(conditions, c);
		
		c.gridy = 1;
		terms.add(agree, c);
		
		c.gridy = 2;
		c.insets = new Insets(0, 5, 5, 0);
		c.anchor = GridBagConstraints.WEST;
		terms.add(close, c);
		
		c.gridx = 1;
		c.insets = new Insets(0, 0, 5, 5);
		c.anchor = GridBagConstraints.EAST;
		terms.add(next, c);
		
		frame.setContentPane(terms);
		frame.pack();
		frame.setLocation((int) ((screenSize.getWidth() / 2) - ((double) (frame.getWidth()) / 2)), 
				(int) ((screenSize.getHeight() / 2) - ((double) (frame.getHeight()) / 2)));
	}
	
	/**
	 * Load and display the panel to choose if a build file will be used or not
	 */
	private void loadPreChooseBuild(JPanel parent) {
		parent.removeAll();
		frame.remove(parent);
		
		preChooseBuild = (JPanel) frame.getContentPane();
		
		JButton next = new JButton("Next");
		next.setSize(new Dimension(30, 15));
		JButton back = new JButton("Back");
		back.setSize(new Dimension(30, 15));
		
		final JRadioButton useBuild = new JRadioButton("Use install file");
		final JRadioButton choose = new JRadioButton("Manually configure installation");
		choose.setSelected(true);
		
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (choose.isSelected()) {
					loadChooseJar(preChooseBuild);
				} else if (useBuild.isSelected()) {
					loadChooseBuild(preChooseBuild);
				}
			}
		});
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 loadTerms(preChooseBuild);
			}
		});
		
		ButtonGroup group = new ButtonGroup();
		group.add(useBuild);
		group.add(choose);
		
		preChooseBuild.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 5, 5, 5);
		c.anchor = GridBagConstraints.WEST;
		preChooseBuild.add(useBuild, c);
		
		c.gridy = 1;
		preChooseBuild.add(choose, c);
		
		c.gridy = 2;
		c.insets = new Insets(0, 5, 5, 0);
		preChooseBuild.add(back, c);
		
		c.gridx = 1;
		c.insets = new Insets(0, 0, 5, 5);
		c.anchor = GridBagConstraints.EAST;
		preChooseBuild.add(next, c);
		
		frame.setContentPane(preChooseBuild);
		frame.pack();
		frame.setLocation((int) ((screenSize.getWidth() / 2) - ((double) (frame.getWidth()) / 2)), 
				(int) ((screenSize.getHeight() / 2) - ((double) (frame.getHeight()) / 2)));
	}
	
	/**
	 * Load and display the panel to choose if a build file to load install settings from
	 */
	private void loadChooseBuild(JPanel parent) {
		parent.removeAll();
		frame.remove(parent);
		
		chooseBuild = (JPanel) frame.getContentPane();
		
		JButton next = new JButton("Next");
		next.setSize(new Dimension(30, 15));
		JButton back = new JButton("Back");
		back.setSize(new Dimension(30, 15));
		
		final JFileChooser buildFileChooser = new JFileChooser(new File("."));
		buildFileChooser.setSelectedFile(new File(".", "install.json"));
		buildFileChooser.setFileFilter(new FileFilter() {
			public String getDescription() {
				return "json";
			}
			public boolean accept(File f) {
				try {
					if (f.getName().substring(f.getName().indexOf(".")).equals(".json")) {
						return true;
					}
				} catch (Exception e) {}
				return false;
			}
		});
		buildFileChooser.setMultiSelectionEnabled(false);
		
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JsonParser parser = new JsonParser();
				try {
					installConfig = parser.parse(new FileReader(buildFileChooser.getSelectedFile())).getAsJsonObject();
					loadSettings();
					loadInstallPanel(chooseBuild);
				} catch (FileNotFoundException e1) {
					System.err.println("Error, install file not found");
				}
			}
		});
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadPreChooseBuild(chooseBuild);
			}
		});
		
		chooseBuild.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		chooseBuild.add(buildFileChooser, c);
		
		c.gridy = 1;
		c.insets = new Insets(0, 5, 5, 0);
		c.anchor = GridBagConstraints.WEST;
		chooseBuild.add(back, c);
		
		c.insets = new Insets(0, 0, 5, 5);
		c.anchor = GridBagConstraints.EAST;
		chooseBuild.add(next, c);
		
		frame.setContentPane(chooseBuild);
		frame.pack();
		frame.setLocation((int) ((screenSize.getWidth() / 2) - ((double) (frame.getWidth()) / 2)), 
				(int) ((screenSize.getHeight() / 2) - ((double) (frame.getHeight()) / 2)));
	}
	
	/**
	 * Load and display the panel to choose what jar file to copy
	 */
	private void loadChooseJar(JPanel parent) {
		parent.removeAll();
		frame.remove(parent);
		
		chooseJar = (JPanel) frame.getContentPane();
		
		JButton next = new JButton("Next");
		next.setSize(new Dimension(30, 15));
		JButton back = new JButton("Back");
		back.setSize(new Dimension(30, 15));
		
		final JFileChooser jarFileChooser = new JFileChooser(new File("."));
		jarFileChooser.setSelectedFile(new File(".", "Darklight.jar"));
		jarFileChooser.setFileFilter(new FileFilter() {
			public String getDescription() {
				return "jar";
			}
			public boolean accept(File f) {
				try {
					if (f.getName().substring(f.getName().indexOf(".")).equals(".jar")) {
						return true;
					}
				} catch (Exception e) {}
				return false;
			}
		});
		jarFileChooser.setMultiSelectionEnabled(false);
		
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jarToCopy = jarFileChooser.getSelectedFile().getAbsolutePath();
				loadPreChooseConfig(chooseJar);
			}
		});
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadPreChooseBuild(chooseJar);
			}
		});
		
		chooseJar.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		chooseJar.add(jarFileChooser, c);
		
		c.gridy = 1;
		c.insets = new Insets(0, 5, 5, 0);
		c.anchor = GridBagConstraints.WEST;
		chooseJar.add(back, c);
		
		c.insets = new Insets(0, 0, 5, 5);
		c.anchor = GridBagConstraints.EAST;
		chooseJar.add(next, c);
		
		frame.setContentPane(chooseJar);
		frame.pack();
		frame.setLocation((int) ((screenSize.getWidth() / 2) - ((double) (frame.getWidth()) / 2)), 
				(int) ((screenSize.getHeight() / 2) - ((double) (frame.getHeight()) / 2)));
	}
	
	/**
	 * Load and display the panel to choose if an existing config file will be used
	 */
	private void loadPreChooseConfig(JPanel parent) {
		parent.removeAll();
		frame.remove(parent);
		
		preChooseConfig = (JPanel) frame.getContentPane();
		
		JButton next = new JButton("Next");
		next.setSize(new Dimension(30, 15));
		JButton back = new JButton("Back");
		back.setSize(new Dimension(30, 15));
		
		final JRadioButton useConfig = new JRadioButton("Copy existing config file");
		final JRadioButton newConfig = new JRadioButton("Create a new config file");
		newConfig.setSelected(true);
		
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (useConfig.isSelected()) {
					loadChooseConfig(preChooseConfig);
				} else if (newConfig.isSelected()) {
					configFileToCopy = null;
					loadChooseShortcut(preChooseConfig);
				}
			}
		});
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadChooseJar(preChooseConfig);
			}
		});
		
		ButtonGroup group = new ButtonGroup();
		group.add(useConfig);
		group.add(newConfig);
		
		preChooseConfig.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 5, 5, 5);
		c.anchor = GridBagConstraints.WEST;
		preChooseConfig.add(useConfig, c);
		
		c.gridy = 1;
		preChooseConfig.add(newConfig, c);
		
		c.gridy = 2;
		c.insets = new Insets(0, 5, 5, 0);
		c.anchor = GridBagConstraints.WEST;
		preChooseConfig.add(back, c);
		
		c.gridx = 1;
		c.insets = new Insets(0, 0, 5, 5);
		c.anchor = GridBagConstraints.EAST;
		preChooseConfig.add(next, c);
		
		frame.setContentPane(preChooseConfig);
		frame.pack();
		frame.setLocation((int) ((screenSize.getWidth() / 2) - ((double) (frame.getWidth()) / 2)), 
				(int) ((screenSize.getHeight() / 2) - ((double) (frame.getHeight()) / 2)));
	}
	
	/**
	 * Load and display the panel to choose what config file to copy
	 */
	private void loadChooseConfig(JPanel parent) {
		parent.removeAll();
		frame.remove(parent);
		
		chooseConfig = (JPanel) frame.getContentPane();
		
		JButton next = new JButton("Next");
		next.setSize(new Dimension(30, 15));
		JButton back = new JButton("Back");
		back.setSize(new Dimension(30, 15));
		
		final JFileChooser configFileChooser = new JFileChooser(new File("."));
		configFileChooser.setSelectedFile(new File(".", "config.json"));
		configFileChooser.setFileFilter(new FileFilter() {
			public String getDescription() {
				return "json";
			}
			public boolean accept(File f) {
				try {
					if (f.getName().substring(f.getName().indexOf(".")).equals(".json")) {
						return true;
					}
				} catch (Exception e) {}
				return false;
			}
		});
		configFileChooser.setMultiSelectionEnabled(false);
		
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				configFileToCopy = configFileChooser.getSelectedFile().getAbsolutePath();
				loadChooseShortcut(chooseConfig);
			}
		});
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadPreChooseConfig(chooseConfig);
			}
		});
		
		chooseConfig.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		chooseConfig.add(configFileChooser, c);
		
		c.gridy = 1;
		c.insets = new Insets(0, 5, 5, 0);
		c.anchor = GridBagConstraints.WEST;
		chooseConfig.add(back, c);
		
		c.insets = new Insets(0, 0, 5, 5);
		c.anchor = GridBagConstraints.EAST;
		chooseConfig.add(next, c);
		
		frame.setContentPane(chooseConfig);
		frame.pack();
		frame.setLocation((int) ((screenSize.getWidth() / 2) - ((double) (frame.getWidth()) / 2)), 
				(int) ((screenSize.getHeight() / 2) - ((double) (frame.getHeight()) / 2)));
	}
	
	/**
	 * Load and display the panel to choose what shortcut file to copy
	 */
	private void loadChooseShortcut(JPanel parent) {
		parent.removeAll();
		frame.remove(parent);
		
		chooseShortcut = (JPanel) frame.getContentPane();
		
		final JButton next = new JButton("Next");
		next.setSize(new Dimension(30, 15));
		next.setEnabled(false);
		JButton back = new JButton("Back");
		back.setSize(new Dimension(30, 15));
		
		JLabel sourceLabel = new JLabel("Source:");
		JLabel destLabel = new JLabel("Destination:");
		
		final JTextField source = new JTextField();
		source.setColumns(25);
		final JTextField dest = new JTextField();
		dest.setColumns(25);
		
		chooseShortcut.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {}
			@Override
			public void keyReleased(KeyEvent arg0) {}
			@Override
			public void keyTyped(KeyEvent arg0) {
				try {
					if (source.getText() != null && dest.getText() != null) {
						next.setEnabled(true);
					} else {
						next.setEnabled(false);
					}
				} catch (NullPointerException e) {
					next.setEnabled(false);
				}
			}
			
		});
		
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyInfo.add("source", new JsonPrimitive(source.getText()));
				copyInfo.add("destination", new JsonPrimitive(dest.getText()));
				loadInstallPanel(chooseShortcut);
			}
		});
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadPreChooseConfig(chooseConfig);
			}
		});
		
		chooseShortcut.setLayout(new BoxLayout(chooseShortcut, BoxLayout.LINE_AXIS));
		
		chooseShortcut.add(sourceLabel);
		chooseShortcut.add(source);
		
		chooseShortcut.add(new JSeparator(SwingConstants.HORIZONTAL));
		
		chooseShortcut.add(destLabel);
		chooseShortcut.add(dest);
		
		chooseShortcut.add(new JSeparator(SwingConstants.HORIZONTAL));
		
		chooseShortcut.add(back, SwingConstants.WEST);
		chooseShortcut.add(next, SwingConstants.EAST);
		
		chooseShortcut.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		/*chooseShortcut.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 5, 5, 5);
		c.anchor = GridBagConstraints.WEST;
		chooseShortcut.add(sourceLabel);
		
		c.gridx = 1;
		c.insets = null;
		c.anchor = GridBagConstraints.EAST;
		chooseShortcut.add(source);
		
		chooseShortcut.add(new JSeparator(SwingConstants.HORIZONTAL));
		
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(5, 5, 5, 5);
		c.anchor = GridBagConstraints.WEST;
		chooseShortcut.add(destLabel);
		
		c.gridx = 1;
		c.insets = null;
		c.anchor = GridBagConstraints.EAST;
		chooseShortcut.add(dest);
		
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(0, 5, 5, 0);
		c.anchor = GridBagConstraints.WEST;
		chooseShortcut.add(back, c);
		
		c.insets = new Insets(0, 0, 5, 5);
		c.anchor = GridBagConstraints.EAST;
		chooseShortcut.add(next, c);*/
		
		frame.setContentPane(chooseShortcut);
		frame.pack();
		frame.setLocation((int) ((screenSize.getWidth() / 2) - ((double) (frame.getWidth()) / 2)), 
				(int) ((screenSize.getHeight() / 2) - ((double) (frame.getHeight()) / 2)));
	}
	
	/**
	 * Load and display the panel that shows installation progress
	 */
	private void loadInstallPanel(JPanel parent) {
		parent.removeAll();
		frame.remove(parent);
		
		installPanel = (JPanel) frame.getContentPane();
		
		JButton next = new JButton("finish");
		next.setSize(new Dimension(30, 15));
		next.setEnabled(false);
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kill();
			}
		});
		
		JLabel installText = new JLabel("Installing Darklight Nova Core");
		JProgressBar progress = new JProgressBar();
		
		installPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 5, 5, 5);
		installPanel.add(installText, c);
		
		c.gridy = 1;
		installPanel.add(progress, c);
		
		c.gridy = 2;
		c.anchor = GridBagConstraints.EAST;
		installPanel.add(next, c);
		
		frame.setContentPane(installPanel);
		frame.pack();
		frame.setLocation((int) ((screenSize.getWidth() / 2) - ((double) (frame.getWidth()) / 2)), 
				(int) ((screenSize.getHeight() / 2) - ((double) (frame.getHeight()) / 2)));
		
		progress.setValue(10);
		createFileSystem();
		progress.setValue(30);
		copyJar();
		progress.setValue(50);
		copyModules();
		progress.setValue(60);
		copyScoreOutputDirs();
		progress.setValue(70);
		installIssues();
		progress.setValue(80);
		writeDefaultSettings();
		progress.setValue(90);
		copyFiles();
		progress.setValue(100);
		next.setEnabled(true);
	}
	
	/**
	 * Load install settings from configurationf ile
	 */
	private void loadSettings() {
		installPath = installConfig.get("installpath").getAsString();
		jarToCopy = installConfig.get("jar").getAsString();
		configFileToCopy = installConfig.get("config").getAsString();
		copyInfo = installConfig.get("copy").getAsJsonObject();
	}
	
	/**
	 * Create the installation
	 */
	private void createFileSystem() {
		File installFolder = new File(installPath);
		File pluginsFolder = new File(installPath, "plugins");
		
		if (pluginsFolder.exists()) pluginsFolder.delete();
		if (installFolder.exists()) installFolder.delete();
		
		if (installFolder.mkdir()) {
			if (pluginsFolder.mkdir()) {
				System.out.println("Successfully created file system in "
						+ installFolder.getAbsolutePath());
				return;
			} else {
				System.err.println("Error creating plugins folder");
			}
		} else {
			System.out.println("Error creating install folder");
		}
	}
	
	/**
	 * Copy the Darklight jar to the install directory
	 */
	private void copyJar() {
		File darklightJar = new File(jarToCopy);
		if (darklightJar.exists() && darklightJar.isFile()) {
			darklightJar.renameTo(new File(installPath, "Darklight.jar"));
		}  else {
			System.err.println("Error, invalid Darklight jar");
		}
	}
	
	/**
	 * Copy the directories pertaining to score output to the installation directory
	 */
	private void copyScoreOutputDirs() {
		File staticsDest = new File(installPath, "statics");
		File templatesDest = new File(installPath, "templates");
		
		staticsDest.mkdir();
		templatesDest.mkdir();
		
		try {
			JarFile jar = new JarFile(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
			Enumeration<JarEntry> jarEntries = jar.entries();
			while (jarEntries.hasMoreElements()) {
				JarEntry entry = jarEntries.nextElement();
				if (!entry.isDirectory()) {
					if (entry.getName().contains("statics/") || entry.getName().contains("templates/")) {
						InputStream inputStream = jar.getInputStream(entry);
						OutputStream outputStream = new FileOutputStream(new File(installPath, entry.getName().replace("/", "\\")));
						byte[] buffer = new byte[4096];
						int length;
						while ((length = inputStream.read(buffer)) > 0) {
							outputStream.write(buffer, 0, length);
						}
						inputStream.close();
						outputStream.close();
					}
				} else if (entry.getName().contains("statics/") || entry.getName().contains("templates/")) {
					File dir = new File(installPath, entry.getName().replace("/", "\\"));
					dir.mkdir();
				}
			}
			jar.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Copy the modules to be used into the plugins folder
	 */
	private void copyModules() {
		File pluginsFolder = new File("plugins");
		File[] modules = pluginsFolder.listFiles();
		for (File module : modules) {
			if (!module.renameTo(new File(new File(installPath, "plugins"), module.getName()))) {
				System.err.println("Error moving " + module.getName());
			}
		}
	}

	/**
	 * Copy existing config file, or serialize a new one
	 */
	private void writeDefaultSettings() {
		if (configFileToCopy == null) {
			Settings.setSettingsFile(new File(installPath, "config.json"));
			Settings settings = new Settings();
			try {
				settings.serialize();
			} catch (IOException e) {
				System.err.println("Error serializing default settings");
			}
		} else {
			File config = new File(configFileToCopy);
			if (config.exists() && config.isFile()) {
				config.renameTo(new File(installPath, "config.json"));
			} else {
				System.err.println("Error, specified config file is invalid");
			}
		}
	}

	/**
	 * Install issues
	 */
	private void installIssues() {
		File root = new File(installPath, "plugins");
		if (root.exists() && root.isDirectory()) {
			File[] fileList = root.getAbsoluteFile().listFiles();
			for (File issue : fileList) {
				if (issue.getName().contains("Module")) {
					if (issue.getName().endsWith(".jar")) {
						String name = issue.getName().substring(0, issue.getName().indexOf("."));
						Class<?> issueClass;
						try {
							issueClass = DarklightLoader.loadClassFromJar("com.darklight.core.scoring." + name, issue.getPath());
							Method installIssue = issueClass.getMethod("install");
							installIssue.invoke(issueClass, new Object[] {});
						} catch (MalformedURLException | ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	/**
	 * Copy shortcuts
	 */
	private void copyFiles() {
		if (copyInfo != null) {
			File source = new File(copyInfo.get("source").getAsString());
			File dest = new File(copyInfo.get("destination").getAsString());
			if (source.exists() && source.isFile()) {
				if (dest.isFile()) {
					source.renameTo(dest);
				} else {
					System.err.println("Error, invalid destination for shortcut: Not a file!!");
				}
			} else {
				System.err.println("Error, invalid source shortcut to copy");
			}
		}
	}
	
	/**
	 * Safely kill the installation
	 */
	private void kill() {
		System.exit(0);
	}
}
