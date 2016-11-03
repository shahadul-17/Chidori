package chidori;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

public class TabbedPane extends JPanel implements ComponentListener, AdjustmentListener, MouseListener, MouseWheelListener {
	
	private static final long serialVersionUID = 8533418898459103258L;
	
	private JPanel panelTop, panelTabStrip, panelTopRight;
	public JPanel panelCenter;
	private JScrollPane scrollPaneTop;
	private JLabel labelCreateNewTab;
	
	public Tab selectedTab = null;
	public static volatile HashMap<Integer, Tab> hashMapTabs = new HashMap<Integer, Tab>();
	
	public TabbedPane() {
		initialize();
	}
	
	private void initialize() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
		addComponentListener(this);
		
		panelTop = new JPanel();
		panelTop.setPreferredSize(new Dimension(0, 40));
		panelTop.setBackground(Color.WHITE);
		add(panelTop, BorderLayout.NORTH);
		panelTop.setLayout(new BorderLayout(0, 0));
		
		scrollPaneTop = new JScrollPane();
		scrollPaneTop.setOpaque(false);
		scrollPaneTop.setBorder(null);
		scrollPaneTop.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPaneTop.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelTop.add(scrollPaneTop, BorderLayout.CENTER);
		
		panelTabStrip = new JPanel();
		panelTabStrip.setBackground(Color.WHITE);
		FlowLayout flowLayout = (FlowLayout) panelTabStrip.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setVgap(0);
		flowLayout.setHgap(2);
		panelTabStrip.addMouseWheelListener(this);
		panelTabStrip.addComponentListener(this);
		scrollPaneTop.setViewportView(panelTabStrip);
		
		labelCreateNewTab = new JLabel("");
		labelCreateNewTab.setIcon(ImageIcons.iconsCreateNewTab[0]);
		labelCreateNewTab.setBorder(new MatteBorder(5, 1 , 0, 1, Main.DODGER_BLUE));
		labelCreateNewTab.setPreferredSize(new Dimension(40, 40));
		labelCreateNewTab.setHorizontalAlignment(SwingConstants.CENTER);
		labelCreateNewTab.setToolTipText("New tab");
		labelCreateNewTab.addMouseListener(this);
		labelCreateNewTab.addMouseWheelListener(this);
		
		panelTopRight = new JPanel();
		panelTopRight.setBackground(Color.WHITE);
		panelTopRight.setPreferredSize(new Dimension(40, 40));
		panelTopRight.setLayout(new BorderLayout(0, 0));
		panelTopRight.setVisible(false);
		panelTop.add(panelTopRight, BorderLayout.EAST);
		
