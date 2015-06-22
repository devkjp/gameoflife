import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class SwingGUI implements ifGUI {
	
	JFrame frame;
	JPanel gamePanel;
	CellWrapper[][] wrappers;
	
	public SwingGUI(GameParameter gp, Cell[][] cells){
				
		this.frame = new JFrame("Game of Life");
		frame.setSize(new Dimension(600,600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		this.gamePanel = new JPanel();
		this.gamePanel.setLayout(new GridLayout(cells.length, cells[0].length));
		
		JPanel controlPanel = new JPanel();
		
		
		frame.add(gamePanel, BorderLayout.CENTER);
		
		JButton btnPlayPause = new JButton("Play / Pause");
		btnPlayPause.setText((gp.getRunningState() == SimulationEngine.RunningState.RUNNING)? "Pause":"Play");
		
		btnPlayPause.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton src = (JButton) e.getSource();
				if (gp.getRunningState() == SimulationEngine.RunningState.RUNNING){
					src.setText("Play");
					gp.setRunningState(SimulationEngine.RunningState.PAUSE);
				} else {
					src.setText("Pause");
					gp.setRunningState(SimulationEngine.RunningState.RUNNING);
				}
			}
		});
		
		JSlider sldTicks = new JSlider(0,1000,(int) gp.getTicks());
		sldTicks.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider sld = (JSlider) e.getSource();
				gp.setTicks(sld.getValue());
			}
		});
		
		JCheckBox cbTorus = new JCheckBox("Torus Mode");
		cbTorus.setSelected((gp.getEdgeMode() == SimulationEngine.EdgeMode.TORUS));
		cbTorus.addChangeListener( new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JCheckBox cb = (JCheckBox) e.getSource();
				if (cb.isSelected()) {
					gp.setEdgeMode(SimulationEngine.EdgeMode.TORUS);
				} else {
					gp.setEdgeMode(SimulationEngine.EdgeMode.BORDERED);
				}
			}
		});
		
		controlPanel.add(btnPlayPause);
		controlPanel.add(cbTorus);
		controlPanel.add(sldTicks);
		
		frame.add(controlPanel, BorderLayout.NORTH);
		
		
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
