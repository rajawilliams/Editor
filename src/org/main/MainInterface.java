package org.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.dialog.OpenDialog;
import org.dialog.SaveDialog;

public class MainInterface extends JFrame {

	/**
	 *
	 */
	private static final long	serialVersionUID	= 1L;
	private JPanel				contentPane;
	private JTextArea			textArea;
	public JPanel				panel_3;
	private MainInterface		me;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				MainInterface frame = new MainInterface();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainInterface() {
		setTitle("Editor");
		me = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 744, 609);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		panel.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel.add(tabbedPane);

		panel_3 = new JPanel();
		tabbedPane.addTab("Structure",
				new ImageIcon(
						MainInterface.class.getResource("/com/sun/java/swing/plaf/windows/icons/DetailsView.gif")),
				panel_3, "The structure of the decompiled directory");
		tabbedPane.setDisabledIconAt(0, new ImageIcon(
				MainInterface.class.getResource("/com/sun/java/swing/plaf/windows/icons/image-failed.png")));
		panel_3.setLayout(new GridLayout(0, 1, 0, 2));

		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("Settings",
				new ImageIcon(MainInterface.class.getResource("/javax/swing/plaf/metal/icons/ocean/menu.gif")), panel_4,
				null);

		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "File Type",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_4.add(panel_5);

		JComboBox<Object> comboBox = new JComboBox<Object>();
		panel_5.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel<Object>(SettingsChoice.values()));

		JButton btnSaveSettings = new JButton("Save Settings");
		panel_5.add(btnSaveSettings);
		btnSaveSettings.addActionListener(e -> {
			int index = comboBox.getSelectedIndex();
			if (index == 0) {
				Settings.extension = ".java";
			}
			if (index == 1) {
				Settings.extension = ".txt";
			}
			if (index == 2) {
				Settings.extension = ".tex";
			}
			if (index == 3) {
				Settings.extension = ".sgml";
			}
			if (index == 4) {
				Settings.extension = ".cs";
			}
		});

		System.out.println(SettingsChoice.values());

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		textArea = new JTextArea();
		textArea.setFont(new Font("Arial", Font.PLAIN, 13));
		textArea.setColumns(10);
		textArea.setTabSize(2);
		panel_1.add(textArea, BorderLayout.CENTER);

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.EAST);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(e -> {
			SaveDialog dialog = new SaveDialog(textArea.getText(), me);
			dialog.setVisible(true);
		});

		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(e -> {
			OpenDialog dialog = new OpenDialog(me);
			dialog.setVisible(true);
		});
		panel_2.add(btnOpen);
		panel_2.add(btnSave);
	}

	public static void createButton(String text, String name, MainInterface face, String path) {
		JButton button = new JButton(name);
		button.addActionListener(e -> {
			face.textArea.setText(text);
			face.setTitle(path + "\\" + name + "  - Editor");

		});
		System.out.println(button);
		face.panel_3.add(button);
		face.repaint();
	}

	public static void clearButtons(MainInterface face) {
		face.panel_3.removeAll();
	}
}
