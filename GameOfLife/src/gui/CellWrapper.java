package gui;
import java.awt.Color;

import javax.swing.JPanel;

import engine.Cell;

public class CellWrapper extends JPanel {

	private Cell cell;

	public CellWrapper(Cell cell) {
		//System.out.print("-");
		this.cell = cell;
		setBackground(getColorByCell());
	}
	
	public void toggleCell(){
		cell.toggle();
	}

	private Color getColorByCell() {
		if (cell.isAlive()) {

			switch (cell.getAge()) {
			case 0:
				return new Color(0.8f, 0.8f, 0.8f);
			case 1:
				return new Color(0.6f, 0.6f, 0.6f);
			case 2:
				return new Color(0.4f, 0.4f, 0.4f);
			case 3:
				return new Color(0.2f, 0.2f, 0.2f);
			default:
				return new Color(0f, 0.f, 0f);
			}
		} else {
			return new Color(1f, 1f, 1f);
		}
	}

	public void updateColor(int width, int height) {
		this.setSize(width,height);
		setBackground(getColorByCell());
		
	}
}
