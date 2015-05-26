package youtube;

/**
 * Exception class I created to represent an error during a call to the YouTubeAPI, so that if caught,
 * the program doesn't have to completely error out.
 * @author: William Navey
 */
public class YouTubeAPIException extends RuntimeException {

    public YouTubeAPIException(String msg){
        super(msg);
    }

    public YouTubeAPIException(String msg, Throwable t){
        super(msg, t);
    }

}
