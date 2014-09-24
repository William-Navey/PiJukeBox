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
import twitter.TwitterParser;
import youtube.YouTubeProxy;
import youtube.YouTubeVideo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
        BlockingQueue<String> tweetStreamBlockingQueue = new LinkedBlockingQueue<String>(10000);
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
                .processor(new StringDelimitedProcessor(tweetStreamBlockingQueue))
                .build();

        // Establish a connection
        client.connect();

        // Init VideoQueueRunner, launch in separate thread
        PriorityBlockingQueue<YouTubeVideo> videoPriorityBlockingQueue = new PriorityBlockingQueue<YouTubeVideo>();
        new VideoQueueRunner(videoPriorityBlockingQueue).start();

        //TODO: improve exception handling
        int exitStatus = 0;
        try{
            while (true) {
                //  Retrieves and removes the head of this queue, waiting if necessary
                //   until an element becomes available.
                String tweetJson = tweetStreamBlockingQueue.take();
                String tweetText = TwitterParser.extractTweetText(tweetJson);

                if(TwitterParser.tweetTextContainsYouTubeURL(tweetText)){
                    System.out.println("Youtube tweet." +
                            "\ntweet json: " + tweetJson +
                            "\ntweet text: " + tweetText);
                    String youtubeUrl = TwitterParser.extractYouTubeUrlFromTweetExpandedUrl(tweetJson);
                    String videoId = TwitterParser.extractVideoIdFromYouTubeURL(youtubeUrl);
                    int videoDuration = youTubeProxy.requestVideoDuration(videoId);
                    String screenName = TwitterParser.extractScreenNameFromTweetJson(tweetJson);
                    videoPriorityBlockingQueue.add(new YouTubeVideo(videoId, videoDuration, youtubeUrl, screenName));
                    System.out.println("Added video to priorityQueue");
                }
                else{
                    System.out.println("Tweet received, but did not contain youtube url:\n" + tweetText);
                }
            }

        } catch (Exception ex){
            System.err.println("Error occurred in TwitterFilterStream run loop: " + ex.getMessage());
            exitStatus = 1;
        }
        finally {
            client.stop();
            System.exit(exitStatus);
        }
    }
}