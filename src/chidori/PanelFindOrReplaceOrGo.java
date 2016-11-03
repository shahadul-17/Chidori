package chidori;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.Highlight;
import javax.swing.text.Highlighter.HighlightPainter;

public class PanelFindOrReplaceOrGo extends JPanel implements ActionListener, KeyListener {
	
	private int position, counter, highlights;
	private static final long serialVersionUID = 6105722765462785712L;
	private boolean replaced = false;
	
	private JLabel labelFind, labelReplace, labelLineNumber;
	public JTextField textFieldReplace, textFieldFind, textFieldLineNumber;
	private JCheckBox checkBoxMatchCase;
	private JButton buttonFind, buttonReplace, buttonGo, buttonCancel;
	
	private JTextArea textArea;
	
	private Highlighter highlighter;
	
	private HighlightPainter[] highlightPainters = {
			new DefaultHighlighter.DefaultHighlightPainter(Main.DODGER_BLUE),
			new DefaultHighlighter.DefaultHighlightPainter(Main.ORANGE),
			new DefaultHighlighter.DefaultHighlightPainter(Main.VIOLET)
	};
	
	public PanelFindOrReplaceOrGo(JTextArea textArea) {
		this.textArea = textArea;
		highlighter = textArea.getHighlighter();
		
		initialize();
	}
	
