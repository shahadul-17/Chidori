package chidori;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

public class TabBody extends JPanel implements UndoableEditListener, ActionListener, CaretListener, KeyListener, ItemListener, MouseListener {
	
	private int lastLineCount = 1, beginIndex;
	private static final int[] sizes = {8, 9, 10, 11, 12, 13, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72};
	private static final long serialVersionUID = 5923008365957034626L;
	
	public boolean saved = true;
	
	private String lastLabelLineNumbersText = "<html>1</html>";
	public String lastText = "";
	private static final String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	private static final Font font = new Font("Segoe UI", Font.PLAIN, 13);
	
	private FontSettings fontSettings = new FontSettings();
	
	private JPopupMenu popupMenu;
	private JMenuItem menuItemUndo, menuItemRedo, menuItemCut, menuItemCopy, menuItemPaste,
			menuItemSelectAll, menuItemFindOrReplace, menuItemFindNext, menuItemReplaceNext,
			menuItemGoTo, menuItemTimeAndDate;
	
	private JPanel panelControls, panelControlsTop, panelControlsBottom, panelLineNumbers, panelCenter;
	private JScrollPane scrollPane;
	private JLabel labelLineNumbers, labelLines, labelColumns;
	private JButton buttonOpen, buttonSave, buttonPrint, buttonPaste, buttonCopy, buttonCut;
	private JButton buttonColor, buttonUndo, buttonRedo;
	private JToggleButton buttonBold, buttonItalik;
	private JComboBox comboBoxFontName, comboBoxFontSize;
	
	public JTextArea textArea;
	public JPanel statusBar;
	
	private PanelSuggestion panelSuggestion;
	public UndoManager undoManager = new UndoManager();
	private PanelFindOrReplaceOrGo panelFindOrReplaceOrGo;
	
	public Tab tab;		// just a reference...
	
	public TabBody(Tab tab) {
		this.tab = tab;
		
		initialize();
	}
	
	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		
		panelControls = new JPanel();
		panelControls.setPreferredSize(new Dimension(0, 78));
		panelControls.setLayout(new GridLayout(2, 0, 0, 0));
		add(panelControls, BorderLayout.NORTH);
		
		panelControlsTop = new JPanel();
		panelControlsTop.setBackground(Color.WHITE);
		panelControlsTop.setLayout(new FlowLayout(5, 5, 5));
		panelControls.add(panelControlsTop);
		
		buttonOpen = new JButton("");
		buttonOpen.setIcon(ImageIcons.iconOpen);
		buttonOpen.setToolTipText("Open");
		buttonOpen.addActionListener(this);
		panelControlsTop.add(buttonOpen);
		
		buttonSave = new JButton("");
		buttonSave.setIcon(ImageIcons.iconSave);
		buttonSave.setToolTipText("Save");
		buttonSave.addActionListener(this);
		panelControlsTop.add(buttonSave);
		
		buttonPrint = new JButton("");
		buttonPrint.setIcon(ImageIcons.iconPrint);
		buttonPrint.setToolTipText("Print");
		buttonPrint.addActionListener(this);
		panelControlsTop.add(buttonPrint);
		
		buttonCut = new JButton("");
		buttonCut.setIcon(ImageIcons.iconCut);
		buttonCut.setToolTipText("Cut");
		buttonCut.addActionListener(this);
		panelControlsTop.add(buttonCut);
		
		buttonCopy = new JButton("");
		buttonCopy.setIcon(ImageIcons.iconCopy);
		buttonCopy.setToolTipText("Copy");
		buttonCopy.addActionListener(this);
		panelControlsTop.add(buttonCopy);
		
		buttonPaste = new JButton("");
		buttonPaste.setIcon(ImageIcons.iconPaste);
		buttonPaste.setToolTipText("Paste");
		buttonPaste.addActionListener(this);
		panelControlsTop.add(buttonPaste);
		
		panelControlsBottom = new JPanel();
		panelControlsBottom.setBackground(Color.WHITE);
		panelControlsBottom.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panelControls.add(panelControlsBottom);
		
		buttonUndo = new JButton("");
		buttonUndo.setIcon(ImageIcons.iconUndo);
		buttonUndo.setToolTipText("Undo");
		buttonUndo.setEnabled(false);
		buttonUndo.addActionListener(this);
		panelControlsBottom.add(buttonUndo);
		
