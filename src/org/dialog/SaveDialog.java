package org.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.main.MainInterface;
import org.main.Settings;

public class SaveDialog extends JDialog {

	/**
	 *
	 */
	private static final long	serialVersionUID	= 1L;
	private final JPanel		contentPanel		= new JPanel();
	private JTextField			textField;
	private JPasswordField		passwordField;
	private boolean				passwordLocked		= false;

	private String				path;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the dialog.
	 */
	public SaveDialog(String text, MainInterface face) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Name", TitledBorder.LEADING,
					TitledBorder.TOP, null, new Color(0, 0, 0)));
			contentPanel.add(panel);
			{
				textField = new JTextField();
				panel.add(textField);
				textField.setColumns(10);
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Password",
					TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			contentPanel.add(panel);
			{
				JButton btnPasswordLocked = new JButton("Password Locked");
				btnPasswordLocked.setEnabled(false);
				btnPasswordLocked.addActionListener(arg0 -> {
					passwordLocked = !passwordLocked;
					passwordField.setEnabled(passwordLocked);
				});
				panel.add(btnPasswordLocked);
			}
			{
				passwordField = new JPasswordField();
				panel.add(passwordField);
				passwordField.setColumns(10);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
			}
			JLabel lblNameMustBe = new JLabel("Name must be more than 2 characters!");
			lblNameMustBe.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNameMustBe.setForeground(Color.RED);
			buttonPane.add(lblNameMustBe);

			JButton okButton = new JButton("OK");
			okButton.addActionListener(arg0 -> {
				if (textField.getText().length() <= 2) {
					lblNameMustBe.setVisible(true);
				} else {
					lblNameMustBe.setVisible(false);
					JFileChooser chooser = new JFileChooser();
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int returnval = chooser.showOpenDialog(getParent());
					if (returnval == JFileChooser.APPROVE_OPTION) {
						path = chooser.getSelectedFile().getPath();
						try {
							PrintWriter writer = new PrintWriter(path + "/" + textField.getText() + Settings.extension);
							for (String string : text.split("\\r?\\n")) {
								writer.println(string);
							}
							writer.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						MainInterface.clearButtons(face);
						File folder = new File(path);
						File[] files = folder.listFiles();
						for (File file : files) {
							if (file.getName().endsWith((String) Settings.extension)) {
								MainInterface.createButton(
										OpenDialog.getFileContents(Paths.get(path + "/" + file.getName())),
										file.getName(), face, path);
								System.out.println(OpenDialog.getFileContents(Paths.get(path + "/" + file.getName())));
							}
						}
						setVisible(false);
					} else {
						path = null;
					}
				}

			});
			{

				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(e -> setVisible(false));
				buttonPane.add(cancelButton);
			}
		}
	}

}
