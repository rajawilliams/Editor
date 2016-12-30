package org.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.main.MainInterface;
import org.main.Settings;

public class OpenDialog extends JDialog {

	/**
	 *
	 */
	private static final long	serialVersionUID	= 1L;
	private final JPanel		contentPanel		= new JPanel();
	private String				path;
	private String				path2;

	/**
	 * Create the dialog.
	 */
	public OpenDialog(MainInterface face) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(e -> setVisible(false));
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(e -> setVisible(false));
				buttonPane.add(cancelButton);
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Choose File",
					TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			contentPanel.add(panel);
			{
				JButton btnChooseFile = new JButton("Choose file");
				btnChooseFile.addActionListener(e -> {
					JFileChooser chooser = new JFileChooser();
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int returnval = chooser.showOpenDialog(getParent());
					if (returnval == JFileChooser.APPROVE_OPTION) {
						path = chooser.getSelectedFile().getPath();
						MainInterface.clearButtons(face);
						File folder = new File(path);
						File[] files = folder.listFiles();
						for (File file : files) {
							if (file.getName().endsWith((String) Settings.extension)) {
								MainInterface.createButton(getFileContents(Paths.get(path + "/" + file.getName())),
										file.getName(), face, path);
								System.out.println(getFileContents(Paths.get(path + "/" + file.getName())));
							}
							if (Files.isDirectory(Paths.get(path + "/" + file.getName()))) {
								path2 = chooser.getSelectedFile().getPath();
								File folder2 = new File(path2);
								File[] files2 = folder2.listFiles();
								for (File file2 : files2) {
									if (file.getName().endsWith((String) Settings.extension)) {
										MainInterface.createButton(
												getFileContents(Paths.get(path2 + "/" + file.getName())),
												file2.getName(), face, path2);
										System.out.println(getFileContents(Paths.get(path2 + "/" + file.getName())));
									}
								}
							}
						}
					}
				});
				panel.add(btnChooseFile);
			}
		}
	}

	public static String getFileContents(Path file) {
		String str = "";
		try {
			Object[] sdf = Files.lines(file).toArray();
			for (Object string : sdf) {
				str = str + string;
				str = str + System.lineSeparator();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

}
