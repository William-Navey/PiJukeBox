package config;

/**
 * Java bean for deserializing jukebox_config.json in resources
 * @author: William Navey
 */
public class JukeboxConfig {

    private String twitter_handle;
    private String browser_path;
    private boolean prioritize_users;
    private boolean prioritize_songs;

    public JukeboxConfig() {}

    public JukeboxConfig(String twitter_handle, String browser_path, boolean prioritize_users, boolean prioritize_songs) {
        this.twitter_handle = twitter_handle;
        this.browser_path = browser_path;
        this.prioritize_users = prioritize_users;
        this.prioritize_songs = prioritize_songs;
    }

    public String getTwitter_handle() {
        return twitter_handle;
    }

    public void setTwitter_handle(String twitter_handle) {
        this.twitter_handle = twitter_handle;
    }

    public String getBrowser_path() {
        return browser_path;
    }

    public void setBrowser_path(String browser_path) {
        this.browser_path = browser_path;
    }

    public boolean isPrioritize_users() {
        return prioritize_users;
    }

    public void setPrioritize_users(boolean prioritize_users) {
        this.prioritize_users = prioritize_users;
    }

    public boolean isPrioritize_songs() {
        return prioritize_songs;
    }

    public void setPrioritize_songs(boolean prioritize_songs) {
        this.prioritize_songs = prioritize_songs;
    }
}
