package chidori;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

public class TabStrip extends JPanel implements MouseListener {
	
	public static int indices = 0;
	private static final long serialVersionUID = 4472634942238823281L;
	public boolean isSelected = false;
	
	public String title = "";
	private JLabel labelTitle;
	public JLabel labelClose;
	private MatteBorder matteBorder = new MatteBorder(5, 0, 0, 1, Main.DODGER_BLUE);
	
	public Tab tab;		// just a reference...
	
	public TabStrip() {
		initialize();
	}
	
	public TabStrip(String title, Tab tab) {
		this.tab = tab;
		this.title = title;
		
		initialize();
	}
	
	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		setBackground(Color.WHITE);
		setBorder(matteBorder);
		
		labelTitle = new JLabel(title);
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		labelTitle.setToolTipText(title);
		labelTitle.addMouseListener(this);
		add(labelTitle, BorderLayout.CENTER);
		
		labelClose = new JLabel("");
		labelClose.setIcon(ImageIcons.iconsCloseButton[0]);
		labelClose.setHorizontalAlignment(SwingConstants.CENTER);
		labelClose.setToolTipText("Close tab");
		labelClose.setPreferredSize(new Dimension(35, 0));
		labelClose.addMouseListener(this);
		add(labelClose, BorderLayout.EAST);
		
		tab.index = indices++;
	}
	
	public void setTitle(String title) {
		this.title = title;
		labelTitle.setText(title);
	}
	
	public void setSelected(boolean isSelected) {
		if (isSelected) {
			if (Frame.tabbedPane.selectedTab != null) {
				Frame.tabbedPane.selectedTab.tabStrip.setSelected(false);
			}
			
			setBorder(null);
			setBackground(matteBorder.getMatteColor());
			labelTitle.setForeground(Color.WHITE);
			labelClose.setIcon(ImageIcons.iconsCloseButton[4]);
			
			Frame.tabbedPane.setSelectedTab(tab);
			
			this.isSelected = true;
		}
		else {
			matteBorder = new MatteBorder(5, 0, 0, 1, getBackground());
			setBorder(matteBorder);
			
			if (getBackground().equals(Main.DODGER_BLUE)) {
				labelClose.setIcon(ImageIcons.iconsCloseButton[0]);
			}
			else if (getBackground().equals(Main.ORANGE)) {
				labelClose.setIcon(ImageIcons.iconsCloseButton[2]);
			}
			else {
				labelClose.setIcon(ImageIcons.iconsCloseButton[4]);
			}
			
			setBackground(Color.WHITE);
			labelTitle.setForeground(Color.BLACK);
			
			this.isSelected = false;
		}
	}
	
	public void changeState(boolean saved) {
		if (saved) {
			setBackground(Main.DODGER_BLUE);
		}
		else {
			setBackground(Main.ORANGE);
		}
		
		labelClose.setIcon(ImageIcons.iconsCloseButton[4]);
		setBorder(null);
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		/*
		 * nothing to be implemented here...
		 */
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		if (event.getSource().equals(labelClose)) {
			if (labelClose.getIcon().equals(ImageIcons.iconsCloseButton[0])) {
				labelClose.setIcon(ImageIcons.iconsCloseButton[1]);
			}
			else if (labelClose.getIcon().equals(ImageIcons.iconsCloseButton[2])) {
				labelClose.setIcon(ImageIcons.iconsCloseButton[3]);
			}
			else if (labelClose.getIcon().equals(ImageIcons.iconsCloseButton[4])) {
				labelClose.setIcon(ImageIcons.iconsCloseButton[5]);
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent event) {
		if (event.getSource().equals(labelClose)) {
			if (labelClose.getIcon().equals(ImageIcons.iconsCloseButton[1])) {
				labelClose.setIcon(ImageIcons.iconsCloseButton[0]);
			}
			else if (labelClose.getIcon().equals(ImageIcons.iconsCloseButton[3])) {
				labelClose.setIcon(ImageIcons.iconsCloseButton[2]);
			}
			else if (labelClose.getIcon().equals(ImageIcons.iconsCloseButton[5])) {
				labelClose.setIcon(ImageIcons.iconsCloseButton[4]);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent event) {
		if (!event.getSource().equals(labelClose)) {
			setSelected(true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		/*
		 * nothing to be implemented here...
		 */
	}
	
}