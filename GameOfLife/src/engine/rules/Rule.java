package engine.rules;

import engine.Cell;
import engine.IncorrectSizeException;


public abstract class Rule {
	
	public boolean apply(Cell[] neighbourhood, Cell currentCell) throws IncorrectSizeException{
		if (neighbourhood.length != 8) {
			throw new IncorrectSizeException();
		}
		return applyRule(neighbourhood, currentCell);		
	}
	
	protected abstract boolean applyRule(Cell[] neighbourhood, Cell currentCell);

}
