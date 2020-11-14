/**
 * 
 */
package ytdl;

import java.awt.EventQueue;

import javax.swing.UIManager;

/**
 * @author Brett Daniel Smith
 *
 */
public class Main {

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");  // This line gives Windows Theme
				     
					Config config = new Config();
					
					YTDL ytdl = new YTDL();
					ytdl.setConfig(config);
					
					YTDLWindow frame = new YTDLWindow();
					frame.setYTDL(ytdl);
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
