package com.ijg.darklight.build;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class TermsPanel extends JPanel {
	private static final long serialVersionUID = -6076724638000425752L;
	
	private JButton next, close;
	private JCheckBox agree;
	private JTextArea conditions;
	
	public TermsPanel(final InstallerFrame parent) {
		super();
		
		next = Utils.genericButton("Next");
		Utils.addPanelSwitchActionListener(next, EPanels.OPTION_BUILD, parent);
		
		close = Utils.genericButton("Quit");
		Utils.addKillActionListener(close, parent);
		
		agree = new JCheckBox("I agree to the aforementioned Terms of Service");
		agree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (agree.isSelected()) {
					next.setEnabled(true);
				} else {
					next.setEnabled(false);
				}
			}
		});
		
		conditions = new JTextArea(
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
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets(5, 5, 5, 5);
		add(conditions, c);
		
		c.gridy = 1;
		c.gridwidth = 2;
		add(agree, c);
		
		c.gridy = 2;
		c.insets = new Insets(0, 5, 5, 0);
		c.anchor = GridBagConstraints.WEST;
		add(close, c);
		
		c.gridx = 1;
		c.insets = new Insets(0, 0, 5, 5);
		c.anchor = GridBagConstraints.EAST;
		add(next, c);
	}
}
