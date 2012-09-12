package com.ijg.darklightnova.headless;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.ijg.darklightnova.core.Issue;

public class Engine implements Runnable {
	
	boolean running, bNotFinished;
	
	/*
	 * If this "kill file" exists, Darklight-Nova
	 * will terminate
	 */
	File killFile = new File("/home/blank/Desktop/killdln");
	
	String progressFile = "/usr/local/Darklight-Nova/progress";
	
	public AssessmentModule assessModule;
	
	public static void main(String[] args) {
		new Engine();
	}

	public Engine() {
		bNotFinished = true;
		assessModule = new AssessmentModule(this);
		start();
	}
	
	public void start() {
		/*
		 * Init thread, start the tick,
		 * do initial assessment
		 */
		running = true;
		Thread engine = new Thread(this, "engine");
		engine.start();
		assessModule.assess();
	}

	public void run() {
		long last = System.currentTimeMillis();
		long interval = 5000L; // Assess every 5 seconds
		while (running) {
			if (System.currentTimeMillis() - last >= interval) {
				// Check for kill file
				if (killFile.exists()) {
					running = false;
				} else {
					assessModule.assess();
				}
				last = System.currentTimeMillis();
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
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
			out.write("Found: " + assessModule.issues.size() + 
					"\nTotal: " + assessModule.total + 
					"\nPercent: " + (int) ((double) (assessModule.issues.size()) / assessModule.total) + "%" + "\n\n");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		for (Issue issue : assessModule.issues) {
			try {
				out.write(issue.name + ": " + issue.description + "\n");
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
}
