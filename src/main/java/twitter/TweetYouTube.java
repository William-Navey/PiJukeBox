package twitter;

/**
 * Class encapsulating the necessary data extracted from a tweet containing a YouTube Url
 * @author: William Navey
 */
public class TweetYouTube {

    private final String youtubeUrl;
    private final String videoId;
    private final String tweetUserHandle;

    public TweetYouTube(String youtubeUrl, String videoId, String tweetUserHandle) {
        this.youtubeUrl = youtubeUrl;
        this.videoId = videoId;
        this.tweetUserHandle = tweetUserHandle;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getTweetUserHandle() {
        return tweetUserHandle;
    }
}
