import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class SwingGUI implements ifGUI {
	
	JFrame frame;
	JPanel gamePanel;
	CellWrapper[][] wrappers;
	
	public SwingGUI(SimulationEngine engine){
		
		Cell[][] cells = engine.getCells();
		
		this.frame = new JFrame("Game of Life");
		frame.setSize(new Dimension(600,600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		this.gamePanel = new JPanel();
		this.gamePanel.setLayout(new GridLayout(cells.length, cells[0].length));
		
		frame.add(gamePanel, BorderLayout.CENTER);
		
		JButton btnPlayPause = new JButton("Play / Pause");
		btnPlayPause.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				engine.toggleRunningState();
			}
		});
		
		frame.add(btnPlayPause, BorderLayout.NORTH);
		
		wrappers = new CellWrapper[cells.length][cells[0].length];	
		CellWrapperMouseListener listener = new CellWrapperMouseListener();
		
		for (int i=0 ;i<wrappers.length; i++){
			for (int j=0; j<wrappers[0].length; j++){
				wrappers[i][j] = new CellWrapper(cells[i][j]);
				gamePanel.add(wrappers[i][j]);
				wrappers[i][j].addMouseListener(listener);
			}
		}	
		frame.setVisible(true);
	}

	@Override
	public void displayArray(Cell[][] cells) {
		int cellWidth = gamePanel.getWidth() / cells[0].length;
		int cellHeight = gamePanel.getHeight() / cells.length;	

		for (CellWrapper[] row: wrappers){
			for (CellWrapper wrapper: row){
				wrapper.updateColor(cellWidth, cellHeight);
			}
		}
		gamePanel.validate();
	}

}
