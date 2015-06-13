

public abstract class Rule {
	
	public Cell.State apply(Cell[] neighbourhood, Cell currentCell) throws IncorrectSizeException{
		if (neighbourhood.length != 8) {
			throw new IncorrectSizeException();
		}
		return applyRule(neighbourhood, currentCell);		
	}
	
	protected abstract Cell.State applyRule(Cell[] neighbourhood, Cell currentCell);

}