		panelCenter = new JPanel();
		panelCenter.setBackground(Color.WHITE);
		add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 0));
	}
	
	public void createNewTab(String title) {
		Tab tab = new Tab(title);
		tab.tabStrip.addMouseWheelListener(this);
		tab.tabStrip.labelClose.addMouseListener(this);
		
		add(tab);
	}
	
	public void add(Tab tab) {
		panelTabStrip.add(tab.tabStrip);
		panelTabStrip.add(labelCreateNewTab);
		scrollPaneTop.getHorizontalScrollBar().addAdjustmentListener(this);
		tab.tabStrip.setSelected(true);
	}
	
	public void setSelectedIndex(int index) {
		hashMapTabs.get(index).tabStrip.setSelected(true);
		scrollPaneTop.getHorizontalScrollBar().setValue(hashMapTabs.get(index).tabStrip.getBounds().x);
	}
	
	public void setSelectedTab(Tab tab) {
		selectedTab = tab;
		
		panelCenter.removeAll();
		panelCenter.add(tab.tabBody, BorderLayout.CENTER);
		
		tab.tabBody.setLineNumbersVisible(Frame.settings.lineNumbersVisible);
		tab.tabBody.statusBar.setVisible(Frame.settings.statusBarVisible);
		tab.tabBody.textArea.setLineWrap(Frame.settings.activateLineWrap);
		tab.tabBody.textArea.setWrapStyleWord(Frame.settings.activateWordWrap);
		
		panelCenter.repaint();
		panelCenter.revalidate();
		scrollPaneTop.revalidate();
		
		tab.tabBody.textArea.requestFocusInWindow();
	}
	
	@Override
	public void componentHidden(ComponentEvent event) {
		/*
		 * nothing to be implemented here...
		 */
	}

	@Override
	public void componentMoved(ComponentEvent event) {
		/*
		 * nothing to be implemented here...
		 */
	}

	@Override
	public void componentResized(ComponentEvent event) {
		if (getWidth() < (hashMapTabs.size()*130) + 60) {
			panelTabStrip.remove(labelCreateNewTab);
			panelTopRight.setVisible(true);
			panelTopRight.add(labelCreateNewTab, BorderLayout.CENTER);
		}
		else {
			panelTopRight.remove(labelCreateNewTab);
			panelTopRight.setVisible(false);
			panelTabStrip.add(labelCreateNewTab);
		}
		
		panelTop.revalidate();
		panelCenter.repaint();
		panelCenter.revalidate();
		scrollPaneTop.revalidate();
	}

	@Override
	public void componentShown(ComponentEvent event) {
		/*
		 * nothing to be implemented here...
		 */
	}
	
	@Override
	public void adjustmentValueChanged(AdjustmentEvent event) {
		if(event.getSource().equals(scrollPaneTop.getHorizontalScrollBar())) {
			event.getAdjustable().setValue(event.getAdjustable().getMaximum());
		}
		
		scrollPaneTop.getHorizontalScrollBar().removeAdjustmentListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent event) {
		/*
		 * nothing to be implemented here...
		 */
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		if (event.getSource().equals(labelCreateNewTab)) {
			labelCreateNewTab.setIcon(ImageIcons.iconsCreateNewTab[1]);
		}
	}

	@Override
	public void mouseExited(MouseEvent event) {
		if (event.getSource().equals(labelCreateNewTab)) {
			labelCreateNewTab.setIcon(ImageIcons.iconsCreateNewTab[0]);
		}
	}

	@Override
	public void mousePressed(MouseEvent event) {
		if (event.getSource().equals(labelCreateNewTab)) {
			createNewTab(Main.defaultTabTitle);
		}
		else {
			int option = 0;
			TabStrip tabStrip = (TabStrip) ((JLabel) event.getSource()).getParent();
			
			if (!tabStrip.tab.tabBody.saved) {
				option = JOptionPane.showConfirmDialog(Main.frame, "Would you like to save the document?", tabStrip.title, JOptionPane.YES_NO_CANCEL_OPTION);
				
				if (option == JOptionPane.YES_OPTION) {
					Main.frame.save(tabStrip.tab);
				}
				else if (option == JOptionPane.CANCEL_OPTION) {
					return;
				}
			}
			
			if (tabStrip.tab.equals(TextToSpeech.tab) && (option == JOptionPane.YES_OPTION || option == JOptionPane.NO_OPTION)) {
				TextToSpeech.stop();
			}
			
			Frame.arrayListFilePaths.remove(tabStrip.tab.filePath);
			panelCenter.remove(tabStrip.tab.tabBody);
			
			if (hashMapTabs.size() > 1 && tabStrip.isSelected) {
				int index = 0;
				
				ArrayList<Integer> arrayListKeys = new ArrayList<Integer>(hashMapTabs.keySet());
				Collections.sort(arrayListKeys);
				
				if (tabStrip.tab.index < arrayListKeys.get(arrayListKeys.size() - 1)) {
					index = arrayListKeys.get(arrayListKeys.indexOf(tabStrip.tab.index) + 1);
				}
				else {
					index = arrayListKeys.get(arrayListKeys.indexOf(tabStrip.tab.index) - 1);
				}
				
				hashMapTabs.get(index).tabStrip.setSelected(true);
				panelCenter.add(hashMapTabs.get(index).tabBody);
			}
			
			hashMapTabs.remove(tabStrip.tab.index);
			panelTabStrip.remove(tabStrip);
			panelCenter.repaint();
			panelCenter.revalidate();
			scrollPaneTop.revalidate();
			
			if (hashMapTabs.size() < 1) {
				TabStrip.indices = 0;
				
				hashMapTabs.clear();
				Frame.arrayListFilePaths.clear();
				createNewTab(Main.defaultTabTitle);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		/*
		 * nothing to be implemented here...
		 */
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent event) {
		int value = 0;
		
		if (event.getWheelRotation() > 0) {
			value = scrollPaneTop.getHorizontalScrollBar().getValue() + 130;
		}
		else {
			value = scrollPaneTop.getHorizontalScrollBar().getValue() - 130;
		}
		
		scrollPaneTop.getHorizontalScrollBar().setValue(value);
	}
	
}