	private void initialize() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setBackground(Color.WHITE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		setVisible(false);
		
		labelFind = new JLabel("Find");
		labelFind.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		GridBagConstraints gridBagConstraintsLabelFind = new GridBagConstraints();
		gridBagConstraintsLabelFind.fill = GridBagConstraints.BOTH;
		gridBagConstraintsLabelFind.insets = new Insets(0, 0, 5, 5);
		gridBagConstraintsLabelFind.gridx = 2;
		gridBagConstraintsLabelFind.gridy = 0;
		add(labelFind, gridBagConstraintsLabelFind);
		
		textFieldFind = new JTextField();
		textFieldFind.setColumns(10);
		GridBagConstraints gridBagConstraintsTextFieldFind = new GridBagConstraints();
		gridBagConstraintsTextFieldFind.gridwidth = 11;
		gridBagConstraintsTextFieldFind.insets = new Insets(0, 0, 5, 5);
		gridBagConstraintsTextFieldFind.fill = GridBagConstraints.BOTH;
		gridBagConstraintsTextFieldFind.gridx = 3;
		gridBagConstraintsTextFieldFind.gridy = 0;
		textFieldFind.addKeyListener(this);
		add(textFieldFind, gridBagConstraintsTextFieldFind);
		
		buttonFind = new JButton("Find");
		GridBagConstraints gbc_buttonFind = new GridBagConstraints();
		gbc_buttonFind.fill = GridBagConstraints.BOTH;
		gbc_buttonFind.insets = new Insets(0, 0, 5, 5);
		gbc_buttonFind.gridx = 14;
		gbc_buttonFind.gridy = 0;
		buttonFind.addActionListener(this);
		add(buttonFind, gbc_buttonFind);
		
		labelReplace = new JLabel("Replace");
		labelReplace.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		GridBagConstraints gridBagConstraintsLabelReplace = new GridBagConstraints();
		gridBagConstraintsLabelReplace.fill = GridBagConstraints.BOTH;
		gridBagConstraintsLabelReplace.insets = new Insets(0, 0, 5, 5);
		gridBagConstraintsLabelReplace.gridx = 2;
		gridBagConstraintsLabelReplace.gridy = 1;
		add(labelReplace, gridBagConstraintsLabelReplace);
		
		textFieldReplace = new JTextField();
		GridBagConstraints gridBagConstraintsTextFieldReplace = new GridBagConstraints();
		gridBagConstraintsTextFieldReplace.gridwidth = 11;
		gridBagConstraintsTextFieldReplace.fill = GridBagConstraints.BOTH;
		gridBagConstraintsTextFieldReplace.insets = new Insets(0, 0, 5, 5);
		gridBagConstraintsTextFieldReplace.gridx = 3;
		gridBagConstraintsTextFieldReplace.gridy = 1;
		add(textFieldReplace, gridBagConstraintsTextFieldReplace);
		
		buttonReplace = new JButton("Replace");
		GridBagConstraints gridBagConstraintsButtonReplace = new GridBagConstraints();
		gridBagConstraintsButtonReplace.fill = GridBagConstraints.BOTH;
		gridBagConstraintsButtonReplace.insets = new Insets(0, 0, 5, 5);
		gridBagConstraintsButtonReplace.gridx = 14;
		gridBagConstraintsButtonReplace.gridy = 1;
		buttonReplace.addActionListener(this);
		add(buttonReplace, gridBagConstraintsButtonReplace);
		
		checkBoxMatchCase = new JCheckBox("Match Case");
		checkBoxMatchCase.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		checkBoxMatchCase.setBackground(Color.WHITE);
		GridBagConstraints gridBagConstraintscheckBoxMatchCase = new GridBagConstraints();
		gridBagConstraintscheckBoxMatchCase.gridwidth = 6;
		gridBagConstraintscheckBoxMatchCase.fill = GridBagConstraints.BOTH;
		gridBagConstraintscheckBoxMatchCase.insets = new Insets(0, 0, 5, 5);
		gridBagConstraintscheckBoxMatchCase.gridx = 3;
		gridBagConstraintscheckBoxMatchCase.gridy = 2;
		checkBoxMatchCase.addActionListener(this);
		add(checkBoxMatchCase, gridBagConstraintscheckBoxMatchCase);
		
		labelLineNumber = new JLabel("Line Number");
		labelLineNumber.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		GridBagConstraints gridBagConstraintsLabelLineNumber = new GridBagConstraints();
		gridBagConstraintsLabelLineNumber.fill = GridBagConstraints.BOTH;
		gridBagConstraintsLabelLineNumber.insets = new Insets(0, 0, 5, 5);
		gridBagConstraintsLabelLineNumber.gridx = 2;
		gridBagConstraintsLabelLineNumber.gridy = 3;
		add(labelLineNumber, gridBagConstraintsLabelLineNumber);
		
		textFieldLineNumber = new JTextField();
		textFieldLineNumber.setColumns(10);
		GridBagConstraints gridBagConstraintsTextFieldLineNumber = new GridBagConstraints();
		gridBagConstraintsTextFieldLineNumber.insets = new Insets(0, 0, 5, 5);
		gridBagConstraintsTextFieldLineNumber.gridwidth = 11;
		gridBagConstraintsTextFieldLineNumber.fill = GridBagConstraints.BOTH;
		gridBagConstraintsTextFieldLineNumber.gridx = 3;
		gridBagConstraintsTextFieldLineNumber.gridy = 3;
		textFieldLineNumber.addActionListener(this);
		add(textFieldLineNumber, gridBagConstraintsTextFieldLineNumber);
		
		buttonGo = new JButton("Go");
		GridBagConstraints gridBagConstraintsButtonGo = new GridBagConstraints();
		gridBagConstraintsButtonGo.fill = GridBagConstraints.BOTH;
		gridBagConstraintsButtonGo.insets = new Insets(0, 0, 5, 5);
		gridBagConstraintsButtonGo.gridx = 14;
		gridBagConstraintsButtonGo.gridy = 3;
		buttonGo.addActionListener(this);
		add(buttonGo, gridBagConstraintsButtonGo);
		
		buttonCancel = new JButton("Cancel");
		GridBagConstraints gridBagConstraintsButtonCancel = new GridBagConstraints();
		gridBagConstraintsButtonCancel.fill = GridBagConstraints.BOTH;
		gridBagConstraintsButtonCancel.insets = new Insets(0, 0, 5, 5);
		gridBagConstraintsButtonCancel.gridx = 15;
		gridBagConstraintsButtonCancel.gridy = 3;
		buttonCancel.addActionListener(this);
		add(buttonCancel, gridBagConstraintsButtonCancel);
	}
	
	private void count() {
		int position = 0;
		highlights = 0;
		
		String text = textFieldFind.getText(), textAreaText = textArea.getText();
		
		if (!checkBoxMatchCase.isSelected()) {
			textAreaText = textAreaText.toLowerCase();
			text = text.toLowerCase();
		}
		
		while ((position = textAreaText.indexOf(text, position)) >= 0) {
			position += text.length();
			highlights++;
		}
	}
	
