import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class BarPanel extends JPanel {
	
	//fields
	ArrayList<Bar> bars;
	LayoutPanel layoutPanel;
	private static int STEPSIZE = 30;
	
	ArrayList<Step> steps;
	int stepCounter;
	
	ArrayList<Bar> startBars;
	
	private ArrayList<Bar> buffer;
	
	//constructor
	public BarPanel(LayoutPanel lp) {
		lp = this.layoutPanel;
		bars = new ArrayList<Bar>();
		steps = new ArrayList<Step>();
		stepCounter = -1;	
		
		startBars = new ArrayList<Bar>();
		
		buffer = new ArrayList<Bar>();
		
	}
	
	
	public void addBar() {
		int randHeight = (int)(Math.random() * (this.getHeight() - STEPSIZE) + STEPSIZE);
		Bar b = new Bar(randHeight);
		bars.add(b);
		buffer.add(null); 
		//System.out.println(bars.get(0).getHeight());
		this.repaint();
	}
	
	public void paintComponent(Graphics g) { //verify that importing graphics is correct
		super.paintComponent(g);
		if(bars.size() == 0) {
			return;
		}
		int barWidth = this.getWidth()/bars.size();
		int totalBarWidth = barWidth * bars.size();
		int startX = (this.getWidth()/2) - (totalBarWidth/2);
		
		for(int i = 0; i < bars.size(); i++) {
			Bar tempBar = bars.get(i);
			g.setColor(tempBar.getColor());
			g.fillRect(getBarX(i), this.getHeight()-tempBar.getHeight() -50, barWidth, tempBar.getHeight()); //THIS IS NOT CORRECT! y cord is height - barHeight
			//startX += barWidth;
		}
		this.drawBuffer(g);
		drawStepMark(g);
	}
	
	private void swapBars(int index1, int index2) {
		Bar temp1 = bars.get(index1);
		Bar temp2 = bars.get(index2);
		bars.set(index1, temp2);
		bars.set(index2, temp1);
	}
	
	public void randomize() {
		for(int j = 0; j < bars.size(); j++) {
			int k = (int)(Math.random() * (bars.size() - 0)); // (max - min) + min
			swapBars(j, k);
			this.repaint();
		}
		clearSteps(); // verify this is correct plaement
	}
	
	private int getBarX(int index) {
		int barWidth = this.getWidth()/bars.size();
		int totalBarWidth = barWidth * bars.size();
		int startX = (this.getWidth()/2) - (totalBarWidth/2);
		int xcord = startX + barWidth * (index);
		return xcord;
		
	} 
	
	private void drawStepMark(Graphics g) {
		if(stepCounter == -1 || stepCounter == steps.size()) {
			return;
		}
		Step s = steps.get(stepCounter);
		int x1 = getBarX(s.getIndex1());
		int x2 = getBarX(s.getIndex2());
		if(s.getType() == Step.COMPARE) {
			g.setColor(Color.RED);
		}
		if(s.getType() == Step.SWAP) {
			g.setColor(Color.ORANGE);
		}
		g.fillRect(x1, this.getHeight()-10, this.getWidth()/bars.size(), 10);
		g.fillRect(x2, this.getHeight()-10, this.getWidth()/bars.size(), 10);
	}
	
	private void storeStartBars() {
		startBars.clear();
		for(int i = 0; i < bars.size(); i++) {
			startBars.add(i, bars.get(i));
		}
	}
	
	private void restoreStart() {
		bars.clear();
		for(int i = 0; i < startBars.size(); i++) {
			bars.add(i, startBars.get(i));
		}
		stepCounter = 0;
	}
	
	private void clearSteps() {
		steps.clear();
		stepCounter = -1;
	}
	
	private void drawBuffer(Graphics g) {
		for(int i = 0; i < buffer.size(); i++) {
			Bar bar = buffer.get(i);
			if(bar == null) {
				continue;
			}
			g.setColor(Color.black);
			int x = getBarX(i);
			int barWidth = this.getWidth()/bars.size();
			g.drawRect(x, this.getHeight()-10, barWidth, bar.getHeight());
		}
	}
	
	public void stepForward() {
		if(stepCounter == -1 || stepCounter == steps.size()) {
			System.out.println("Step counter is: " + stepCounter);
			return;
		}
		System.out.println("doing step " + stepCounter);
		Step s = steps.get(stepCounter);
		System.out.println("step has " + s.getIndex1() + " " + s.getIndex2());
		if(s.getType() == Step.SWAP) {
			swapBars(s.getIndex1(), s.getIndex2());
		}
	
		if(s.getType() == Step.TOBUFFER) {
			buffer.set(s.getIndex1(), bars.get(s.getIndex2())); //error
		}
		if(s.getType() == Step.TOBARS) {
			bars.set(s.getIndex1(), buffer.get(s.getIndex2())); //error
			buffer.set(s.getIndex2(), null);
		}
		
		stepCounter++;
		//System.out.println("Step counter is: " + stepCounter);
		this.repaint();
	}
	
	//sorting
	
	public void bubbleSort() {
		if(bars.size() == 0) {
			return;
		}
		else if (bars.size() > 0) { // verify this is correct placement
			clearSteps();
		}
		storeStartBars();
		for(int sortedIndex = bars.size(); sortedIndex > 0; sortedIndex--) {
			for(int index = 0; index < sortedIndex -1; index++) {
				Bar bar1 = bars.get(index);
				Bar bar2 = bars.get(index+1);
				
				Step compareStep = new Step(index, index+1, Step.COMPARE);
				steps.add(compareStep);
				
				if(bar1.getHeight() > bar2.getHeight()) {
					Step swapStep = new Step(index, index+1, Step.SWAP);
					steps.add(swapStep);
					
					swapBars(index, index+1);
				}
			}
			this.repaint();
		}
		restoreStart();	
	}
	
	public void selectionSort() {
		if(bars.size() == 0) {
			return;
		}
		clearSteps();
		storeStartBars();
		for(int j = 0; j < bars.size(); j++) {
			int minIndex = j;
			for(int k = j+1; k < bars.size(); k++) {
				System.out.println("test");
				Step compareStep = new Step(minIndex, k, Step.COMPARE);
				steps.add(compareStep);
				
				if(bars.get(minIndex).getHeight() > bars.get(k).getHeight()) {
					minIndex = k; // dont think this is correct
				}
			}
			Step swapStep = new Step(minIndex, j, Step.SWAP);
			steps.add(swapStep);
			
			swapBars(minIndex, j);
		}
		restoreStart();
		this.repaint();
	}
	
	public void insertionSort() {
		if(bars.size() == 0) {
			return;
		}
		clearSteps();
		storeStartBars();
		for(int insertIndex = 1; insertIndex < bars.size(); insertIndex++) {
			// each pass, insert another value into the sorted portion of array
			
			int newValue = bars.get(insertIndex).getHeight();
			for(int index = insertIndex -1; index >= 0; index--) {

				int curValue = bars.get(index).getHeight();
				
				Step compareStep = new Step(index, index+1, Step.COMPARE);
				steps.add(compareStep);
				
				if(newValue < curValue) {
					//swap @ indices index and index + 1
					
					Step swapStep = new Step(index, index+1, Step.SWAP);
					steps.add(swapStep);
					
					swapBars(index, index+1);
				}
				else {
					break;
				}
			}
		}
		restoreStart();
		this.repaint();

	}
	
	public void mergeSort() {
		storeStartBars();
		mergeSortRecursion(0, bars.size() - 1);
		restoreStart();
		this.repaint();

	}
	
	public void bufferSetAndSave(int bufferIndex, int barIndex) {
		Step step = new Step(bufferIndex, barIndex, Step.TOBUFFER);
		steps.add(step);
		buffer.set(bufferIndex, bars.get(barIndex));
	}
	
	public void mergeSortRecursion(int lowIndex, int highIndex) {
		if(lowIndex >= highIndex) { 
			return;
		}
		if(lowIndex + 1 == highIndex) {
			
			Step compareStep = new Step(lowIndex, highIndex, Step.COMPARE); //error
			steps.add(compareStep);
			
			if(bars.get(lowIndex).getHeight() > bars.get(highIndex).getHeight()) {
				
				Step swapStep = new Step(lowIndex, highIndex, Step.SWAP);
				steps.add(swapStep);
				
				swapBars(lowIndex, highIndex);
			}
			return;
		}
		
		int midIndex = (lowIndex + highIndex) / 2;
		mergeSortRecursion(lowIndex, midIndex);
		mergeSortRecursion(midIndex+1, highIndex);
		
		int frontIndex = lowIndex;
		int backIndex = midIndex+1;
		
		boolean frontDone = false;
		boolean backDone = false;
		
		for(int bufferIndex = lowIndex; bufferIndex <= highIndex; bufferIndex++) {
			if(frontDone == true) {
				//buffer.set(bufferIndex, bars.get(backIndex));
				bufferSetAndSave(bufferIndex, backIndex);
				backIndex++;
				continue;
			}
			if(backDone == true) {
				//buffer.set(bufferIndex, bars.get(frontIndex));
				bufferSetAndSave(bufferIndex, frontIndex);
				frontIndex++;
				continue;
			}
			
			Bar frontBar = bars.get(frontIndex);
			Bar backBar = bars.get(backIndex);
			
			if(frontBar.getHeight() < backBar.getHeight()) {
				//buffer.set(bufferIndex, frontBar);
				bufferSetAndSave(bufferIndex, frontIndex);
				frontIndex++;
				if(frontIndex > midIndex) { 
					frontDone = true;
				}
			} else {
				//buffer.set(bufferIndex, backBar);
				bufferSetAndSave(bufferIndex, backIndex);
				backIndex++;
				if(backIndex > highIndex) { //POSSIBLE ERROR POINT!********
					backDone = true;
				}
			}
			
		}
		for(int index = lowIndex; index <= highIndex; index++) {
			Step step = new Step(index, index, Step.TOBARS);
			steps.add(step);
			
			bars.set(index, buffer.get(index)); //possible error
			buffer.set(index, null);
			
		}
		
	}
	
}
