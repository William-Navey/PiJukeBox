import org.junit.*;
import twitter.TwitterParser;

import static org.junit.Assert.*;

/**
 * @author: William Navey
 */
public class TwitterParserTest {

    String desktopClientTweet1 = "{\"created_at\":\"Fri Oct 10 19:15:24 +0000 2014\",\"id\":520653845387431936,\"id_str\":\"520653845387431936\",\"text\":\"@RaspberryBBox https:\\/\\/t.co\\/pxMxhnCX9b\",\"source\":\"\\u003ca href=\\\"http:\\/\\/twitter.com\\\" rel=\\\"nofollow\\\"\\u003eTwitter Web Client\\u003c\\/a\\u003e\",\"truncated\":false,\"in_reply_to_status_id\":null,\"in_reply_to_status_id_str\":null,\"in_reply_to_user_id\":2634630697,\"in_reply_to_user_id_str\":\"2634630697\",\"in_reply_to_screen_name\":\"RaspberryBBox\",\"user\":{\"id\":2634630697,\"id_str\":\"2634630697\",\"name\":\"RaspberryBBox\",\"screen_name\":\"RaspberryBBox\",\"location\":\"\",\"url\":null,\"description\":null,\"protected\":false,\"verified\":false,\"followers_count\":0,\"friends_count\":0,\"listed_count\":0,\"favourites_count\":0,\"statuses_count\":68,\"created_at\":\"Sun Jul 13 04:04:14 +0000 2014\",\"utc_offset\":null,\"time_zone\":null,\"geo_enabled\":false,\"lang\":\"en\",\"contributors_enabled\":false,\"is_translator\":false,\"profile_background_color\":\"C0DEED\",\"profile_background_image_url\":\"http:\\/\\/abs.twimg.com\\/images\\/themes\\/theme1\\/bg.png\",\"profile_background_image_url_https\":\"https:\\/\\/abs.twimg.com\\/images\\/themes\\/theme1\\/bg.png\",\"profile_background_tile\":false,\"profile_link_color\":\"090A0A\",\"profile_sidebar_border_color\":\"C0DEED\",\"profile_sidebar_fill_color\":\"DDEEF6\",\"profile_text_color\":\"333333\",\"profile_use_background_image\":true,\"profile_image_url\":\"http:\\/\\/pbs.twimg.com\\/profile_images\\/488195668964429826\\/aSa7Cy1G_normal.png\",\"profile_image_url_https\":\"https:\\/\\/pbs.twimg.com\\/profile_images\\/488195668964429826\\/aSa7Cy1G_normal.png\",\"profile_banner_url\":\"https:\\/\\/pbs.twimg.com\\/profile_banners\\/2634630697\\/1405230195\",\"default_profile\":false,\"default_profile_image\":false,\"following\":null,\"follow_request_sent\":null,\"notifications\":null},\"geo\":null,\"coordinates\":null,\"place\":null,\"contributors\":null,\"retweet_count\":0,\"favorite_count\":0,\"entities\":{\"hashtags\":[],\"trends\":[],\"urls\":[{\"url\":\"https:\\/\\/t.co\\/pxMxhnCX9b\",\"expanded_url\":\"https:\\/\\/www.youtube.com\\/watch?v=K9WOBsPVjFE\",\"display_url\":\"youtube.com\\/watch?v=K9WOBs\\u2026\",\"indices\":[15,38]}],\"user_mentions\":[{\"screen_name\":\"RaspberryBBox\",\"name\":\"RaspberryBBox\",\"id\":2634630697,\"id_str\":\"2634630697\",\"indices\":[0,14]}],\"symbols\":[]},\"favorited\":false,\"retweeted\":false,\"possibly_sensitive\":false,\"filter_level\":\"medium\",\"lang\":\"und\",\"timestamp_ms\":\"1412968524682\"}";
    String desktopClientTweet2 = "{\"created_at\":\"Fri Oct 10 19:17:01 +0000 2014\",\"id\":520654249756086272,\"id_str\":\"520654249756086272\",\"text\":\"@RaspberryBBox https:\\/\\/t.co\\/bGb5CZawUg\",\"source\":\"\\u003ca href=\\\"http:\\/\\/twitter.com\\\" rel=\\\"nofollow\\\"\\u003eTwitter Web Client\\u003c\\/a\\u003e\",\"truncated\":false,\"in_reply_to_status_id\":null,\"in_reply_to_status_id_str\":null,\"in_reply_to_user_id\":2634630697,\"in_reply_to_user_id_str\":\"2634630697\",\"in_reply_to_screen_name\":\"RaspberryBBox\",\"user\":{\"id\":2634630697,\"id_str\":\"2634630697\",\"name\":\"RaspberryBBox\",\"screen_name\":\"RaspberryBBox\",\"location\":\"\",\"url\":null,\"description\":null,\"protected\":false,\"verified\":false,\"followers_count\":0,\"friends_count\":0,\"listed_count\":0,\"favourites_count\":0,\"statuses_count\":68,\"created_at\":\"Sun Jul 13 04:04:14 +0000 2014\",\"utc_offset\":null,\"time_zone\":null,\"geo_enabled\":false,\"lang\":\"en\",\"contributors_enabled\":false,\"is_translator\":false,\"profile_background_color\":\"C0DEED\",\"profile_background_image_url\":\"http:\\/\\/abs.twimg.com\\/images\\/themes\\/theme1\\/bg.png\",\"profile_background_image_url_https\":\"https:\\/\\/abs.twimg.com\\/images\\/themes\\/theme1\\/bg.png\",\"profile_background_tile\":false,\"profile_link_color\":\"090A0A\",\"profile_sidebar_border_color\":\"C0DEED\",\"profile_sidebar_fill_color\":\"DDEEF6\",\"profile_text_color\":\"333333\",\"profile_use_background_image\":true,\"profile_image_url\":\"http:\\/\\/pbs.twimg.com\\/profile_images\\/488195668964429826\\/aSa7Cy1G_normal.png\",\"profile_image_url_https\":\"https:\\/\\/pbs.twimg.com\\/profile_images\\/488195668964429826\\/aSa7Cy1G_normal.png\",\"profile_banner_url\":\"https:\\/\\/pbs.twimg.com\\/profile_banners\\/2634630697\\/1405230195\",\"default_profile\":false,\"default_profile_image\":false,\"following\":null,\"follow_request_sent\":null,\"notifications\":null},\"geo\":null,\"coordinates\":null,\"place\":null,\"contributors\":null,\"retweet_count\":0,\"favorite_count\":0,\"entities\":{\"hashtags\":[],\"trends\":[],\"urls\":[{\"url\":\"https:\\/\\/t.co\\/bGb5CZawUg\",\"expanded_url\":\"https:\\/\\/www.youtube.com\\/watch?v=Iw1Fm61HBA8\",\"display_url\":\"youtube.com\\/watch?v=Iw1Fm6\\u2026\",\"indices\":[15,38]}],\"user_mentions\":[{\"screen_name\":\"RaspberryBBox\",\"name\":\"RaspberryBBox\",\"id\":2634630697,\"id_str\":\"2634630697\",\"indices\":[0,14]}],\"symbols\":[]},\"favorited\":false,\"retweeted\":false,\"possibly_sensitive\":false,\"filter_level\":\"medium\",\"lang\":\"und\",\"timestamp_ms\":\"1412968621091\"}";

