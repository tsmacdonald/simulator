package edu.wheaton.simulator.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SetupScreen extends Screen {

	private JTextField nameField;

	private JTextField width;

	private JTextField height;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8347080877399964861L;

	public SetupScreen(final ScreenManager sm) {
		super(sm);
		this.setLayout(new BorderLayout());
		JLabel label = new JLabel("Simulation Setup");
		label.setPreferredSize(new Dimension(300, 150));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(label, BorderLayout.NORTH);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setAlignmentX(CENTER_ALIGNMENT);
		JLabel nameLabel = new JLabel ("Name: ");
		nameLabel.setMaximumSize(new Dimension(100, 40));
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameField = new JTextField(sm.getGUIname(), 25);
		nameField.setMaximumSize(new Dimension(400, 30));
		JLabel widthLabel = new JLabel("Width: ");
		widthLabel.setMaximumSize(new Dimension(100, 40));
		widthLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		width = new JTextField(sm.getGUIwidth()+"", 10);
		width.setMaximumSize(new Dimension(80, 30));
		JLabel heightLabel = new JLabel("Height: ");
		heightLabel.setMaximumSize(new Dimension(210, 40));
		heightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		height = new JTextField(sm.getGUIheight()+"", 0);
		height.setMaximumSize(new Dimension(80, 30));
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
		panel1.add(nameLabel);
		panel1.add(nameField);
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		panel2.add(heightLabel);
		panel2.add(height);
		panel2.add(widthLabel);
		panel2.add(width);
		JPanel buttonPanel = new JPanel();
		JButton finishButton = new JButton("Finish");
		finishButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						sm.updateGUIManager(nameField.getText(), width.getText(), height.getText());
						JPanel[][] grid = new JPanel[GUIManager.getGridWidth()][GUIManager.getGridHeight()];
						for (int j = 0; j < GUIManager.getGridWidth(); j++){
							//TODO figure out memory space issue. Also centralize grid making, grid factory?
				            for (int i = 0; i < GUIManager.getGridHeight(); i++) {
				                grid[i][j] = new JPanel();
				                grid[i][j].setOpaque(false);
				                grid[i][j].setBorder(BorderFactory.createEtchedBorder());
				            }	
						}
						sm.setGrid(grid);
						sm.update(sm.getScreen("Edit Simulation"));
					}
				}
				);
		JButton backButton = new JButton("Back");
		backButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						sm.update(sm.getScreen("Edit Simulation"));
					}
				}
				);
		buttonPanel.add(backButton);
		buttonPanel.add(finishButton);
		mainPanel.add(panel1);
		mainPanel.add(panel2);
		this.add(mainPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}

	@Override
	public void sendInfo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public void updateSetUpScreen(String nameString, String width, String height) {
		nameField.setText(nameString);
		this.width.setText(width);
		this.height.setText(height);
	}

}
