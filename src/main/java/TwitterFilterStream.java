/**
 * Copyright 2013 Twitter, Inc.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import youtube.YouTubeProxy;
import youtube.YouTubeVideo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import java.awt.Desktop;
import java.net.URI;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Twitter Client class to establish a connection to twitter,
 */
public class TwitterFilterStream {

    private final YouTubeProxy youTubeProxy;

    public TwitterFilterStream(YouTubeProxy youTubeProxy){
        this.youTubeProxy = youTubeProxy;
    }

    public void run(String twitterHandle, String consumerKey, String consumerSecret,
                           String token, String secret) throws InterruptedException {
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
        // add some track terms
        endpoint.trackTerms(Lists.newArrayList(twitterHandle));

        Authentication auth = new OAuth1(consumerKey, consumerSecret,
                token, secret);
        // Authentication auth = new BasicAuth(username, password);

        // Create a new BasicClient. By default gzip is enabled.
        Client client = new ClientBuilder()
                .hosts(Constants.STREAM_HOST)
                .endpoint(endpoint)
                .authentication(auth)
                .processor(new StringDelimitedProcessor(queue))
                .build();

        // Establish a connection
        client.connect();

        // Init VideoQueueRunner, launch in separate thread
        PriorityBlockingQueue<YouTubeVideo> priorityBlockingQueue = new PriorityBlockingQueue<YouTubeVideo>();
        new VideoQueueRunner(priorityBlockingQueue).start();

        //TODO: improve exception handling
        try{
            for (int msgRead = 0; msgRead < 1000; msgRead++) {
                //  Retrieves and removes the head of this queue, waiting if necessary
                //   until an element becomes available.

                //while the queue is not empty,
                String tweetJson = queue.take();
                String tweetText = extractTweetText(tweetJson);

                if(tweetTextContainsYouTubeURL(tweetText)){
                    System.out.println("A YOUTUBE LINK WAS TWEETED!");
                    System.out.println("tweet text: " + tweetText);
                    String youtubeUrl = extractYouTubeUrlFromTweetExpandedUrl(tweetJson);
                    String videoId = extractVideoIdFromYouTubeURL(youtubeUrl);
                    int videoDuration = youTubeProxy.requestVideoDuration(videoId);
                    priorityBlockingQueue.add(new YouTubeVideo(videoId, videoDuration, youtubeUrl));
                    System.out.println("Added video to priorityQueue");
                }



                System.out.println(tweetJson);
            }

        } catch (Exception ex){
            System.err.println("Error while reading TwitterFilterStream" + ex.getMessage());
        }

        client.stop();
    }

    /**
     *
     * @param msg representing the entire tweet captured by the TwitterFilterStream
     * @return String value of Youtube link
     */
    static String extractYouTubeUrlFromTweetExpandedUrl(String msg){
        String[] msgSplitOnExpandedUrl = msg.split("\"expanded_url\"");
        String youtubeLink = msgSplitOnExpandedUrl[1].split("\"")[1];
        youtubeLink = youtubeLink.trim();
        // Remove backslashes
        youtubeLink = youtubeLink.replaceAll("\\\\","");
        return youtubeLink;
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

    static boolean tweetTextContainsYouTubeURL(String msg){
        return msg.contains("t.co");
    }

    static String extractTweetText(String msg){
        String[] msgSplitOnText = msg.split("\"text\"");
        String tweetText = msgSplitOnText[1].split("\"")[1];
        return tweetText;
    }

    static String extractYouTubeLinkFromTweetText(String tweetText){
        String youtubeLink = tweetText.replaceAll("@RaspberryBBox", "");
        youtubeLink = youtubeLink.trim();
        // Remove backslashes
        youtubeLink = youtubeLink.replaceAll("\\\\","");
        return youtubeLink;
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