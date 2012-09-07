package com.ijg.darklightnova.engine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.ijg.darklightnova.gui.GUI;

public class Engine implements Runnable {
	
	boolean running, bNotFinished;
	
	public double pFound;
	public double total;
	public double percent;
	public ArrayList<Vulnerability> vulnerabilities;
	
	public String progressFile = "/home/blank/Desktop/progress";
	
	public AssessmentModule assessModule;
	
	GUI gui;
	
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
		while (running) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
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
			out = new BufferedWriter(new FileWriter(new File(progressFile)));
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
		return "" + (int) total;
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
		pFound = vulnerabilities.size();
		if (vulnerability != null) {
			vulnerabilities.add(vulnerability);
		}
		if (vulnerabilities.size() == total) {
			bNotFinished = false;
		}
	}
	
	public void removeVulnerability(Vulnerability vulnerability) {
		pFound = vulnerabilities.size();
		if (vulnerability != null) {
			vulnerabilities.remove(vulnerability);
		}
		if (vulnerabilities.size() == total) {
			bNotFinished = false;
		}
	}
	
	public boolean finished() {
		return !bNotFinished;
	}
	
	/*
	 * Should be used if a GUI is made to view the found vulnerabilities
	 */
	public ArrayList<Vulnerability> getVulnerabilities() {
		return vulnerabilities;
	}
}
