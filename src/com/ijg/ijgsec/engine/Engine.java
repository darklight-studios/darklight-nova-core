package com.ijg.ijgsec.engine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.ijg.ijgsec.gui.GUI;

public class Engine implements Runnable {
	
	boolean running, bNotFinished;
	
	public double pFound;
	public double total;
	public double percent;
	public ArrayList<Vulnerability> vulnerabilities;
	
	public AssessmentModule assessModule;
	
	GUI gui;
	
	long assessIntervalTimer;
	
	public static void main(String[] args) {
		new Engine();
	}

	public Engine() {
		bNotFinished = true;
		vulnerabilities = new ArrayList<Vulnerability>(); // list of found vulnerabilities used to write the progress file
		assessModule = new AssessmentModule(this);
		start();
	}
	
	public void start() {
		/*
		 * Init the gui and the thread, start
		 * the gears turning, do initial 
		 * scoring and display...
		 */
		running = true;
		gui = new GUI(this);
		Thread engine = new Thread(this, "engine");
		engine.start();
		assessModule.report();
		gui.update();
	}

	public void run() {
		assessIntervalTimer = System.currentTimeMillis();
		while (running) {
			if (bNotFinished) { // assessment is active
				if (System.currentTimeMillis() - assessIntervalTimer >= 120000L) { // auto check every 120 seconds
					assessModule.report();
					gui.update();
					assessIntervalTimer = System.currentTimeMillis();
				}
			}
			try {
				Thread.sleep(20); // arbitrarily chose 20, runs ok at this
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
	}
	
	public void update() {
		/*
		 * Instead of placing this in add/remove
		 * vulnerability methods, made an update
		 * method to be run from AssessmentModule
		 * in report()
		 */
		pFound = vulnerabilities.size();
		
		if (vulnerabilities.size() == total) {
			bNotFinished = false;
		} else if (vulnerabilities.size() < total) {
			bNotFinished = true;
		}
	}
	
	public void finishSession() {
		running = false;
	}
	
	public void writeFoundList() {
		/*
		 * Write all found vulnerabilities
		 * to the progress file in the format
		 * of "name: description"
		 */
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(new File("progress")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			out.write("#Any found (fixed) vulnerabilities are shown here in the format of:\n#Vulnerability name: Vulnerability description\n");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		for (Vulnerability vuln : vulnerabilities) {
			try {
				out.write(vuln.name + ": " + vuln.description + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * =============================
	 * Getters and setters
	 * =============================
	 */
	
	public String getTotal() {
		return "" + total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getFound() {
		return "" + vulnerabilities.size();
	}

	public String getPercent() {
		return "" + (int) (percent*100) + "%";
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}
	
	public void addVulnerability(Vulnerability vulnerability) {
		if (vulnerability != null) {
			vulnerabilities.add(vulnerability);
		}
	}
	
	public void removeVulnerability(Vulnerability vulnerability) {
		if (vulnerability != null) {
			vulnerabilities.remove(vulnerability);
		}
	}
	
	public boolean finished() {
		return !bNotFinished;
	}
	
	public void resetIntervalTimer() {
		assessIntervalTimer = System.currentTimeMillis();
	}
	
	/*
	 * Should be used if a GUI is made to view the found vulnerabilities
	 */
	public ArrayList<Vulnerability> getVulnerabilities() {
		return vulnerabilities;
	}
}
