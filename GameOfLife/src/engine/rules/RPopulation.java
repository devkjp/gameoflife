package engine.rules;
import java.util.Arrays;

import engine.Cell;

public class RPopulation extends Rule {

	@Override
	protected boolean applyRule(Cell[] neighbourhood, Cell currentCell) {

		long liveCellCount = Arrays.stream(neighbourhood)
				.filter(c -> (c.isAlive() == Cell.State.ALIVE)).count();

		if (currentCell.isAlive() == Cell.State.ALIVE) {
			if (liveCellCount < 2 || liveCellCount > 3) {
				return Cell.State.DEAD;
			} else {
				return Cell.State.ALIVE;
			}
		} else {
			if (liveCellCount == 3){
				return Cell.State.ALIVE;
			} else {
				return Cell.State.DEAD;
			}
		}

	}
}
