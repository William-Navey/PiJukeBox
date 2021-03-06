package twitter; /**
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

import client.BrowserFacade;
import client.BrowserFacadeFactory;
import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import config.JukeboxConfig;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import threads.VideoQueueRunner;
import youtube.YouTubeAPIException;
import youtube.YouTubeClientProxy;
import youtube.YouTubeVideo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * NOTICE: This class was created from modifying Twitter's open source code.
 *  See https://github.com/twitter/hbc/tree/master/hbc-core/
 *
 * Twitter client proxy class to establish a connection to twitter using library com.twitter hbc-core 2.2.0.
 */
public class TwitterClientProxy {

    private final JukeboxConfig jukeboxConfig;
    private final YouTubeClientProxy youTubeClientProxy;
    private final Authentication auth;
    private final Logger logger;
    private volatile boolean listenForTweets;

    public TwitterClientProxy(JukeboxConfig jukeboxConfig, YouTubeClientProxy youTubeClientProxy, String twitterOauthFile, Logger logger) throws IOException{
        this.listenForTweets = true;
        this.jukeboxConfig = jukeboxConfig;
        this.youTubeClientProxy = youTubeClientProxy;
        this.logger = logger;
        try{
            this.auth = authenticate(twitterOauthFile);
        } catch (IOException ex){
            throw new IOException("Problem authenticating twitter from file: " + twitterOauthFile, ex);
        }
    }

    private Authentication authenticate(String twitterOauthFile) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        Reader twitterOauthSecretsReader =
                new InputStreamReader(TwitterClientProxy.class.getResourceAsStream(twitterOauthFile));
        TwitterOauthSecrets twitterOauthSecrets = mapper.readValue(twitterOauthSecretsReader, TwitterOauthSecrets.class);

        return new OAuth1(
                twitterOauthSecrets.getConsumer_key(),
                twitterOauthSecrets.getConsumer_secret(),
                twitterOauthSecrets.getAccess_token(),
                twitterOauthSecrets.getAccess_token_secret());

    }

    /**
     * Opens a connection stream that listens for tweets received by the specified screen name.
     *  If a tweet received is a youtube video, that video is added to the video queue
     * @throws InterruptedException
     * @throws IOException
     */
    public void launchTweetVideoQueue() throws InterruptedException, IOException {
        BlockingQueue<String> tweetStreamBlockingQueue = new LinkedBlockingQueue<String>(10000);
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();

        final String activeTwitterScreenName = jukeboxConfig.getTwitter_handle();
        // add some track terms
        endpoint.trackTerms(Lists.newArrayList(activeTwitterScreenName));

        // Create a new BasicClient. By default gzip is enabled.
        Client client = new ClientBuilder()
                .hosts(Constants.STREAM_HOST)
                .endpoint(endpoint)
                .authentication(auth)
                .processor(new StringDelimitedProcessor(tweetStreamBlockingQueue))
                .build();

        // Establish a connection
        logger.info("Establishing connection...");
        client.connect();

        BrowserFacade browserFacade = BrowserFacadeFactory.getBrowserFacade(jukeboxConfig.getBrowser_path());
        TweetHistorian tweetHistorian = new TweetHistorian(jukeboxConfig.isPrioritize_users(), jukeboxConfig.isPrioritize_songs());
        // Init VideoQueueRunner, launch in separate thread
        PriorityBlockingQueue<YouTubeVideo> videoPriorityBlockingQueue = new PriorityBlockingQueue<YouTubeVideo>();
        new VideoQueueRunner(videoPriorityBlockingQueue, browserFacade).start();

        int exitStatus = 0;
        this.listenForTweets = true;
        logger.info("Connected! Listening for tweets at " + activeTwitterScreenName);
        try {
            while (this.listenForTweets) {
                try {
                    //  Retrieves and removes the head of this queue, waiting if necessary
                    //   until an element becomes available.
                    String tweetJson = tweetStreamBlockingQueue.take();

                    if (TwitterParser.tweetContainsYouTubeURL(tweetJson)) {
                        logger.debug("Youtube tweet received.");
                        logger.debug(
                                "\ntweet json: " + tweetJson);

                        TweetYouTube youtubeTweetYouTube = TwitterParser.parseYouTubeTweet(tweetJson);
                        int priorityScore = tweetHistorian.calculateTweetYouTubePriorityScore(youtubeTweetYouTube);

                        YouTubeVideo tweetedYouTubeVideo = youTubeClientProxy.createYouTubeVideo(
                                youtubeTweetYouTube.getVideoId(),
                                youtubeTweetYouTube.getYoutubeUrl(),
                                priorityScore
                        );
                        tweetHistorian.logTweetYouTube(youtubeTweetYouTube);
                        videoPriorityBlockingQueue.add(tweetedYouTubeVideo);
                        logger.info("Added video \"" + tweetedYouTubeVideo.getVideoTitle() + "\" to priorityQueue");
                    }
                    else {
                        logger.debug("Tweet received, but did not contain youtube url:");
                    }
                }
                catch (TwitterParserException ex){
                    logger.error("Parsing tweet failed failed: " + ex.getMessage() +
                            "\n\tResuming listening loop.");
                    // Don't break or throw exception, resume loop to listen for next tweet
                }
                catch (YouTubeAPIException ex) {
                    logger.error("YouTube API call failed: " + ex.getMessage() +
                            "\n\tResuming listening loop.");
                    // Don't break or throw exception, resume loop to listen for next tweet
                }
                catch (Exception ex){
                    exitStatus = 1;
                    throw new IOException("Error occurred during TwitterFilterStream launchTweetVideoQueue loop: " + ex.getMessage(), ex);
                }
            }
        } finally {
            client.stop();
            System.exit(exitStatus);
        }
    }

    public boolean isListeningForTweets() {
        return listenForTweets;
    }

    public void setListenForTweets(boolean listenForTweets) {
        this.listenForTweets = listenForTweets;
    }
}