/**
 * @author: wnavey
 */
public class TwitterParser {


    /**
     *
     * @param youtubeUrl
     * @return String value of youtube videoId
     */
    // mobile tweets don't have "watch?v=". example mobile youtube link: http://youtu.be/ViwtNLUqkMY
    static String extractVideoIdFromYouTubeURL(String youtubeUrl){
        try{
            //TODO: add "ifLinkIsMobile" method or something
            if(youtubeUrl.contains("watch")){
                return youtubeUrl.split("watch\\?v=")[1];
            }
            else if (youtubeUrl.contains("youtu.be")){
                return youtubeUrl.split("youtu.be\\/")[1];
            }
            else{
                throw new RuntimeException("Encountered youtube link format not yet accounted for: " + youtubeUrl);
            }
        }
        catch(RuntimeException e){
            System.err.println("Error extracting videoId from youtubeUrl: " + youtubeUrl);
            throw e;
        }
    }

    /**
     *
     * @param msg representing the entire tweet captured by the TwitterFilterStream
     * @return String value of Youtube link
     */
    static String extractYouTubeUrlFromTweetExpandedUrl(String msg){
        String[] msgSplitOnExpandedUrl = msg.split("\"expanded_url\"");
        String youtubeLink = msgSplitOnExpandedUrl[1].split("\"")[1];
        youtubeLink = youtubeLink.trim();
        // Remove backslashes
        youtubeLink = youtubeLink.replaceAll("\\\\","");
        return youtubeLink;
    }

    static boolean tweetTextContainsYouTubeURL(String msg){
        return msg.contains("t.co");
    }

    static String extractTweetText(String msg){
        String[] msgSplitOnText = msg.split("\"text\"");
        String tweetText = msgSplitOnText[1].split("\"")[1];
        return tweetText;
    }

    static String extractYouTubeLinkFromTweetText(String tweetText){
        String youtubeLink = tweetText.replaceAll("@RaspberryBBox", "");
        youtubeLink = youtubeLink.trim();
        // Remove backslashes
        youtubeLink = youtubeLink.replaceAll("\\\\","");
        return youtubeLink;
    }
}
