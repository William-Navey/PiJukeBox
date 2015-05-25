package twitter;

/**
 * Exception class I created to represent an error during the process of parsing the tweet json, so that if caught,
 * the program doesn't have to completely error out.
 * @author: William Navey
 */
public class TwitterParserException extends RuntimeException {

    public TwitterParserException(String msg){
        super(msg);
    }

    public TwitterParserException(String msg, Throwable t){
        super(msg, t);
    }
}
