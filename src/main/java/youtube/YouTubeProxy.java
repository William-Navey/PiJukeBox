package youtube;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.common.collect.Lists;
import org.joda.time.Period;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;

import java.io.IOException;
import java.util.List;

/**
 * Facade class for interacting with the youtube
 * @author: wnavey
 */
public class YouTubeProxy {

    // Global instance of YouTube object to make all API requests.
    private static YouTube youtube;

    public YouTubeProxy(String clientSecretsFile) throws IOException{

        // General read/write scope for YouTube APIs.
        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");
        // Authorization.
        Credential credential = GoogleAuthorizer.authorize(scopes, "myVideoDuration", clientSecretsFile);

        // YouTube object used to make all API requests.
        youtube = new YouTube.Builder(GoogleAuthorizer.HTTP_TRANSPORT, GoogleAuthorizer.JSON_FACTORY, credential)
                .setApplicationName("JukeBox-YouTube-Proxy")
                .build();
    }

    public int requestVideoDuration(String videoId) throws IOException{

        try {


            YouTube.Videos.List videoListRequest = youtube.videos().list("contentDetails");
            videoListRequest.setId(videoId);
            videoListRequest.setFields("items/contentDetails");

            VideoListResponse videoListResponse = videoListRequest.execute();
            List<Video> videoList = videoListResponse.getItems();

            //TODO: improve
            Video video = videoList.get(0);
            String strDuration = video.getContentDetails().getDuration();

            PeriodFormatter periodFormatter = ISOPeriodFormat.standard();
            Period period = periodFormatter.parsePeriod(strDuration);

            return period.toStandardSeconds().getSeconds() * 1000;

        } catch (IOException ex){
            throw new IOException("Error requesting video duration: " + ex.getMessage(), ex);
        }

    }
}
