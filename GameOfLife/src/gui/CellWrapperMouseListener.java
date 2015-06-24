package gui;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class CellWrapperMouseListener extends MouseAdapter {
	
	public void mouseClicked(final MouseEvent e){
		CellWrapper cw = (CellWrapper) e.getSource();
		cw.toggleCell();
		
	}
}
