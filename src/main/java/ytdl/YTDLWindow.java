package ytdl;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.sapher.youtubedl.DownloadProgressCallback;
import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLException;
import com.sapher.youtubedl.YoutubeDLResponse;

public class YTDLWindow extends JFrame {

	private static final long serialVersionUID = 3602446118311445086L;
	private JPanel contentPane = null;
	private YTDL ytdl = null;
	private JTextField textField_Output;
	private JTextField textField_URL;
	private JMenuBar menuBar;
	private JMenu mn_File;
	private JProgressBar progressBar;
	private JLabel lbl_downloadPercentage;
	private Canvas canvas;
	protected Image img;
	private PrintStream standardOut;
	private JButton btn_browse;
	private JButton btn_check;
	private JMenuItem mntm_forcestop;
	private YoutubeDLResponse response;
	private JMenuItem mntm_config;
	private ConfigWindow configWindow;

	public List<Profile> profiles = new ArrayList<Profile>();
	private JComboBox<String> comboBox;

	/**
	 * Create the frame.
	 */
	public YTDLWindow()
	{
		
		loadProfiles();
		
		setTitle("YouTube Downloader v0.01a by Brett Daniel Smith");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1280, 720);
		setLocationRelativeTo(null);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mn_File = new JMenu("File");
		menuBar.add(mn_File);

