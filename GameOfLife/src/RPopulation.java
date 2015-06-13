import java.util.Arrays;

public class RPopulation extends Rule {

	@Override
	protected Cell.State applyRule(Cell[] neighbourhood, Cell currentCell) {

		long liveCellCount = Arrays.stream(neighbourhood)
				.filter(c -> (c.getState() == Cell.State.ALIVE)).count();

		if (currentCell.getState() == Cell.State.ALIVE) {
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
