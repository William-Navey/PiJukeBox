package threads;

import client.BrowserFacade;
import client.BrowserFacadeFactory;
import youtube.YouTubeVideo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Thread launched to handle playing the videos.
 * @author: William Navey
 */
public class VideoQueueRunner extends Thread {

    private final int videoBufferMillis;
    private final PriorityBlockingQueue<YouTubeVideo> priorityBlockingQueue;
    private final BrowserFacade browserFacade;

    public VideoQueueRunner(PriorityBlockingQueue<YouTubeVideo> priorityQueue) throws FileNotFoundException{
        this(priorityQueue, BrowserFacadeFactory.getBrowserFacade());
    }

    public VideoQueueRunner(PriorityBlockingQueue<YouTubeVideo> priorityQueue, BrowserFacade browserFacade){
        this(priorityQueue, browserFacade, 3000);
    }

    public VideoQueueRunner(PriorityBlockingQueue<YouTubeVideo> priorityQueue, BrowserFacade browserFacade, int videoBufferMillis){
        this.priorityBlockingQueue = priorityQueue;
        this.browserFacade = browserFacade;
        this.videoBufferMillis = videoBufferMillis;
    }

    public void run(){
        while (true) {
            try{
                YouTubeVideo currentVideo = priorityBlockingQueue.take();
                Process videoProcess = browserFacade.launchBrowserProcess(currentVideo.getYoutubeLink());
                Thread.sleep(currentVideo.getMilisecDuration() + videoBufferMillis);
                videoProcess.destroy();
            } catch (InterruptedException ex){
                System.err.println("VideoQueueRunner thread was interrupted: " + ex.getMessage());
            } catch (IOException ex){
                System.err.println("VideoQueueRunner thread encountered IOException: " + ex.getMessage());
            }
        }
    }
}
