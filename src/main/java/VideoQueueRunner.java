import youtube.YouTubeVideo;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Thread launched to handle playing the videos.
 * @author: wnavey
 */
public class VideoQueueRunner extends Thread {

    private static final int VIDEO_BUFFER_MILIS = 3000;
    private final PriorityBlockingQueue<YouTubeVideo> priorityBlockingQueue;

    public VideoQueueRunner(PriorityBlockingQueue<YouTubeVideo> priorityQueue){
        this.priorityBlockingQueue = priorityQueue;
    }

    public void run(){
        while (true) {
            try{
                YouTubeVideo currentVideo = priorityBlockingQueue.take();
                Process videoProcess = launchFirefoxBrowser(currentVideo.getYoutubeLink());
                Thread.sleep(currentVideo.getMilisecDuration() + VIDEO_BUFFER_MILIS );
                videoProcess.destroy();
            } catch (InterruptedException ex){
                System.err.println("VideoQueueRunner thread was interrupted: " + ex.getMessage());
            } catch (IOException ex){
                System.err.println("VideoQueueRunner thread encountered IOException: " + ex.getMessage());
            }
        }
    }

    static Process launchFirefoxBrowser(String url) throws IOException{
        // method inspired from
        //http://www.programcreek.com/2009/05/using-java-open-ie-and-close-ie/
        try {
            //TODO: add File.exists check on firefox executable before attempting to launch, throw exception if not exist
            return Runtime.getRuntime().exec("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe " + url);
        } catch (IOException e) {
            throw new IOException("Error launching IE browser: " + e.getMessage(), e);
        }
    }


    static void launchWebBrowser(String uristring)
    {
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
