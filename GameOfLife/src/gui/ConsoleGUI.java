package gui;
import java.util.Arrays;

import engine.Cell;


public class ConsoleGUI implements ifGUI {

	@Override
	public void displayArray(Cell[][] cells) {
		System.out.print("\n\n\n");
		
		Arrays.stream(cells).forEach( row -> {
			
			Arrays.stream(row).forEach(cell -> System.out.print(getSymbolForCell(cell)));
			System.out.println();
		});
		

	}

	private String getSymbolForCell(Cell cell) {
		
		if(cell.isAlive()){
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
