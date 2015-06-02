package youtube;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import twitter.TweetYouTube;
import twitter.TwitterClientProxy;

import java.util.Set;

/**
 * YouTubeVideo class to encapsulate all the important data of a YouTubeVideo. This class makes no API calls.
 *  All the field values must be provided during object construction. Implements Comparable<> for priority queueing
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

    public YouTubeVideo(int milisecDuration, String videoTitle, String youtubeUrl, int priorityScore){
        this.priority = priorityScore;
        this.milisecDuration = milisecDuration;
        this.youtubeUrl = youtubeUrl;
        this.videoTitle = videoTitle;
    }

    @Override
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
