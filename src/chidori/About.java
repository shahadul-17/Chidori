package chidori;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class About extends JDialog implements ActionListener, MouseListener {
	
	private static final long serialVersionUID = -3362340977892455626L;
	
	private JPanel contentPane, panelAboutDeveloper;
	private JScrollPane scrollPaneAboutDeveloper;
	private JLabel labelLogo, labelSoftwareName, labelDeveloper;
	private JTextArea textAreaAboutDeveloper;
	private JButton buttonOk;
	
	public About() {
		initialize();
	}
	
	private void initialize() {
		setSize(450, 300);
		setResizable(false);
		setIconImage(ImageIcons.iconsLogo[0].getImage());
		setTitle("About");
		setModal(true);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		
		labelLogo = new JLabel("");
		labelLogo.setHorizontalAlignment(SwingConstants.CENTER);
		labelLogo.setIcon(ImageIcons.iconsLogo[1]);
		labelLogo.setBounds(10, 10, 128, 128);
		contentPane.add(labelLogo);
		
		labelSoftwareName = new JLabel(Main.title);
		labelSoftwareName.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		labelSoftwareName.setHorizontalAlignment(SwingConstants.CENTER);
		labelSoftwareName.setBounds(148, 25, 286, 55);
		contentPane.add(labelSoftwareName);
		
		labelDeveloper = new JLabel("Developer : Md. Shahadul Alam Patwary");
		labelDeveloper.setHorizontalAlignment(SwingConstants.CENTER);
		labelDeveloper.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		labelDeveloper.setBounds(148, 75, 286, 14);
		labelDeveloper.setCursor(new Cursor(Cursor.HAND_CURSOR));
		labelDeveloper.addMouseListener(this);
		contentPane.add(labelDeveloper);
		
		panelAboutDeveloper = new JPanel();
		panelAboutDeveloper.setBorder(new TitledBorder(null, "About Developer", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelAboutDeveloper.setLayout(new BorderLayout(0, 0));
		panelAboutDeveloper.setBackground(Color.WHITE);
		panelAboutDeveloper.setBounds(148, 105, 286, 155);
		contentPane.add(panelAboutDeveloper);
		
		scrollPaneAboutDeveloper = new JScrollPane();
		panelAboutDeveloper.add(scrollPaneAboutDeveloper, BorderLayout.CENTER);
		
		textAreaAboutDeveloper = new JTextArea("I am currently studying in North South University (CSE). "
				+ "For past few years I've developed many softwares. It feels good to work on programs that people need.\n\n"
				+ "Programming Languages I Know :\n\t(a) C\n\t(b) C++\n\t(c) Java (used most often)\n\t(d) C# (used most often)");
		textAreaAboutDeveloper.setEditable(false);
		textAreaAboutDeveloper.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		textAreaAboutDeveloper.setTabSize(4);
		textAreaAboutDeveloper.setWrapStyleWord(true);
		textAreaAboutDeveloper.setLineWrap(true);
		scrollPaneAboutDeveloper.setViewportView(textAreaAboutDeveloper);
		
		buttonOk = new JButton("OK");
		buttonOk.setBounds(10, 230, 89, 30);
		buttonOk.addActionListener(this);
		contentPane.add(buttonOk);
	}
	
	public void showDialog() {
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(buttonOk)) {
			setVisible(false);
		}
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (event.getSource().equals(labelDeveloper)) {
			try {
				Desktop.getDesktop().browse(new URL("https://www.facebook.com/sahedul.alam").toURI());
			}
			catch (Exception exc) {
				/*
				 * don't need to know this exception...
				 */
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