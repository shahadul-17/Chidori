package chidori;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class Frame extends JFrame implements ActionListener, WindowListener {
	
	private static final long serialVersionUID = -1207426031441448511L;
	
	private static JMenuBar menuBar;
	private static JMenu menuFile, menuFormat, menuView, menuRead, menuHelp;
	private static JMenuItem menuItemNew, menuItemOpen, menuItemSave,
			menuItemSaveAs, menuItemPageSetup, menuItemPrint, menuItemExit,
			menuItemStatusBar, menuItemLineNumbers, menuItemLineWrap,
			menuItemWordWrap, menuItemAsIType, menuItemFromCaret,
			menuItemEntireDocument, menuItemPause, menuItemResume, menuItemStop, menuItemAbout;
	
	private static JPanel contentPane;
	public static TabbedPane tabbedPane;
	
	private static JFileChooser fileChooser = new JFileChooser();
	
	private static Printer printer = new Printer();
	public static Settings settings = new Settings();
	private static About about = new About();
	
	public static ArrayList<String> arrayListFilePaths = new ArrayList<String>();
	
	public Frame(final String[] directories) {
		initialize();
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				Tab.restore(tabbedPane);
				
				for (int i=0; i<directories.length; i++) {
					open(new File(directories[i]));
				}
				
				if (TabbedPane.hashMapTabs.size() == 0) {
					tabbedPane.createNewTab(Main.defaultTabTitle);
				}
				
				updateMenuItemIcons(settings.lineNumbersVisible, menuItemLineNumbers);
				updateMenuItemIcons(settings.statusBarVisible, menuItemStatusBar);
				updateMenuItemIcons(settings.activateLineWrap, menuItemLineWrap);
				updateMenuItemIcons(settings.activateWordWrap, menuItemWordWrap);
				updateMenuItemIcons(settings.activateReadAsIType, menuItemAsIType);
			}
		});
	}
	
	private void initialize() {
		setTitle(Main.title);
		setSize(settings.width, settings.height);
		setIconImage(ImageIcons.iconsLogo[0].getImage());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		menuItemNew = new JMenuItem("New");
		menuItemNew.setIcon(ImageIcons.iconNew);
		menuItemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		menuItemNew.addActionListener(this);
		menuFile.add(menuItemNew);
		
		menuItemOpen = new JMenuItem("Open");
		menuItemOpen.setIcon(ImageIcons.iconOpen);
		menuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menuItemOpen.addActionListener(this);
		menuFile.add(menuItemOpen);
		
		menuItemSave = new JMenuItem("Save");
		menuItemSave.setIcon(ImageIcons.iconSave);
		menuItemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		menuItemSave.addActionListener(this);
		menuFile.add(menuItemSave);
		
		menuItemSaveAs = new JMenuItem("Save As");
		menuItemSaveAs.addActionListener(this);
		menuFile.add(menuItemSaveAs);
		
		menuFile.addSeparator();
		
		menuItemPageSetup = new JMenuItem("Page Setup");
		menuItemPageSetup.addActionListener(this);
		menuFile.add(menuItemPageSetup);
		
		menuItemPrint = new JMenuItem("Print");
		menuItemPrint.setIcon(ImageIcons.iconPrint);
		menuItemPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		menuItemPrint.addActionListener(this);
		menuFile.add(menuItemPrint);
		
		menuFile.addSeparator();
		
		menuItemExit = new JMenuItem("Exit");
		menuItemExit.addActionListener(this);
		menuFile.add(menuItemExit);
		
		menuFormat = new JMenu("Format");
		menuBar.add(menuFormat);
		
		menuItemLineWrap = new JMenuItem("Line Wrap");
		menuItemLineWrap.addActionListener(this);
		menuFormat.add(menuItemLineWrap);
		
		menuItemWordWrap = new JMenuItem("Word Wrap");
		menuItemWordWrap.setEnabled(false);
		menuItemWordWrap.addActionListener(this);
		menuFormat.add(menuItemWordWrap);
		
		menuView = new JMenu("View");
		menuBar.add(menuView);
		
		menuItemLineNumbers = new JMenuItem("Line Numbers");
		menuItemLineNumbers.setIcon(ImageIcons.iconActive);
		menuItemLineNumbers.addActionListener(this);
		menuView.add(menuItemLineNumbers);
		
		menuItemStatusBar = new JMenuItem("Status Bar");
		menuItemStatusBar.setIcon(ImageIcons.iconActive);
		menuItemStatusBar.addActionListener(this);
		menuView.add(menuItemStatusBar);
		
		menuRead = new JMenu("Read");
		menuBar.add(menuRead);
		
		menuItemAsIType = new JMenuItem("As I Type");
		menuItemAsIType.addActionListener(this);
		menuRead.add(menuItemAsIType);
		
		menuItemFromCaret = new JMenuItem("From Caret");
		menuItemFromCaret.addActionListener(this);
		menuRead.add(menuItemFromCaret);
		
		menuItemEntireDocument = new JMenuItem("Entire Document");
		menuItemEntireDocument.addActionListener(this);
		menuRead.add(menuItemEntireDocument);
		
		menuRead.addSeparator();
		
		menuItemPause = new JMenuItem("Pause");
		menuItemPause.addActionListener(this);
		menuRead.add(menuItemPause);
		
		menuItemResume = new JMenuItem("Resume");
		menuItemResume.addActionListener(this);
		menuRead.add(menuItemResume);
		
		menuItemStop = new JMenuItem("Stop");
		menuItemStop.addActionListener(this);
		menuRead.add(menuItemStop);
		
		menuHelp = new JMenu("Help");
		menuBar.add(menuHelp);
		
		menuItemAbout = new JMenuItem("About");
		menuItemAbout.addActionListener(this);
		menuHelp.add(menuItemAbout);
		
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		tabbedPane = new TabbedPane();
		contentPane.add(tabbedPane, BorderLayout.CENTER);
	}
	
	private JTextArea getSelectedTextArea() {
		return tabbedPane.selectedTab.tabBody.textArea;
	}
	
	public void open(File file) {
		if (!arrayListFilePaths.contains(file.getAbsolutePath())) {
			try {
				tabbedPane.createNewTab(file.getName());
				tabbedPane.selectedTab.filePath = file.getAbsolutePath();
				arrayListFilePaths.add(file.getAbsolutePath());
				
				getSelectedTextArea().setText(tabbedPane.selectedTab.tabBody.lastText = Main.read(file));
			}
			catch (Exception exc) {
				JOptionPane.showMessageDialog(this, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else {
			ArrayList<Integer> arrayListKeys = new ArrayList<Integer>(TabbedPane.hashMapTabs.keySet());
			
			for (int j=0; j<arrayListKeys.size(); j++) {
				if (TabbedPane.hashMapTabs.get(arrayListKeys.get(j)).filePath.equals(file.getAbsolutePath())) {
					tabbedPane.setSelectedIndex(arrayListKeys.get(j));
					
					break;
				}
			}
		}
	}
	
	public void open() {
		fileChooser.setDialogTitle("Open");
		fileChooser.setMultiSelectionEnabled(true);
		
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File[] files = fileChooser.getSelectedFiles();
			
			for (int i=0; i<files.length; i++) {
				open(files[i]);
			}
		}
	}
	
	public void save(Tab tab) {
		if (tab.filePath.equals("")) {
			saveAs(tab);
		}
		else {
			try {
				Main.write(tab.tabBody.lastText = tab.tabBody.textArea.getText(), new File(tab.filePath));
				tab.tabBody.isSaved();
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
		}
	}
	
	private void saveAs(Tab tab) {
		fileChooser.setDialogTitle("Save As");
		fileChooser.setMultiSelectionEnabled(false);
		
		if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			
			try {
				Main.write(tab.tabBody.textArea.getText(), file);
				tab.tabStrip.setTitle(file.getName());
				tab.filePath = file.getAbsolutePath();
				arrayListFilePaths.add(file.getAbsolutePath());
				
				tab.tabBody.lastText = tab.tabBody.textArea.getText();
				tab.tabBody.isSaved();
			}
			catch (Exception exc) {
				JOptionPane.showMessageDialog(this, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public static void print(JTextArea textArea) {
		textArea.setFocusable(false);
		printer.showPrintDialog(textArea);
		textArea.setFocusable(true);
		textArea.requestFocus();
	}
	
	private void exit() {
		Server.run = false;
		
		settings.save();
		Tab.backup();
		System.exit(0);
	}
	
	private void updateMenuItemIcons(boolean active, JMenuItem menuItem) {
		if (menuItem.equals(menuItemLineWrap)) {
			if (menuItemLineNumbers.getIcon() != null) {
				settings.lineNumbersVisible = !active;
				
				tabbedPane.selectedTab.tabBody.setLineNumbersVisible(settings.lineNumbersVisible);
			}
			
			menuItemLineNumbers.setEnabled(!active);
			menuItemWordWrap.setEnabled(active);
		}
		
		if (active) {
			menuItem.setIcon(ImageIcons.iconActive);
		}
		else {
			menuItem.setIcon(null);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(menuItemNew)) {
			tabbedPane.createNewTab(Main.defaultTabTitle);
		}
		else if (event.getSource().equals(menuItemOpen)) {
			open();
		}
		else if (event.getSource().equals(menuItemSave)) {
			save(tabbedPane.selectedTab);
		}
		else if (event.getSource().equals(menuItemSaveAs)) {
			saveAs(tabbedPane.selectedTab);
		}
		else if (event.getSource().equals(menuItemPageSetup)) {
			printer.showPageDialog();
		}
		else if (event.getSource().equals(menuItemPrint)) {
			print(getSelectedTextArea());
		}
		else if (event.getSource().equals(menuItemExit)) {
			exit();
		}
		else if (event.getSource().equals(menuItemLineWrap)) {
			settings.activateLineWrap = !settings.activateLineWrap;
			getSelectedTextArea().setLineWrap(settings.activateLineWrap);
			
			updateMenuItemIcons(settings.activateLineWrap, menuItemLineWrap);
		}
		else if (event.getSource().equals(menuItemWordWrap)) {
			settings.activateWordWrap = !settings.activateWordWrap;
			getSelectedTextArea().setWrapStyleWord(settings.activateWordWrap);
			
			updateMenuItemIcons(settings.activateWordWrap, menuItemWordWrap);
		}
		else if (event.getSource().equals(menuItemLineNumbers)) {
			settings.lineNumbersVisible = !settings.lineNumbersVisible;
			tabbedPane.selectedTab.tabBody.setLineNumbersVisible(settings.lineNumbersVisible);
			
			updateMenuItemIcons(settings.lineNumbersVisible, menuItemLineNumbers);
		}
		else if (event.getSource().equals(menuItemStatusBar)) {
			settings.statusBarVisible = !settings.statusBarVisible;
			tabbedPane.selectedTab.tabBody.statusBar.setVisible(settings.statusBarVisible);
			
			updateMenuItemIcons(settings.statusBarVisible, menuItemStatusBar);
		}
		else if (event.getSource().equals(menuItemAsIType)) {
			settings.activateReadAsIType = !settings.activateReadAsIType;
			
			updateMenuItemIcons(settings.activateReadAsIType, menuItemAsIType);
		}
		else if (event.getSource().equals(menuItemFromCaret)) {
			TextToSpeech.tab = tabbedPane.selectedTab;
			
			TextToSpeech.read(true, getSelectedTextArea());
		}
		else if (event.getSource().equals(menuItemEntireDocument)) {
			TextToSpeech.tab = tabbedPane.selectedTab;
			
			TextToSpeech.read(false, getSelectedTextArea());
		}
		else if (event.getSource().equals(menuItemPause)) {
			TextToSpeech.pause();
		}
		else if (event.getSource().equals(menuItemResume)) {
			TextToSpeech.resume();
		}
		else if (event.getSource().equals(menuItemStop)) {
			TextToSpeech.stop();
		}
		else if (event.getSource().equals(menuItemAbout)) {
			about.setLocationRelativeTo(this);
			about.showDialog();
		}
		
		getSelectedTextArea().requestFocusInWindow();
	}
	
	@Override
	public void windowActivated(WindowEvent event) {
		tabbedPane.selectedTab.tabBody.textArea.requestFocusInWindow();
	}

	@Override
	public void windowClosed(WindowEvent event) {
		/*
		 * nothing to be implemented here...
		 */
	}

	@Override
	public void windowClosing(WindowEvent event) {
		exit();
	}

	@Override
	public void windowDeactivated(WindowEvent event) {
		/*
		 * nothing to be implemented here...
		 */
	}

	@Override
	public void windowDeiconified(WindowEvent event) {
		/*
		 * nothing to be implemented here...
		 */
	}

	@Override
	public void windowIconified(WindowEvent event) {
		/*
		 * nothing to be implemented here...
		 */
	}

	@Override
	public void windowOpened(WindowEvent event) {
		/*
		 * nothing to be implemented here...
		 */
	}
	
}