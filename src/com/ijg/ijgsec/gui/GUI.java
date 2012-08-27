package com.ijg.ijgsec.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ijg.ijgsec.engine.Engine;

public class GUI {
	Engine engine;
	
	static String title = "IJGSec ";
	static final int WIDTH = 300;
	static final int HEIGHT = 200;
	
	static final String TOTAL_TEXT = "Total vulnerabilities:";
	static final String FOUND_TEXT = "Found vulnerabilities:";
	static final String PERCENT_TEXT = "Percent complete:";
	
	JFrame frame;
	JPanel panel;
	JButton refresh, finish, view;
	JLabel totalLabel, foundLabel, percentLabel;
	JLabel total, found, percent;
	
	/*
	 * Basic GUI using the Java
	 * GridBag layout
	 */
	
	public GUI(Engine engine) {
		this.engine = engine;
		
		title += engine.assessModule.toString();
		
		frame = new JFrame();
		panel = (JPanel) frame.getContentPane();
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		refresh = new JButton("Refresh");
		refresh.setActionCommand("refresh");
		new MouseListener(this, refresh);
		
		finish = new JButton("End Session");
		finish.setActionCommand("finish");
		new MouseListener(this, finish);
		
		view = new JButton("View Found Vulnerabilities");
		view.setActionCommand("view");
		new MouseListener(this, view);
		
		totalLabel = new JLabel(TOTAL_TEXT);
		foundLabel = new JLabel(FOUND_TEXT);
		percentLabel = new JLabel(PERCENT_TEXT);
		
		total = new JLabel(engine.getTotal());
		found = new JLabel("0");
		percent = new JLabel("0%");
		
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		panel.add(totalLabel, c);
		
		c.gridy = 1;
		panel.add(foundLabel, c);
		
		c.gridy = 2;
		panel.add(percentLabel, c);
		
		c.gridx = 3;
		c.gridy = 0;
		panel.add(total, c);
		
		c.gridy = 1;
		panel.add(found, c);
		
		c.gridy = 2;
		panel.add(percent, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0; 
		c.gridy = 3; 
		c.insets = new Insets(0, 5, 0, 5);
		panel.add(refresh, c);
		
		c.gridx = 3;
		c.ipadx = 0;
		c.insets = new Insets(0, 5, 0, 5);
		panel.add(finish, c);
		
		c.weightx = 0;
		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 4;
		panel.add(view, c);

		frame.setContentPane(panel);
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocation(500, 500);
		frame.setVisible(true);
		frame.pack();
		update();
	}
	
	public void update() {
		// Customizing the color based on progress/changes
		if (engine.finished()) {
			found.setForeground(Color.green);
			percent.setForeground(Color.green);
		} else if (engine.pFound > engine.vulnerabilities.size()) {
			found.setForeground(Color.red);
			percent.setForeground(Color.red);
		} else {
			found.setForeground(Color.black);
			percent.setForeground(Color.black);
		}
		
		found.setText(engine.getFound());
		percent.setText(engine.getPercent());
	}
}
