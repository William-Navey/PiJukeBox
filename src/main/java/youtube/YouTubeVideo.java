package youtube;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import twitter.TwitterFilterStream;

/**
 * @author: William Navey
 */
public class YouTubeVideo implements Comparable<YouTubeVideo> {

    // The higher the priority integer value, the less "important" it is
    // e.x: A YouTubeVideo of priority 0 will get chosen over a YouTubeVideo of priority 1
    private final int priority;
    private final int milisecDuration;
    private final String youtubeUrl;
    private final String videoTitle;

    public static final Logger logger = LogManager.getLogger(YouTubeVideo.class.getClass().getName());

    public YouTubeVideo(String videoId, int milisecDuration, String videoTitle, String youtubeUrl, String screenNameOfTweeter){
        this.priority = calculatePriority(videoId, screenNameOfTweeter);
        this.milisecDuration = milisecDuration;
        this.youtubeUrl = youtubeUrl;
        this.videoTitle = videoTitle;
    }

    private int calculatePriority(String videoId, String screenName){
        int priority = 0;
        if (TwitterFilterStream.usersLogged.contains(screenName)) {
            logger.debug("User has tweeted before, video priority decreased.");
            priority+=2;
        } else {
            TwitterFilterStream.usersLogged.add(screenName);
        }

        if (TwitterFilterStream.songsLogged.contains(videoId)) {
            logger.debug("Song has been tweeted before, video priority increased.");
            priority++;
        } else {
            TwitterFilterStream.songsLogged.add(videoId);
        }
        return priority;
    }

    public int compareTo(YouTubeVideo o){
        // if THIS object is "less than" the specified object (o), return a negative integer
        if (this.priority < o.getPriority()) {
            return -1;
        } else if (this.priority == o.getPriority()){
            return 0;
        } else {
            return 1;
        }
    }

    public int getPriority() {
        return priority;
    }

    public int getMilisecDuration() {
        return milisecDuration;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public String getVideoTitle() {
        return videoTitle;
    }
}
