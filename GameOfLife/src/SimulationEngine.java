import java.util.Arrays;
import java.util.stream.IntStream;

public class SimulationEngine {

	static enum EdgeMode {
		TORUS, BORDERED
	};

	static enum RunningState {
		RUNNING, PAUSE
	}

	private Cell[][] cells;
	private Rule[] rules;
	private GameParameter gp;
	
	public SimulationEngine(GameParameter gp, Rule[] rules, int xSize, int ySize) {

		this.gp = gp;
		this.rules = rules;

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

			Cell[] neighbourhood = new Cell[8];

			IntStream.range(0, cells.length).forEach( x -> {
				IntStream.range(0, cells[0].length).forEach( y -> {
					neighbourhood[0] = getCell(x - 1, y - 1);
					neighbourhood[1] = getCell(x, y - 1);
					neighbourhood[2] = getCell(x + 1, y - 1);
					neighbourhood[3] = getCell(x - 1, y);
					neighbourhood[4] = getCell(x + 1, y);
					neighbourhood[5] = getCell(x - 1, y + 1);
					neighbourhood[6] = getCell(x, y + 1);
					neighbourhood[7] = getCell(x + 1, y + 1);
					
					applyRulesAndBufferState(neighbourhood, x, y);
				});
			});

			// persist buffer state
			Arrays.stream(cells).forEach( row -> Arrays.stream(row).forEach( cell -> saveNewCellState(cell) ) );


		}
	
	private void saveNewCellState(Cell c){
		c.persistBufferState();
		c.age();
	}

	private void applyRulesAndBufferState(Cell[] neighbourhood, int x, int y) {
		Arrays.stream(rules).forEach( r -> {
			try {
				cells[y][x].setBufferState( r.apply(neighbourhood, cells[y][x]) );
			} catch (Exception e) {
				System.out.println("Implementation Error!");
				e.printStackTrace();
				System.exit(-1);
			}
			
		});
		
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
			if (this.gp.getEdgeMode() == EdgeMode.TORUS) {
				x = this.cells[0].length - 1;
			} else {
				return new Cell();
			}
		} else if (x >= this.cells[0].length) {
			if (this.gp.getEdgeMode() == EdgeMode.TORUS) {
				x = 0;
			} else {
				return new Cell();
			}
		}

		if (y < 0) {
			if (this.gp.getEdgeMode() == EdgeMode.TORUS) {
				y = this.cells.length - 1;
			} else {
				return new Cell();
			}
		} else if (y >= this.cells.length) {
			if (this.gp.getEdgeMode() == EdgeMode.TORUS) {
				y = 0;
			} else {
				return new Cell();
			}
		}

		return this.cells[y][x];
	}
}