		buttonRedo = new JButton("");
		buttonRedo.setIcon(ImageIcons.iconRedo);
		buttonRedo.setToolTipText("Redo");
		buttonRedo.setEnabled(false);
		buttonRedo.addActionListener(this);
		panelControlsBottom.add(buttonRedo);
		
		comboBoxFontName = new JComboBox();
		comboBoxFontName.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		
		for (int i=0; i<fontNames.length; i++) {
			comboBoxFontName.addItem(fontNames[i]);
		}
		
		comboBoxFontName.setSelectedItem("Segoe UI");
		comboBoxFontName.setToolTipText("Font name");
		comboBoxFontName.addItemListener(this);
		panelControlsBottom.add(comboBoxFontName);
		
		comboBoxFontSize = new JComboBox();
		comboBoxFontSize.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		
		for (int i=0; i<sizes.length; i++) {
			comboBoxFontSize.addItem(sizes[i]);
		}
		
		comboBoxFontSize.setSelectedIndex(5);
		comboBoxFontSize.setToolTipText("Font size");
		comboBoxFontSize.addItemListener(this);
		panelControlsBottom.add(comboBoxFontSize);
		
		buttonBold = new JToggleButton("B");
		buttonBold.setFont(new Font("Segoe UI", Font.BOLD, 13));
		buttonBold.setToolTipText("Bold");
		buttonBold.addActionListener(this);
		panelControlsBottom.add(buttonBold);
		
		buttonItalik = new JToggleButton("I");
		buttonItalik.setFont(new Font("Segoe UI", Font.ITALIC, 13));
		buttonItalik.setToolTipText("Italik");
		buttonItalik.addActionListener(this);
		panelControlsBottom.add(buttonItalik);
		
		buttonColor = new JButton("A");
		buttonColor.setFont(buttonBold.getFont());
		buttonColor.setToolTipText("<html>Left click to choose Font color<br>Right click to choose Background color</html>");
		buttonColor.addMouseListener(this);
		panelControlsBottom.add(buttonColor);
		
		panelCenter = new JPanel();
		panelCenter.setLayout(new BorderLayout(0, 0));
		add(panelCenter, BorderLayout.CENTER);
		
		scrollPane = new JScrollPane();
		panelCenter.add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		textArea.setTabSize(4);
		textArea.setFont(font);
		textArea.getDocument().addUndoableEditListener(this);
		textArea.addCaretListener(this);
		textArea.addKeyListener(this);
		scrollPane.setViewportView(textArea);
		
		popupMenu = new JPopupMenu();
		addPopup(textArea, popupMenu);
		
		menuItemCut = new JMenuItem("Cut");
		menuItemCut.setIcon(ImageIcons.iconCut);
		menuItemCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		menuItemCut.addActionListener(this);
		popupMenu.add(menuItemCut);
		
		menuItemCopy = new JMenuItem("Copy");
		menuItemCopy.setIcon(ImageIcons.iconCopy);
		menuItemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		menuItemCopy.addActionListener(this);
		popupMenu.add(menuItemCopy);
		
		menuItemPaste = new JMenuItem("Paste");
		menuItemPaste.setIcon(ImageIcons.iconPaste);
		menuItemPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		menuItemPaste.addActionListener(this);
		popupMenu.add(menuItemPaste);
		
		popupMenu.addSeparator();
		
		menuItemUndo = new JMenuItem("Undo");
		menuItemUndo.setIcon(ImageIcons.iconUndo);
		menuItemUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		menuItemUndo.addActionListener(this);
		popupMenu.add(menuItemUndo);
		
		menuItemRedo = new JMenuItem("Redo");
		menuItemRedo.setIcon(ImageIcons.iconRedo);
		menuItemRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		menuItemRedo.addActionListener(this);
		popupMenu.add(menuItemRedo);
		
		updateCanUndoRedo();
		
		popupMenu.addSeparator();
		
		menuItemSelectAll = new JMenuItem("Select All");
		menuItemSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		menuItemSelectAll.addActionListener(this);
		popupMenu.add(menuItemSelectAll);
		
		popupMenu.addSeparator();
		
		menuItemFindOrReplace = new JMenuItem("Find/Replace");
		menuItemFindOrReplace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		menuItemFindOrReplace.addActionListener(this);
		popupMenu.add(menuItemFindOrReplace);
		
