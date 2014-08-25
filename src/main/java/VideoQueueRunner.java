import youtube.YouTubeVideo;

import java.awt.*;
import java.net.URI;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Thread launched to handle playing the videos.
 * @author: wnavey
 */
public class VideoQueueRunner extends Thread {

    private final PriorityBlockingQueue<YouTubeVideo> priorityBlockingQueue;

    public VideoQueueRunner(PriorityBlockingQueue<YouTubeVideo> priorityQueue){
        this.priorityBlockingQueue = priorityQueue;
    }

    public void run(){
        while (true) {
            try{
                YouTubeVideo currentVideo = priorityBlockingQueue.take();
                launchWebBrowser(currentVideo.getYoutubeLink());
                Thread.sleep(currentVideo.getMilisecDuration());
            } catch (InterruptedException ex){
                System.err.println("VideoQueueRunner thread was interrupted: " + ex.getMessage());
            }
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
