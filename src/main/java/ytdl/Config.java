/**
 * 
 */
package ytdl;

/**
 * @author Brett Daniel Smith
 *
 */
public class Config {
	public boolean ignore_errors = true;
	public boolean restrict_filenames = true;
	public boolean extract_audio = false;
	public String output = "%(title)s-%(height)sp";
	public String merge_output_format = "mp4";
	public String recode_video = "mp4";   
	public String format = "(\"bestvideo[width>=1920]\"/bestvideo)+bestaudio/best";
	public String audio_format = "";
	public int retries = 10;
	public int audio_quality = -1;
	
}