		menuItemFindNext = new JMenuItem("Find Next");
		menuItemFindNext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
		menuItemFindNext.addActionListener(this);
		popupMenu.add(menuItemFindNext);
		
		menuItemReplaceNext = new JMenuItem("Replace Next");
		menuItemReplaceNext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0));
		menuItemReplaceNext.addActionListener(this);
		popupMenu.add(menuItemReplaceNext);
		
		menuItemGoTo = new JMenuItem("Go To");
		menuItemGoTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		menuItemGoTo.addActionListener(this);
		popupMenu.add(menuItemGoTo);
		
		popupMenu.addSeparator();
		
		menuItemTimeAndDate = new JMenuItem("Time/Date");
		menuItemTimeAndDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		menuItemTimeAndDate.addActionListener(this);
		popupMenu.add(menuItemTimeAndDate);
		
		panelLineNumbers = new JPanel();
		panelLineNumbers.setBackground(Main.DODGER_BLUE);
		panelLineNumbers.setLayout(new BorderLayout(0, 0));
		panelLineNumbers.setBorder(new EmptyBorder(0, 5, 0, 5));
		setLineNumbersVisible(Frame.settings.lineNumbersVisible);
		
		labelLineNumbers = new JLabel(lastLabelLineNumbersText);
		labelLineNumbers.setForeground(Color.WHITE);
		labelLineNumbers.setFont(font);
		labelLineNumbers.setVerticalAlignment(SwingConstants.TOP);
		labelLineNumbers.setHorizontalAlignment(SwingConstants.CENTER);
		panelLineNumbers.add(labelLineNumbers, BorderLayout.CENTER);
		
		statusBar = new JPanel();
		statusBar.setBackground(Color.WHITE);
		statusBar.setPreferredSize(new Dimension(0, 30));
		statusBar.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		statusBar.setVisible(Frame.settings.statusBarVisible);
		add(statusBar, BorderLayout.SOUTH);
		
		labelLines = new JLabel("Line(s) = 1");
		labelLines.setFont(font);
		statusBar.add(labelLines);
		
		labelColumns = new JLabel("Column(s) = 0");
		labelColumns.setFont(font);
		statusBar.add(labelColumns);
		
		panelSuggestion = new PanelSuggestion(textArea);
		textArea.add(panelSuggestion);
		
		panelFindOrReplaceOrGo = new PanelFindOrReplaceOrGo(textArea);
		panelCenter.add(panelFindOrReplaceOrGo, BorderLayout.SOUTH);
		panelFindOrReplaceOrGo.setVisible(false);
	}
	
	private static int getLines(JTextArea textArea) {
		int lines = -1;
		
		try {
			lines = textArea.getLineOfOffset(textArea.getCaretPosition());
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		
		return lines;
	}
	
	public static int getColumns(JTextArea textArea) {
		int columns = -1;
		
		try {
			columns = textArea.getCaretPosition() - textArea.getLineStartOffset(getLines(textArea));
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		
		return columns;
	}
	
	private void updateStatus(int flag) {
		if (flag == 0) {
			labelLines.setText("Line(s) = " + (getLines(textArea) + 1));
			labelColumns.setText("Column(s) = " + getColumns(textArea));
		}
	}
	
	private void updateLineNumbers() {
		int lineCount = textArea.getLineCount();
		
		if (lastLineCount != lineCount) {
			if (lastLineCount < lineCount) {
				for (int i=lastLineCount; i<lineCount; i++) {
					lastLabelLineNumbersText = lastLabelLineNumbersText.substring(0, lastLabelLineNumbersText.lastIndexOf("</html>")) + "<br>" + (i+1) + "</html>";
				}
			}
			else if (lastLineCount > lineCount) {
				for (int i=0; i<lastLineCount - lineCount; i++) {
					lastLabelLineNumbersText = lastLabelLineNumbersText.substring(0, lastLabelLineNumbersText.lastIndexOf("<br>")) + "</html>";
				}
			}
			
			labelLineNumbers.setText(lastLabelLineNumbersText);
			lastLineCount = lineCount;
		}
	}
	
	public void isSaved() {
		if (lastText.equals(textArea.getText())) {
			saved = true;
			panelLineNumbers.setBackground(Main.DODGER_BLUE);
		}
		else {
			saved = false;
			panelLineNumbers.setBackground(Main.ORANGE);
		}
		
		tab.tabStrip.changeState(saved);
	}
	
	public void setUnsaved() {
		saved = false;
		lastText = "x6&7#8@5)9^1(6*4&3x";	// garbage value...
		
		panelLineNumbers.setBackground(Main.ORANGE);
		tab.tabStrip.changeState(saved);
	}
	
	public void setLineNumbersVisible(boolean visible) {
		try {
			if (visible) {
				scrollPane.setRowHeaderView(panelLineNumbers);
			}
			else {
				scrollPane.getRowHeader().remove(panelLineNumbers);
			}
			
			scrollPane.getRowHeader().repaint();
			scrollPane.getRowHeader().revalidate();
		}
		catch (Exception exc) {
			/*
			 * don't need to know this exception...
			 */
		}
	}
	
	@Override
	public void caretUpdate(CaretEvent event) {
		if (event.getSource().equals(textArea)) {
			updateStatus(0);
			updateLineNumbers();
			isSaved();
		}
	}
	
	private void updateCanUndoRedo() {
		menuItemUndo.setEnabled(undoManager.canUndo());
		buttonUndo.setEnabled(undoManager.canUndo());
		menuItemRedo.setEnabled(undoManager.canRedo());
		buttonRedo.setEnabled(undoManager.canRedo());
	}
	
	@Override
	public void undoableEditHappened(UndoableEditEvent event) {
		if (event.getSource().equals(textArea.getDocument())) {
			undoManager.addEdit(event.getEdit());
			updateCanUndoRedo();
		}
	}
	
	private void updateFontStyle() {
		if (buttonBold.isSelected() && !buttonItalik.isSelected()) {
			fontSettings.isBold = true;
			fontSettings.isItalik = false;
		}
		else if (!buttonBold.isSelected() && buttonItalik.isSelected()) {
			fontSettings.isBold = false;
			fontSettings.isItalik = true;
		}
		else if (buttonBold.isSelected() && buttonItalik.isSelected()) {
			fontSettings.isBold = fontSettings.isItalik = true;
		}
		else {
			fontSettings.isBold = fontSettings.isItalik = false;
		}
		
		updateFont();
	}
	
	private void undo() {
		try {
			undoManager.undo();
		}
		catch (Exception exc) {
			/*
			 * don't need to know this exception...
			 */
		}
		
		updateCanUndoRedo();
	}
	
	private void redo() {
		try {
			undoManager.redo();
		}
		catch (Exception exc) {
			/*
			 * don't need to know this exception...
			 */
		}
		
		updateCanUndoRedo();
	}
	
	private void insertTimeAndDate() {
		textArea.insert(((DateFormat) new SimpleDateFormat("h:mm a dd/MM/yyyy")).format(new Date()), textArea.getCaretPosition());
	}
	
	private void findOrReplace() {
		panelFindOrReplaceOrGo.setVisible(true);
		panelFindOrReplaceOrGo.textFieldFind.requestFocusInWindow();
	}
	
	private void goTo() {
		panelFindOrReplaceOrGo.setVisible(true);
		panelFindOrReplaceOrGo.textFieldLineNumber.requestFocusInWindow();
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(menuItemCut) ||
				event.getSource().equals(buttonCut)) {
			textArea.cut();
		}
		else if (event.getSource().equals(menuItemCopy) ||
				event.getSource().equals(buttonCopy)) {
			textArea.copy();
		}
		else if (event.getSource().equals(menuItemPaste) ||
				event.getSource().equals(buttonPaste)) {
			textArea.paste();
		}
		else if (event.getSource().equals(menuItemUndo) ||
				event.getSource().equals(buttonUndo)) {
			undo();
		}
		else if (event.getSource().equals(buttonRedo) ||
				event.getSource().equals(menuItemRedo)) {
			redo();
		}
		else if (event.getSource().equals(menuItemSelectAll)) {
			textArea.selectAll();
		}
		else if (event.getSource().equals(menuItemFindOrReplace)) {
			findOrReplace();
		}
		else if (event.getSource().equals(menuItemFindNext)) {
			panelFindOrReplaceOrGo.find();
		}
		else if (event.getSource().equals(menuItemReplaceNext)) {
			panelFindOrReplaceOrGo.replace();
		}
		else if (event.getSource().equals(menuItemGoTo)) {
			goTo();
		}
		else if (event.getSource().equals(menuItemTimeAndDate)) {
			insertTimeAndDate();
		}
		else if (event.getSource().equals(buttonOpen)) {
			Main.frame.open();
		}
		else if (event.getSource().equals(buttonSave)) {
			Main.frame.save(tab);
		}
		else if (event.getSource().equals(buttonPrint)) {
			Frame.print(textArea);
		}
		else if (event.getSource().equals(buttonBold) || event.getSource().equals(buttonItalik)) {
			updateFontStyle();
		}
		
		textArea.requestFocusInWindow();
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getSource().equals(textArea)) {
			if ((event.getKeyCode() == KeyEvent.VK_BACK_SPACE) &&
					(textArea.getText().length() == 0)) {
				event.consume();
			}
			else if (Frame.settings.activateReadAsIType && !TextToSpeech.reading &&
					!event.isControlDown() && event.getKeyCode() == KeyEvent.VK_SPACE) {
				beginIndex = textArea.getText().lastIndexOf(' ', textArea.getCaretPosition());
				beginIndex = textArea.getText().lastIndexOf(' ', beginIndex);
				TextToSpeech.tab = tab;
				
				TextToSpeech.read(textArea.getText().substring(beginIndex + 1, textArea.getCaretPosition()));
			}
			else if (event.isControlDown() && (event.getKeyCode() == KeyEvent.VK_SPACE)) {
				panelSuggestion.setVisible(true);
			}
			else if(event.isControlDown() && (event.getKeyCode() == KeyEvent.VK_Z)) {
				undo();
			}
			else if(event.isControlDown() && (event.getKeyCode() == KeyEvent.VK_Y)) {
				redo();
			}
			else if (event.isControlDown() && (event.getKeyCode() == KeyEvent.VK_B)) {
				buttonBold.doClick();
			}
			else if (event.isControlDown() && (event.getKeyCode() == KeyEvent.VK_I)) {
				buttonItalik.doClick();
			}
			else if(event.isControlDown() && (event.getKeyCode() == KeyEvent.VK_F)) {
				findOrReplace();
			}
			else if(event.getKeyCode() == KeyEvent.VK_F3) {
				panelFindOrReplaceOrGo.find();
			}
			else if(event.getKeyCode() == KeyEvent.VK_F4) {
				panelFindOrReplaceOrGo.replace();
			}
			else if(event.isControlDown() && (event.getKeyCode() == KeyEvent.VK_G)) {
				goTo();
			}
			else if(event.getKeyCode() == KeyEvent.VK_F5) {
				insertTimeAndDate();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		/*
		 * nothing to be implemented here...
		 */
	}

	@Override
	public void keyTyped(KeyEvent event) {
		if (event.getSource().equals(textArea)) {
			if ((event.getKeyCode() == (KeyEvent.VK_CONTROL | KeyEvent.VK_Z))) {
				buttonUndo.doClick();
			}
		}
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	private void updateFont() {
		labelLineNumbers.setFont(fontSettings.getFont());
		textArea.setFont(fontSettings.getFont());
	}
	
	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getSource().equals(comboBoxFontName)) {
			fontSettings.name = (String) comboBoxFontName.getSelectedItem();
		}
		else if (event.getSource().equals(comboBoxFontSize)) {
			fontSettings.size = (Integer) comboBoxFontSize.getSelectedItem();
		}
		
		updateFont();
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (event.getSource().equals(buttonColor)) {
			Color color = JColorChooser.showDialog(Main.frame, "Color", Color.BLACK);
			
			if (color != null) {
				if ((event.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
					buttonColor.setForeground(color);
					textArea.setForeground(color);
				}
				else {
					buttonColor.setBackground(color);
					textArea.setBackground(color);
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		/*
		 * nothing to be implemented here...
		 */
	}

	@Override
	public void mouseExited(MouseEvent event) {
		/*
		 * nothing to be implemented here...
		 */
	}

	@Override
	public void mousePressed(MouseEvent event) {
		/*
		 * nothing to be implemented here...
		 */
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		/*
		 * nothing to be implemented here...
		 */
	}
	
}