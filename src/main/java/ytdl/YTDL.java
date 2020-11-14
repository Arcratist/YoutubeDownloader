/**
 * 
 */
package ytdl;

import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLException;
import com.sapher.youtubedl.YoutubeDLRequest;
import com.sapher.youtubedl.YoutubeDLResponse;

/**
 * @author Brett Daniel Smith
 *
 */
public class YTDL {

	private Config config = null;

	public YTDL() {
		
	}
	
	public YoutubeDLResponse makeRequest(String videoUrl, String directory) throws YoutubeDLException {
		YoutubeDLRequest request = new YoutubeDLRequest(videoUrl, directory);
		
		if (config.ignore_errors) request.setOption("ignore-errors"); // --ignore-errors
		if (config.output != null) request.setOption("output", config.output); // --output <param>
		if (config.retries >= 0) request.setOption("retries", config.retries); // --retries <param>
		
		return YoutubeDL.execute(request);
	}

	public void setConfig(Config config) {
		this.config = config;
	}
	
	public Config getConfig() {
		return this.config;
	}
	
}
