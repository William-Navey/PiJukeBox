package youtube;

/**
 * @author: wnavey
 */
public class YouTubeVideo implements Comparable<YouTubeVideo> {

    // The higher the priority integer value, the less "important" it is
    // e.x: A YouTubeVideo of priority 0 will get chosen over a YouTubeVideo of priority 1
    private final int priority;
    private final int milisecDuration;
    private final String youtubeLink;

    public YouTubeVideo(String videoId, int milisecDuration, String youtubeLink){
        this.priority = calculatePriority(videoId);
        this.milisecDuration = milisecDuration;
        this.youtubeLink = youtubeLink;
    }

    public static int calculatePriority(String videoId){
        int priority = 0;
        //TODO: impl
        // Has the user tweeted before? +1
        // Has the same song been tweeted before? +1
        return priority;
    }

    public int compareTo(YouTubeVideo o){
        // if THIS object is "less than" the specified object (o), return a negative integer
        if(this.priority < o.getPriority()){
            return -1;
        }
        else if (this.priority == o.getPriority()){
            return 0;
        }
        else{
            return 1;
        }
    }

    public int getPriority() {
        return priority;
    }

    public int getMilisecDuration() {
        return milisecDuration;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }
}
