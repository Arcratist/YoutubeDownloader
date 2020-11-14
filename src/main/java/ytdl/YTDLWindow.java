package ytdl;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class YTDLWindow extends JFrame {

	private static final long serialVersionUID = 3602446118311445086L;
	private JPanel contentPane = null;
	private YTDL ytdl = null;

	/**
	 * Create the frame.
	 */
	public YTDLWindow() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1280, 720);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	public void setYTDL(YTDL ytdl) {
		this.ytdl = ytdl;
	}

	public YTDL getYTDL() {
		return this.ytdl;
	}

}
