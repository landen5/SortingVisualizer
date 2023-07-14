import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class LayoutPanel extends JPanel implements ActionListener {
	
	// after creating BarPanel, uncomment out the three lines
	
	private OptionsPanel optionP;
	private BarPanel barP;
	
	public LayoutPanel() {
		this.setLayout(new BorderLayout());
		
		optionP = new OptionsPanel(this);
		this.add(optionP, BorderLayout.NORTH);

		barP = new BarPanel(this);
		this.add(barP, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(optionP.isAutoRunOn()) {
			barP.stepForward();
		}
	}
	
	public void performOption(OptionEvent type) {
		if(type == OptionEvent.ADD) {
			barP.addBar();
		}
		if(type == OptionEvent.ADD10) {
			for(int i = 0; i< 10; i++) {
				barP.addBar();
			}
		}
		if(type == OptionEvent.RANDOMIZE) {
			barP.randomize();
		}
		if(type == OptionEvent.SORT) {
			if(optionP.getSortChoice() == "BUBBLE SORT") {
				barP.bubbleSort();
			}
			if(optionP.getSortChoice() == "SELECTION SORT") {
				barP.selectionSort();
			}
			if(optionP.getSortChoice() == "INSERTION SORT") {
				barP.insertionSort();
			}
			if(optionP.getSortChoice() == "MERGE SORT") {
				barP.mergeSort();
			}
		}
		if(type == OptionEvent.STEP) {
			barP.stepForward();
		}
		
	}
}