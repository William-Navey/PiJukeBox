package twitter;

/**
 * @author: wnavey
 */
public class TweetLogger {

    private static TweetLogger instance = null;

    private TweetLogger(){}

    public synchronized static TweetLogger getInstance(){
        if(instance == null){
            instance = new TweetLogger();
        }
        return instance;
    }


}
