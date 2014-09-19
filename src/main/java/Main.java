/**
 * @author: wnavey
 */

import java.awt.*;
import java.io.IOException;
import java.net.URI;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import youtube.YouTubeProxy;

// PRE-REQS for running this software!
// Install Firefox
// install add-block firefox plugin
// install adobe flash firefox plugin (some youtube videos require flash
// in Firefox, type "about:config" in url bar, filter with "resume", and change browser.sessionstore.resume_from_crash to false

public class Main {

    // Twitter OAuth credentials
    private static final String TWITTER_CONSUMER_KEY = "25OhIyt0Ennr7w449r1ed1qeK";
    private static final String TWITTER_CONSUMER_SECRET = "WNV3D1kIiitFug7pYPWGRvMSppmfAFnAFzHxlPuVDhOMZEoBso";
    private static final String TWITTER_ACCESS_TOKEN = "2634630697-2KxNbTZ7ZoKpc63xFkgmfJsxWXgri4L5dhczPSk";
    private static final String TWITTER_ACCESS_TOKEN_SECRET = "LXhGPdhX1QDZ6K0kAgTFbSBLmmbpvoik7SaRgP726w9YU";


    public static void main( String[] args ) {

        try {
            String twitterHandle = "@RaspberryBBox";
            YouTubeProxy youTubeProxy = new YouTubeProxy();
            TwitterFilterStream twitterFilterStream = new TwitterFilterStream(youTubeProxy);

            twitterFilterStream.run(twitterHandle, TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET, TWITTER_ACCESS_TOKEN, TWITTER_ACCESS_TOKEN_SECRET);

        } catch (GoogleJsonResponseException e) {
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

    static void testWebBrowser(String uristring){
        try{
            if(Desktop.isDesktopSupported())
            {
                System.out.println("Launching link in browser...");
                Desktop.getDesktop().browse(new URI(uristring));
            }
            else{
                System.err.println("Failed to launch " + uristring);
            }
        } catch (Exception ex){
            System.err.println(ex.getStackTrace());
        }
    }
}