    @Test
    public void testExtractScreenNameFromTweetJson() throws Exception {
        String tweetJson = "{\"created_at\":\"Wed Sep 24 17:32:58 +0000 2014\",\"id\":514829858766348288,\"id_str\":\"514829858766348288\",\"text\":\"@RaspberryBBox https:\\/\\/t.co\\/hkvYtRUPP0 .\",\"source\":\"\\u003ca href=\\\"http:\\/\\/twitter.com\\\" rel=\\\"nofollow\\\"\\u003eTwitter Web Client\\u003c\\/a\\u003e\",\"truncated\":false,\"in_reply_to_status_id\":null,\"in_reply_to_status_id_str\":null,\"in_reply_to_user_id\":2634630697,\"in_reply_to_user_id_str\":\"2634630697\",\"in_reply_to_screen_name\":\"RaspberryBBox\",\"user\":{\"id\":2518865569,\"id_str\":\"2518865569\",\"name\":\"William Navey\",\"screen_name\":\"WilliamNavey\",\"location\":\"\",\"url\":null,\"description\":null,\"protected\":false,\"verified\":false,\"followers_count\":5,\"friends_count\":5,\"listed_count\":0,\"favourites_count\":0,\"statuses_count\":3,\"created_at\":\"Fri May 23 22:41:45 +0000 2014\",\"utc_offset\":null,\"time_zone\":null,\"geo_enabled\":false,\"lang\":\"en\",\"contributors_enabled\":false,\"is_translator\":false,\"profile_background_color\":\"C0DEED\",\"profile_background_image_url\":\"http:\\/\\/abs.twimg.com\\/images\\/themes\\/theme1\\/bg.png\",\"profile_background_image_url_https\":\"https:\\/\\/abs.twimg.com\\/images\\/themes\\/theme1\\/bg.png\",\"profile_background_tile\":false,\"profile_link_color\":\"0084B4\",\"profile_sidebar_border_color\":\"C0DEED\",\"profile_sidebar_fill_color\":\"DDEEF6\",\"profile_text_color\":\"333333\",\"profile_use_background_image\":true,\"profile_image_url\":\"http:\\/\\/abs.twimg.com\\/sticky\\/default_profile_images\\/default_profile_3_normal.png\",\"profile_image_url_https\":\"https:\\/\\/abs.twimg.com\\/sticky\\/default_profile_images\\/default_profile_3_normal.png\",\"default_profile\":true,\"default_profile_image\":true,\"following\":null,\"follow_request_sent\":null,\"notifications\":null},\"geo\":null,\"coordinates\":null,\"place\":null,\"contributors\":null,\"retweet_count\":0,\"favorite_count\":0,\"entities\":{\"hashtags\":[],\"trends\":[],\"urls\":[{\"url\":\"https:\\/\\/t.co\\/hkvYtRUPP0\",\"expanded_url\":\"https:\\/\\/www.youtube.com\\/watch?v=jltN3fLFmTQ\",\"display_url\":\"youtube.com\\/watch?v=jltN3f\\u2026\",\"indices\":[15,38]}],\"user_mentions\":[{\"screen_name\":\"RaspberryBBox\",\"name\":\"RaspberryBBox\",\"id\":2634630697,\"id_str\":\"2634630697\",\"indices\":[0,14]}],\"symbols\":[]},\"favorited\":false,\"retweeted\":false,\"possibly_sensitive\":false,\"filter_level\":\"medium\",\"lang\":\"und\",\"timestamp_ms\":\"1411579978071\"}";
        String parsedScreenName = TwitterParser.extractScreenNameFromTweetJson(tweetJson);
        assertEquals("WilliamNavey", parsedScreenName);
    }
}
