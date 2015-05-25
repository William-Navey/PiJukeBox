package twitter;

/**
 * @author: William Navey
 */
public class TwitterParser {

    /**
     * Returns true if the tweet contains a youtube url
     * @param msg
     * @return
     */
    public static boolean tweetTextContainsYouTubeURL(String msg){
        try{
            if(msg==null){
                //Log twitter msg was null
                System.err.println("Twitter msg was null");
            }
            return msg.contains("t.co");
        } catch(Exception ex){
            throw new TwitterParserException("Error in tweetTextContainsYouTubeURL method: " + ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param youtubeUrl
     * @return String value of youtube videoId
     */
    // mobile tweets don't have "watch?v=". example mobile youtube link: http://youtu.be/ViwtNLUqkMY
    public static String extractVideoIdFromYouTubeURL(String youtubeUrl){
        try{
            //TODO: add "ifLinkIsMobile" method or something
            if(youtubeUrl.contains("watch")){
                return youtubeUrl.split("watch\\?v=")[1];
            }
            else if (youtubeUrl.contains("youtu.be")){
                return youtubeUrl.split("youtu.be\\/")[1];
            }
            else{
                throw new TwitterParserException("Encountered youtube link format not yet accounted for: " + youtubeUrl);
            }
        }
        catch(Exception ex){
            throw new TwitterParserException("Error in extractVideoIdFromYouTubeURL method: " + ex.getMessage(), ex);
        }
    }

    public static String extractScreenNameFromTweetJson(String tweetJson){
        try{
            String[] tweetSplitOnScreenName = tweetJson.split("\"screen_name\"");
            String screenName = tweetSplitOnScreenName[1].split("\"")[1];
            return screenName;
        } catch(Exception ex){
            throw new TwitterParserException("Error in extractScreenNameFromTweetJson method: " + ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param msg representing the entire tweet captured by the TwitterFilterStream
     * @return String value of Youtube link
     */
    public static String extractYouTubeUrlFromTweetExpandedUrl(String msg){
        try{
            String[] msgSplitOnExpandedUrl = msg.split("\"expanded_url\"");
            String youtubeLink = msgSplitOnExpandedUrl[1].split("\"")[1];
            youtubeLink = youtubeLink.trim();
            // Remove backslashes
            youtubeLink = youtubeLink.replaceAll("\\\\","");
            return youtubeLink;
        } catch(Exception ex){
            throw new TwitterParserException("Error in extractYouTubeUrlFromTweetExpandedUrl method: " + ex.getMessage(), ex);
        }
    }

    public static String extractTweetText(String msg){
        try{
            String[] msgSplitOnText = msg.split("\"text\"");
            String tweetText = msgSplitOnText[1].split("\"")[1];
            return tweetText;
        } catch(Exception ex){
            throw new TwitterParserException("Error in extractTweetText method: " + ex.getMessage(), ex);
        }
    }
}
