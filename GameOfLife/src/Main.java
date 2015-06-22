public class Main {

	public static void main(String[] args) {

		Rule[] rules = { new RPopulation() };

		GameParameter gp = new GameParameter(SimulationEngine.EdgeMode.TORUS,
				SimulationEngine.RunningState.PAUSE, 
				100);

		SimulationEngine engine = new SimulationEngine(gp,rules, 50, 50);
		
		
		engine.setCellAtTo(11, 12, Cell.State.ALIVE);
		engine.setCellAtTo(12, 12, Cell.State.ALIVE);
		engine.setCellAtTo(13, 12, Cell.State.ALIVE);
		
		
		ifGUI gui = new SwingGUI(gp, engine.getCells());
		// ifGUI gui = new ConsoleGUI();

		long lastTime;
		long lastDelta;

		while (true) {
			if (gp.getRunningState() != SimulationEngine.RunningState.PAUSE) {
				lastTime = System.currentTimeMillis();
				engine.tick();
				lastDelta = System.currentTimeMillis() - lastTime;
			}
			else {
				lastDelta = 0;
			}
			try {
				gui.displayArray(engine.getCells());
				Thread.sleep(Math.max(0, gp.getTicks() - lastDelta));
			} catch (InterruptedException e) {
				// Sleep interrupted
			}
		}

	}

}