	private void highlight(int currentSelection) {
		highlighter.removeAllHighlights();
		
		String text = textFieldFind.getText();
		
		if (text.length() == 0) {
			return;
		}
		
		int counter = 0, position = 0;
		String textAreaText = textArea.getText();
		HighlightPainter highlightPainter;
		
		if (!checkBoxMatchCase.isSelected()) {
			textAreaText = textAreaText.toLowerCase();
			text = text.toLowerCase();
		}
		
		if (currentSelection == 0) {
			this.counter = 0;
		}
		
		try {
			while ((position = textAreaText.indexOf(text, position)) >= 0) {
				if (currentSelection == counter) {
					textArea.select(position, position + text.length());
					
					highlightPainter = highlightPainters[0];
					this.position = position;
				}
				else {
					highlightPainter = highlightPainters[1];
				}
				
				highlighter.addHighlight(position, position + text.length(), highlightPainter);
				
				position += text.length();
				counter++;
			}
		}
		catch (Exception exc) {
			highlighter.removeAllHighlights();
		}
	}
	
	public void find() {
		count();
		
		if ((replaced && counter <= highlights - 1) || (counter < highlights - 1)) {
			counter++;
		}
		else {
			counter = 0;
		}
		
		if (replaced && counter != 0) {
			counter--;
		}
		
		replaced = false;
		
		highlight(counter);
	}
	
	public void replace() {
		String[] texts = { textFieldFind.getText(), textFieldReplace.getText(), textArea.getText() };
		
		if (!checkBoxMatchCase.isSelected()) {
			texts[0] = texts[0].toLowerCase();
			texts[2] = texts[2].toLowerCase();
		}
		
		if ((position = texts[2].indexOf(texts[0], position)) >= 0) {
			textArea.select(position, (position + texts[0].length()));
			textArea.replaceSelection(texts[1]);
			textArea.select(position, (position + texts[1].length()));
			
			Highlight[] highlights = highlighter.getHighlights();
			
			for (int i=0; i<highlights.length; i++) {
				if (highlights[i].getStartOffset() == position) {
					highlighter.removeHighlight(highlights[i]);
					
					break;
				}
			}
			
			try {
				highlighter.addHighlight(position, (position + texts[1].length()), highlightPainters[2]);
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
			
			position += texts[1].length();
			replaced = true;
		}
		
		if (position == -1) {
			int selectionEnd = textArea.getSelectionEnd();
			
			textArea.setSelectionStart(selectionEnd);
			textArea.setSelectionEnd(selectionEnd);
			
			JOptionPane.showMessageDialog(this, "Finished replacing", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	@Override
	public void setVisible(boolean aFlag) {
		JPanel panelParent = (JPanel) getParent();
		
		if (panelParent != null) {
			if (aFlag) {
				setPreferredSize(new Dimension(0, 125));
				highlight(0);
			}
			else {
				setPreferredSize(new Dimension(0, 0));
				highlighter.removeAllHighlights();
			}
			
			panelParent.repaint();
			panelParent.revalidate();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			if (event.getSource().equals(buttonFind)) {
				find();
			}
			else if (event.getSource().equals(buttonReplace)) {
				replace();
			}
			else if (event.getSource().equals(checkBoxMatchCase)) {
				highlight(0);
			}
			else if (event.getSource().equals(textFieldLineNumber) || event.getSource().equals(buttonGo)) {
				textArea.setCaretPosition(textArea.getLineStartOffset(Integer.parseInt(textFieldLineNumber.getText()) - 1));
			}
			else if (event.getSource().equals(buttonCancel)) {
				setVisible(false);
			}
		}
		catch (Exception exc) {
			JOptionPane.showMessageDialog(this, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		textArea.requestFocusInWindow();
	}

	@Override
	public void keyPressed(KeyEvent event) {
		/*
		 * nothing to be implemented here...
		 */
	}

	@Override
	public void keyReleased(KeyEvent event) {
		if (event.getSource().equals(textFieldFind)) {
			highlight(0);
		}
	}

	@Override
	public void keyTyped(KeyEvent event) {
		/*
		 * nothing to be implemented here...
		 */
	}
	
}