		JMenuItem mntm_File_Exit = new JMenuItem("Exit");
		mntm_File_Exit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
		});

		mntm_forcestop = new JMenuItem("Force Stop");
		mntm_forcestop.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("Forced Stop!");
				if (YoutubeDL.process != null)
					YoutubeDL.process.destroyForcibly();
			}
		});

		mntm_config = new JMenuItem("Config");
		mntm_config.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (configWindow == null)
					configWindow = new ConfigWindow(getYTDL());
				configWindow.setVisible(true);
			}
		});
		mn_File.add(mntm_config);
		mn_File.add(mntm_forcestop);
		mn_File.add(mntm_File_Exit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField_Output = new JTextField();
		textField_Output.setText(".");
		textField_Output.setEnabled(false);
		textField_Output.setToolTipText("Output directory.");
		textField_Output.setBounds(5, 485, 700, 20);
		contentPane.add(textField_Output);
		textField_Output.setColumns(10);

		JButton btn_download = new JButton("Download");
		btn_download.setEnabled(false);
		btn_download.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!btn_download.isEnabled())
					return;
				ytdl.setConfig(profiles.get(comboBox.getSelectedIndex()).config);
				
				Runnable runnable = new Runnable() {

					@Override
					public void run() {
						try {
							System.out.println("Starting download of " + textField_URL.getText() + "...");
							progressBar.setValue(0);
							lbl_downloadPercentage.setText("0%");
							btn_browse.setEnabled(false);
							textField_Output.setEnabled(false);
							textField_URL.setEnabled(false);
							btn_check.setEnabled(false);
							btn_download.setEnabled(false);
							btn_download.setText("Downloading...");
							response = ytdl.makeRequest(textField_URL.getText(), textField_Output.getText(),
									new DownloadProgressCallback() {

										@Override
										public void onProgressUpdate(float percentage, long eta) {
											System.out.println("Downloaded: " + percentage + "%, ETA: " + eta + "s...");
											progressBar.setValue((int) percentage);
											lbl_downloadPercentage.setText(percentage + "%");
										}

									});
							Scanner scanner = new Scanner(response.getOut());
							while (scanner.hasNextLine()) {
								String line = scanner.nextLine();
								if (line.startsWith("[youtube] ") || line.startsWith("[download] ")) {
								} else {
									System.out.println(line);
								}

							}
							scanner.close();
							progressBar.setValue(100);
							lbl_downloadPercentage.setText("Complete");
							btn_browse.setEnabled(true);
							textField_Output.setEnabled(true);
							textField_URL.setEnabled(true);
							btn_check.setEnabled(true);
							btn_download.setEnabled(true);
							btn_download.setText("Download");
							System.out.println("Download complete!");
						} catch (YoutubeDLException e1) {
							progressBar.setValue(0);
							lbl_downloadPercentage.setText("ERROR!");
							btn_browse.setEnabled(true);
							textField_Output.setEnabled(true);
							textField_URL.setEnabled(true);
							btn_check.setEnabled(true);
							btn_download.setEnabled(true);
							btn_download.setText("Download");
							System.out.println("Download failed!");
							e1.printStackTrace();
						}
					}
				};
				Thread thread = new Thread(runnable, "tydl_downloader");
				thread.start();
			}
		});
		btn_download.setBounds(710, 635, 95, 20);
		contentPane.add(btn_download);

		textField_URL = new JTextField();
		textField_URL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_download.setEnabled(false);
				btn_browse.setEnabled(false);
				textField_Output.setEnabled(false);
				progressBar.setValue(0);
				lbl_downloadPercentage.setText("0%");
			}
		});
		textField_URL.setToolTipText("Youtube link.");
		textField_URL.setText("");
		textField_URL.setBounds(5, 460, 700, 20);
		contentPane.add(textField_URL);
		textField_URL.setColumns(10);

		btn_browse = new JButton("Browse");
		btn_browse.setEnabled(false);
		btn_browse.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (!btn_browse.isEnabled())
					return;
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File(textField_Output.getText()));
				chooser.setDialogTitle("Select destionation folder...");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setApproveButtonText("Select");
				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					textField_Output.setText(chooser.getSelectedFile().getAbsolutePath());
					btn_download.setEnabled(true);
				} else {
				}
			}
		});
		btn_browse.setBounds(710, 485, 95, 20);
		contentPane.add(btn_browse);

		progressBar = new JProgressBar();
		progressBar.setEnabled(false);
		progressBar.setBounds(5, 635, 700, 20);
		progressBar.setMaximum(100);
		progressBar.setMinimum(0);
		contentPane.add(progressBar);

		lbl_downloadPercentage = new JLabel("0%");
		progressBar.add(lbl_downloadPercentage);
		lbl_downloadPercentage.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_downloadPercentage.setBounds(300, 0, 100, 20);

		canvas = new Canvas() {
			private static final long serialVersionUID = 1265062518747501579L;

			@Override
			public void paint(Graphics g) {
				if (img != null)
					g.drawImage(img, 0, 0, 800, 450, null);
			}
		};
		canvas.setBackground(Color.BLACK);
		canvas.setBounds(5, 5, 800, 450);
		contentPane.add(canvas);

		JTextArea textArea = new JTextArea();
		textArea.setRows(20);
		textArea.setColumns(50);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		textArea.setBounds(810, 5, 450, 650);
		textArea.setEditable(false);
		PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));

		// keeps reference of standard output stream
		standardOut = System.out;

		// re-assigns standard output stream and error output stream
		System.setOut(printStream);
		System.setErr(printStream);

		JScrollPane jSP = new JScrollPane(textArea);
		jSP.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jSP.setBounds(810, 5, 450, 650);
		contentPane.add(jSP);

		btn_check = new JButton("Check");
		btn_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Checking " + textField_URL.getText() + "...");
				String videoID = textField_URL.getText().split("\\?v=")[1];
				String url = "http://img.youtube.com/vi/" + videoID + "/0.jpg";
				try {
					img = ImageIO.read(new URL(url));
					System.out.println("Found thumbnail: " + url + "...");
					canvas.repaint();
					btn_browse.setEnabled(true);
					progressBar.setEnabled(true);
					textField_Output.setEnabled(true);
					if (!textField_Output.getText().isEmpty())
						btn_download.setEnabled(true);
				} catch (Exception e1) {
					System.out.println("ERROR! Failed to aquire thumbnail!");
					e1.printStackTrace();
				}
			}
		});
		btn_check.setBounds(710, 460, 95, 20);
		contentPane.add(btn_check);

		comboBox = new JComboBox<String>();
		String[] p_arr = new String[profiles.size()];
		for (Profile profile : profiles) {
			p_arr[profiles.indexOf(profile)] = profile.name;
		}
		comboBox.setModel(new DefaultComboBoxModel<String>(p_arr));
		comboBox.setBounds(710, 510, 95, 20);
		contentPane.add(comboBox);

		JLabel lblNewLabel = new JLabel("Profile:");
		lblNewLabel.setBounds(671, 510, 34, 20);
		contentPane.add(lblNewLabel);
		
		System.out.println();
		System.out.println("=============================================================");
		System.out.println("               Welcome to YouTube Downloader!");
		System.out.println("                     Version: 0.01a");
		System.out.println("              Developed by: Brett Daniel Smith");
		// System.out.println(" Based on youtube-dl by Ricardo Garcia");
		System.out.println("=============================================================");
		System.out.println();
	}
	
	public void loadProfiles() {
		Config config_mp41080 = new Config();
		config_mp41080.format = "(\"bestvideo[width<=1920]\"/bestvideo)+bestaudio/best";
		config_mp41080.merge_output_format = "mp4";
		config_mp41080.recode_video = "mp4";
		profiles.add(new Profile("mp4 (1080p)", config_mp41080));
		
		Config config_mp4720 = new Config();
		config_mp4720.format = "(\"bestvideo[width<=1280]\"/bestvideo)+bestaudio/best";
		config_mp4720.merge_output_format = "mp4";
		config_mp4720.recode_video = "mp4";
		profiles.add(new Profile("mp4 (720p)", config_mp4720));
		
		Config config_mp4480 = new Config();
		config_mp4480.format = "(\"bestvideo[width<=640]\"/bestvideo)+bestaudio/best";
		config_mp4480.merge_output_format = "mp4";
		config_mp4480.recode_video = "mp4";
		profiles.add(new Profile("mp4 (480p)", config_mp4480));
		
		Config config_mkv1080 = new Config();
		config_mkv1080.format = "(\"bestvideo[width<=1920]\"/bestvideo)+bestaudio/best";
		config_mkv1080.merge_output_format = "mkv";
		config_mkv1080.recode_video = "mkv";
		profiles.add(new Profile("mkv (1080p)", config_mkv1080));
		
		Config config_mkv720 = new Config();
		config_mkv720.format = "(\"bestvideo[width<=1280]\"/bestvideo)+bestaudio/best";
		config_mkv720.merge_output_format = "mkv";
		config_mkv720.recode_video = "mkv";
		profiles.add(new Profile("mkv (720p)", config_mkv720));
		
		Config config_mkv480 = new Config();
		config_mkv480.format = "(\"bestvideo[width<=640]\"/bestvideo)+bestaudio/best";
		config_mkv480.merge_output_format = "mkv";
		config_mkv480.recode_video = "mkv";
		profiles.add(new Profile("mkv (480p)", config_mkv480));
		
		Config config_avi1080 = new Config();
		config_avi1080.format = "(\"bestvideo[width<=1920]\"/bestvideo)+bestaudio/best";
		config_avi1080.merge_output_format = "avi";
		config_avi1080.recode_video = "avi";
		profiles.add(new Profile("avi (1080p)", config_avi1080));
		
		Config config_avi720 = new Config();
		config_avi720.format = "(\"bestvideo[width<=1280]\"/bestvideo)+bestaudio/best";
		config_avi720.merge_output_format = "avi";
		config_avi720.recode_video = "avi";
		profiles.add(new Profile("avi (720p)", config_avi720));
		
		Config config_avi480 = new Config();
		config_avi480.format = "(\"bestvideo[width<=640]\"/bestvideo)+bestaudio/best";
		config_avi480.merge_output_format = "avi";
		config_avi480.recode_video = "avi";
		profiles.add(new Profile("avi (480p)", config_avi480));

		Config config_mp3 = new Config();
		config_mp3.format = "";
		config_mp3.merge_output_format = "";
		config_mp3.recode_video = "";
		config_mp3.extract_audio = true;
		config_mp3.audio_format = "mp3";
		config_mp3.audio_quality = 0;
		config_mp3.output = "%(title)s.%(ext)s";
		profiles.add(new Profile("mp3", config_mp3));

	}

	public void setYTDL(YTDL ytdl) {
		this.ytdl = ytdl;
	}

	public YTDL getYTDL() {
		return this.ytdl;
	}
}
