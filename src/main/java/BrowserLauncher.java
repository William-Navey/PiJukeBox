import sun.security.krb5.internal.crypto.Des;

import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * A class I've used to test launching browsers. Most likely will be removed from "production" code.
 *
 * @author: wnavey
 */
public class BrowserLauncher {

    public static void main(String[] args) throws Exception{

//        String url1 = "https://www.youtube.com/watch?v=QUwxKWT6m7U";
//        String url2 = "https://www.youtube.com/watch?v=jYa1eI1hpDE";
//        String url3 = "https://www.youtube.com/watch?v=cMPEd8m79Hw";
//        String url4 = "https://www.youtube.com/watch?v=fWNaR-rxAic";
//
//        int intervalMilis = 10 * 1000;
//
//        Process p1 = launchFirefoxBrowser(url1);
//        Thread.sleep(intervalMilis);
//        p1.destroy();
//
//        Process p2 = launchFirefoxBrowser(url2);
//        Thread.sleep(intervalMilis);
//        p2.destroy();
//
//        Process p3 = launchFirefoxBrowser(url3);
//        Thread.sleep(intervalMilis);
//        p3.destroy();
//
//        Process p4 = launchFirefoxBrowser(url4);

        String youtubeLink = "https://www.youtube.com/watch?v=ViwtNLUqkMY";
        String videoId = extractVideoIdFromYouTubeURL(youtubeLink);
        System.out.println("VideoId: " + videoId);


//        launchIEBrowser();

//        launchChromeBrowser();

    }

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

    static Process launchFirefoxBrowser(String url) throws Exception{
        //http://www.programcreek.com/2009/05/using-java-open-ie-and-close-ie/
        try {
            return Runtime.getRuntime().exec("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe " + url);
        } catch (Exception e) {
            throw e;
        }
    }

    static Process launchIEBrowser(String url) throws Exception{
        //http://www.programcreek.com/2009/05/using-java-open-ie-and-close-ie/
        while(true){
            try {
                Process p = Runtime.getRuntime().exec("C:\\Test\\iexplore.exe \"http://www.google.com\"");
                Thread.sleep(5000);
                p.destroy();
                System.out.println("Return value was " + p.waitFor());
            } catch (Exception e) {}
        }
    }

        static void launchChromeBrowser(){
        //http://www.programcreek.com/2009/05/using-java-open-ie-and-close-ie/
        while(true){
            try {
                Process p = Runtime.getRuntime().exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe \"http://www.google.com\"");
                Thread.sleep(5000);
                p.destroy();
                System.out.println("Return value was " + p.waitFor());
            } catch (Exception e) {}
        }
    }

//    static void launchAThing
}
