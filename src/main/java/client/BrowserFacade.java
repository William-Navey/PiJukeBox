package client;

import java.io.IOException;

/**
 * @author: wnavey
 */
public interface BrowserFacade {

    /**
     * Launches a browser window with a new tab set to the url provided
     * @param url
     * @return
     * @throws IOException
     */
    public Process launchBrowserProcess(String url) throws IOException;
}
