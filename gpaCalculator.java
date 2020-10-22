/**
 * @author William Zheng
 * Shared ideas with Sandesh Banskota
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;

public class gpaCalculator extends JFrame {
	JPanel panel;
	JLabel instructions1, instructions2, instructions3, coursesEntered, currentGPA, currentGPAval, summary, targInstr, requiredLabel, requiredAmt;
	JLabel suggestions;
	JButton insert, remove, removeAll, add15, calcRequired;
	JTextField subject, creditHour, targetGPA;

	String[] grade = {"N/A", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "E/F"};
	ArrayList<String> items = new ArrayList<String>();
	DefaultListModel<String> model = new DefaultListModel<>();
	JList<String> choices = new JList(model);
	JComboBox<String> comboGrade = new JComboBox<String>(grade);
	
	ArrayList<Double> gpaHours = new ArrayList<Double>();
	ArrayList<Integer> hourList = new ArrayList<Integer>();
	ArrayList<Integer> blankHours = new ArrayList<Integer>();
	double gpa = 0.0;
	double totHours = 0.0;
	double aveGpa = 0.0;
	double nullHours = 0.0;	
	double negHours = 0.0;
	
	public gpaCalculator() {
		instructions1 = new JLabel("Enter the Credit Hours (Required): ");
		instructions2 = new JLabel("Enter Course Name (Optional):");
		instructions3 = new JLabel("Select the Grade (Optional):");
		targInstr = new JLabel("Enter your Target GPA: ");
		summary = new JLabel("SUMMARY");
		coursesEntered = new JLabel("Courses Entered: ");
		currentGPA = new JLabel("Current GPA:");
		currentGPAval = new JLabel("0.0");
		insert = new JButton("Insert");
		insert.addActionListener(new InsertListener());
		remove = new JButton("Remove Course");
		remove.addActionListener(new RemoveListener());
		removeAll = new JButton("Clear List");
		removeAll.addActionListener(new RemoveAllListener());
		add15 = new JButton("Add 15 Blank Credit Hours");
		add15.addActionListener(new Add15Listener());
		subject = new JTextField("N/A",10);
		creditHour = new JTextField(3);
		targetGPA = new JTextField(3);
		requiredLabel = new JLabel("Required GPA:");
		calcRequired = new JButton("Calculate Required GPA");
		calcRequired.addActionListener(new CalculateRequiredListener());
		requiredAmt = new JLabel("           ");
		suggestions = new JLabel("Suggestions");
		
		setLayout(null);
		Dimension instrSize1 = instructions1.getPreferredSize();
		Dimension instrSize2 = instructions2.getPreferredSize();
		Dimension instrSize3 = instructions3.getPreferredSize();
		Dimension subjSize = subject.getPreferredSize();
		Dimension credSize = creditHour.getPreferredSize();
		Dimension combSize = comboGrade.getPreferredSize();
		Dimension insertSize = insert.getPreferredSize();
		Dimension removeSize = remove.getPreferredSize();
		Dimension removeAllSize = removeAll.getPreferredSize();
		Dimension curGpaSize = currentGPA.getPreferredSize();
		Dimension curValSize = currentGPAval.getPreferredSize();
		Dimension entList = coursesEntered.getPreferredSize();
		Dimension summSize = summary.getPreferredSize();
		Dimension targSize = targetGPA.getPreferredSize();
		Dimension targInstSize = targInstr.getPreferredSize();
		Dimension reqLabelSize = requiredLabel.getPreferredSize();
		Dimension add15Size = add15.getPreferredSize();
		Dimension calcReqSize = calcRequired.getPreferredSize();
		Dimension reqAmtSize = requiredAmt.getPreferredSize();
		
		instructions1.setBounds(10,5, instrSize1.width, instrSize1.height);
		instructions2.setBounds(10,45, instrSize2.width, instrSize2.height);
		instructions3.setBounds(10,90, instrSize3.width, instrSize3.height);
		subject.setBounds(10, 60, subjSize.width, subjSize.height);
		creditHour.setBounds(10, 20, credSize.width, credSize.height);
		comboGrade.setBounds(7, 105, combSize.width, combSize.height);
		insert.setBounds(5, 130, insertSize.width, insertSize.height);
		remove.setBounds(250, 420, removeSize.width, removeSize.height);
		removeAll.setBounds(250, 450, removeAllSize.width, removeAllSize.height);
		choices.setBounds(250, 20, 520, 400);
		currentGPA.setBounds(15, 450, curGpaSize.width, curGpaSize.height);
		currentGPAval.setBounds(100, 450, 40, 18);
		summary.setBounds(15, 430, summSize.width, summSize.height);
		targetGPA.setBounds(10, 500, targSize.width, targSize.height);
		targInstr.setBounds(15, 485, targInstSize.width, targInstSize.height);
		requiredLabel.setBounds(15, 600, reqLabelSize.width, reqLabelSize.height);
		add15.setBounds(8, 555, add15Size.width, add15Size.height);
		calcRequired.setBounds(8, 530, calcReqSize.width, calcReqSize.height);
		requiredAmt.setBounds(110, 600, reqAmtSize.width, reqAmtSize.height);
		suggestions.setBounds(15, 590, 500, 100);
		coursesEntered.setBounds(250, 0, entList.width, entList.height);
		
		add(instructions1);
		add(creditHour);
		add(instructions2);
		add(subject);
		add(instructions3);
		add(comboGrade);
		add(insert);
		add(remove);
		add(removeAll);
		add(choices);
		add(currentGPA);
		add(currentGPAval);
		add(coursesEntered);
		add(summary);
		add(targInstr);
		add(targetGPA);
		add(requiredLabel);
		add(add15);
		add(calcRequired);
		add(requiredAmt);
		add(suggestions);
		
		
		
		
		

		
		setTitle("GPA Calculator");
		setSize(800,700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	


	private class InsertListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			int posHours = Integer.parseInt(creditHour.getText());

			String g = (String) comboGrade.getSelectedItem();
			String stuff = "Credit Hours: " + creditHour.getText() + ", Course Name: " + subject.getText() + ", Grade: " + comboGrade.getSelectedItem();
			
			items.add(stuff);

			model.addElement(items.get(items.size()-1));
		
			if (g.equals("N/A")) {
				gpaHours.add(0.0);
				hourList.add(0);
				nullHours += posHours;
			}
			else if (g.equals("A")) {
				gpaHours.add(4.0*posHours);
				hourList.add(posHours);
			}
			else if (g.equals("A-")) {
				gpaHours.add(3.67*posHours);
				hourList.add(posHours);
			}
			else if (g.equals("B+")) {
				gpaHours.add(3.33*posHours);
				hourList.add(posHours);
				}
			else if (g.equals("B")) {
				gpaHours.add(3.00*posHours);
				hourList.add(posHours);
				}
			else if (g.equals("B-")) {
				gpaHours.add(2.67*posHours);
				hourList.add(posHours);
			}
			else if (g.equals("C+")) {
				gpaHours.add(2.33*posHours);
				hourList.add(posHours);
			}
			else if (g.equals("C")) {
				gpaHours.add(2.00*posHours);
				hourList.add(posHours);
			}
			else if (g.equals("C-")) {
				gpaHours.add(1.67*posHours);
				hourList.add(posHours);
			}
			else if (g.equals("D+")) {
				gpaHours.add(1.33*posHours);
				hourList.add(posHours);
			}
			else if (g.equals("D")) {
				gpaHours.add(1.00*posHours);
				hourList.add(posHours);
			}
			else if (g.equals("E/F")) {
				gpaHours.add(0.00*posHours);
				hourList.add(posHours);
			}
			
			gpa += gpaHours.get(gpaHours.size()-1);
			totHours += hourList.get(hourList.size()-1);
			if (totHours != 0) {
				aveGpa = (gpa/totHours);
				aveGpa = Math.round(aveGpa * 100);
				aveGpa = aveGpa/100;
			}
			else
			{
				aveGpa = 0.0;
			}
			currentGPAval.setText(aveGpa+"");
		}
	}
	private class RemoveListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent r) {
			int selectedIndex = choices.getSelectedIndex();
			
			if ((model.getElementAt(selectedIndex).substring(model.getElementAt(selectedIndex).length()-3)).equals("N/A")) {
				String[] parts = model.getElementAt(selectedIndex).split(" ");
				String part3 = parts[2].replaceAll(",", "");
				negHours = Integer.parseInt(part3);
				nullHours -= negHours;
			}

			
			if (selectedIndex != -1) {

				model.remove(selectedIndex);
				
			}
			
			
			
			
			
			//nullHours -= blankHours.get(blankIndex);
			
			gpa -= gpaHours.get(selectedIndex);
			totHours -= hourList.get(selectedIndex);
			if (totHours != 0) {
				aveGpa = (gpa/totHours);
				aveGpa = Math.round(aveGpa * 100);
				aveGpa = aveGpa/100;
			}
			else
			{
				aveGpa = 0.0;
			}
			currentGPAval.setText(aveGpa+"");
			//blankHours.remove(blankIndex);
			gpaHours.remove(selectedIndex);
			hourList.remove(selectedIndex);
		}
	}
	private class RemoveAllListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent ra) {
			model.clear();
			aveGpa = 0.0;
			gpa = 0.0;
			totHours = 0.0;
			nullHours = 0.0;
			currentGPAval.setText("");
			gpaHours.clear();
			hourList.clear();	
			blankHours.clear();
		}
	}
	private class CalculateRequiredListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent c) {

			double target = Double.parseDouble(targetGPA.getText());
			double required = ((target)*(nullHours+totHours) - (aveGpa)*(totHours))/(nullHours);
			required = Math.round(required * 100);
			required = required/100;
			requiredAmt.setText(required+"");
			

			if (items.size() == 0) {
				suggestions.setText("You don't have any classes for calcualating Required GPA");
			}
			else if (required > 4.0) {
				suggestions.setText("You should try adding more credit hours");
			}
			else if (required >= 2 && required <= 4.0)
				suggestions.setText("");
			else if (required < 2.0) {
				suggestions.setText("You could take fewer credit hours if you wish");
			}
			
		}
	}
	private class Add15Listener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent a) {
			nullHours += 15;
			String stuff = "Credit Hours: " + 15 + ", Course Name: " + subject.getText() + ", Grade: N/A";
			items.add(stuff);
			model.addElement(items.get(items.size()-1));
		}
	}
	public static void main(String[] args) {
		new gpaCalculator();
	}
}