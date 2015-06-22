import java.util.Arrays;
import java.util.stream.IntStream;

public class SimulationEngine {

	static enum EdgeMode {
		TORUS, BORDERED
	};

	static enum RunningState {
		RUNNING, PAUSE
	}

	private final EdgeMode mode;
	private RunningState runningState;

	private Cell[][] cells;
	private Rule[] rules;

	public SimulationEngine(EdgeMode mode, Rule[] rules, int xSize, int ySize) {

		this.mode = mode;
		this.rules = rules;
		this.runningState = RunningState.RUNNING;

		this.cells = new Cell[ySize][xSize];
		for (int y = 0; y < ySize; y++) {
			for (int x = 0; x < xSize; x++) {
				cells[y][x] = new Cell();
			}
		}

	}

	public Cell[][] getCells() {
		return cells;
	}

	public void setCellAtTo(int x, int y, Cell.State state) {
		this.cells[y][x].setBufferState(state);
		this.cells[y][x].persistBufferState();
	}

	/**
	 * One tick of the simulation
	 */
	public void tick() {
		if (this.runningState == RunningState.RUNNING) {
			try {

				Cell[] neighbourhood = new Cell[8];

				// Set Buffer State for each cell
				for (int y = 0; y < cells.length; y++) {
					for (int x = 0; x < cells[0].length; x++) {

						neighbourhood[0] = getCell(x - 1, y - 1);
						neighbourhood[1] = getCell(x, y - 1);
						neighbourhood[2] = getCell(x + 1, y - 1);
						neighbourhood[3] = getCell(x - 1, y);
						neighbourhood[4] = getCell(x + 1, y);
						neighbourhood[5] = getCell(x - 1, y + 1);
						neighbourhood[6] = getCell(x, y + 1);
						neighbourhood[7] = getCell(x + 1, y + 1);

						for (Rule r : rules) {
							Cell.State newState = r.apply(neighbourhood,
									cells[y][x]);
							cells[y][x].setBufferState(newState);
						}

					}
				}

				// persist buffer state
				for (int y = 0; y < cells.length; y++) {
					for (int x = 0; x < cells[0].length; x++) {
						cells[y][x].persistBufferState();
						cells[y][x].age();
					}
				}

			} catch (IncorrectSizeException e) {
				System.out.println("Implementation Error!");
				System.exit(-1);
			}
		}
	}
	
	public void toggleRunningState(){
		this.runningState = (this.runningState == RunningState.RUNNING)? RunningState.PAUSE : RunningState.RUNNING;
	}

	/**
	 * Returns a Cell at the given place in the cell array and treats borders in
	 * the right way
	 * 
	 * @param x
	 *            x value of cell in array
	 * @param y
	 *            y value of cell in array
	 * @return Cell at place (x|y) in array
	 */
	private Cell getCell(int x, int y) {
		if (x < 0) {
			if (this.mode == EdgeMode.TORUS) {
				x = this.cells[0].length - 1;
			} else {
				return new Cell();
			}
		} else if (x >= this.cells[0].length) {
			if (this.mode == EdgeMode.TORUS) {
				x = 0;
			} else {
				return new Cell();
			}
		}

		if (y < 0) {
			if (this.mode == EdgeMode.TORUS) {
				y = this.cells.length - 1;
			} else {
				return new Cell();
			}
		} else if (y >= this.cells.length) {
			if (this.mode == EdgeMode.TORUS) {
				y = 0;
			} else {
				return new Cell();
			}
		}

		return this.cells[y][x];
	}
}
