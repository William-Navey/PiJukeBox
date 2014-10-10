package threads;

import youtube.YouTubeVideo;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Thread launched to handle playing the videos.
 * @author: wnavey
 */
public class VideoQueueRunner extends Thread {

    private final int videoBufferMillis;
    private final PriorityBlockingQueue<YouTubeVideo> priorityBlockingQueue;
    private final String pathToBrowser;

    public VideoQueueRunner(PriorityBlockingQueue<YouTubeVideo> priorityQueue){
        //TODO: dynamically determine firefox default path based on OS
        this(priorityQueue, "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe ");
    }

    public VideoQueueRunner(PriorityBlockingQueue<YouTubeVideo> priorityQueue, String pathToBrowser){
        this(priorityQueue, pathToBrowser, 3000);
    }

    public VideoQueueRunner(PriorityBlockingQueue<YouTubeVideo> priorityQueue, String pathToBrowser, int videoBufferMillis){
        if(!(new File(pathToBrowser).exists())){
            throw new RuntimeException("Launching VideoQueueRunner thread failed. Invalid path to web browser: " + pathToBrowser);
        }
        this.priorityBlockingQueue = priorityQueue;
        this.pathToBrowser = pathToBrowser;
        this.videoBufferMillis = videoBufferMillis;
    }

    public void run(){
        while (true) {
            try{
                YouTubeVideo currentVideo = priorityBlockingQueue.take();
                Process videoProcess = launchFirefoxBrowser(currentVideo.getYoutubeLink());
                Thread.sleep(currentVideo.getMilisecDuration() + videoBufferMillis);
                videoProcess.destroy();
            } catch (InterruptedException ex){
                System.err.println("VideoQueueRunner thread was interrupted: " + ex.getMessage());
            } catch (IOException ex){
                System.err.println("VideoQueueRunner thread encountered IOException: " + ex.getMessage());
            }
        }
    }

    private Process launchFirefoxBrowser(String url) throws IOException{
        // method inspired from
        //http://www.programcreek.com/2009/05/using-java-open-ie-and-close-ie/
        try {
            return Runtime.getRuntime().exec(pathToBrowser + url);
        } catch (IOException e) {
            throw new IOException("Error launching Firefox browser: " + e.getMessage(), e);
        }
    }
}
