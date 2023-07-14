import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class OptionsPanel extends JPanel implements ActionListener {
	
	private JButton[] buttons;
	private LayoutPanel layoutP;
	private JComboBox<String> sortAlgorithms;

	
	JCheckBox autorunToggle;
	
	public OptionsPanel(LayoutPanel lp) {
		layoutP = lp;
		
		this.sortAlgorithms = new JComboBox<String>();
		this.sortAlgorithms.addItem("SELECTION SORT");
		this.sortAlgorithms.addItem("BUBBLE SORT");
		this.sortAlgorithms.addItem("INSERTION SORT");
		this.sortAlgorithms.addItem("MERGE SORT");
		this.add(sortAlgorithms);

		
		buttons = new JButton[5];
		buttons[0] = new JButton("add");
		buttons[1] = new JButton("add10");
		buttons[2] = new JButton("randomize");
		buttons[3] = new JButton("step");
		buttons[4] = new JButton("sort");
		
		for (JButton b : buttons) {
			this.add(b);
			b.addActionListener(this);
		}
		
		this.autorunToggle = new JCheckBox("Autorun");
		this.add(autorunToggle);
	}
	
	public String getSortChoice() {
		return (String) this.sortAlgorithms.getSelectedItem();
	}
	
	public boolean isAutoRunOn() { 
		if(autorunToggle.isSelected() == true) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() instanceof JButton) {
			JButton b = (JButton) arg0.getSource();
			System.out.println("button " + b.getText() + " was pressed");
			
			if (b.getText().equals("add")) {
				layoutP.performOption(OptionEvent.ADD);
			} 
			else if (b.getText().equals("add10")) {
				layoutP.performOption(OptionEvent.ADD10);
			} 
			else if (b.getText().equals("randomize")) {
				layoutP.performOption(OptionEvent.RANDOMIZE);
			}
			else if (b.getText().equals("sort")) {
				layoutP.performOption(OptionEvent.SORT);
			}
			else if (b.getText().equals("step")) {
				layoutP.performOption(OptionEvent.STEP);
			}
		}
	}
}