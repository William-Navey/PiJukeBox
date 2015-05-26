package youtube;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.common.collect.Lists;
import org.joda.time.Period;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Facade class for interacting with the youtube api
 * Code reference: https://developers.google.com/youtube/v3/docs/videos/list
 *
 * @author: William Navey
 */
public class YouTubeClientProxy {

    // Global instance of YouTube object to make all API requests.
    private final YouTube youtube;

    public YouTubeClientProxy(String clientSecretsFile) throws IOException{

        // General read/write scope for YouTube APIs.
        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");
        // Authorization.
        Credential credential = GoogleAuthorizer.authorize(scopes, "myVideoDuration", clientSecretsFile);

        // YouTube object used to make all API requests.
        youtube = new YouTube.Builder(GoogleAuthorizer.HTTP_TRANSPORT, GoogleAuthorizer.JSON_FACTORY, credential)
                .setApplicationName("JukeBox-YouTube-Proxy")
                .build();
    }

    /**
     * Makes API calls to retrieve the necessary data given a youtube video id, and returns the constructed
     * YouTubeVideo object to encapsulate said data.
     * @param videoId Id of a YouTube video. Required to make API request.
     * @param youtubeUrl Url of video, used for prioritization.
     * @param twitterHandle Handle of user who tweeted the url, used for prioritization.
     * @return
     */
    public YouTubeVideo createYouTubeVideo(String videoId, String youtubeUrl, String twitterHandle) {
        final String videoParts = "snippet, contentDetails";
        try {
            // Execute API request for video parts
            YouTube.Videos.List videoListRequest = youtube.videos().list(videoParts).setId(videoId);
            VideoListResponse videoListResponse = videoListRequest.execute();
            List<Video> videoList = videoListResponse.getItems();

            if (videoList == null || videoList.size() == 0) {
                throw new YouTubeAPIException(
                        "Request for parts \"" + videoParts + "\" of video of id \""+videoId+"\" returned a null or empty response."
                );
            }

            Video video = videoList.get(0);
            String videoTitle = video.getSnippet().getTitle();
            int videoDurationMS = calculateDuratrionFromString(video.getContentDetails().getDuration());
            return new YouTubeVideo(videoId, videoDurationMS, videoTitle, youtubeUrl, twitterHandle);
        } catch (IOException ex){
            throw new YouTubeAPIException(
                    "Error requesting video parts \""+videoParts+"\" from video of id \"" + videoId + "\":" + ex.getMessage(), ex);
        }
    }

    // Returns an integer representing the video duration in milliseconds, derived from the string duration returned
    // from the YouTube API
    private int calculateDuratrionFromString(String strDuration) {
        PeriodFormatter periodFormatter = ISOPeriodFormat.standard();
        Period period = periodFormatter.parsePeriod(strDuration);
        return period.toStandardSeconds().getSeconds() * 1000;
    }
}
