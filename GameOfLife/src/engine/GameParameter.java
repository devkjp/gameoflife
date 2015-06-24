package engine;

public class GameParameter {
	
	private SimulationEngine.EdgeMode edgeMode;
	private SimulationEngine.RunningState runningState;
	
	private long ticks;
	
	public GameParameter(SimulationEngine.EdgeMode edgeMode, SimulationEngine.RunningState runningState, long ticks){
		this.edgeMode = edgeMode;
		this.runningState = runningState;
		this.ticks = ticks;
	}

	public SimulationEngine.EdgeMode getEdgeMode() {
		return edgeMode;
	}

	public void setEdgeMode(SimulationEngine.EdgeMode edgeMode) {
		this.edgeMode = edgeMode;
	}

	public SimulationEngine.RunningState getRunningState() {
		return runningState;
	}

	public void setRunningState(SimulationEngine.RunningState runningState) {
		this.runningState = runningState;
	}

	public long getTicks() {
		return ticks;
	}

	public void setTicks(long ticks) {
		this.ticks = ticks;
	}

}
