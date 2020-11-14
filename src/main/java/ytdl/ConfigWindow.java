package ytdl;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ConfigWindow extends JFrame {

	private static final long serialVersionUID = 8858515486331168354L;
	private JPanel contentPane;
	private JTextField txt_output;
	private JTextField txt_merge_output_format;
	private JTextField txt_format;
	private JTextField txt_retries;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Create the frame.
	 */
	public ConfigWindow(YTDL ytdl) {
		setResizable(false);
		setTitle("Config");
		setBounds(100, 100, 400, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JToggleButton tglbtn_ignore_errors = new JToggleButton(
				Boolean.toString(ytdl.getConfig().ignore_errors).toUpperCase());
		tglbtn_ignore_errors.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				ytdl.getConfig().ignore_errors = tglbtn_ignore_errors.isSelected();
				tglbtn_ignore_errors.setText(Boolean.toString(ytdl.getConfig().ignore_errors).toUpperCase());
			}
		});
		tglbtn_ignore_errors.setSelected(ytdl.getConfig().ignore_errors);
		tglbtn_ignore_errors.setBounds(115, 5, 265, 20);
		contentPane.add(tglbtn_ignore_errors);

		JToggleButton tglbtn_restrict_filenames = new JToggleButton(
				Boolean.toString(ytdl.getConfig().restrict_filenames).toUpperCase());
		tglbtn_restrict_filenames.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				ytdl.getConfig().restrict_filenames = tglbtn_restrict_filenames.isSelected();
				tglbtn_restrict_filenames.setText(Boolean.toString(ytdl.getConfig().restrict_filenames).toUpperCase());
			}
		});
		tglbtn_restrict_filenames.setSelected(ytdl.getConfig().restrict_filenames);
		tglbtn_restrict_filenames.setBounds(115, 30, 265, 20);
		contentPane.add(tglbtn_restrict_filenames);

		txt_output = new JTextField();
		txt_output.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				ytdl.getConfig().output = txt_output.getText();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
		});

		txt_output.setText(ytdl.getConfig().output);
		txt_output.setBounds(115, 80, 265, 20);
		contentPane.add(txt_output);
		txt_output.setColumns(10);

		txt_merge_output_format = new JTextField();
		txt_merge_output_format.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				ytdl.getConfig().merge_output_format = txt_merge_output_format.getText();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
		});
		txt_merge_output_format.setText(ytdl.getConfig().merge_output_format);
		txt_merge_output_format.setBounds(115, 105, 265, 20);
		contentPane.add(txt_merge_output_format);
		txt_merge_output_format.setColumns(10);

		txt_format = new JTextField();
		txt_format.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				ytdl.getConfig().format = txt_format.getText();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
		});
		txt_format.setText(ytdl.getConfig().format);
		txt_format.setBounds(115, 130, 265, 20);
		contentPane.add(txt_format);
		txt_format.setColumns(10);

		JLabel lblNewLabel = new JLabel("Ignore Errors:");
		lblNewLabel.setBounds(5, 5, 100, 20);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Restrict Filenames:");
		lblNewLabel_1.setBounds(5, 30, 100, 20);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Output:");
		lblNewLabel_2.setBounds(5, 80, 46, 20);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Merge Output Format:");
		lblNewLabel_3.setBounds(5, 105, 110, 20);
		contentPane.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Format: ");
		lblNewLabel_4.setBounds(5, 130, 100, 20);
		contentPane.add(lblNewLabel_4);

		txt_retries = new JTextField();
		txt_retries.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				ytdl.getConfig().retries = Integer.parseInt(txt_retries.getText());
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
		});
		txt_retries.setText("" + ytdl.getConfig().retries);
		txt_retries.setColumns(10);
		txt_retries.setBounds(115, 205, 265, 20);
		contentPane.add(txt_retries);

		JLabel lblNewLabel_5 = new JLabel("Retries:");
		lblNewLabel_5.setBounds(5, 205, 100, 20);
		contentPane.add(lblNewLabel_5);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				setVisible(false);
			}
		});
		btnNewButton.setBounds(5, 535, 375, 20);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_4_1 = new JLabel("Recode Video:");
		lblNewLabel_4_1.setBounds(5, 155, 100, 20);
		contentPane.add(lblNewLabel_4_1);
		
		textField = new JTextField();
		textField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				ytdl.getConfig().recode_video = textField.getText();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
		});
		textField.setText(ytdl.getConfig().recode_video);
		textField.setColumns(10);
		textField.setBounds(115, 155, 265, 20);
		contentPane.add(textField);
		
		JLabel lblNewLabel_6 = new JLabel("Extract Audio:");
		lblNewLabel_6.setBounds(5, 55, 100, 20);
		contentPane.add(lblNewLabel_6);
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton(Boolean.toString(ytdl.getConfig().extract_audio).toUpperCase());
		tglbtnNewToggleButton.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				ytdl.getConfig().extract_audio = tglbtnNewToggleButton.isSelected();
				tglbtnNewToggleButton.setText(Boolean.toString(ytdl.getConfig().extract_audio).toUpperCase());
			}
		});
		tglbtnNewToggleButton.setSelected(ytdl.getConfig().extract_audio);
		tglbtnNewToggleButton.setBounds(115, 55, 265, 20);
		contentPane.add(tglbtnNewToggleButton);
		
		textField_1 = new JTextField();
		textField_1.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				ytdl.getConfig().audio_format = textField_1.getText();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
		});
		textField_1.setText(ytdl.getConfig().audio_format);
		textField_1.setColumns(10);
		textField_1.setBounds(115, 180, 265, 20);
		contentPane.add(textField_1);
		
		JLabel lblNewLabel_4_1_1 = new JLabel("Audio Format");
		lblNewLabel_4_1_1.setBounds(5, 180, 100, 20);
		contentPane.add(lblNewLabel_4_1_1);
		
		textField_2 = new JTextField();
		textField_2.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				ytdl.getConfig().audio_quality = Integer.parseInt(textField_2.getText());
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
		});
		textField_2.setText("" + ytdl.getConfig().audio_quality);
		textField_2.setColumns(10);
		textField_2.setBounds(115, 230, 265, 20);
		contentPane.add(textField_2);
		
		JLabel lblNewLabel_5_1 = new JLabel("Audio Quality:");
		lblNewLabel_5_1.setBounds(5, 230, 100, 20);
		contentPane.add(lblNewLabel_5_1);
	}
}
