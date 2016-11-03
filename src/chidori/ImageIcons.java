package chidori;

import javax.swing.ImageIcon;

public class ImageIcons {
	
	public static final ImageIcon iconActive = new ImageIcon(ImageIcons.class.getResource("/icons/active.png")),
			iconNew = new ImageIcon(ImageIcons.class.getResource("/icons/new.png")),
			iconOpen = new ImageIcon(ImageIcons.class.getResource("/icons/open.png")),
			iconSave = new ImageIcon(ImageIcons.class.getResource("/icons/save.png")),
			iconPrint = new ImageIcon(ImageIcons.class.getResource("/icons/print.png")),
			iconCut = new ImageIcon(ImageIcons.class.getResource("/icons/cut.png")),
			iconCopy = new ImageIcon(ImageIcons.class.getResource("/icons/copy.png")),
			iconPaste = new ImageIcon(ImageIcons.class.getResource("/icons/paste.png")),
			iconUndo = new ImageIcon(ImageIcons.class.getResource("/icons/undo.png")),
			iconRedo = new ImageIcon(ImageIcons.class.getResource("/icons/redo.png"));
	
	public static final ImageIcon[] iconsLogo = {
			new ImageIcon(ImageIcons.class.getResource("/icons/logo.png")),
			new ImageIcon(ImageIcons.class.getResource("/icons/logoLarge.png"))
	},
	iconsCreateNewTab = {
			new ImageIcon(ImageIcons.class.getResource("/icons/create_new_tab_button_1.png")),
			new ImageIcon(ImageIcons.class.getResource("/icons/create_new_tab_button_2.png"))
	},
	iconsCloseButton = {
			new ImageIcon(TabStrip.class.getResource("/icons/close_button_1.png")),
			new ImageIcon(TabStrip.class.getResource("/icons/close_button_2.png")),
			new ImageIcon(TabStrip.class.getResource("/icons/close_button_3.png")),
			new ImageIcon(TabStrip.class.getResource("/icons/close_button_4.png")),
			new ImageIcon(TabStrip.class.getResource("/icons/close_button_5.png")),
			new ImageIcon(TabStrip.class.getResource("/icons/close_button_6.png"))
	};
	
}