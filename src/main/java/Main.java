/**
 * @author: wnavey
 */

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import twitter.TwitterFilterStream;
import youtube.YouTubeProxy;

// PRE-REQS for running this software!
// Install Firefox
// install add-block firefox plugin
// install adobe flash firefox plugin (some youtube videos require flash
// in Firefox, type "about:config" in url bar, filter with "resume", and change browser.sessionstore.resume_from_crash to false

public class Main {


    static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main( String[] args ) {

        try {
            String twitterScreenName = "@RaspberryBBox";
            YouTubeProxy youTubeProxy = new YouTubeProxy("/google_client_secrets.json");
            TwitterFilterStream twitterFilterStream = new TwitterFilterStream(youTubeProxy, "/twitter_oath_credentials.json");

            logger.info("Listening for tweets at " + twitterScreenName);
            twitterFilterStream.run(twitterScreenName);

        }
        catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (Throwable t) {
            System.err.println("Throwable: " + t.getMessage());
            t.printStackTrace();
        }
    }
}
