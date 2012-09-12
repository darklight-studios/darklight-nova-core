package com.ijg.darklightnova.live;

public class Engine implements Runnable {
	
	boolean running, bNotFinished;
	
	public AssessmentModule assessModule;
	
	public static void main(String[] args) {
		new Engine();
	}

	public Engine() {
		bNotFinished = true;
		assessModule = new AssessmentModule();
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
		long last = 0;
		long interval = 5000L; // Assess every 5 seconds
		while (running) {
			if (System.currentTimeMillis() - last >= interval) {
				assessModule.assess();
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
	
	public void finishSession() {
		running = false;
	}
}
