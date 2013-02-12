package com.ijg.darklight.build;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class OptionBuildPanel extends JPanel {
	private static final long serialVersionUID = -5317185056383324444L;

	private JButton next, back;
	private JRadioButton useBuild, choose;
	private ButtonGroup group;
	
	
	public OptionBuildPanel(final InstallerFrame parent) {
		super();
		
		next = Utils.genericButton("Next");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (choose.isSelected()) {
					parent.changePanel(EPanels.CHOOSE_JAR);
				} else if (useBuild.isSelected()) {
					parent.changePanel(EPanels.CHOOSE_BUILD);
				}
			}
		});
		
		back = Utils.genericButton("Back");
		Utils.addPanelSwitchActionListener(back, EPanels.TERMS, parent);
		
		useBuild = new JRadioButton("Use install file");
		choose = new JRadioButton("Manually configure installation");
		
		group = new ButtonGroup();
		group.add(useBuild);
		group.add(choose);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets(5, 5, 5, 5);
		c.anchor = GridBagConstraints.WEST;
		add(useBuild, c);
		
		c.gridy = 1;
		add(choose, c);
		
		c.gridy = 2;
		c.insets = new Insets(0, 5, 5, 0);
		add(back, c);
		
		c.gridx = 1;
		c.insets = new Insets(0, 0, 5, 5);
		c.anchor = GridBagConstraints.EAST;
		add(next, c);
	}
}
