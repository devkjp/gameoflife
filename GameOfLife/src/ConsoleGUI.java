
public class ConsoleGUI implements ifGUI {

	@Override
	public void displayArray(Cell[][] cells) {
		System.out.printf("\n\n\n");
		
		for (Cell[] row: cells)
		{
			for (Cell cell : row)
			{
				System.out.print(getSymbolForCell(cell));	
			}
			System.out.println();
		}
		

	}

	private String getSymbolForCell(Cell cell) {
		
		if(cell.getState() == Cell.State.DEAD){
			return " ";
		} else {
			switch (cell.getAge()){
				case 0: return "."; 
				case 1: return "-"; 
				case 2: return "+";
				case 3: return "*";
				default: return "#";
			}
		}
		
		
	}

}
