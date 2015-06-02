package twitter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: William Navey
 */
public class TweetHistorian {

    private final Set<String> usersTweeted = new HashSet<String>();
    private final Set<String> youtubeVideoIdsTweeted = new HashSet<String>();
    private final boolean prioritizeNewUsers;
    private final boolean prioritizeNewSongs;

    public static final Logger logger = LogManager.getLogger(TweetHistorian.class.getClass().getName());

    public TweetHistorian(boolean prioritizeNewUsers, boolean prioritizeNewSongs) {
        this.prioritizeNewSongs = prioritizeNewSongs;
        this.prioritizeNewUsers = prioritizeNewUsers;
    }


    /**
     * Calculates the priority score of a given tweet. The higher the priority integer value, the less "important" it is.
     *  e.x: A YouTubeVideo of priority 0 will get chosen over a YouTubeVideo of priority 1
     * @param tweetYouTube
     * @return priorityScore
     */
    public int calculateTweetYouTubePriorityScore(TweetYouTube tweetYouTube) {
        int priority = 0;

        if (prioritizeNewSongs) {
            if (usersTweeted.contains(tweetYouTube.getTweetUserHandle())) {
                logger.debug("User has tweeted before, video priority decreased.");
                priority+=2;
            }
        }

        if (prioritizeNewUsers) {
            if (youtubeVideoIdsTweeted.contains(tweetYouTube.getVideoId())) {
                logger.debug("Song has been tweeted before, video priority increased.");
                priority++;
            }
        }

        return priority;
    }

    /**
     * Logs the tweet metadata
     * @param tweet
     */
    public void logTweetYouTube(TweetYouTube tweet) {
        if (tweet == null) {
            throw new NullPointerException("TweetHistorian cannot log null tweet.");
        }
        if (tweet.getTweetUserHandle() == null || tweet.getTweetUserHandle().trim().length()==0) {
            throw new TwitterParserException(
                    "TweetHistorian cannot log userHandle : " + tweet.getTweetUserHandle() + ". " +
                    "UserHandle must be a non-null, non-zero String.");
        }
        if (tweet.getVideoId() == null || tweet.getVideoId().trim().length()==0) {
            throw new TwitterParserException(
                    "TweetHistorian cannot log videoId : " + tweet.getTweetUserHandle() + ". " +
                    "videoId must be a non-null, non-zero String.");
        }

        this.usersTweeted.add(tweet.getTweetUserHandle());
        logger.debug("Added " + tweet.getTweetUserHandle() + " to usersTweeted");

        this.youtubeVideoIdsTweeted.add(tweet.getVideoId());
        logger.debug("Added " + tweet.getVideoId() + " to youtubeVideoIdsTweeted");
    }
}
