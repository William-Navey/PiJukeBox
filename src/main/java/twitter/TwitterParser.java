package twitter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class used for parsing the full json of a tweet.
 * @author: William Navey
 */
public class TwitterParser {

    public static final Logger logger = LogManager.getLogger(TwitterParser.class.getClass().getName());

    /**
     * Returns true if the tweet contains a youtube url
     * @param tweetJson
     * @return
     */
    public static boolean tweetContainsYouTubeURL(String tweetJson){
        String tweetText = extractTweetText(tweetJson);
        try {
            if (tweetText == null) {
                //Log twitter msg was null
                logger.debug("Twitter msg was null");
                return false;
            }
            return tweetText.contains("t.co");
        } catch(Exception ex) {
            throw new TwitterParserException("Error in tweetContainsYouTubeURL method: " + ex.getMessage(), ex);
        }
    }

    /**
     * Extract the tweet text from the full tweet json
     * @param tweetJson
     * @return
     */
    private static String extractTweetText(String tweetJson) {
        if (tweetJson == null) {
            throw new TwitterParserException("Cannot parse null tweetJson.");
        }
        try {
            String[] msgSplitOnText = tweetJson.split("\"text\"");
            String tweetText = msgSplitOnText[1].split("\"")[1];
            return tweetText;
        } catch(Exception ex) {
            throw new TwitterParserException("Error in extractTweetText method: " + ex.getMessage(), ex);
        }
    }

    /**
     * Parses the tweet param and returns an object encapsulating all the necessary data of a tweet containing a youtube url
     * @param tweetJson
     * @return
     */
    public static TweetYouTube parseYouTubeTweet(String tweetJson) {
        String url = extractYouTubeUrlFromTweetExpandedUrl(tweetJson);
        String videoId = extractVideoIdFromYouTubeURL(url);
        String screenName = extractScreenNameFromTweet(tweetJson);

        return new TweetYouTube(url, videoId, screenName);
    }

    /**
     * Given a YouTube video url, extract the videoId
     * @param youtubeUrl
     * @return String value of youtube videoId
     */
    // mobile tweets don't have "watch?v=". example mobile youtube link: http://youtu.be/ViwtNLUqkMY
    private static String extractVideoIdFromYouTubeURL(String youtubeUrl) {
        try{
            // Desktop tweet
            if (youtubeUrl.contains("watch")) {
                return youtubeUrl.split("watch\\?v=")[1];
            }
            // Mobile tweet
            else if (youtubeUrl.contains("youtu.be")) {
                return youtubeUrl.split("youtu.be\\/")[1];
            }
            else {
                throw new TwitterParserException("Encountered youtube link format not yet accounted for: " + youtubeUrl);
            }
        }
        catch(Exception ex) {
            throw new TwitterParserException("Error in extractVideoIdFromYouTubeURL method: " + ex.getMessage(), ex);
        }
    }

    /**
     * Extract the tweeter's screen name from a tweet
     * @param tweetJson
     * @return
     */
    private static String extractScreenNameFromTweet(String tweetJson) {
        try {
            String[] tweetSplitOnScreenName = tweetJson.split("\"screen_name\"");
            String screenName = tweetSplitOnScreenName[1].split("\"")[1];
            return screenName;
        } catch(Exception ex) {
            throw new TwitterParserException("Error in extractScreenNameFromTweet method: " + ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param msg representing the entire tweet captured by the TwitterFilterStream
     * @return String value of Youtube link
     */
    private static String extractYouTubeUrlFromTweetExpandedUrl(String msg) {
        try {
            String[] msgSplitOnExpandedUrl = msg.split("\"expanded_url\"");
            String youtubeLink = msgSplitOnExpandedUrl[1].split("\"")[1];
            youtubeLink = youtubeLink.trim();
            // Remove backslashes
            youtubeLink = youtubeLink.replaceAll("\\\\","");
            return youtubeLink;
        } catch(Exception ex) {
            throw new TwitterParserException("Error in extractYouTubeUrlFromTweetExpandedUrl method: " + ex.getMessage(), ex);
        }
    }


}
