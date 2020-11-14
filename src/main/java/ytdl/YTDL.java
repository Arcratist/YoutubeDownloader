/**
 * 
 */
package ytdl;

import com.sapher.youtubedl.DownloadProgressCallback;
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
	
	public YoutubeDLResponse makeRequest(String videoUrl, String directory, DownloadProgressCallback callback) throws YoutubeDLException {
		YoutubeDLRequest request = new YoutubeDLRequest(videoUrl, directory);
		
		if (config.ignore_errors) request.setOption("ignore-errors"); // --ignore-errors
		if (config.restrict_filenames) request.setOption("restrict-filenames"); // --restrict-filenames
		if (config.extract_audio) request.setOption("extract-audio"); // --extract-audio
		if (config.output != "") request.setOption("output", config.output); // --output <param>
		if (config.merge_output_format != "") request.setOption("merge-output-format", config.merge_output_format); // --merge-output-format <param>
		if (config.recode_video != "") request.setOption("recode-video", config.recode_video); // --recode-video <param>
		if (config.format != "") request.setOption("format", config.format); // --format <param>
		if (config.audio_format != "") request.setOption("audio-format", config.audio_format); // --audio-format <param>
		if (config.retries >= 0) request.setOption("retries", config.retries); // --retries <param>
		if (config.audio_quality >= 0) request.setOption("audio-quality", config.audio_quality); // --audio-quality <param>
		
		if (callback == null)
			return YoutubeDL.execute(request);
		else
			return YoutubeDL.execute(request, callback);
	}

	public void setConfig(Config config) {
		this.config = config;
	}
	
	public Config getConfig() {
		return this.config;
	}
	
}
