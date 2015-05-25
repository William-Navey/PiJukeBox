package twitter;

/**
 * Java bean for deserializing twitter oath json file in resources
 * @author: William Navey
 */
public class TwitterOauthSecrets {

    private String consumer_key;
    private String consumer_secret;
    private String access_token;
    private String access_token_secret;

    public TwitterOauthSecrets(){}

    public TwitterOauthSecrets(String ck, String cs, String at, String ats){
        this.consumer_key = ck;
        this.consumer_secret = cs;
        this.access_token = at;
        this.access_token_secret = ats;
    }

    public String getConsumer_key() {
        return consumer_key;
    }

    public void setConsumer_key(String consumer_key) {
        this.consumer_key = consumer_key;
    }

    public String getConsumer_secret() {
        return consumer_secret;
    }

    public void setConsumer_secret(String consumer_secret) {
        this.consumer_secret = consumer_secret;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token_secret() {
        return access_token_secret;
    }

    public void setAccess_token_secret(String access_token_secret) {
        this.access_token_secret = access_token_secret;
    }
}
