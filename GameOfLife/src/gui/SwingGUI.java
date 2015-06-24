package gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import engine.Cell;
import engine.GameParameter;
import engine.SimulationEngine;
import engine.SimulationEngine.EdgeMode;
import engine.SimulationEngine.RunningState;


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
				gp.setTicks(sldTicks.getValue());
			}
		});
		
		JCheckBox cbTorus = new JCheckBox("Torus Mode");
		cbTorus.setSelected((gp.getEdgeMode() == SimulationEngine.EdgeMode.TORUS));
		cbTorus.addChangeListener( new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if (cbTorus.isSelected()) {
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
		
		IntStream.range(0, wrappers.length).forEach( i -> {
			IntStream.range(0, wrappers[0].length).forEach( j -> {
				wrappers[i][j] = new CellWrapper(cells[i][j]);
				gamePanel.add(wrappers[i][j]);
				wrappers[i][j].addMouseListener(listener);
			});
		});
		
		frame.setVisible(true);
	}

	@Override
	public void displayArray(Cell[][] cells) {
		int cellWidth = gamePanel.getWidth() / cells[0].length;
		int cellHeight = gamePanel.getHeight() / cells.length;	

		Arrays.stream(wrappers).forEach( 
				row -> Arrays.stream(row).forEach( 
						wrapper -> wrapper.updateColor(cellWidth, cellHeight)));
		
		gamePanel.validate();
	}

}
