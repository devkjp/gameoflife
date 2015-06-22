
public class Main {

	public static void main(String[] args) {
		
		
		Rule[] rules = {new RPopulation()};
		
		SimulationEngine engine = new SimulationEngine(SimulationEngine.EdgeMode.TORUS, rules, 24, 24);
		
		engine.setCellAtTo(11, 12, Cell.State.ALIVE);
		engine.setCellAtTo(12, 12, Cell.State.ALIVE);
		engine.setCellAtTo(13, 12, Cell.State.ALIVE);
		
		ifGUI gui = new SwingGUI(engine);
		//ifGUI gui = new ConsoleGUI();
		
		long lastTime;
		long lastDelta;
		
		while(true)
		{
			lastTime = System.currentTimeMillis();
			engine.tick();
			gui.displayArray(engine.getCells());
			lastDelta = System.currentTimeMillis() - lastTime;
			try {
				Thread.sleep(Math.max(0, 100 - lastDelta));
			} catch (InterruptedException e) {
				// Sleep interrupted
			}
		}
		
		
		

	}

}
